package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.model.Category;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.model.Snippet;

import java.util.ArrayList;
import java.util.HashMap;

public class SnippetManager {
    private HashMap<Category, ArrayList<Snippet>> categories2snippets = new HashMap<>();
    private HashMap<Feature, ArrayList<Snippet>> feature2snippets = new HashMap<>();
    private HashMap<Snippet, ArrayList<Feature>> snippet2Features ;


    public HashMap<Snippet, ArrayList<Feature>> filterByFeatures(Feature...features){
        HashMap<Snippet, ArrayList<Feature>> res = new HashMap<>();
        ArrayList<Snippet> snippets;
        for(Feature f: features){
            snippets = feature2snippets.get(f);
            if(snippets!=null){
                for (Snippet snippet: snippets){
                    res.put(snippet, snippet2Features.get(snippet));
                }
            }
        }

        return res;
    }

    public HashMap<Snippet, ArrayList<Feature>> filterByCategories(Category... categories){
        HashMap<Snippet, ArrayList<Feature>> res = new HashMap<>();
        ArrayList<Snippet> snippets;
        for(Category cat: categories){
            snippets = categories2snippets.get(cat);
            if(snippets!=null){
                for (Snippet snippet: snippets){
                    res.put(snippet, snippet2Features.get(snippet));
                }
            }
        }
        return res;
    }

    public void update(){
        BunchManager bunchManager = Context.getInstance().get(BunchManager.class);
        snippet2Features = bunchManager.fetchSnippetsBySource();
        categories2snippets.clear();
        feature2snippets.clear();
        ArrayList<Feature> nextList;
        for (Snippet snip: snippet2Features.keySet()){
            nextList = snippet2Features.get(snip);
            Category category;

            for(Feature feature: nextList){
                if(!feature2snippets.containsKey(feature)){
                    feature2snippets.put(feature, new ArrayList<>());
                }
                feature2snippets.get(feature).add(snip);

                category = feature.getCategory();
                if (!categories2snippets.containsKey(category)){
                    categories2snippets.put(category, new ArrayList<>());
                }
                categories2snippets.get(category).add(snip);


            }

        }
    }



}
