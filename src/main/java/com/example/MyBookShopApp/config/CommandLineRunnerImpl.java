package com.example.MyBookShopApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.example.MyBookShopApp.data.BookRepository;

//@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {

//    EntityManagerFactory entityManagerFactory;
//    TestEntityDao testEntityDao;
//
//    @Autowired
//    public CommandLineRunnerImpl(EntityManagerFactory entityManagerFactory, TestEntityDao testEntityDao) {
//        this.entityManagerFactory = entityManagerFactory;
//        this.testEntityDao = testEntityDao;
//    }
//    TestEntityCrudRepository repository;
//
//    @Autowired
//    public CommandLineRunnerImpl(TestEntityCrudRepository repository) {
//        this.repository = repository;
//    }
//



    BookRepository bookRepository;

    @Autowired
    public CommandLineRunnerImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.findBooksByAuthor_FirstName("Aleen").toString());
//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.customFindAllBooks().toString());
//        for (int i=0;i<5;i++){
//            createTestEntity(new TestEntity());
//        }
//
////        TestEntity readTestEntity = testEntityDao.findOne(3L);
//        TestEntity readTestEntity = readTestEntityById(3L);
//        if (readTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(readTestEntity.toString());
//        }
//        else {
//            throw new NullPointerException();
//        }
//
//        TestEntity updateTestEntity = updateTestEntityById(5L);
//        if (updateTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update "+updateTestEntity.toString());
//        }
//        else {
//            throw new NullPointerException();
//        }
//
//
//        TestEntity delatedTestEntity = delateTestEntityById(4L);
//        if (delatedTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("Delate "+delatedTestEntity.toString());
//        }
//        else {
//            throw new NullPointerException();
//        }
    }
//
//    private TestEntity delateTestEntityById(Long id) {
//        TestEntity entity = repository.findById(id).get();
//        repository.delete(entity);
//        return entity;
//    }
//
//    private TestEntity updateTestEntityById(Long id) {
//        TestEntity entity = repository.findById(id).get();
//        entity.setData("NEW DATA");
//        repository.save(entity);
//        return entity;
//    }
//
//    private TestEntity readTestEntityById(Long id) {
//        return repository.findById(id).get();
//    }
//
//    private void createTestEntity(TestEntity entity) {
//        entity.setData(TestEntity.class.getSimpleName()+entity.hashCode());
//        repository.save(entity);
//
//
//    }
}
