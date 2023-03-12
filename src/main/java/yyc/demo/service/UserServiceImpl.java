package yyc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import yyc.demo.bean.Student;
import yyc.demo.mapper.UserMapper;

@Component
public class UserServiceImpl implements UserService{


    @Autowired
    UserMapper userMapper;



    @Transactional(rollbackFor = Exception.class)
    public int add(Student student) {

        userMapper.insert(student);
        return 0;
    }

     @Override
     //@Transactional(rollbackFor = Exception.class)
     public int addStudent(Student student){
         userMapper.insert(student);
         throw new NullPointerException();

    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation =  Propagation.REQUIRES_NEW)
    public int addStudent2(Student student){
        userMapper.insert(student);
        throw new NullPointerException();

    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation =  Propagation.NESTED)
    public int addStudent3(Student student){
        userMapper.insert(student);
        throw new NullPointerException();

    }


}
