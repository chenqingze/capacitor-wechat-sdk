//
//  WechatSDKApplicationDelegate.swift
//  Plugin
//
//  Created by ChenQingze on 2022/1/11.
//  Copyright © 2022 Max Lynch. All rights reserved.
//

import Foundation
import Capacitor
public class WechatSDKApplicationDelegate: NSObject{
    
    public static let sharedInstance = WechatSDKApplicationDelegate();
    
    public func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        // TODO: Support other types, emit to rest of plugins
        return WXApi.handleOpenUniversalLink(userActivity, delegate: WXApiManager.sharedInstance)
    }
    
    
}
