/**
 * Project:      AsyncTask
 * FileName:     MyBatis.java
 * @version      V1.0 
 * Createdate:   2015年3月11日 下午6:05:26
 */
package com.kael.mybatis;

import java.io.FileReader;
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 类 MyBatis 的实现描述：TODO 类实现描述
 * 
 */
public class MyBatis {

    private static SqlSessionFactory sessionFactory = null;
    private static SqlSession        session        = null;

    /**
     * 获得MyBatis SqlSessionFactory
     * SqlSessionFactory负责创建SqlSession，一旦创建成功，就可以用SqlSession实例来执行映射语句
     * ，commit，rollback，close等方法。
     * 
     * @return
     */
    public static SqlSessionFactory getSessionFactory() {
        if (sessionFactory != null) {
            return sessionFactory;
        }

        String resource = "./config/mybatis.xml";
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(new FileReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sessionFactory;
    }

    public static SqlSession getSession() {

        if (session == null) {
            session = getSessionFactory().openSession();
        }

        return session;
    }
}
