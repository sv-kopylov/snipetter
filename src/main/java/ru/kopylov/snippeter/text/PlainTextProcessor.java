package ru.kopylov.snippeter.text;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Операции с текстом
 * 2147483647 - максимальное число символов, если будет больше будет нужна новая реализация
 */
public class PlainTextProcessor {
    private static Logger logger = Logger.getLogger(PlainTextProcessor.class);

    private StringBuilder text = new StringBuilder();

    public PlainTextProcessor(String path)  {
        File file = new File(html2txt(path));
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String next = br.readLine();
            while (next!=null){
                text.append(next);
                text.append("\r\n");
                next=br.readLine();
            }

        } catch (FileNotFoundException e) {
            logger.error("File not found "+path+" "+e.getMessage());
        } catch (IOException e) {
            logger.error("Exception due to reading file "+path+" "+e.getMessage());
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("cannot close BufferedReader "+e.getMessage());
                }
            }
        }

    }

    public String getText(){
        return text.toString();
    }

    // меняет .html на .txt
    private String html2txt(String source){
        if(source.endsWith(".html")){
            return source.replace(".html", ".txt");
        }
        return source;
    }
}
