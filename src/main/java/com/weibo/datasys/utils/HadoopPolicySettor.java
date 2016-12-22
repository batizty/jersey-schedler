package com.weibo.datasys.utils;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by tuoyu on 12/8/16.
 * 给Hadoop生成正确的Hadoop Policy的xml文件，根据MySql中的结果生成正确的XML文件
 * 主要修改的项目为 security.client.protocol.acl
 */
public class HadoopPolicySettor {
    // 控制用户 1 能否访问hdfs文件 2 能否提交mr作业
    private static final String dfs_acl = "security.client.protocol.acl";

    NodeList nList;
    Document doc;


    public String getStringFromDocument() {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HadoopPolicySettor(String file) {
        try {
            File fXmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            nList = doc.getElementsByTagName("property");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toXML() {
        return getStringFromDocument();
    }

    private String getNodeName(Element e) {
        return e.getElementsByTagName("name").item(0).getTextContent();
    }

    private String getNodeValue(Element e) {
        return e.getElementsByTagName("value").item(0).getTextContent();
    }

    public void setNodeValue(Element e, String value) throws DOMException {
        e.getElementsByTagName("value").item(0).setTextContent(value);
    }

    public String getValueByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        Element e = getElementByName(name);
        if (e != null) {
            return getNodeValue(e);
        }

        return null;
    }

    public Element getElementByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        if (nList.getLength() <= 0) {
            return null;
        }

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (StringUtils.equals(name, getNodeName(eElement))) {
                    return eElement;
                }
            }
        }

        return null;
    }

    public void setValueByName(String name, String value) throws DOMException {
        if (StringUtils.isBlank(name)) {
            //TODO throw error
            return;
        }

        if (StringUtils.isBlank(value)) {
            // TODO throw error
            return;
        }

        Element e = getElementByName(name);
        if (e != null) {
            setNodeValue(e, value);
            return;
        }

        return;
    }

    public void setHDFSAcl(String[] users, String[] groups) {
        String us = StringUtils.join(users, ",");
        String gs = StringUtils.join(groups, ",");
        String users_and_groups = StringUtils.join(new String[]{us, gs}, " ");
        setValueByName(dfs_acl, users_and_groups);
    }

    public Document getDoc() {
        return this.doc;
    }

    public static String getNewHadoopPolicyXML(String path, String[] users, String[] groups) {
        HadoopPolicySettor hdp = new HadoopPolicySettor(path);
        hdp.setHDFSAcl(users, groups);
        return hdp.toXML();
    }

    public static String getNewHadoopPolicyXML(String path, List<String> users, List<String> groups) {
        return getNewHadoopPolicyXML(path,
            (String[])users.toArray(new String[users.size()]),
            (String[])groups.toArray(new String[groups.size()]));
    }

    public static void main(String[] args) {
        HadoopPolicySettor hdp = new HadoopPolicySettor("hadoop-policy.xml");

        String value = hdp.getValueByName(HadoopPolicySettor.dfs_acl);
        System.out.println(" before dfs_acl = " + value);

        hdp.setHDFSAcl(new String[]{"tuoyu", "facai"}, new String[]{"hadoop", "tuoyu"});
        value = hdp.getValueByName(HadoopPolicySettor.dfs_acl);
        System.out.println(" after dfs_acl = " + value);


        System.out.print(hdp.toXML());
        return;
    }

}
