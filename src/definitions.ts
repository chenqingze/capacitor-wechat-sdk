export interface WechatSDKPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
