package com.appodeal.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Color;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
//import com.appodeal.ads.Native;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.RewardedVideoCallbacks;
//import com.appodeal.ads.NonSkippableVideoCallbacks;
import com.appodeal.ads.UserSettings;
import com.appodeal.ads.BannerView;
//import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;
import com.appodeal.ads.initializing.ApdInitializationCallback;
import com.appodeal.ads.initializing.ApdInitializationError;
import com.appodeal.ads.rewarded.Reward;
import com.appodeal.ads.utils.Log;
import com.appodeal.ads.NativeMediaViewContentType;

import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentForm;
import com.explorestack.consent.ConsentFormListener;
import com.explorestack.consent.ConsentInfoUpdateListener;
import com.explorestack.consent.ConsentManager;
import com.explorestack.consent.exception.ConsentManagerException;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import androidx.annotation.Nullable;

public class CDVAppodeal extends CordovaPlugin {

    private static final String TAG = "com.appodeal.plugin";

    private static final String ACTION_IS_INITIALIZED = "isInitialized";

    private static ConsentForm consentForm;
    private static final String ACTION_INITIALIZE = "initialize";

    private static final String ACTION_SHOW = "show";
    private static final String ACTION_IS_LOADED = "isLoaded";
    private static final String ACTION_CACHE = "cache";
    private static final String ACTION_HIDE = "hide";
    private static final String ACTION_DESTROY = "destroy";
    private static final String ACTION_SET_AUTO_CACHE = "setAutoCache";
    private static final String ACTION_IS_PRECACHE = "isPrecache";

    private static final String ACTION_BANNER_ANIMATION = "setBannerAnimation";
    private static final String ACTION_SMART_BANNERS = "setSmartBanners";
    private static final String ACTION_728X90_BANNERS = "set728x90Banners";

    private static final String ACTION_SET_TESTING = "setTesting";
    private static final String ACTION_SET_LOGGING = "setLogLevel";
    private static final String ACTION_SET_CHILD_TREATMENT = "setChildDirectedTreatment";
    private static final String ACTION_DISABLE_NETWORK = "disableNetwork";
    private static final String ACTION_SET_ON_LOADED_TRIGGER_BOTH = "setTriggerOnLoadedOnPrecache";
    private static final String ACTION_MUTE_VIDEOS_IF_CALLS_MUTED = "muteVideosIfCallsMuted";
    private static final String ACTION_START_TEST_ACTIVITY = "showTestScreen";
    private static final String ACTION_GET_VERSION = "getVersion";

    private static final String ACTION_CAN_SHOW = "canShow";
    private static final String ACTION_CAN_SHOW_WITH_PLACEMENT = "canShowWithPlacement";
    private static final String ACTION_SET_CUSTOM_INTEGER_RULE = "setCustomIntegerRule";
    private static final String ACTION_SET_CUSTOM_BOOLEAN_RULE = "setCustomBooleanRule";
    private static final String ACTION_SET_CUSTOM_DOUBLE_RULE = "setCustomDoubleRule";
    private static final String ACTION_SET_CUSTOM_STRING_RULE = "setCustomStringRule";
    private static final String ACTION_GET_REWARD_PARAMETERS = "getRewardParameters";
    private static final String ACTION_GET_PREDICTED_ECPM = "getPredictedEcpm";

    private static final String ACTION_SET_USER_ID = "setUserId";
    private static final String ACTION_SET_INTERSTITIAL_CALLBACKS = "setInterstitialCallbacks";
    private static final String ACTION_SET_REWARDED_CALLBACKS = "setRewardedVideoCallbacks";
    private static final String ACTION_SET_BANNER_CALLBACKS = "setBannerCallbacks";

