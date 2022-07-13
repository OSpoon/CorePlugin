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

```js
/* 此交互js可公共使用
 * hybridStr 调用原生事例
 * MACallPlugin 为 业务类 类名
 * 202 方法ID
 * showSignMutiViewWithInfo 为方法名
 * ?问号后面为方法参数 
 */
let hybridStr = 'hybrid://MACallPlugin:202/showSignMutiViewWithInfo?{"paramters":"hello"}';
cordova.CorePlugin.hybridCallAction(hybridStr, (result)=>{
	//调用插件 成功返回
	console.log(result);
},(error)=>{
	//调用插件 失败返回
	console.log(error);
});
```

## License ##
MIT License

Copyright (c) 2019 OSpoon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
