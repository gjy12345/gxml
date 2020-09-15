package cn.gjyniubi.gxml.parse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;

public class Dom4jTool {

    public static Document parseXmlByFile(String xmlPath) throws DocumentException {
        return parseXmlByFile(new File(xmlPath));
    }

    public static Document parseXmlByFile(File file) throws DocumentException {
        if(!file.exists()){
            return null;
        }
        return new SAXReader().read(file);
    }

    public Document parseXmlByUrl(URL url) throws DocumentException {
        return new SAXReader().read(url);
    }

}
