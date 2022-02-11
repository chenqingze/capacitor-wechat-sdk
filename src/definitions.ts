export interface WechatSDKPlugin {

  /**
   * 测试
   * @param options
   */
  echo(options: { value: string }): Promise<any>;

  /**
   * 调起微信支付
   * @param options
   */
  pay(options: { prepayId: string, packageValue: string, nonceStr: string, timeStamp: string, sign: string }): Promise<any>;

  /**
   * 判断是否安装微信
   */
  isInstalled(): Promise<any>;

  /**
   * 分享文本
   * @param options
   */
  shareText(options: { text: string, scene: string }): Promise<any>;

  /**
   * 分享链接
   * @param options
   * thumb - 图片url地址 例如：http://xxx.com/test.png
   */
  shareLink(options: { url: string, title: string, description: string, thumb?: string, scene: number }): Promise<any>;

  /**
   * 分享图片,可配合@capacitor/filesystem 使用
   * @param options
   *  image - 本地图片的名称，如有特殊需求请自行修改获取图片的位置或方式
   *  ios默认去documentDirectory下寻找
   *  android默认去 /XXX/yourAppPackageName/cache目录下寻找图片文件
   */
  shareImage(options: { image: string, title: string, description: string, scene: number }): Promise<any>;

  /**
   * 分享微信小程序
   * @param options
   *  hdImageData - 图片url地址 例如：http://xxx.com/test.png
   */
  shareMiniProgram(options: { webpageUrl: string, userName: string, path: string, hdImageData: string, withShareTicket: boolean, miniProgramType: number, title: string, description: string, scene: number }): Promise<any>;

  /**
   * 调起微信小程序
   * @param options
   */
  launchMiniProgram(options: { userName: string, path: string, miniProgramType: number }): Promise<any>;

  /**
   * 微信登录
   * @param options
   */
  sendAuthRequest(options: { scope: string, state: string }): Promise<any>;


  /**
   * 拉起微信客服
   * @param options
   * corpId - 企业ID
   * url - 客服URL
   * @constructor
   */
  wxOpenCustomerServiceChat(options: { corpId: string, url: string }): Promise<any>;

}
