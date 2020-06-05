package com.lch.thread;

import com.lch.cfgreader.CfgReader;
import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread{

    private Socket socket;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    //重写run方法
    public void run() {
        // 接收请求
        this.receiveRequest();
        // 解析请求
        // 分发请求
    }


    // 接收请求
    private void receiveRequest() {
        try {
            // 获取一个输入流
            InputStream inputStream = socket.getInputStream();//字节型输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//字符型输入流
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//高级流
            String request = bufferedReader.readLine();

            // 解析请求
            this.obtainRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取请求 得到的信息 GET /index?key=value&key=value HTTP/1.1 放到集合中
    private void obtainRequest(String request) {
        // 按空格拆分参数
        String[] allContent = request.split(" ");
        // 用一个map把这些参数装起来
        Map<String,String> contentMap = new HashMap<>();
        contentMap.put("requestMethod",allContent[0]);
        contentMap.put("contentAndParam",allContent[1]);
        contentMap.put("httpversion",allContent[2]);
        this.parseParam(contentMap);
    }

    // 解析map中的参数
    private void parseParam(Map map) {
        String contentAndParam = (String) map.get("contentAndParam");
        int questionMarkIndex = contentAndParam.indexOf("?");//问号的位置
        String resourceName = contentAndParam.substring(1,questionMarkIndex);//资源名字
        //resourceName.split("/");
        String allKeyAndParam = contentAndParam.substring(questionMarkIndex);
        // 把所有的key和value拆成一组一组的 按照“&”
        String[] keysAndParams = allKeyAndParam.split("&");
        Map<String,String> paramMap = new HashMap<>();// 把遍历得到的key和value装入这个集合 以便后面使用
        for (String keyAndParam:keysAndParams) {
            String[] KV = keyAndParam.split("=");
            paramMap.put(KV[0],KV[1]);
        }
        // 做完这些  把资源名和参数都携带好  可以通过对象
        // HttpServletRequest这个类用来存放请求携带的参数
        HttpServletRequest request = new HttpServletRequest(resourceName,paramMap);
        HttpServletResponse response = new HttpServletResponse();
        this.findController(request,response);
    }

    // 找寻在服务器中对应请求的资源  控制层
    private void findController(HttpServletRequest request , HttpServletResponse response) {
        String resourceName = request.getResourceName();
        //去web.properties文件中找到该请求名字对应的controller类
        //去找到一个叫inedex的控制层
        String controllerName = CfgReader.webConfigReader(resourceName);
        try {
            //通过反射 用得到的名字取找寻controller类
            Class clazz = Class.forName(controllerName);
            Object obj = clazz.newInstance();
            // 找到类中service方法
            Method method = clazz.getMethod("service",HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(obj,request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
