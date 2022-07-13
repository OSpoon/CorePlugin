package org.apache.cordova.it200;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class CorePlugin extends CordovaPlugin {
    private Activity activity;
    private CordovaWebView webView;

    public CorePlugin() {
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.activity = cordova.getActivity();
        this.webView = webView;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("callNative".equals(action)) {
            System.out.println("callNative" + action);
            Map<String, Object> paramMap = parseProtocol((String) args.get(0));
            String protocolType = (String) paramMap.get("type"),
                    name = (String) paramMap.get("nameId"),
                    methodAction = (String) paramMap.get("method"),
                    parameterStr = (String) paramMap.get("parm");
            if (name.indexOf(":") != -1) {
                name = name.substring(0, name.indexOf(":"));
            }
            System.out.println(paramMap);
            try {
                Object test;
                test = ReflectUtil.invokeMethod(Class.forName("org.apache.cordova.it200.plugin." + name).newInstance(), methodAction, new Class<?>[]{CallbackContext.class, Context.class, String.class},
                        new Object[]{callbackContext, activity, parameterStr});
                System.out.println(test);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callbackContext.error("调用原生反射错误");
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callbackContext.error("调用原生反射错误");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                callbackContext.error("调用原生反射错误");
            }
            return true;
        }
        return false;
    }

    public static Map<String, Object> parseProtocol(String parmStr) {
        String protocolType = null, name = null, methodAction = null, parameterStr = null;
        String parmIn = new String(parmStr);
        if (parmIn.indexOf("hybrid://") != -1) {
            protocolType = "hybrid";
            parmIn = parmIn.replace("hybrid://", "");
            name = parmIn.substring(0, parmIn.indexOf("/"));
            if (parmIn.indexOf("?") != -1) {
                methodAction = parmIn.substring(parmIn.indexOf("/") + 1, parmIn.indexOf("?"));
            } else {
                methodAction = parmIn.substring(parmIn.indexOf("/") + 1, parmIn.length());
            }

            parameterStr = parmIn.substring(parmIn.indexOf("?") + 1, parmIn.length());
        }
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("type", protocolType);
        res.put("nameId", name);
        res.put("method", methodAction);
        res.put("parm", parameterStr);

        return res;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        System.out.println("进入返回");
    }
}
