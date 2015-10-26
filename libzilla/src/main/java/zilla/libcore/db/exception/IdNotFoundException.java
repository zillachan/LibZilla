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
package zilla.libcore.db.exception;

/**
 * Id annotation not found for the model.
 * <br>
 * Id注解没有找到异常
 * Created by chenze on 14-2-21.
 */
public class IdNotFoundException extends Exception {

    public IdNotFoundException() {
        super("Id annotation can't find，make sure you add the @Id annotation for the model.");
    }

    public IdNotFoundException(String detailMessage) {
        super("Id annotation can't find，make sure you add the @Id annotation for the model."+detailMessage);
    }

    public IdNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public IdNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
