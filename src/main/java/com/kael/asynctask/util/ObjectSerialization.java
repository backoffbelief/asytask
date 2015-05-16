package com.kael.asynctask.util;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * 类 ObjectSerialization 的实现描述：TODO 类实现描述
 * 
 */
public class ObjectSerialization {

    /**
     * @author ligh4 2015年3月23日下午1:32:04
     * @param obj
     * @return null if failed.
     * @throws Exception
     */
    public static synchronized String ToString(Object obj) {
        Kryo kryo = new Kryo();

        ByteOutputStream stream = new ByteOutputStream();
        Output output = new Output(stream);
        kryo.writeClassAndObject(output, obj);
        output.flush();

        String str = null;
        try {
            byte[] bytes = stream.getBytes();
            str = new String(bytes, "ISO8859-1");
        } catch (Exception e) {
            str = null;
        } finally {
            output.close();
            stream.close();
        }

        return str;
    }

    /**
     * @author ligh4 2015年3月23日下午1:32:19
     * @param str
     * @return null if failed.
     * @throws Exception
     */
    public static synchronized Object FromString(String str) {

        Kryo kryo = new Kryo();

        try {
            byte[] bytes = str.getBytes("ISO8859-1");
            Input input = new Input(bytes);
            Object obj = kryo.readClassAndObject(input);
            input.close();
            return obj;
        } catch (Exception e) {
            return null;
        }

    }

    static class Persion {
        public String name;
        public int    age;

        public Persion(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Persion() {

        }
    }

    /**
     * @author ligh4 2015年3月20日下午4:07:50
     * @param args
     */
    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
    public static void main(String[] args) throws Exception {

        Map<String, Persion> map = new HashMap<String, Persion>();
        map.put("cn1", new Persion("中国1", 30));
        map.put("cn2", new Persion("中国2", 30));
        Map<Integer, Map<String, Persion>> classes = new HashMap<Integer, Map<String, Persion>>();
        classes.put(1, map);

        {
            Kryo kryo = new Kryo();

            ByteOutputStream stream = new ByteOutputStream();
            Output output = new Output(stream);
            kryo.writeClassAndObject(output, classes);
            output.flush();

            byte[] bytes = stream.getBytes();
            String str = new String(bytes, "ISO8859-1");
            byte[] bytes2 = str.getBytes("ISO8859-1");

            System.out.println(bytes.length + "\n" + str.length() + "\n" + bytes2.length);
        }

        String str = ObjectSerialization.ToString(classes);

        System.out.println(str.length() + ":" + str);

        Map<String, Persion> map2 = (Map) ((Map) ObjectSerialization.FromString(str)).get(1);

        Persion p1 = map2.get("cn2");
    }

}