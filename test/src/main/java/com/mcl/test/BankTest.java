package com.mcl.test;

import com.mcl.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-server-bootstrap.xml")
public class BankTest {
    @Test
    public void testServer() {
        new ClassPathXmlApplicationContext("spring-server-bootstrap.xml");

    }

    @Test
    public void testClient() {

        new ClassPathXmlApplicationContext("spring-client-bootstrap.xml");
    }
}