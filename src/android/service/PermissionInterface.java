package org.apache.cordova.n22.service;

public interface PermissionInterface {

    /**
     * 申请所有权限之后的逻辑
     */
    void onAfterApplyAllPermission(int requestCode);

}
