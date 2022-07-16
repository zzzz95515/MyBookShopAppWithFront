package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {

    EntityManagerFactory entityManagerFactory;

    @Autowired
    public CommandLineRunnerImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<5;i++){
            createTestEntity(new TestEntity());
        }

        TestEntity readTestEntity = readTestEntityById(3L);
        if (readTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(readTestEntity.toString());
        }
        else {
            throw new NullPointerException();
        }

        TestEntity updateTestEntity = updateTestEntityById(5L);
        if (updateTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update "+updateTestEntity.toString());
        }
        else {
            throw new NullPointerException();
        }


        TestEntity delatedTestEntity = delateTestEntityById(4L);
        if (delatedTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("Delate "+delatedTestEntity.toString());
        }
        else {
            throw new NullPointerException();
        }
    }

    private TestEntity delateTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx=null;
        TestEntity result = null;
        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntityById(id);
            TestEntity mergedTestEntity= (TestEntity) session.merge(findEntity);
            result=findEntity;
            session.remove(mergedTestEntity);

            tx.commit();
        }
        catch (HibernateError hex){
            if (tx != null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }finally {
            session.close();
        }
        return result;
    }

    private TestEntity updateTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx=null;
        TestEntity result = null;
        try {
            tx = session.beginTransaction();
            TestEntity findEntity = readTestEntityById(id);
            findEntity.setData("NEW DATA");
            result= (TestEntity) session.merge(findEntity);

            tx.commit();
        }
        catch (HibernateError hex){
            if (tx != null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }finally {
            session.close();
        }
        return result;
    }

    private TestEntity readTestEntityById(Long id) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx=null;
        TestEntity result = null;
        try {
            tx = session.beginTransaction();
            result=session.find(TestEntity.class,id);

            tx.commit();
        }
        catch (HibernateError hex){
            if (tx != null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }finally {
            session.close();
        }
        return result;
    }

    private void createTestEntity(TestEntity entity) {
        Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
        Transaction tx=null;
        try {
            tx = session.beginTransaction();
            entity.setData(entity.getClass().getSimpleName()+entity.hashCode());
            session.save(entity);
            tx.commit();
        }
        catch (HibernateError hex){
            if (tx != null){
                tx.rollback();
            }else{
                hex.printStackTrace();
            }
        }finally {
            session.close();
        }

    }
}
