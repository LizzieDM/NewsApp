package com.example.newsapp.newsapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by ASUS on 24/05/2015.
 */
public class ElDiaXMLParser {
    private URL url;

    public ElDiaXMLParser(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<FeedElement> parse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        LinkedList<FeedElement> elements = new LinkedList<FeedElement>();
        FeedElement e;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(this.url.openConnection().getInputStream());
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");
            for (int i=0;i<items.getLength();i++){
                e = new FeedElement();
                Node item = items.item(i);
                NodeList properties = item.getChildNodes();
                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase("title")){
                        e.setTitle(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("link")){
                        e.setLink(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("pubDate")){
                        e.setDate(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("dc:creator")){
                        e.setAuthor(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase("description")){
                        StringBuilder text = new StringBuilder();
                        NodeList chars = property.getChildNodes();
                        for (int k=0;k<chars.getLength();k++){
                            text.append(chars.item(k).getNodeValue());
                        }
                        e.setDescription(text.toString());
                    } /*else if (name.equalsIgnoreCase("content:encoded")){
                        String text = property.getFirstChild().getNodeValue();
                        Pattern p = Pattern.compile(".*<enclosure[^>]*url=\"([^\"]*)",Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(text.toString());
                        if (m.find()) {
                            e.setImage(m.group(1));
                        }
                    }*/
                }
                elements.add(e);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return elements;
    }
}
