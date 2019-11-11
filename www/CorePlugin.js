/* 此交互js可公共使用
 * hybridStr 调用原生事例
 * MABjCaPlugin 为 业务类 类名
 * 202 方法ID
 * showSignMutiViewWithInfo 为方法名
 * ?问号后面为方法参数 
 * var hybridStr = 'hybrid://MABjCaPlugin:202/showSignMutiViewWithInfo?{\"paramters\":\"hello\"}';
 * CorePlugin.hybridCallAction(hybridStr, successBlock, failBlock);
 * //调用插件 成功返回
 * function successBlock(result){
 * 	console.log(result);
 * }
 * //调用插件 失败返回
 * function failBlock(errorStr){
 *	console.log(errorStr);
 * }
 */
var exec = require('cordova/exec');

var CorePlugin = {
	hybridCallAction:function(hybridStr,successBlock,failBlock) {
		exec(successBlock, failBlock, "CorePlugin", "callNative", [hybridStr]);
	}
};
module.exports = CorePlugin;