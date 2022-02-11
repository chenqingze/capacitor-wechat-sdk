//
//  WXApiManager.swift
//  Plugin
//
//  Created by ChenQingze on 2022/1/11.
//  Copyright © 2022 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor

class WXApiManager:NSObject,WXApiDelegate{
    
    static let sharedInstance = WXApiManager();
    typealias JSObject = [String:Any];
    
    static let ERROR_WECHAT_NOT_INSTALLED = "未安装微信";
    static let  ERROR_SEND_REQUEST_FAILED = "发送请求失败";
    static let  WECHAT_RESPONSE_OK = "操作成功";
    static let  ERROR_WECHAT_RESPONSE_COMMON = "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、微信账号异常等。";
    static let  ERROR_WECHAT_RESPONSE_USER_CANCEL = "用户点击取消并返回";
    static let  ERROR_WECHAT_RESPONSE_SENT_FAILED = "发送失败";
    static let  ERROR_WECHAT_RESPONSE_AUTH_DENIED = "授权失败";
    static let  ERROR_WECHAT_RESPONSE_UNSUPPORT = "微信不支持";
    static let  ERROR_WECHAT_RESPONSE_UNKNOWN = "未知错误";
    
    
    public func onReq(_ req: BaseReq) {
        
    }
    
    public func onResp(_ resp: BaseResp) {
        print("微信响应回调");
        self.wechatCallback(resp)
    }
    
    
    public func wechatCallback(_ resp: BaseResp){
        print("微信响应回调码解析 WXErrCode");
        let code = WXErrCode(resp.errCode);
        var result = JSObject();
        let call = WechatSDKPlugin.sharedInstance!.bridge!.savedCall(withID: WechatSDKPlugin.sharedInstance!.callbackId!)!
        //支付返回结果，实际支付结果需要去微信服务器端查询
        switch code{
        case WXSuccess: /**< 成功    */
            result["message"] = WXApiManager.WECHAT_RESPONSE_OK;
            result["errCode"] = "0"
            if resp.isKind(of: SendAuthResp.self){
                let  response = resp as! SendAuthResp
                result["state"] = response.state;
                result["lang"] = response.lang;
                result["country"] = response.country;
            }else if resp.isKind(of: WXOpenCustomerServiceResp.self){
                let  response = resp as! WXOpenCustomerServiceResp
                result["extMsg"] = response.extMsg
            }
            call.resolve(result)
            break
        case WXErrCodeCommon:  /**< 普通错误类型    */
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_COMMON,"-1");
            break;
        case WXErrCodeUserCancel:   /**< 用户点击取消并返回    */
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_USER_CANCEL,"-2");
            break;
        case  WXErrCodeSentFail: /**< 发送失败    */
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_SENT_FAILED,"-3");
            break;
        case  WXErrCodeAuthDeny:  /**< 授权失败    */
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_AUTH_DENIED,"-4");
            break;
        case  WXErrCodeUnsupport:  /**< 微信不支持    */
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_UNSUPPORT,"-5");
            break;
        default:
            call.reject(WXApiManager.ERROR_WECHAT_RESPONSE_UNKNOWN,"-6");
            break;
        }
        WechatSDKPlugin.sharedInstance!.bridge!.releaseCall(call)
    }
    
}
