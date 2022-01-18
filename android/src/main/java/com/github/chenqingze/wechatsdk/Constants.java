package com.github.chenqingze.wechatsdk;

public interface Constants {
    String TAG = "WechatSDKPlugin.Util";

    String ERROR_INVALID_PARAMETERS = "参数格式错误";
    String ERROR_WECHAT_NOT_INSTALLED = "未安装微信";
    String ERROR_SEND_REQUEST_FAILED = "发送请求失败";

    String WECHAT_RESPONSE_OK = "操作成功";
    String ERROR_WECHAT_RESPONSE_COMMON = "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、微信账号异常等。";
    String ERROR_WECHAT_RESPONSE_USER_CANCEL = "用户点击取消并返回";
    String ERROR_WECHAT_RESPONSE_SENT_FAILED = "发送失败";
    String ERROR_WECHAT_RESPONSE_AUTH_DENIED = "授权失败";
    String ERROR_WECHAT_RESPONSE_UNSUPPORT = "微信不支持";
    String ERROR_WECHAT_RESPONSE_UNKNOWN = "未知错误";

}
