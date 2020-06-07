package com.lch.controller.impl;

import com.lch.controller.HttpServlet;
import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

public class loginController implements HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("login controller");
    }
}
