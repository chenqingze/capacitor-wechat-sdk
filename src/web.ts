import { WebPlugin } from '@capacitor/core';

import type { WechatSDKPlugin } from './definitions';

export class WechatSDKWeb extends WebPlugin implements WechatSDKPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
