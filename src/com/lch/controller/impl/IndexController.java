package com.lch.controller.impl;

import com.lch.controller.HttpServlet;
import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

public class IndexController implements HttpServlet {

    public void service(HttpServletRequest request , HttpServletResponse response) {
        System.out.println("恭喜找到我了");
        response.sendRedirect("index.html");
    }
}
