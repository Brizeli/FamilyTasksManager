package com.company;

import com.company.model.Family;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new Date());
        AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-cfg.xml");
        FamilyTaskGenerator familyGenerator = (FamilyTaskGenerator) ctx.getBean("familyGenerator");
        familyGenerator.generateFamilies();
        ctx.close();
    }

}
