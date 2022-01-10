import Foundation

@objc public class WechatSDK: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
