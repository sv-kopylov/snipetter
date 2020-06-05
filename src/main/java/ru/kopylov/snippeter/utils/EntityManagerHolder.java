package ru.kopylov.snippeter.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Синглтон управляет етити менеджером, и предоставляет к нему доступ
public class EntityManagerHolder {

//
    private EntityManagerHolder(){

    }

    private static EntityManagerHolder instance;

    public static EntityManagerHolder getInstance(){
        if(instance==null){
            instance=new EntityManagerHolder();
        }
        return instance;
    }



    private EntityManagerFactory emf;
    private  EntityManager entityManager;


    public void init (String puName){
        if(entityManager ==null){
            emf = Persistence.createEntityManagerFactory( "snip_pu" );
            entityManager = emf.createEntityManager();
        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void close(){
        if (entityManager!=null) {

            if(entityManager.getTransaction().isActive()){
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
            entityManager.close();
            if(emf!=null){
                emf.close();
            }
        } else {
            System.out.println("entity man is null");
        }
    }
}
