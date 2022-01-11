export interface WechatSDKPlugin {

  /**
   * 测试
   * @param options
   */
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
   * 调起微信支付
   * @param options
   */
  pay(options: { prepayId: string, packageValue: string, nonceStr: string, timeStamp: string, sign: string }): Promise<{ value: string }>;

  /**
   * 判断是否安装微信
   */
  isInstalled(): Promise<any>;

  /**
   * 分享文本
   * @param options
   */
  shareText(options: { text: string, scene: string }): Promise<void>;

  /**
   * 分享链接
   * @param options
   * thumb - 图片base64字符串
   */
  shareLink(options: { url: string, title: string, description: string, thumb?: string, scene: number }): Promise<void>;

  /**
   * 分享图片
   * @param options
   *  imageUrl - 图片的本地路径
   */
  shareImage(options: { imageUrl: string, title: string, description: string, scene: number }): Promise<void>;

  /**
   * 分享微信小程序
   * @param options
   *  hdImageData - 图片base64字符串
   */
  shareMiniProgram(options: { webpageUrl: string, userName: string, path: string, hdImageData: string, withShareTicket: boolean, miniProgramType: number, title: string, description: string, scene: number }): Promise<void>;

  /**
   * 调起微信小程序
   * @param options
   */
  launchMiniProgram(options: { userName: string, path: string, miniProgramType: number }): Promise<void>;

  /**
   * 微信登录
   * @param options
   */
  sendAuthRequest(options: { scope: string, state: string }): Promise<void>;

}
