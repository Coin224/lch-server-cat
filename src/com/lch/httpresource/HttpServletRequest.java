package com.lch.httpresource;

import java.util.Map;

/**
 * 这个类用来存放资源名和携带的参数
 */
public class HttpServletRequest {
    private String resourceName;
    private Map<String,String> params;

    public HttpServletRequest() {}

    public HttpServletRequest(String resourceName, Map<String, String> params) {
        this.resourceName = resourceName;
        this.params = params;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
