/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.zilla.android.zillacore.libzilla.module;


import com.github.snowdream.android.util.Log;
import com.zilla.android.zillacore.libzilla.module.exception.MalformedZillaURLException;

/**
 * zilla模块跳转协议<br>
 * Zilla模块跳转协议，为了能实现模块之间的松耦合，模块之间的跳转将使用该协议实现<br>
 * 形如：协议/模块/模块下级页面/参数
 *
 * @author ze.chen
 * @version 1.0
 */

public final class ZillaURL {
    /**
     * zilla
     */
    private String protocol;
    /**
     * zilla://module
     */
    private String module;
    /**
     * loging/logindetail
     */
    private String path;
    /**
     * name=xxx&psw=xxx
     */
    private String query;

    private static final String ZILLA_PROTOCAL = "zilla";

    public ZillaURL(String spec) throws MalformedZillaURLException {
        if ("".equals(spec) || !spec.startsWith(ZILLA_PROTOCAL + "://")) {
            throw new MalformedZillaURLException("protocal is not zilla://");
        }
        String protocalNext = spec.substring((ZILLA_PROTOCAL + "://").length());
        // 如果url中含有/或者?，则module为?或/之前的内容
        int module_end = protocalNext.length();
        if (protocalNext.contains("?")) {
            int queylength = protocalNext.indexOf("?");
            module_end = (queylength < module_end ? queylength : module_end);
            // query
            query = protocalNext.substring(queylength + 1);
            Log.i("query-->" + query);
        }
        if (protocalNext.contains("/")) {
            int pathlength = protocalNext.indexOf("/");
            module_end = (pathlength < module_end ? pathlength : module_end);
            // path
            String pathNext = protocalNext.substring(module_end);
            if (pathNext.contains("?")) {
                path = pathNext.substring(0, pathNext.indexOf("?"));
            } else {
                path = pathNext;
            }
            Log.i("path-->" + path);
        }
        module = protocalNext.substring(0, module_end);
        Log.i("module-->" + module);
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
