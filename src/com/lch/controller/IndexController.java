package com.lch.controller;

import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

public class IndexController {

    public void service(HttpServletRequest request , HttpServletResponse response) {
        System.out.println("恭喜找到我了");
    }
}
