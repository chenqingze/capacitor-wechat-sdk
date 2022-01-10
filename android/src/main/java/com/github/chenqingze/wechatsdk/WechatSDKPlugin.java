package com.github.chenqingze.wechatsdk;

import static com.github.chenqingze.wechatsdk.Constants.ERROR_INVALID_PARAMETERS;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_SEND_REQUEST_FAILED;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_NOT_INSTALLED;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

@CapacitorPlugin(name = "WechatSDK")
public class WechatSDKPlugin extends Plugin {

    public final static int REQUEST_READ_PHONE_STATE = 1;
    public static Bridge bridge;
    private WechatSDK implementation = new WechatSDK();
    private IWXAPI wxApi;
    public static String callbackId;

    /**
     * 初始化操作
     */
    @Override
    public void load() {
        super.load();
        bridge = this.getBridge();
        registerWeChat();
    }

    /**
     * 获取微信appId
     *
     * @return
     */
    private String getWxAppId() {
        return bridge.getConfig().getString("wechatAppId");
    }


    /**
     * 获取商户id
     *
     * @return
     */
    private String getMchId() {
        return bridge.getConfig().getString("mchid");
    }

    /**
     * 向微信注册app
     */
    private void registerWeChat() {
        String wxAppId = getWxAppId();
        wxApi = WXAPIFactory.createWXAPI(this.getContext(), wxAppId, true);
        wxApi.registerApp(wxAppId);
    }

    /**
     * 测试
     *
     * @param call
     */
    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }


    /**
     * 微信是否安装:todo ios改成未安装时改为reject
     *
     * @param call
     */
    @PluginMethod
    public void isInstalled(PluginCall call) {

        if (wxApi.isWXAppInstalled()) {
            call.resolve();
        } else {
            call.reject(ERROR_WECHAT_NOT_INSTALLED);
        }

    }


    /**
     * 支付
     *
     * @param call
     */
    @PluginMethod
    public void pay(PluginCall call) {

        bridge = this.getBridge();
        //        requestPermissions();
        String appId = getWxAppId(); //appid
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = this.getMchId(); // 商户号
        req.prepayId = call.getString("prepayId"); // 预支付交易会话标识
        req.nonceStr = call.getString("nonceStr"); // 随机字符串
        req.timeStamp = call.getString("timeStamp"); // 时间戳
        req.packageValue = call.getString("packageValue"); // 签名
        req.sign = call.getString("sign");// 签名

        callbackId = call.getCallbackId();
        bridge.saveCall(call);

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }

        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

}
