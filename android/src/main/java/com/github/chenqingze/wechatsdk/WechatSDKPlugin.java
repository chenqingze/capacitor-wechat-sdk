package com.github.chenqingze.wechatsdk;

import static com.github.chenqingze.wechatsdk.Constants.ERROR_INVALID_PARAMETERS;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_SEND_REQUEST_FAILED;
import static com.github.chenqingze.wechatsdk.Constants.ERROR_WECHAT_NOT_INSTALLED;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

@CapacitorPlugin(name = "WechatSDK")
public class WechatSDKPlugin extends Plugin {

    private WechatSDK implementation = new WechatSDK();

    public static Bridge bridge;
    public static IWXAPI wxApi;
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
        PayReq req = new PayReq(); // appId
        req.appId = getWxAppId();
        req.partnerId = this.getMchId(); // 商户号
        req.prepayId = call.getString("prepayId"); // 预支付交易会话标识
        req.nonceStr = call.getString("nonceStr"); // 随机字符串
        req.timeStamp = call.getString("timeStamp"); // 时间戳
        req.packageValue = call.getString("packageValue"); // 签名
        req.sign = call.getString("sign");// 签名

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }

        callbackId = call.getCallbackId();
        if (callbackId != null) {
            Log.d(Constants.TAG, "print===================>saveCall");
            bridge.saveCall(call);
        }

        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    /**
     * 分享文本
     *
     * @param call
     */
    @PluginMethod
    public void shareText(PluginCall call) {
        Integer scene = call.getInt("scene");
        String text = call.getString("text");
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;

        req.scene = scene;

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }

        //调用api接口，发送数据到微信
        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }


    /**
     * 分享链接
     *
     * @param call
     */
    @PluginMethod
    public void shareLink(PluginCall call) {

        // String webpageUrl = call.getString("url");
        // String title = call.getString("title");
        // String description = call.getString("description");
        // String thumb = call.getString("thumb");
        // 初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = call.getString("url");

        // 用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = call.getString("title");
        msg.description = call.getString("description");

        String thumb = call.getString("thumb");
        if (thumb == null || thumb.length() <= 0) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        msg.thumbData = Util.getByteArrayThumbFromBitmap(Util.covertBase64ToBitmap(thumb));

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = call.getInt("scene");

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }

        // 调用api接口，发送数据到微信
        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    /**
     * 分享图片
     *
     * @param call
     */
    @PluginMethod
    public void shareImage(PluginCall call) {
        String imgUrl = call.getString("imageUrl");
        //Integer scene = call.getInt("scene");
        if (imgUrl == null || imgUrl.length() <= 0) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        String imagePath = getContext().getCacheDir().getAbsolutePath() + "/" + imgUrl;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);

        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        msg.thumbData = Util.getByteArrayThumbFromBitmap(bmp);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = call.getInt("scene");

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }

        //调用api接口，发送数据到微信
        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    /**
     * 分享微信小程序
     *
     * @param call
     */
    @PluginMethod
    public void shareMiniProgram(PluginCall call) {

        // String webpageUrl = call.getString("webpageUrl");
        // String userName = call.getString("userName");
        // String path = call.getString("path");
        // String hdImageData = call.getString("hdImageData");
        // Boolean withShareTicket = call.getBoolean("withShareTicket");
        // String title = call.getString("title");
        // String description = call.getString("description");
        // Integer scene = call.getInt("scene");
        // Integer miniProgramType = call.getInt("miniProgramType");
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = call.getString("webpageUrl");   // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = call.getInt("miniProgramType");    // 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = call.getString("userName");   // 小程序原始id
        miniProgramObj.path = call.getString("path");       // 小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        miniProgramObj.withShareTicket = call.getBoolean("withShareTicket");
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = call.getString("title");    // 小程序消息title
        msg.description = call.getString("description");    // 小程序消息desc
        String hdImageData = call.getString("hdImageData");
        if (hdImageData == null || hdImageData.length() <= 0) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        new DownloadImageTask(msg).execute(hdImageData); // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }

        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    /**
     * 调起微信小程序
     *
     * @param call
     */
    @PluginMethod
    public void launchMiniProgram(PluginCall call) {

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = call.getString("userName"); // 填小程序原始id
        req.path = call.getString("path");   //拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType = call.getInt("miniProgramType");// 可选打开 开发版，体验版和正式版

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }

        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    /**
     * 微信登录
     *
     * @param call
     */
    @PluginMethod
    public void sendAuthRequest(PluginCall call) {

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = call.getString("scope");
        req.state = call.getString("state");

        if (!req.checkArgs()) {
            call.reject(ERROR_INVALID_PARAMETERS);
            return;
        }
        callbackId = call.getCallbackId();
        if (callbackId != null) {
            bridge.saveCall(call);
        }


        if (!wxApi.sendReq(req)) {
            call.reject(ERROR_SEND_REQUEST_FAILED);
        }
    }

    public static IWXAPI getWxAPI(Context ctx) {
        if (wxApi == null) {
            wxApi = WXAPIFactory.createWXAPI(ctx, getWxAppId(), true);
        }
        return wxApi;
    }

    /**
     * 获取微信appId
     *
     * @return
     */
    public static String getWxAppId() {
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
        getWxAPI(this.getContext()).registerApp(getWxAppId());
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private WXMediaMessage mediaMessage;

        public DownloadImageTask(WXMediaMessage mediaMessage) {
            this.mediaMessage = mediaMessage;
        }

        protected Bitmap doInBackground(String... urls) {
            return Util.getBitmap(urls[0]);
        }


        /*  @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showProgressDialog();
        }*/

        protected void onPostExecute(Bitmap bitmap) {
            if (Util.isOverSize(bitmap, 128)) {
                bitmap = Util.imageZoom(bitmap);
            }
            this.mediaMessage.thumbData = Util.bmpToByteArray(bitmap, true);
        }
    }

}


