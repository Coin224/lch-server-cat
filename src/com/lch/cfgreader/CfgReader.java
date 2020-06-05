package com.lch.cfgreader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 这个类用来读取web.properties文件
 */
public class CfgReader {

    private static Map<String ,String> cfgMap = new HashMap<>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src//web.properties"));
            Enumeration enumeration = properties.propertyNames();//获取所有的key
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                // 把所有的配置文件中的信息存入到map缓存中
                cfgMap.put(key,value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String webConfigReader(String key) {
        return cfgMap.get(key);
    }
}
