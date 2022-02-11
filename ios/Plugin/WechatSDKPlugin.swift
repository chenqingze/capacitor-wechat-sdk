import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(WechatSDKPlugin)
public class WechatSDKPlugin: CAPPlugin {
    private let implementation = WechatSDK()
    var callbackId: String? = nil
    public static var sharedInstance:WechatSDKPlugin? = nil
    override public func load() {
        WechatSDKPlugin.sharedInstance=self
        //在register之前打开log, 后续可以根据log排查问题 0:打印正常日志；1:打印详细日志
        //        WXApi.startLog(by: WXLogLevel(rawValue: 0)!) { log in
        //            print("WeChatSDK:",log);
        //        }
        
        //注册微信支付
        let appId = self.bridge?.config.getString("wechatAppId") ?? ""
        let universalLink = self.bridge?.config.getString("wechatUniversalLink") ?? ""
        //        print("wechat appId:",appId)
        //        print("wechat universalLink:",universalLink)
        WXApi.registerApp(appId , universalLink: universalLink );
        //调用自检函数
        //        WXApi.checkUniversalLinkReady { step, result in
        //            print("自检测步骤=====>",step, "结果=====>",result.success, "错误原因=====>",result.errorInfo,"建议=====>", result.suggestion);
        //        }
        
        //        WXApiManager.sharedInstance.delegate=self
        
    }
    
    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    // 调起支付
    @objc func pay(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            
            guard let partnerId = self.bridge?.config.getString("mchid") else {
                call.reject("商户号没有配置.relation param: partnerId")
                return
            }
            guard let prepayId = call.getString("prepayId") else {
                call.reject("参数错误：预支付交易会话ID不能为空.relation param: prepayId")
                return
            }
            guard let nonceStr = call.getString("nonceStr") else {
                call.reject("参数错误：随机字符串不能为空.relation param: nonceStr")
                return
            }
            guard let timeStamp = call.getString("timeStamp") else {
                call.reject("参数错误：时间戳不能为空.relation param: timeStamp")
                return
            }
            guard let package = call.getString("packageValue") else {
                call.reject("参数错误：订单详情扩展字符串不能为空.relation param: packageValue")
                return
            }
            guard let sign = call.getString("sign") else {
                call.reject("参数错误：预支付交易会话ID不能为空.relation param: sign")
                return
            }
            
            let  req = PayReq()
            req.partnerId           = partnerId ;
            req.prepayId            = prepayId;
            req.nonceStr            = nonceStr;
            req.timeStamp           = UInt32(timeStamp)!
            req.package             = package;
            req.sign                = sign;
            
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            //日志输出
            debugPrint("微信支付调起url:partnerId-->",partnerId,"prepayId-->",prepayId,"nonceStr-->",nonceStr,"timeStamp-->",timeStamp,"package-->",package,"sign-->",sign,separator: " :")
            
            WXApi.send(req) {success in
                if !success{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
        }
        
    }
    
    
    // 微信是否安装
    @objc func isInstalled(_ call: CAPPluginCall) {
        //use DispatchQueue.main.async isn't necessary
        DispatchQueue.main.async {
            if !WXApi.isWXAppInstalled(){
                call.reject(WXApiManager.ERROR_WECHAT_NOT_INSTALLED,"-8");
            }else{
                call.resolve();
            }
            
        }
        
    }
    
    // 分享文本
    @objc func shareText(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            
            let req = SendMessageToWXReq()
            
            req.text = call.getString("text")!
            req.bText = true
            req.scene = Int32(call.getInt("scene")!)
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req) { (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
                
            }
            
        }
    }
    
    // 分享链接
    @objc func shareLink(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            
            let ext = WXWebpageObject()
            ext.webpageUrl = call.getString("url")!
            
            
            let message = WXMediaMessage()
            message.title = call.getString("title")!
            message.description = call.getString("description")!
            
            let thumb = call.getString("thumb") ?? ""
            
            let url = URL(string: thumb)
            if let data = try? Data(contentsOf: url!) {
                let thumbimage = self.compressImage(UIImage(data: data)!, toByte: 65536)
                message.setThumbImage(thumbimage)
            }
            
            message.mediaObject = ext
            
            let req = SendMessageToWXReq()
            req.bText = false
            req.scene = Int32(call.getInt("scene")!)
            req.message = message
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req) { (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
            
        }
    }
    
