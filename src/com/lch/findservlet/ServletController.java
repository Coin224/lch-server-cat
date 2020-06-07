package com.lch.findservlet;

import com.lch.controller.HttpServlet;
import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServletController {


    // 一个Map和静态块 一开始就把配置文件中的信息读出来
    // 放在缓存中 这样每次不用再去重新读取配置文件
    // 直接在map中取 提高了性能
    private static Map<String, String> cfgMap = new HashMap<>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src//web.properties"));
            Enumeration enumeration = properties.propertyNames();//获取所有的key
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                // 把所有的配置文件中的信息存入到map缓存中
                cfgMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 这个类用来管理controller类对象
    // 把用过的controller对象放在缓存map中
    // 下次有用户使用的时候直接从缓存中取
    // 可以提高性能
    private static Map<String, HttpServlet> servletMap = new HashMap<>();


    // 找寻在服务器中对应请求的资源  控制层
    public static void findController(HttpServletRequest request, HttpServletResponse response) {
        try {
            String resourceName = request.getResourceName();
            // 先去集合中找到controller类对象
            // 如果不存在再去通过配置文件中的类名字反射
            //去web.properties文件中找到该请求名字对应的controller类
            //去找到一个叫index的控制层
            HttpServlet controller = servletMap.get(resourceName);
            //集合中不存在这个对象
            if (controller == null) {
                // 取cfgmap中找真实的类名字
                String realControllerName = cfgMap.get(resourceName);
                if (realControllerName != null) {
                    // 反射
                    //通过反射 用得到的名字取找寻controller类
                    Class clazz = Class.forName(realControllerName);
                    controller = (HttpServlet) clazz.newInstance();
                    // 把对象放到集合中
                    servletMap.put(resourceName, controller);
                }
                // 上面的方法保证了controller存在
                Class clazz = controller.getClass();
                // 找到类中service方法
                Method method = clazz.getMethod("service", HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(controller, request, response);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            response.write("方法错误");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            response.write("请求的资源名错误");
        }
    }
}
