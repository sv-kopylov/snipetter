package ru.kopylov.snippeter.utils;



import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.net.URL;

public class SimpleTransformer {
    public static void main(String[] args) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
//установка используемого XSL-преобразования
            SimpleTransformer main = new SimpleTransformer();
            File file = main.getFileFromResources("FB2_2_html.xsl");
            Transformer transformer = tf.newTransformer(new StreamSource(file));
//установка исходного XML-документа и конечного XML-файла

            transformer.transform(

                    new StreamSource("Гадкие лебеди.fb2"),

                    new StreamResult("Гадкие лебеди.html"));

            System.out.print("complete");

        } catch(TransformerException e) {

            e.printStackTrace();
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


