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
 * 非法页面类型异常<br>
 * 如果在配置中配置对应的页面不是Activity/Fragment则该页面无法打开，将抛出该异常
 * @author ze.chen
 * @version 1.0
 */
public class ModuleTypeException extends Exception{

	public ModuleTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModuleTypeException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

	public ModuleTypeException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public ModuleTypeException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}
