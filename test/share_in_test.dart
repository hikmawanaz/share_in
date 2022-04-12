import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:share_in/share_in.dart';

void main() {
  const MethodChannel channel = MethodChannel('share_in');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await ShareIn.platformVersion, '42');
  });
}
