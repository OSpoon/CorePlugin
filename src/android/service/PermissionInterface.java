package org.apache.cordova.it200.service;

public interface PermissionInterface {

    /**
     * 申请所有权限之后的逻辑
     */
    void onAfterApplyAllPermission(int requestCode);

}