    // 分享图片
    @objc func shareImage(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            
            let ext = WXImageObject()
            
            let documentsUrl = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!;
            
            let image = documentsUrl.appendingPathComponent(call.getString("image")!)
            
            var thumbImage :UIImage? = nil;
            if let imageData = try? Data(contentsOf: image) {
                ext.imageData = UIImage(data: imageData)!.pngData()!
                thumbImage = self.compressImage(UIImage(data: imageData)!, toByte: 65536)
            }
            
            let message = WXMediaMessage()
            message.title = call.getString("title")!
            message.description = call.getString("description")!
            if thumbImage != nil {
                message.setThumbImage(thumbImage!)
            }
            
            message.mediaObject = ext
            
            let req = SendMessageToWXReq()
            req.bText = false
            req.scene = Int32(call.getInt("scene")!)
            req.message = message
            
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req) { (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
            
        }
    }
    
    // 分享小程序
    @objc func shareMiniProgram(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let obj = WXMiniProgramObject()
            obj.webpageUrl = call.getString("webpageUrl")!
            obj.userName = call.getString("userName")!
            obj.path = call.getString("path")!
            
            let hdImageData = call.getString("hdImageData")!
            let url = URL(string: hdImageData)
            
            if let data = try? Data(contentsOf: url!) {
                obj.hdImageData = data;
            }
            
            obj.withShareTicket = call.getBool("withShareTicket")!
            obj.miniProgramType = WXMiniProgramType(rawValue: UInt(call.getInt("miniProgramType")!)) ?? WXMiniProgramType.release
            
            
            let message = WXMediaMessage()
            message.title = call.getString("title")!
            message.description = call.getString("description")!
            message.thumbData = nil
            message.mediaObject = obj
            
            let req = SendMessageToWXReq()
            req.bText = false
            req.scene = Int32(call.getInt("scene")!)
            req.message = message
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req) { (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
        }
    }
    
    // 调起小程序
    @objc func launchMiniProgram(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            
            let req = WXLaunchMiniProgramReq()
            req.userName = call.getString("userName")!
            req.path = call.getString("path")!
            req.miniProgramType = WXMiniProgramType(rawValue: UInt(call.getInt("miniProgramType")!)) ?? WXMiniProgramType.release
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req) { (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
            
        }
    }
    
    // 授权登录
    @objc func sendAuthRequest(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            //构造SendAuthReq结构体
            guard let scope = call.getString("scope")  else {
                call.reject("scope 参数错误")
                return
            }
            guard let state = call.getString("state")  else {
                call.reject("state 参数错误")
                return
            }
            
            let req = SendAuthReq()
            req.scope = scope;
            req.state = state;
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            
            //第三方向微信终端发送一个SendAuthReq消息结构
            WXApi.send(req){ (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
            
        }
        
    }
    
    // 拉起微信客服
    @objc func wxOpenCustomerServiceChat(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            guard let corpId = call.getString("corpId")  else {
                call.reject("corpId 参数异常")
                return
            }
            guard let url = call.getString("url")  else {
                call.reject("url 参数异常")
                return
            }
            
            let req = WXOpenCustomerServiceReq();
            req.corpid = corpId;    //企业ID
            req.url = url;          //客服URL
            
            
            guard let callbackId = call.callbackId else {
                call.reject("The call has no callbackId")
                return
            }
            self.callbackId = callbackId
            self.bridge?.saveCall(call);
            
            WXApi.send(req){ (res) in
                if !res{
                    call.reject(WXApiManager.ERROR_SEND_REQUEST_FAILED,"-9")
                }
            }
            
        }
        
    }
    
    
    @objc private func compressImage(_ image: UIImage, toByte maxLength: Int) -> UIImage {
        var compression: CGFloat = 1
        guard var data = image.jpegData(compressionQuality: compression),
              data.count > maxLength else { return image }
        
        // Compress by size
        var max: CGFloat = 1
        var min: CGFloat = 0
        for _ in 0..<6 {
            compression = (max + min) / 2
            data = image.jpegData(compressionQuality: compression)!
            if CGFloat(data.count) < CGFloat(maxLength) * 0.9 {
                min = compression
            } else if data.count > maxLength {
                max = compression
            } else {
                break
            }
        }
        var resultImage: UIImage = UIImage(data: data)!
        if data.count < maxLength { return resultImage }
        
        // Compress by size
        var lastDataLength: Int = 0
        while data.count > maxLength, data.count != lastDataLength {
            lastDataLength = data.count
            let ratio: CGFloat = CGFloat(maxLength) / CGFloat(data.count)
            let size: CGSize = CGSize(width: Int(resultImage.size.width * sqrt(ratio)),
                                      height: Int(resultImage.size.height * sqrt(ratio)))
            UIGraphicsBeginImageContext(size)
            resultImage.draw(in: CGRect(x: 0, y: 0, width: size.width, height: size.height))
            resultImage = UIGraphicsGetImageFromCurrentImageContext()!
            UIGraphicsEndImageContext()
            data = resultImage.jpegData(compressionQuality: compression)!
        }
        return resultImage
    }
    
}
