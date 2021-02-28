package ru.kopylov.snippeter.text;


import org.apache.log4j.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.net.URL;

public class SimpleTransformer {
    public static String FB2HTML = "xslt/FB2_2_html.xsl";
    public static String FB2TEXT = "xslt/FB2_2_txt.xsl";


    private static Logger logger = Logger.getLogger(SimpleTransformer.class);
    public static void main(String[] args) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
//установка используемого XSL-преобразования
            SimpleTransformer main = new SimpleTransformer();
            File file = main.getFileFromResources("FB2_2_txt.xsl");
            Transformer transformer = tf.newTransformer(new StreamSource(file));
//установка исходного XML-документа и конечного XML-файла

            transformer.transform(

                    new StreamSource("avtoasopom_po_galactike_D.Adams.fb2"),

                    new StreamResult("avtoasopom_po_galactike_D.Adams.txt"));

            logger.info("xslt transformation complete");

        } catch(TransformerException e) {

            e.printStackTrace();
        }
    }

    public void transform2html(String inFilePath, String outFilePath){
        transform(inFilePath, outFilePath,  FB2HTML);
    }

    public void transform2text(String inFilePath, String outFilePath){
        transform(inFilePath, outFilePath,  FB2TEXT);
    }


    public void transform(String inFilePath, String outFilePath, String shema){
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
//установка используемого XSL-преобразования
            SimpleTransformer main = new SimpleTransformer();
            File file = new File(shema);
            Transformer transformer = tf.newTransformer(new StreamSource(file));
//установка исходного XML-документа и конечного XML-файла

            transformer.transform(
                    new StreamSource(inFilePath),
                    new StreamResult(outFilePath));
            logger.info("xslt transformation complete");

        } catch(TransformerException e) {
            logger.warn("xslt transformation failed "+e.getMessage());
        }

    }



    private  File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}


