package com.myblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Test
    void contextLoads() {



        /*System.out.println(dataSource.getClass());
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        System.out.println(connection);
        try{connection.close();}
        catch (Exception e){
            System.out.println(e);
        }*/
    }

}
