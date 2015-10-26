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
package zilla.libcore.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.github.snowdream.android.util.Log;

/**
 * Created by zilla on 14-7-28.
 */
public class SystemUtil {

    public static Intent getMetaData(Activity activity, String key) {
        ActivityInfo info = null;
        String action = "";
        try {
            info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(e.getMessage());
        }
        Intent intent = new Intent();
        action = info.metaData.getString(key);
        intent.setAction(action);
        return intent;
    }
}
