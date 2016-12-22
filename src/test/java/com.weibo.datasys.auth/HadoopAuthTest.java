package com.weibo.datasys.auth;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class HadoopAuthTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(HadoopAuth.class);
    }

//    @Test
//    public void test() {
//        final String hello = target(HadoopAuth.HADOOP_POLICY_XML_TEMPLATE).request().get(String.class);
//        System.out.println("out : " + hello);
//    }

}