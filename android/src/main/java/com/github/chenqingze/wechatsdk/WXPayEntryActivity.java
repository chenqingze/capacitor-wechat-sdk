package com.github.chenqingze.wechatsdk;

import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_AUTH_DENIED;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_COMMON;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_SENT_FAILED;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_UNKNOWN;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_UNSUPPORT;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_RESPONSE_USER_CANCEL;
import static com.github.chenqingze.wechatsdk.Constants.WECHAT_RESPONSE_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String TAG = "Plugin.WechatPay";
    protected static IWXAPI wxAPI;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxAPI = getWxAPI(this);
        wxAPI.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxAPI.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        System.out.println("print===================>onResp");
        PluginCall call = WechatSDKPlugin.bridge.getSavedCall(WechatSDKPlugin.callbackId);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                JSObject ret = new JSObject();
                ret.put("code", 0);
                ret.put("message", WECHAT_RESPONSE_OK);
                call.resolve(ret);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                call.reject(ERROR_WECHAT_RESPONSE_USER_CANCEL, "-2");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                call.reject(ERROR_WECHAT_RESPONSE_AUTH_DENIED, "-4");
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                call.reject(ERROR_WECHAT_RESPONSE_SENT_FAILED, "-3");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                call.reject(ERROR_WECHAT_RESPONSE_UNSUPPORT, "-5");
                break;
            case BaseResp.ErrCode.ERR_COMM:
                call.reject(ERROR_WECHAT_RESPONSE_COMMON, "-1");
                break;
            default:
                call.reject(ERROR_WECHAT_RESPONSE_UNKNOWN, "-6");
                break;
        }
        WechatSDKPlugin.bridge.releaseCall(call);
        finish();
    }

    public IWXAPI getWxAPI(Context ctx) {
        if(wxAPI == null) {
            return WXAPIFactory.createWXAPI(ctx,WechatSDKPlugin.getWxAppId(),false);
        }
        return wxAPI;
    }
}
