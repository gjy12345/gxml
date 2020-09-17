package cn.gjyniubi.gxml.test;

import cn.gjyniubi.gxml.parse.Dom4jTool;
import org.dom4j.Document;

public class Test {

    public static void main(String[] args) throws Exception{
        //Document document= Dom4jTool.parseXmlByFile(Test.class.getClassLoader().getResource("rss.xml").getFile());
        Document document=Dom4jTool.parseXmlByUrl("https://www.gcores.com/rss");
        RssDocument rssDocument=XmlParseEngine.parseXmlToObject(RssDocument.class,document);
        System.out.println(rssDocument);
    }
}
