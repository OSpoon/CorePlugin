package org.apache.cordova.it200.service;

import android.content.Intent;

public interface ActivityResultBackInterface {

    /**
     * 处理onActivityResult回调
     */
    void onActivityResult(int requestCode, int resultCode, Intent intent);

}
