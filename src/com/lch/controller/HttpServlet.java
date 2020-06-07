package com.lch.controller;

import com.lch.httpresource.HttpServletRequest;
import com.lch.httpresource.HttpServletResponse;

public interface HttpServlet {
    void service(HttpServletRequest request , HttpServletResponse response);
}
