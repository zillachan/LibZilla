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
package zilla.libcore.module.exception;

/**
 * 非法协议异常<br>
 * 如果输入的协议字符串不符合zilla协议，则抛出该异常
 * @author ze.chen
 * @version 1.0
 */
public class MalformedZillaURLException extends Exception {

	public MalformedZillaURLException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MalformedZillaURLException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public MalformedZillaURLException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public MalformedZillaURLException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}
