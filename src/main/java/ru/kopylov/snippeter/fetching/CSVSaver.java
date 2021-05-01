package ru.kopylov.snippeter.fetching;


import org.apache.log4j.Logger;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.management.BunchManager;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVSaver {

    private static Logger logger = Logger.getLogger(CSVSaver.class);
    public static char CSVDelim='|';
    public static char FeaturesDelim=',';


    public void saveDBToCSV(String filename){
        File file = new File(filename);
        BunchManager bunchManager = Context.getInstance().get(BunchManager.class);
        bunchManager.fetchAllSnippets();
        HashMap<Snippet, ArrayList<Feature>> map = bunchManager.getMap();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) ) {
            bw.write(firstString());
            for(Snippet snip: map.keySet()){
                bw.write(toCSVString(snip, map.get(snip)));
            }
            bw.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private String firstString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Title");
        sb.append(CSVDelim);
        sb.append("Author");
        sb.append(CSVDelim);
        sb.append("Snippet");
        sb.append(CSVDelim);
        for(Category cat: Category.values()){
            sb.append(cat.toString());
            sb.append(CSVDelim);
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append('\n');
        return sb.toString();
    }

    private String toCSVString(Snippet snip, ArrayList<Feature> list){
        StringBuilder sb = new StringBuilder();
        sb.append(snip.getSource().getTitle());
        sb.append(CSVDelim);
        sb.append(snip.getSource().getAuthor());
        sb.append(CSVDelim);
        sb.append(filter(snip.getSnippet()));
        sb.append(CSVDelim);
        for(Category cat: Category.values()){
            sb.append(fetchFeaturesByCategory(cat, list));
            sb.append(CSVDelim);
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append('\n');
        return sb.toString();
    }

    private String filter(String in){
        return in.replaceAll("(\r|\n)"," ");
    }

    private String fetchFeaturesByCategory(Category cat, ArrayList<Feature> list){
        StringBuilder sb = new StringBuilder();
        for(Feature feature: list){
            if(feature.getCategory()==cat){
                sb.append(feature.getValue());
                sb.append(FeaturesDelim);
            }

        }
        return sb.toString();
    }



}
