package cn.gjyniubi.gxml.test;

import cn.gjyniubi.gxml.parse.Dom4jTool;
import org.dom4j.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

public class XmlParseEngine {

    private static final String[] classNames = {
            "int", "double", "char", "boolean", "long",
            "java.lang.Integer", "java.lang.String"
            , "java.lang.Double", "java.lang.Boolean",
            "long","java.lang.Long"
    };

    private static Set<String> classSet;

    static {
        classSet = new HashSet<>();
        classSet.addAll(Arrays.asList(classNames.clone()));
        Map<String,String> uriMap=new HashMap<>();
        uriMap.put("dc","http://purl.org/dc/elements/1.1/");
        DocumentFactory.getInstance().setXPathNamespaceURIs(uriMap);
    }

    public static <T> T parseXmlToObjectBean(Class<T> oClass, Document document) throws Exception{
        return parseXmlToObject(oClass,document);
    }

    private static <T> T parseXmlToObject(Class<T> oClass, Node document, XmlRule... append) throws Exception{
        if(document==null)
            return null;
        T object=oClass.newInstance();
        Field[] fields=oClass.getDeclaredFields();
        XmlRule xmlRule;
        String fieldClassName;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fieldClassName=fields[i].getGenericType().getTypeName();
            if((xmlRule=fields[i].getAnnotation(XmlRule.class))==null){
                continue;
            }
            if(classSet.contains(fieldClassName))
                getXmlValue(fields[i],object,xmlRule,document,append);
            else if (fieldClassName.endsWith("[]")){
                //数组
                getXmlValues(fields[i],fieldClassName.replace("[]",""),object,xmlRule,document,append);
            }else if(fieldClassName.matches(".+<.+?>")){
                //泛型list
                int s= fields[i].getGenericType().getTypeName().indexOf("<");
                int e=fields[i].getGenericType().getTypeName().indexOf(">");
                String type=fields[i].getGenericType().getTypeName().substring(s+1,e);
                getXmlValues(fields[i],type,object,xmlRule,document);
            }else {
                fields[i].set(object,parseXmlToObject(Class.forName(fieldClassName),document,append));
            }
        }
        return object;
    }


    private static void getXmlValues(Field field,String type,Object object,XmlRule xmlRule,Node document,XmlRule... append) throws Exception{
        String rule="";
        if(append!=null&&append.length>0){
            for (int i = 0; i < append.length; i++) {
                if(append[i]!=null)
                rule=rule+append[i].value();
            }
        }
        rule+=xmlRule.value();
        List<Node> nodes=document.selectNodes(rule);
        Object objectArray=Array.newInstance(Class.forName(type),nodes.size());
        if(classSet.contains(type)){
            //基本数据对象
            for (int i = 0; i < nodes.size(); i++) {
                Array.set(objectArray,i,getNodeValue(nodes.get(i),xmlRule,document,type));
            }
        }else {
            Object singleObject;
            for (int i = 0; i < nodes.size(); i++) {
                singleObject=parseXmlToObject(Class.forName(type), nodes.get(i),append);
                Array.set(objectArray,i,singleObject);
            }
        }
        if(field.getGenericType().getTypeName().endsWith("[]")){
            field.set(object,objectArray);
        }else if(field.getType()==List.class||field.getType()==ArrayList.class) {
            List list=new ArrayList();
            for (int i = 0; i < nodes.size(); i++) {
                list.add(Array.get(objectArray,i));
            }
            field.set(object,list);
        }else {
            throw new RuntimeException("not support");
        }
    }

    private static void getXmlValue(Field field,Object object,XmlRule xmlRule,Node document,XmlRule... append) throws Exception{
        String rule="";
        if(append!=null&&append.length>0){
            for (int i = 0; i < append.length; i++) {
                if(append[i]!=null)
                    rule=rule+append[i].value();
            }
        }
        rule+=xmlRule.value();
        Node node=document.selectSingleNode(rule);
        field.set(object,getNodeValue(node,xmlRule,document,field.getGenericType().getTypeName()));
    }

    private static Object getNodeValue(Node node,XmlRule xmlRule,Node document,String type){
        if(node==null)
            return null;
        String v;
        if(!xmlRule.type().equals("")){
            v=document.valueOf(xmlRule.type());
        }else {
            v=node.getText();
        }
        switch (type) {
            case "int":
            case "java.lang.Integer":
                return Integer.parseInt(v);
            case "double":
            case "java.lang.Double":
                return Double.parseDouble(v);
            case "java.lang.String":
                return v;
            case "long":
            case "java.lang.Long":
                return Long.parseLong(v);
        }
        return null;
    }
}
