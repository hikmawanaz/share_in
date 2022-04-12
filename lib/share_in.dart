
import 'dart:async';

import 'package:flutter/services.dart';

class ShareIn {
  static const MethodChannel _channel =
      const MethodChannel('share_in');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
