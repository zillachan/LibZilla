/*
 * Copyright (c) 2015. Zilla Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zilla.libcore.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import pub.zilla.logzilla.Log;
import com.squareup.picasso.Picasso;

import zilla.libcore.Zilla;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by zilla on 10/22/15.
 */
public class ZillaBinding {
    /**
     * binding the model value to container
     *
     * @param container container
     * @param model model
     */
    public static void binding(Object container, Object model) {

        Field[] fields = container.getClass().getDeclaredFields();
        if (fields == null) return;
        if (model == null) return;
        for (Field field : fields) {
            InjectBinding binding = field.getAnnotation(InjectBinding.class);
            if (binding != null) {
                field.setAccessible(true);
                try {
                    String fieldName = binding.value();
                    Field modelFiel = model.getClass().getDeclaredField(fieldName);
                    modelFiel.setAccessible(true);
                    Object fieldValue = modelFiel.get(model);
                    setValue(field, fieldValue, container);
                } catch (Exception e) {
                    Log.e(e.getMessage());
                }
            }
        }
    }

    /**
     * Save container to model
     *
     * @param container container
     * @param model model
     */
    public static void saveModel(Object container, Object model) {
        Field[] fields = container.getClass().getDeclaredFields();
        if (fields == null) return;
        if (model == null) return;
        for (Field field : fields) {
            InjectBinding binding = field.getAnnotation(InjectBinding.class);
            if (binding != null) {
                field.setAccessible(true);
                try {
                    String fieldName = binding.value();
                    saveValue(field, fieldName, model, container);
                } catch (Exception e) {
                    Log.e(e.getMessage());
                }
            }
        }
    }

    private static void saveValue(Field field, String fieldName, Object model, Object container) {
        Class<?> dataType = field.getType();//字段类型
        //TextView
        if (dataType == TextView.class) {
            try {
                String text = (String) ((TextView) field.get(container)).getText();
                if (TextUtils.isEmpty(text)) text = "";
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        // EditText
        else if (dataType == EditText.class) {
            try {
                String text = ((EditText) field.get(container)).getText().toString().trim();
                if (TextUtils.isEmpty(text)) text = "";
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //Button
        else if (dataType == Button.class) {
            try {
                String text = (String) ((Button) field.get(container)).getText();
                if (TextUtils.isEmpty(text)) text = "";
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //CheckBox
        else if (dataType == CheckBox.class) {
            try {
                int isChecked = 0;
                CheckBox checkBox = ((CheckBox) field.get(container));
                if (checkBox.isChecked()) {
                    isChecked = 1;
                }
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, isChecked);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //ImageView
        else if (dataType == ImageView.class) {
            try {
                String text = (String) ((ImageView) field.get(container)).getTag();
                if (TextUtils.isEmpty(text)) text = "";
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //RatingBar
        else if (dataType == RatingBar.class) {
            try {
                RatingBar ratingBar = ((RatingBar) field.get(container));
                float f = ratingBar.getRating();
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, f);

            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        } else if (dataType == SeekBar.class) {
            try {
                SeekBar seekBar = ((SeekBar) field.get(container));
                int i = seekBar.getProgress();
                Field modelFiel = model.getClass().getDeclaredField(fieldName);
                modelFiel.setAccessible(true);
                modelFiel.set(model, i);

            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
    }

    private static void setValue(Field field, Object value, Object container) {
        Class<?> dataType = field.getType();//字段类型
        //TextView
        if (dataType == TextView.class) {
            try {
                String text = "";
                if (value != null) {
                    text = value.toString();
                }
                if (TextUtils.isEmpty(text)) text = "";
                ((TextView) field.get(container)).setText(text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        // EditText
        else if (dataType == EditText.class) {
            try {
                String text = "";
                if (value != null) {
                    text = value.toString();
                }
                if (TextUtils.isEmpty(text)) text = "";
                ((EditText) field.get(container)).setText(text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //Button
        else if (dataType == Button.class) {
            try {
                Button button = (Button) field.get(container);
                String text = "";
                if (value != null) {
                    text = value.toString();
                }
                if (TextUtils.isEmpty(text)) text = "";
                button.setText(text);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //CheckBox
        else if (dataType == CheckBox.class) {
            try {
                boolean isChecked = false;
                if (1 == (Integer) value) {
                    isChecked = true;
                } else {
                    isChecked = false;
                }
                CheckBox checkBox = ((CheckBox) field.get(container));
                checkBox.setChecked(isChecked);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //ImageView
        else if (dataType == ImageView.class) {
            try {
                ImageView img = (ImageView) field.get(container);
                img.getContext();
                if (value.getClass() == String.class) {
                    if (((String) value).startsWith("http")) {//url
                        Picasso.with(Zilla.APP).load((value.toString())).into(img);
                    } else if (((String) value).startsWith("/")) {//file
                        Picasso.with(Zilla.APP).load(new File(value.toString())).into(img);
                    }
                } else {
                    try {
                        int res = (Integer) value;
                        Picasso.with(Zilla.APP).load(res).into(img);
                    } catch (Exception e) {
                        Log.e(e.getMessage());
                    }
                }
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //RatingBar
        else if (dataType == RatingBar.class) {
            try {
                ((RatingBar) field.get(container)).setRating((Integer) value);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        }
        //SeekBar
        else if (dataType == SeekBar.class) {
            try {
                ((SeekBar) field.get(container)).setProgress((Integer) value);
            } catch (Exception e) {
                Log.e(e.getMessage());
            }
        } else if (dataType == LinearLayout.class) {//布局，用于控制显示方式，-1移除，0不显示，1，显示
            try {
                int visiable = (Integer) value;
                LinearLayout layout = (LinearLayout) field.get(container);
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
                Log.e(e.getMessage());
            }
        }
    }
}
