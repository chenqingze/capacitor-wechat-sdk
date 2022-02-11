import {WebPlugin} from '@capacitor/core';

import type {WechatSDKPlugin} from './definitions';

export class WechatSDKWeb extends WebPlugin implements WechatSDKPlugin {
    async echo(options: { value: string }): Promise<{ value: string }> {
        console.log('ECHO', options);
        return options;
    }


    isInstalled(): Promise<{ value: boolean }> {
        return Promise.resolve({value: false});
    }

    pay(payParam: { prepayId: string; packageValue: string; nonceStr: string; timeStamp: string; sign: string }): Promise<{ value: string }> {
        console.log(payParam);
        return Promise.resolve({value: ''});
    }

    launchMiniProgram(options: { userName: string; path: string; miniProgramType: number }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    shareImage(options: { image: string; title: string; description: string; scene: number }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    shareLink(options: { url: string; title: string; description: string; thumb?: string; scene: number }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    shareMiniProgram(options: { webpageUrl: string; userName: string; path: string; hdImageData: string; withShareTicket: boolean; miniProgramType: number; title: string; description: string; scene: number }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    shareText(options: { text: string; scene: string }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    sendAuthRequest(options: { scope: string; state: string }): Promise<void> {
        console.log(options);
        return Promise.resolve(undefined);
    }

    wxOpenCustomerServiceChat(options: { corpId: string; url: string }): Promise<any> {
        console.log(options);
        return Promise.resolve(undefined);
    }


}
