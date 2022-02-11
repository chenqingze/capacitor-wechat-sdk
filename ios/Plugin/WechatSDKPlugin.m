#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(WechatSDKPlugin, "WechatSDK",
           CAP_PLUGIN_METHOD(echo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pay, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isInstalled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(echo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(pay, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isInstalled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(shareText, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(shareLink, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(shareImage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(shareMiniProgram, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(launchMiniProgram, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(sendAuthRequest, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(wxOpenCustomerServiceChat, CAPPluginReturnPromise);
           )
