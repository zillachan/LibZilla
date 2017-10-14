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
package zilla.libcore.ui.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import pub.zilla.logzilla.Log;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.util.ReflectUtil;

/**
 * Created by zilla on 14-7-14.
 */
public class AdapterUtil<T> {

    private Context context;
    private boolean hasGetView = false;


    public AdapterUtil(Context context) {
        this.context = context;
    }

    public View getView(List<T> list, LayoutInflater inflater,
                        int resid, Class<?> viewHolder,
                        CompoundButton.OnCheckedChangeListener onCheckedChangeListener,
                        final ZillaAdapter.ZillaAdapterButtonClickListener zillaAdapterButtonClickListener,
                        final int position, View convertView, ViewGroup parent) {
        T item = list.get(position);
        Object holder;
        if (convertView != null) {
            holder = convertView.getTag();
        } else {
            convertView = inflater.inflate(resid, parent, false);
            if (!hasGetView) {
                try {
                    ViewGroup viewGroup = (ViewGroup) convertView;
                    if (viewGroup.getChildCount() == 0) {
                        Log.e("this view must have a parent layout.");
                    }
                } catch (Exception e) {
                    Log.e("ContentView should be a GroupView");
                }
                hasGetView = true;
            }
            holder = getConstructorInstance(viewHolder, convertView);
            if (holder == null) {
                return convertView;
            }
            convertView.setTag(holder);
        }
        Field[] fields = viewHolder.getDeclaredFields();
        for (Field field : fields) {
            Class<?> dataType = field.getType();
            //if is static,break
            if (Modifier.isStatic(field.getModifiers())) continue;

//            if (dataType.isAssignableFrom(ImageView.class)) {
//
//            }
            //首先判断item中是否存在该字段
            Object modelProperty = "";
            try {
                modelProperty = ReflectUtil.getObj(item, field.getName());
            } catch (Exception e) {
//                Log.e(e.getMessage());
                //如果item中不存在该字段，跳过
                continue;
            }
//            if (TextUtils.isEmpty(modelProperty)) {
//                continue;
//            }
            field.setAccessible(true);

            try {
                View view = (View) field.get(holder);
                view.setTag(item);
            } catch (Exception e) {
//                Log.e(e.getMessage());
            }

            //CheckBox
            if (CheckBox.class.isAssignableFrom(dataType)) {
                try {
                    boolean isChecked = false;
                    if (1 == (Integer) modelProperty) {
                        isChecked = true;
                    } else {
                        isChecked = false;
                    }
                    CheckBox checkBox = ((CheckBox) field.get(holder));
                    checkBox.setChecked(isChecked);
                    if (onCheckedChangeListener != null) {
                        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
                    }
                } catch (Exception e) {
//                    Log.e(e.getMessage());
                }
            }
            //Button
            else if (Button.class.isAssignableFrom(dataType)) {
                try {
                    Button button = (Button) field.get(holder);
                    String text = "";
                    if (modelProperty != null) {
                        text = modelProperty.toString();
                    }
                    if (!TextUtils.isEmpty(text)) {
                        button.setText(text);
                    }

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (zillaAdapterButtonClickListener != null) {
                                zillaAdapterButtonClickListener.onZillaAdapterButtonClick((Button) v, position);
                            }

                        }
                    });

                } catch (IllegalAccessException e) {
//                    Log.e(e.getMessage());
                }
            }
            //TextView
            else if (TextView.class.isAssignableFrom(dataType)) {
                try {
                    String text = "";
                    if (modelProperty != null) {
                        text = modelProperty.toString();
                    }
                    if (TextUtils.isEmpty(text)) text = "";
                    ((TextView) field.get(holder)).setText(text);
                } catch (IllegalAccessException e) {
//                    Log.e(e.getMessage());
                }
            }
            //ImageView
            else if (ImageView.class.isAssignableFrom(dataType)) {
                try {
                    android.widget.ImageView img = (android.widget.ImageView) field.get(holder);
                    if (modelProperty.getClass() == String.class) {
                        if (((String) modelProperty).startsWith("http")) {//url
                            Picasso.with(context).load((modelProperty.toString())).into(img);
                        } else if (((String) modelProperty).startsWith("/")) {//file
                            Picasso.with(context).load(new File(modelProperty.toString())).into(img);
                        }
                    } else {
                        try {
                            int res = (Integer) modelProperty;
                            Picasso.with(context).load(res).into(img);
                        } catch (Exception e) {
                            Log.e(e.getMessage());
                        }
                    }
                } catch (Exception e) {
//                    Log.e(e.getMessage());
                }
            }
            //RatingBar
            else if (RatingBar.class.isAssignableFrom(dataType)) {
                try {
                    ((RatingBar) field.get(holder)).setRating((Integer) modelProperty);
                } catch (Exception e) {
//                    Log.e(e.getMessage());
                }
            }
            //ViewGroup
            else if (ViewGroup.class.isAssignableFrom(dataType)) {//布局，用于控制显示方式，-1移除，0不显示，1，显示
                try {
                    int visiable = (Integer) modelProperty;
                    ViewGroup layout = (ViewGroup) field.get(holder);
                    switch (visiable) {
                        case -1:
                            layout.setVisibility(View.GONE);
                            break;
                        case 0:
                            layout.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            layout.setVisibility(View.VISIBLE);
                            break;
                        default:
                            layout.setVisibility(View.VISIBLE);
                            break;
                    }
                } catch (Exception e) {
//                    Log.e(e.getMessage());
                }
            }
            //其它
        }
        return convertView;
    }


    protected Object getConstructorInstance(Class c, View view) {
        try {
            Constructor ctor = c.getDeclaredConstructor(View.class);
            ctor.setAccessible(true);
            return ctor.newInstance(view);
        } catch (Exception e) {
            Log.e(e.getMessage());
            Log.e("==Error:getConstructorInstance");
        }
        return null;
    }
}
