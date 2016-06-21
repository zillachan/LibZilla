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
package zilla.libcore.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for layout.eg. Activity.setContentView();also for Fragment
 * usage：
 * //@InjectLayout(id=R.layout.activity_welcome)
 * public class WelcomeActivity extends AppCompatActivity {
 * ...
 * }
 * Created by zilla on 15/5/25.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectLayout {
    /**
     * layout of activity/fragment
     *
     * @return
     */
    int value();

    /**
     * activity menu
     *
     * @return
     */
    int menu() default 0;
}
