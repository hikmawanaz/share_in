import 'dart:async';

import 'package:flutter/services.dart';

class ShareIn {
  static const MethodChannel _channel = const MethodChannel('share_in');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<String> shareToTikTok({String msg = '', String imagePath = ''}) async {
    final Map<String, dynamic> arguments = <String, dynamic>{};
    arguments.putIfAbsent('msg', () => msg);
    arguments.putIfAbsent('url', () => imagePath);
    String result;
    try {
      result = await _channel.invokeMethod<String>('shareToTikTok', arguments);
    } catch (e) {
      return e.toString();
    }
    return result;
  }
}
