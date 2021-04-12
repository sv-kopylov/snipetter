package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.AliasItem;
import ru.kopylov.snippeter.model.Feature;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AliasManager {
    private EmTAProxy emProxy;
    private EntityManager em;

    public AliasManager() {
        em = EntityManagerHolder.getInstance().getEntityManager();
        emProxy = EntityManagerHolder.getInstance().getEmTAProxy();
    }

    public List<Feature> getFeatures(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AliasItem> query = cb.createQuery(AliasItem.class);
        Root<AliasItem> from = query.from(AliasItem.class);
        query.select(from);
        query.where(cb.equal(from.get("name"), name));
        return em.createQuery(query).getResultStream().map(AliasItem::getFeature).collect(Collectors.toList());
    }

    public Stream<AliasItem> getAllAliases(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AliasItem> query = cb.createQuery(AliasItem.class);
        Root<AliasItem> from = query.from(AliasItem.class);
        query.select(from);
        return em.createQuery(query).getResultStream();
    }

    public Map<String, List<Feature>> getNameFeatureMap(){
        HashMap<String, List<Feature>> result = new HashMap<>();
        getAllAliases().forEach(aliasItem -> {
            if(result.get(aliasItem.getName())==null){
                result.put(aliasItem.getName(), new ArrayList<Feature>());
            }
            result.get(aliasItem.getName()).add(aliasItem.getFeature());
        });
        return result;
    }

    public void saveAliases(String name, List<Feature> features){
        for(Feature f: features){
            emProxy.persist(new AliasItem(name, f));
        }
    }

    public void removeAlias(String name){
        List<AliasItem> list = getAllAliases()
                .filter(item -> item.getName().equals(name))
                .collect(Collectors.toList());
        for (AliasItem item: list){
            emProxy.delete(item);
        }
    }
}
