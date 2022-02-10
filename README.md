# @capacitor/wechat-sdk
## 关于参数及使用配置
### 【参数配置】
1、修改 capacitor.config.ts 或capacitor.config.json文件，增加微信相关配置参数
```
const config: CapacitorConfig = {
  mchid: '123456', // 商户id
  wechatAppId: 'wxd***c1fdsfsfs7f4f***', // 应用从微信开放平台官方网站申请到的合法appID
  wechatUniversalLink: 'https://com.***.***/' // UniversalLink
};
```

### 【iOS配置】
1、[参考微信官方配置[UniversalLink]并配置URL scheme](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/iOS.html)
2、在你的工程文件中选择 Build Setting，在"Other Linker Flags"中加入"-ObjC -all_load"
3、修改app/appDelegate.swift，增加或修改下面回调处理：
```
 func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {    
      return WechatSDKApplicationDelegate.sharedInstance.application(application,continue: userActivity, restorationHandler: restorationHandler)    
       // return ApplicationDelegateProxy.shared.application(application, continue: userActivity, restorationHandler: restorationHandler)    
}
```

###【Android配置】
1、安卓还需要修改[capacitor-wechat-sdk](https://github.com/chenqingze/capacitor-wechat-sdk)/[android](https://github.com/chenqingze/capacitor-wechat-sdk/tree/main/android)/build.gradle文件：
<img width="674" alt="image" src="https://user-images.githubusercontent.com/8285776/153450222-5326f98c-d225-45f2-b609-dcfaeecacd50.png">

## 关于安装
wechat sdk wrapper with capacitor ,support ios and android,本项目为自用项目并未发布到npm仓库，请clone到本地后，使用本地路径安装 : 
```bash
npm install /pathto/capacitor-wechat-sdk
npx cap sync

```
***下面的安装方式为capacitor自动生成，不可用。
## Install

```bash
npm install @capacitor/wechat-sdk 
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`pay(...)`](#pay)
* [`isInstalled()`](#isinstalled)
* [`shareText(...)`](#sharetext)
* [`shareLink(...)`](#sharelink)
* [`shareImage(...)`](#shareimage)
* [`shareMiniProgram(...)`](#shareminiprogram)
* [`launchMiniProgram(...)`](#launchminiprogram)
* [`sendAuthRequest(...)`](#sendauthrequest)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<any>
```

测试

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### pay(...)

```typescript
pay(options: { prepayId: string; packageValue: string; nonceStr: string; timeStamp: string; sign: string; }) => Promise<any>
```

调起微信支付

| Param         | Type                                                                                                        |
| ------------- | ----------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ prepayId: string; packageValue: string; nonceStr: string; timeStamp: string; sign: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### isInstalled()

```typescript
isInstalled() => Promise<any>
```

判断是否安装微信

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### shareText(...)

```typescript
shareText(options: { text: string; scene: string; }) => Promise<any>
```

分享文本

| Param         | Type                                          |
| ------------- | --------------------------------------------- |
| **`options`** | <code>{ text: string; scene: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### shareLink(...)

```typescript
shareLink(options: { url: string; title: string; description: string; thumb?: string; scene: number; }) => Promise<any>
```

分享链接

| Param         | Type                                                                                             | Description                                |
| ------------- | ------------------------------------------------------------------------------------------------ | ------------------------------------------ |
| **`options`** | <code>{ url: string; title: string; description: string; thumb?: string; scene: number; }</code> | thumb - 图片url地址 例如：http://xxx.com/test.png |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### shareImage(...)

```typescript
shareImage(options: { image: string; title: string; description: string; scene: number; }) => Promise<any>
```

分享图片,可配合@capacitor/filesystem 使用

| Param         | Type                                                                               | Description                                                                                                         |
| ------------- | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ image: string; title: string; description: string; scene: number; }</code> |  image - 本地图片的名称，如有特殊需求请自行修改获取图片的位置或方式 ios默认去documentDirectory下寻找 android默认去 /XXX/yourAppPackageName/cache目录下寻找图片文件 |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### shareMiniProgram(...)

```typescript
shareMiniProgram(options: { webpageUrl: string; userName: string; path: string; hdImageData: string; withShareTicket: boolean; miniProgramType: number; title: string; description: string; scene: number; }) => Promise<any>
```

分享微信小程序

| Param         | Type                                                                                                                                                                                            | Description                                       |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- |
| **`options`** | <code>{ webpageUrl: string; userName: string; path: string; hdImageData: string; withShareTicket: boolean; miniProgramType: number; title: string; description: string; scene: number; }</code> |  hdImageData - 图片url地址 例如：http://xxx.com/test.png |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### launchMiniProgram(...)

```typescript
launchMiniProgram(options: { userName: string; path: string; miniProgramType: number; }) => Promise<any>
```

调起微信小程序

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code>{ userName: string; path: string; miniProgramType: number; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### sendAuthRequest(...)

```typescript
sendAuthRequest(options: { scope: string; state: string; }) => Promise<any>
```

微信登录

| Param         | Type                                           |
| ------------- | ---------------------------------------------- |
| **`options`** | <code>{ scope: string; state: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------

</docgen-api>
