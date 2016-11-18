package com.company;

import com.company.controller.FamilyTasksController;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by Next on 16.11.2016.
 */
public class FamilyTaskApp {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-cfg.xml");
        FamilyGenerator familyGenerator = (FamilyGenerator) ctx.getBean("familyGenerator");
//        familyGenerator.clearDb();
        familyGenerator.generateFamilies();
        FamilyTasksController controller = ctx.getBean(FamilyTasksController.class);
        controller.run();
//        controller.mmm();
        System.out.println("\n\n--------Statisticts-------\n");
        controller.printStatistics();
        ctx.close();
    }
}
