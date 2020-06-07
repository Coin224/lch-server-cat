package com.lch.httpresource;

import java.io.*;

/**
 * 这个类用来装写回浏览器的东西
 */
public class HttpServletResponse {

    // 存储响应信息
    private StringBuilder responseContent = new StringBuilder();
    public void write(String str) {
        this.responseContent.append(str);
    }


    public String getResponseContent() {
        return responseContent.toString();
    }



    public void sendRedirect(String path) {
        File file = new File("src//com//lch//file//"+path);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String value = bufferedReader.readLine();
            while (value != null) {
                responseContent.append(value);
                value = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
