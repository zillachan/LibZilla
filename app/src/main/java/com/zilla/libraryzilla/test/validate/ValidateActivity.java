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

package com.zilla.libraryzilla.test.validate;

import android.view.View;
import android.widget.EditText;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.validate.ValidateLife;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(R.layout.activity_validate)
public class ValidateActivity extends BaseActivity implements Validator.ValidationListener{

    @Required(order = 1, message = "Not Null")
    @InjectView(R.id.editText)
    EditText editText;

    @LifeCircleInject
    ValidateLife validateLife;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.button)
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                validateLife.validate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {
        Util.toastMsg("success");
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        validateLife.onValidationFailed(failedView,failedRule);
    }
}
