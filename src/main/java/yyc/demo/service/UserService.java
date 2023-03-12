package yyc.demo.service;

import org.springframework.transaction.annotation.Propagation;
import yyc.demo.bean.Student;

public interface UserService {
    int add(Student studen);
    int addStudent(Student student);
    int addStudent2(Student student);
    int addStudent3(Student student);

}
