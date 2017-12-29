package com.mybatis;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zyf on 2017/12/26.
 *  https://www.cnblogs.com/xtdxs/p/6628851.html
 *  http://blog.csdn.net/hmiter/article/details/55106263
 *  1.获取document=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(File)
 *  2.解析：
 *     document
 *          element[node]: attribute  TextContent
 *
 *  dom:
 *     DOM的基本对象有5个：Document，Node，NodeList，Element和Attr。
 */
public class XmlTest {

    //获得操作xml文件的对象
    private static Document getDocument() throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//得到创建 DOM 解析器的工厂。
        DocumentBuilder builder = factory.newDocumentBuilder();//得到 DOM 解析器对象。
        Document document = builder.parse(new File("g://log/xmltest.xml")); //得到代表整个文档的 Document 对象
        // Element e = document.getDocumentElement(); 获取根元素
        return document;
    }

    //将内存中的数据保存到XML文件中
    private static void writeXml(Document document) throws TransformerException {

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("g://log/xmltest.xml"));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        trans.transform(source, result);
    }

    @Test
    public  void add() throws Exception {
        Document document = getDocument();
        Element student_node = document.createElement("student");
        student_node.setAttribute("idcart","testIdcart");
        student_node.setAttribute("examid", "testExamid");
        Node name = document.createElement("name");
        name.setTextContent("testName");
        Node location = document.createElement("location");
        location.setTextContent("testLocation");
        Node grade = document.createElement("grade");
        grade.setTextContent("testGrade");

        student_node.appendChild(name);
        student_node.appendChild(location);
        student_node.appendChild(grade);

        Element root = document.getDocumentElement();
        root.appendChild(student_node);
        writeXml(document);
    }

    @Test
    public  void delete() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        String name="testName";
        Document document = getDocument();
        NodeList list = document.getElementsByTagName("name");
        for(int i=0;i<list.getLength();i++){
            Node node = list.item(i);
            if(node.getTextContent().equals(name)){
                node.getParentNode().getParentNode().removeChild(node.getParentNode());
            }
        }
        writeXml(document);
    }

    @Test
    public void find() throws Exception {
        String examid="testIdcart";
        Document document = getDocument();
        NodeList list = document.getElementsByTagName("student");
        for(int i=0;i<list.getLength();i++){
            Element element = (Element) list.item(i);
            String value = element.getAttribute("examid");
            System.out.println("value"+value);
            NodeList nodes=element.getChildNodes();
            for(int j=0;j<nodes.getLength();j++){
                Node node= nodes.item(j);
                if(!"".equals(node.getTextContent().trim())) {
                    System.out.println(node.getTextContent());
                }
            }
        }
    }
  //////////以上为dom解析，以下为dom4j//////////////////////////////////////////////////////////////////////

    public static org.dom4j.Document getDom4jDocument(){
        try {
            SAXReader reader = new SAXReader();
            org.dom4j.Document document = reader.read(new File("g://log/xmltest.xml"));
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //读取XML文本内容获取Document对象
    public void parseTest(){
        try {
            String xmlStr = "<students>......</students>";
            org.dom4j.Document document=DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**读取
     * 使用递归找到下个节点的内容/属性。。
     * @throws DocumentException
     */
    @Test
    public void selectUser() throws DocumentException {
        org.dom4j.Document document = getDom4jDocument();
        //获取根节点元素对象
        org.dom4j.Element root = document.getRootElement();
        listNodes(root);
    }
    //遍历当前节点下的所有节点
    public void listNodes(org.dom4j.Element node){
        System.out.println("当前节点的名称：" + node.getName());
        //首先获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        //遍历属性节点
        for(Attribute attribute : list){
            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());
        }
        //如果当前节点内容不为空，则输出
        if(!(node.getTextTrim().equals(""))){
            System.out.println( node.getName() + "：" + node.getText());
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<org.dom4j.Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            org.dom4j.Element e = iterator.next();
            listNodes(e);
        }
    }

    /**
     * 添加属性
     */
    @Test
    public void addDom4j()throws Exception{
        org.dom4j.Document document=getDom4jDocument();
        org.dom4j.Element root= document.getRootElement();

        org.dom4j.Element student=root.addElement("stuTest");
        student.addAttribute("examid","0");
        org.dom4j.Element name=student.addElement("name");
        name.setText("SHARK");
        listNodes(student);

        writerDocumentToNewFile(document);
    }

    /**
     * document写入新的文件
     * @param document
     * @throws Exception
     */
    public static void writerDocumentToNewFile(org.dom4j.Document document)throws Exception{
        //输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置编码
        format.setEncoding("UTF-8");
        //XMLWriter 指定输出文件以及格式
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File("g://log/xmltest.xml")),"UTF-8"), format);
        //写入新文件
        writer.write(document);
        writer.flush();
        writer.close();
    }

    /**
     * 操作某个元素
     */
    @Test
    public void operateElement(){
        org.dom4j.Document document=getDom4jDocument();
        org.dom4j.Element root= document.getRootElement();
        org.dom4j.Element student=root.element("stuTest");
        listNodes(student);
        student.addAttribute("idcard","999");
        Attribute attribute=student.attribute("examid");
        student.remove(attribute);
        org.dom4j.Element  location=student.addElement("location");
        location.addText("杭州");
        listNodes(student);
    }
}
