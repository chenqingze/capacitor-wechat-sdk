# @capacitor/wechat-sdk

wechat sdk wrapper with capacitor ,support ios and android

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
echo(options: { value: string; }) => Promise<{ value: string; }>
```

测试

| Param         | Type                            | Description |
| ------------- | ------------------------------- | ----------- |
| **`options`** | <code>{ value: string; }</code> | 测试字符串       |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### pay(...)

```typescript
pay(options: { prepayId: string; packageValue: string; nonceStr: string; timeStamp: string; sign: string; }) => Promise<{ value: string; }>
```

调起微信支付

| Param         | Type                                                                                                        |
| ------------- | ----------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ prepayId: string; packageValue: string; nonceStr: string; timeStamp: string; sign: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### isInstalled()

```typescript
isInstalled() => Promise<{ value: boolean; }>
```

判断是否安装微信

**Returns:** <code>Promise&lt;{ value: boolean; }&gt;</code>

--------------------


### shareText(...)

```typescript
shareText(options: { text: string; scene: string; }) => Promise<void>
```

分享文本

| Param         | Type                                          |
| ------------- | --------------------------------------------- |
| **`options`** | <code>{ text: string; scene: string; }</code> |

--------------------


### shareLink(...)

```typescript
shareLink(options: { url: string; title: string; description: string; thumb?: string; scene: number; }) => Promise<void>
```

分享链接

| Param         | Type                                                                                             |
| ------------- | ------------------------------------------------------------------------------------------------ |
| **`options`** | <code>{ url: string; title: string; description: string; thumb?: string; scene: number; }</code> |

--------------------


### shareImage(...)

```typescript
shareImage(options: { imageUrl: string; title: string; description: string; scene: number; }) => Promise<void>
```

分享图片

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code>{ imageUrl: string; title: string; description: string; scene: number; }</code> |

--------------------


### shareMiniProgram(...)

```typescript
shareMiniProgram(options: { webpageUrl: string; userName: string; path: string; hdImageData: string; withShareTicket: boolean; miniProgramType: number; title: string; description: string; scene: number; }) => Promise<void>
```

分享微信小程序

| Param         | Type                                                                                                                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ webpageUrl: string; userName: string; path: string; hdImageData: string; withShareTicket: boolean; miniProgramType: number; title: string; description: string; scene: number; }</code> |

--------------------


### launchMiniProgram(...)

```typescript
launchMiniProgram(options: { userName: string; path: string; miniProgramType: number; }) => Promise<void>
```

调起微信小程序

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code>{ userName: string; path: string; miniProgramType: number; }</code> |

--------------------


### sendAuthRequest(...)

```typescript
sendAuthRequest(options: { scope: string; state: string; }) => Promise<void>
```

微信登录

| Param         | Type                                           |
| ------------- | ---------------------------------------------- |
| **`options`** | <code>{ scope: string; state: string; }</code> |

--------------------

</docgen-api>
