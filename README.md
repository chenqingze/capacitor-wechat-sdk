# @capacitor/wechat-sdk

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

| Param         | Type                                                                                             | Description         |
| ------------- | ------------------------------------------------------------------------------------------------ | ------------------- |
| **`options`** | <code>{ url: string; title: string; description: string; thumb?: string; scene: number; }</code> | thumb - 图片base64字符串 |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### shareImage(...)

```typescript
shareImage(options: { imageUrl: string; title: string; description: string; scene: number; }) => Promise<any>
```

分享图片

| Param         | Type                                                                                  | Description         |
| ------------- | ------------------------------------------------------------------------------------- | ------------------- |
| **`options`** | <code>{ imageUrl: string; title: string; description: string; scene: number; }</code> |  imageUrl - 图片的本地路径 |

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
