/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.applications;

import static com.android.settings.widget.EntityHeaderController.ActionType;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.preference.Preference;

import com.android.settings.R;

import com.android.settings.Utils;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;

public abstract class AppInfoWithHeader extends AppInfoBase {

    private static final String TAG = "AppInfoWithHeader";

    private boolean mCreated;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCreated) {
            Log.w(TAG, "onActivityCreated: ignoring duplicate call");
            return;
        }
        mCreated = true;
        if (mPackageInfo == null) return;
        final Activity activity = getActivity();
        final Preference pref = EntityHeaderController
                .newInstance(activity, this, LayoutInflater.from(activity).inflate(R.layout.op_settings_entity_header, (ViewGroup) null))
                .setRecyclerView(getListView(), getSettingsLifecycle())
                .setIcon(Utils.getBadgedIcon(getContext(), mPackageInfo.applicationInfo))
                .setLabel(mPackageInfo.applicationInfo.loadLabel(mPm))
                .setSummary(mPackageInfo)
                .setIsInstantApp(AppUtils.isInstant(mPackageInfo.applicationInfo))
                .setPackageName(mPackageName)
                .setUid(mPackageInfo.applicationInfo.uid)
                .setHasAppInfoLink(true)
                .setButtonActions(ActionType.ACTION_NONE, ActionType.ACTION_NONE)
                .done(activity, getPrefContext());
        getPreferenceScreen().addPreference(pref);
    }
}
