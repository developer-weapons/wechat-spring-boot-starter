package com.github.developer.weapons.util;

import com.github.developer.weapons.model.component.ComponentTextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by codedrinker on 2019/4/27.
 */
public class XmlUtils {
    public static Map<String, String> xmlToMap(String xml) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static String objectToXml(ComponentTextMessage message) {
        XStream xs = new XStream();
        xs.alias("xml", message.getClass());
        return xs.toXML(message);
    }

    public static String objectToXml(Object message) {
        XStream xs = new XStream();
        xs.alias("xml", message.getClass());
        return xs.toXML(message);
    }
}
