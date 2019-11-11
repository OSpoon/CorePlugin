# Cordova Plugin CorePlugin
================================

Cross-platform CorePlugin for Cordova.

Follows the [Cordova Plugin spec](https://cordova.apache.org/docs/en/latest/plugin_ref/spec.html), so that it works with [Plugman](https://github.com/apache/cordova-plugman).

## Installation
```
    cordova plugin add coreplugin
```
### Supported Platforms

- Android

## Using the plugin ##
The plugin creates the object 
`cordova.CorePlugin` with the method `hybridCallAction(hybridStr, success, fail)`.

```
/* 此交互js可公共使用
 * hybridStr 调用原生事例
 * MABjCaPlugin 为 业务类 类名
 * 202 方法ID
 * showSignMutiViewWithInfo 为方法名
 * ?问号后面为方法参数 
 * let hybridStr = 'hybrid://MABjCaPlugin:202/showSignMutiViewWithInfo?{\"paramters\":\"hello\"}';
 * cordova.CorePlugin.hybridCallAction(hybridStr, successBlock, failBlock);
 * //调用插件 成功返回
 * function successBlock(result){
 * 	console.log(result);
 * }
 * //调用插件 失败返回
 * function failBlock(errorStr){
 *	console.log(errorStr);
 * }
 */
```
