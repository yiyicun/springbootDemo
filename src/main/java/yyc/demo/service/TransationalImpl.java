package yyc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import yyc.demo.bean.Student;


@Component
public class TransationalImpl {

    @Autowired
    UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public void addUser(){
        Student student = new Student();
        student.setName("java 1 号");
        student.setEmail("12121");

        int a= userService.add(student);
        try{
            Student student1 = new Student();
            student1.setName("java 2 号");
            student1.setEmail("12121");
            userService.addStudent3(student1);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("aa:"+a);


    }
}
