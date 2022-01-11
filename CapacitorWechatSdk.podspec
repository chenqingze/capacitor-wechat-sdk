require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapacitorWechatSdk'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = '12.0'
  s.dependency 'Capacitor'
  s.swift_version = '5.1'
  s.libraries = 'c++', 'z','sqlite3.0'
  s.vendored_libraries = 'ios/Plugin/OpenSDK1.9.2/libWeChatSDK.a'
  s.xcconfig = { 'OTHER_LDFLAGS' => '-ObjC -all_load' }
  s.frameworks = 'Security','CoreGraphics','SystemConfiguration','CoreTelephony','WebKit'
  s.public_header_files = 'ios/Plugin/OpenSDK1.9.2/*.h'
end
