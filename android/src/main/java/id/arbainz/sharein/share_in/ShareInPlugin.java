package id.arbainz.sharein.share_in;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ShareInPlugin */
public class ShareInPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Activity activity;
  private static CallbackManager callbackManager;

  public static void registerWith(Registrar registrar) {
        final FlutterShareMePlugin instance = new FlutterShareMePlugin();
        instance.onAttachedToEngine(registrar.messenger());
        instance.activity = registrar.activity();
    }


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "share_in");
    channel.setMethodCallHandler(this);
    callbackManager = CallbackManager.Factory.create();
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    channel = null;
    activity = null;
    }
    

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if(call.method.equals("shareToTikTok")){
       String url, msg, fileType;
      try {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        // if the url is the not empty then get url of the file and share
        url = call.argument("url");
        msg = call.argument("msg");
        if (!TextUtils.isEmpty(url)) {
          System.out.print(url+"url is not empty");
          File file = new File(url);
          Uri fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
          shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
          shareIntent.setType("video/*");
        }
        shareIntent.setPackage("com.zhiliaoapp.musically");
        try {
          activity.startActivity(shareIntent);
          result.success("true");
          } catch (Exception e) {
            result.success("false: TikTok is not installed on this device");
            }
        } catch (Exception e) {
            result.error("error", e.toString(), "");
        }
    } else if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