    private boolean isInitialized = false;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {

        log("execute action:" + action);

        if (action.equals(ACTION_INITIALIZE)) return actionInitialize(args, callback);

        if (action.equals(ACTION_IS_INITIALIZED)) {
            runOnThreadPool(() -> sendPluginResOK(callback, isInitialized));
            return true;
        }

        if (action.equals(ACTION_SHOW)) return actionShow(args, callback);

        if (action.equals(ACTION_HIDE)) return actionHide(args, callback);

        if (action.equals(ACTION_START_TEST_ACTIVITY)) {
            runOnUiThread(() -> Appodeal.startTestActivity(cordova.getActivity()));
            return true;
        }

        if (action.equals(ACTION_SET_TESTING)) {
            final boolean testing = args.getBoolean(0);
            runOnThreadPool(() -> Appodeal.setTesting(testing));
            return true;
        }

        if (action.equals(ACTION_IS_LOADED)) {
            final int adType = args.getInt(0);
            runOnUiThread(() -> sendPluginResOK(callback, Appodeal.isLoaded(adType)));
            return true;
        }

        if (action.equals(ACTION_CACHE)) {
            final int adType = args.getInt(0);
            runOnUiThread(() -> Appodeal.cache(cordova.getActivity(), adType));
            return true;
        }

        if (action.equals(ACTION_DESTROY)) {
            final int adType = args.getInt(0);
            runOnUiThread(() -> Appodeal.destroy(adType));
            return true;
        }


        if (action.equals(ACTION_SET_AUTO_CACHE)) {
            final int adType = args.getInt(0);
            final boolean autoCache = args.getBoolean(1);
            runOnThreadPool(() -> Appodeal.setAutoCache(adType, autoCache));
            return true;
        }

        if (action.equals(ACTION_IS_PRECACHE)) {
            final int adType = args.getInt(0);
            runOnThreadPool(() -> sendPluginResOK(callback, Appodeal.isPrecache(adType)));
            return true;
        }

        if (action.equals(ACTION_BANNER_ANIMATION)) {
            final boolean value = args.getBoolean(0);
            runOnThreadPool(() -> Appodeal.setBannerAnimation(value));
            return true;
        }

        if (action.equals(ACTION_SMART_BANNERS)) {
            final boolean value = args.getBoolean(0);
            runOnThreadPool(() -> Appodeal.setSmartBanners(value));
            return true;
        }

        if (action.equals(ACTION_728X90_BANNERS)) {
            final boolean value = args.getBoolean(0);
            runOnThreadPool(() -> Appodeal.set728x90Banners(value));
            return true;
        }

        if (action.equals(ACTION_SET_LOGGING)) {
            final int logLevel = args.getInt(0);
            runOnThreadPool(() -> {
                switch (logLevel) {
                    case 0:
                        Appodeal.setLogLevel(Log.LogLevel.none);
                        break;
                    case 1:
                        Appodeal.setLogLevel(Log.LogLevel.debug);
                        break;
                    case 2:
                        Appodeal.setLogLevel(Log.LogLevel.verbose);
                        break;
                    default:
                        Appodeal.setLogLevel(Log.LogLevel.none);
                        break;
                }
            });
            return true;
        }

        if (action.equals(ACTION_SET_CHILD_TREATMENT)) {
            final boolean value = args.getBoolean(0);
            runOnUiThread(() -> Appodeal.setChildDirectedTreatment(value));
            return true;
        }

        if (action.equals(ACTION_DISABLE_NETWORK)) {
            final String network = args.getString(0);
            final int adType = args.getInt(1);
            runOnUiThread(() -> Appodeal.disableNetwork(network, adType));
            return true;
        }


        if (action.equals(ACTION_SET_ON_LOADED_TRIGGER_BOTH)) {
            final int adType = args.getInt(0);
            final boolean setOnTriggerBoth = args.getBoolean(1);
            runOnUiThread(() -> Appodeal.setTriggerOnLoadedOnPrecache(adType, setOnTriggerBoth));
            return true;
        }

        if (action.equals(ACTION_MUTE_VIDEOS_IF_CALLS_MUTED)) {
            final boolean value = args.getBoolean(0);
            runOnUiThread(() -> Appodeal.muteVideosIfCallsMuted(value));
            return true;
        }

        if (action.equals(ACTION_GET_VERSION)) {
            runOnUiThread(() -> callback.success(Appodeal.getVersion()));
            return true;
        }

        if (action.equals(ACTION_CAN_SHOW)) {
            final int adType = args.getInt(0);
            runOnUiThread(() -> sendPluginResOK(callback, Appodeal.canShow(adType)));
            return true;
        }

        if (action.equals(ACTION_CAN_SHOW_WITH_PLACEMENT)) {
            final int adType = args.getInt(0);
            final String placement = args.getString(1);
            runOnUiThread(() -> sendPluginResOK(callback, Appodeal.canShow(adType, placement)));
            return true;
        }

        if (action.equals(ACTION_SET_CUSTOM_INTEGER_RULE)) {
            final String name = args.getString(0);
            final int value = args.getInt(1);
            runOnUiThread(() -> Appodeal.setExtraData(name, value));
            return true;
        }

        if (action.equals(ACTION_SET_CUSTOM_BOOLEAN_RULE)) {
            final String name = args.getString(0);
            final boolean value = args.getBoolean(1);
            runOnUiThread(() -> Appodeal.setExtraData(name, value));
            return true;
        }

        if (action.equals(ACTION_SET_CUSTOM_DOUBLE_RULE)) {
            final String name = args.getString(0);
            final double value = args.getDouble(1);
            runOnUiThread(() -> Appodeal.setExtraData(name, value));
            return true;
        }

        if (action.equals(ACTION_SET_CUSTOM_STRING_RULE)) {
            final String name = args.getString(0);
            final String value = args.getString(1);
            runOnUiThread(() -> Appodeal.setExtraData(name, value));
            return true;
        }

        if (action.equals(ACTION_GET_PREDICTED_ECPM)) {
            final int adType = args.getInt(0);
            runOnUiThread(() -> sendPluginResOK(callback, (float) Appodeal.getPredictedEcpm(adType)));
            return true;
        }

        if (action.equals(ACTION_GET_REWARD_PARAMETERS)) {
            final String placement = args.getString(0);

            Reward reward;
            if (placement == null)
                reward = Appodeal.getReward(placement);
            else
                reward = Appodeal.getReward();

            JSONObject vals = new JSONObject();
            vals.put("amount", reward.getAmount());
            vals.put("currency", reward.getCurrency());

            cordova.getActivity().runOnUiThread(() -> callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, vals)));
            return true;
        }

        if (action.equals(ACTION_SET_USER_ID)) {
            final String userId = args.getString(0);
            runOnUiThread(() -> Appodeal.setUserId(userId));
            return true;
        }

        if (action.equals(ACTION_SET_BANNER_CALLBACKS)) {
            runOnThreadPool(() -> Appodeal.setBannerCallbacks(new BannerCallbacksHandler(this, callback)));
            return true;
        }

        if (action.equals(ACTION_SET_INTERSTITIAL_CALLBACKS)) {
            runOnThreadPool(() -> Appodeal.setInterstitialCallbacks(new InterstitialCallbacksHandler(this, callback)));
            return true;
        }

        if (action.equals(ACTION_SET_REWARDED_CALLBACKS)) {
            runOnUiThread(() -> Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacksHandler(this, callback)));
            return true;
        }

        return false;
    }

    private boolean actionInitialize(JSONArray args, final CallbackContext callback) throws JSONException {
        final String appKey = args.getString(0);
        final int adType = args.getInt(1);

        runOnThreadPool(() -> {
            log("Initializing SDK");
            Appodeal.initialize(cordova.getActivity(), appKey, adType, errors -> {
                isInitialized = true;
                log("SDK initialized");
                callback.success();
            });
        });

        return true;
    }

    private boolean actionShow(JSONArray args, CallbackContext callback) throws JSONException {
        final int adType = args.getInt(0);
        final String placement = args.getString(1);

        runOnUiThread(() -> {
            boolean res = false;
            boolean isBanner = adType == Appodeal.BANNER ||
                    adType == Appodeal.BANNER_LEFT ||
                    adType == Appodeal.BANNER_RIGHT ||
                    adType == Appodeal.BANNER_BOTTOM ||
                    adType == Appodeal.BANNER_TOP;

            if (isBanner) {
                res = showBanner(adType, placement);
            } else if (adType == Appodeal.NATIVE) {
                //res = showNativeAd();
            } else {
//                res = Appodeal.show(cordova.getActivity(), adType);
            }

            if (res) callback.success();
            else callback.error(0);
        });

        return true;
    }

    private boolean actionHide(JSONArray args, CallbackContext callback) throws JSONException {
        final int adType = args.getInt(0);
        runOnUiThread(() -> {
            Appodeal.hide(cordova.getActivity(), adType);
            callback.success();
        });
        return true;
    }

    private boolean showBanner(int adType, String placement) {
        if (placement == null)
            return Appodeal.show(cordova.getActivity(), adType);

        return Appodeal.show(cordova.getActivity(), adType, placement);
    }


    private static void log(String message) {
        if (Appodeal.getLogLevel().equals(Log.LogLevel.debug) || Appodeal.getLogLevel().equals(Log.LogLevel.verbose)) {
            android.util.Log.d(TAG, message);
        }
    }

    void runOnThreadPool(Runnable action) {
        cordova.getThreadPool().execute(action);
    }

    void runOnUiThread(Runnable action) {
        cordova.getActivity().runOnUiThread(action);
    }

    private void sendPluginResOK(CallbackContext callback, String result) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
    }

    private void sendPluginResOK(CallbackContext callback, boolean result) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
    }

    private void sendPluginResOK(CallbackContext callback, int result) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
    }

    private void sendPluginResOK(CallbackContext callback, float result) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
    }

    private void sendPluginResOK(CallbackContext callback, JSONObject result) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, result));
    }
}
