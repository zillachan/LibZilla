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
package com.zilla.android.zillacore.libzilla.file.exception;

/**
 * SD卡存储已满异常<br>
 * SD卡存储已满异常,SD卡存储已满时，抛出该自定义异常，以便后续捕获处理
 *
 * @author ze.chen
 * @version 1.0
 */
public class SDCardFullException extends Exception {

    public SDCardFullException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SDCardFullException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

    public SDCardFullException(String detailMessage) {
        super(detailMessage);
        // TODO Auto-generated constructor stub
    }

    public SDCardFullException(Throwable throwable) {
        super(throwable);
        // TODO Auto-generated constructor stub
    }

}
