package org.apache.cordova.it200.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.it200.service.MAInterface;
import org.json.JSONException;
import org.json.JSONObject;

public class MADiallPhone implements MAInterface {

        private Activity activity;
        private CallbackContext mCallbackContext;

        /**
         * h5与原生的回调方法
         * @param callbackContext  回调
         * @param context activity
         * @param args h5页面可传参 需配置
         */
        private void invokeDiallPhone(final CallbackContext callbackContext, final Context context, String args) {
            activity = (Activity) context;
            mCallbackContext = callbackContext;
            if(!TextUtils.isEmpty(args)){
                JSONObject json = null;
                try {
                    json = new JSONObject(args);
                    diallPhone(json.getString("url"));
                } catch (JSONException e) {
                    callbackContext.error(e.getMessage());
                }
            }
        }

        @Override
        public void onAfterApplyAllPermission(int requestCode) {

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        }

        /**
         * 拨打电话（跳转到拨号界面，用户手动点击拨打）
         *
         * @param phoneNum 电话号码
         */
        public void diallPhone(String phoneNum) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            activity.startActivity(intent);
        }
}
