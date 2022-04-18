package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import library.MyStarterClass;

@SpringBootApplication
public class MyStarterClient {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MyStarterClient.class, args);

        MyStarterClass myLibraryClassByClass = applicationContext.getBean(MyStarterClass.class);
        myLibraryClassByClass.printInfo();

        MyStarterClass myLibraryClass = (MyStarterClass) applicationContext.getBean("myLibraryClass");
        myLibraryClass.printInfo();
    }
}
