package org.apache.cordova.n22.service;

import android.content.Intent;

public interface ActivityResultBackInterface {

    /**
     * 处理onActivityResult回调
     */
    void onActivityResult(int requestCode, int resultCode, Intent intent);

}
