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
    private static final String ACTION_SHOW_WITH_PLACEMENT = "showWithPlacement";
    private static final String ACTION_SHOW_BANNER_VIEW = "showBannerView";
    private static final String ACTION_IS_LOADED = "isLoaded";
    private static final String ACTION_CACHE = "cache";
    private static final String ACTION_HIDE = "hide";
    private static final String ACTION_RESUME = "onResume";
    private static final String ACTION_DESTROY = "destroy";
    private static final String ACTION_SET_AUTO_CACHE = "setAutoCache";
    private static final String ACTION_IS_PRECACHE = "isPrecache";

    private static final String ACTION_BANNER_ANIMATION = "setBannerAnimation";
    private static final String ACTION_SMART_BANNERS = "setSmartBanners";
    private static final String ACTION_728X90_BANNERS = "set728x90Banners";
    private static final String ACTION_BANNERS_OVERLAP = "setBannerOverLap";

    private static final String ACTION_SET_TESTING = "setTesting";
    private static final String ACTION_SET_LOGGING = "setLogLevel";
    private static final String ACTION_SET_CHILD_TREATMENT = "setChildDirectedTreatment";
    private static final String ACTION_DISABLE_NETWORK = "disableNetwork";
    private static final String ACTION_DISABLE_NETWORK_FOR_TYPE = "disableNetworkType";
    private static final String ACTION_SET_ON_LOADED_TRIGGER_BOTH = "setTriggerOnLoadedOnPrecache";
    private static final String ACTION_MUTE_VIDEOS_IF_CALLS_MUTED = "muteVideosIfCallsMuted";
    private static final String ACTION_START_TEST_ACTIVITY = "showTestScreen";
    private static final String ACTION_SET_PLUGIN_VERSION = "setPluginVersion";
    private static final String ACTION_GET_VERSION = "getVersion";

    private static final String ACTION_CAN_SHOW = "canShow";
    private static final String ACTION_CAN_SHOW_WITH_PLACEMENT = "canShowWithPlacement";
    private static final String ACTION_SET_CUSTOM_INTEGER_RULE = "setCustomIntegerRule";
    private static final String ACTION_SET_CUSTOM_BOOLEAN_RULE = "setCustomBooleanRule";
    private static final String ACTION_SET_CUSTOM_DOUBLE_RULE = "setCustomDoubleRule";
    private static final String ACTION_SET_CUSTOM_STRING_RULE = "setCustomStringRule";
    private static final String ACTION_GET_REWARD_PARAMETERS = "getRewardParameters";
    private static final String ACTION_GET_REWARD_PARAMETERS_FOR_PLACEMENT = "getRewardParametersForPlacement";
    private static final String ACTION_SET_EXTRA_DATA = "setExtraData";
    private static final String ACTION_GET_PREDICTED_ECPM = "getPredictedEcpm";
    private static final String ACTION_GET_NATIVE_ADS = "getNativeAds";
    public static final String ACTION_SET_NATIVEAD_POSITION = "setNativeAdPosition";
    public static final String ACTION_HIDE_NATIVEAD = "hideNativeAd";
    public static final String ACTION_REVEAL_HIDDEN_NATIVEAD = "revealHiddenNativeAd";
    public static final String ACTION_REMOVE_NATIVEAD = "removeNativeAd";

    private static final String ACTION_SET_AGE = "setAge";
    private static final String ACTION_SET_GENDER = "setGender";
    private static final String ACTION_SET_USER_ID = "setUserId";
    private static final String ACTION_TRACK_IN_APP_PURCHASE = "trackInAppPurchase";

    private static final String ACTION_SET_NATIVE_CALLBACKS = "setNativeCallbacks";
    private static final String ACTION_SET_INTERSTITIAL_CALLBACKS = "setInterstitialCallbacks";
    private static final String ACTION_SET_NON_SKIPPABLE_VIDEO_CALLBACKS = "setNonSkippableVideoCallbacks";
    private static final String ACTION_SET_REWARDED_CALLBACKS = "setRewardedVideoCallbacks";
    private static final String ACTION_SET_BANNER_CALLBACKS = "setBannerCallbacks";

    private boolean isInitialized = false;
    private boolean bannerOverlap = true;
    private BannerView bannerView;
    private UserSettings userSettings;

    private static final String CALLBACK_INIT = "onInit";
    private static final String CALLBACK_LOADED = "onLoaded";
    private static final String CALLBACK_FAILED = "onFailedToLoad";
    private static final String CALLBACK_CLICKED = "onClick";
    private static final String CALLBACK_SHOWN = "onShown";
    private static final String CALLBACK_CLOSED = "onClosed";
    private static final String CALLBACK_FINISHED = "onFinished";
    private static final String CALLBACK_EXPIRED = "onExpired";
    private static final String CALLBACK_SHOW_FAILED = "onInterstitialShowFailed";

    private CallbackContext nativeCallbacks;
    private CallbackContext interstitialCallbacks;
    private CallbackContext bannerCallbacks;
    private CallbackContext nonSkippableCallbacks;
    private CallbackContext rewardedCallbacks;

    //    public static class NativeAdPlaceholder {
//        public float x, y, w, h, contentHdp;
//        public NativeAdViewNewsFeed	ad;
//        public boolean isShowing;
//    }
//    private NativeAdPlaceholder nativeAdPlaceholder = null;
    private static final int NATIVE_AD_ID = 1337;

//    private UserSettings getUserSettings() {
//        if (userSettings == null) {
//            userSettings = Appodeal.getUserSettings(cordova.getActivity());
//        }
//        return userSettings;
//    }

    private boolean actionInitialize(JSONArray args, final CallbackContext callback) throws JSONException {
        final String appKey = args.getString(0);
        final int adType = args.getInt(1);

        cordova.getThreadPool().execute(() -> {
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
        cordova.getActivity().runOnUiThread(() -> {
            boolean res = false;
            boolean isBanner = adType == Appodeal.BANNER ||
                    adType == Appodeal.BANNER_LEFT ||
                    adType == Appodeal.BANNER_RIGHT ||
                    adType == Appodeal.BANNER_BOTTOM ||
                    adType == Appodeal.BANNER_TOP;

            if (isBanner) {
                res = showBanner(adType, null);
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
        cordova.getActivity().runOnUiThread(() -> Appodeal.hide(cordova.getActivity(), adType));
        return true;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callback) throws JSONException {
//        Appodeal.setLogLevel(Log.LogLevel.debug);

        log("execute action:" + action);

        if (action.equals(ACTION_INITIALIZE)) return actionInitialize(args, callback);

        if (action.equals(ACTION_IS_INITIALIZED)) {
            cordova.getThreadPool().execute(() -> {
                callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, isInitialized));
            });
            return true;
        }

        if (action.equals(ACTION_SET_PLUGIN_VERSION)) return true;

        if (action.equals(ACTION_SHOW)) return actionShow(args, callback);

        if (action.equals(ACTION_HIDE)) return actionHide(args, callback);

        if (action.equals(ACTION_START_TEST_ACTIVITY)) {
            cordova.getActivity().runOnUiThread(() -> Appodeal.startTestActivity(cordova.getActivity()));
            return true;
        }

        if (action.equals(ACTION_SET_TESTING)) {
            final boolean testing = args.getBoolean(0);
            cordova.getThreadPool().execute(() -> Appodeal.setTesting(testing));
            return true;
        }
//
//
//        if (action.equals(ACTION_SHOW_WITH_PLACEMENT)) {
//            final int adType = args.getInt(0);
//            final String placement = args.getString(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    int rAdType = getAdType(adType);
//                    boolean res = false;
//                    if (rAdType == Appodeal.BANNER || rAdType == Appodeal.BANNER_BOTTOM
//                            || rAdType == Appodeal.BANNER_TOP) {
//                        res = showBanner(adType, placement);
//                    } else {
//                        res = Appodeal.show(cordova.getActivity(), getAdType(adType), placement);
//                    }
//                    callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, res));
//                }
//            });
//            return true;
//        }
//        if (action.equals(ACTION_IS_LOADED)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (Appodeal.isLoaded(getAdType(adType))) {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
//                    } else {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
//                    }
//                }
//            });
//            return true;
//        }
//        if (action.equals(ACTION_CACHE)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.cache(cordova.getActivity(), getAdType(adType));
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_DESTROY)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.destroy(adType);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_RESUME)) {
//            /* final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    int rAdType = getAdType(adType);
//                    if (rAdType == Appodeal.BANNER || rAdType == Appodeal.BANNER_BOTTOM
//                            || rAdType == Appodeal.BANNER_TOP) {
//                        Appodeal.onResume(cordova.getActivity(), rAdType);
//                    }
//                }
//            }); */
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_AUTO_CACHE)) {
//            final int adType = args.getInt(0);
//            final boolean autoCache = args.getBoolean(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setAutoCache(getAdType(adType), autoCache);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_IS_PRECACHE)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (Appodeal.isPrecache(getAdType(adType))) {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
//                    } else {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_BANNER_ANIMATION)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setBannerAnimation(value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SMART_BANNERS)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setSmartBanners(value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_728X90_BANNERS)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.set728x90Banners(value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_BANNERS_OVERLAP)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    bannerOverlap = value;
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_TESTING)) {
//            final boolean testing = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setTesting(testing);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_LOGGING)) {
//            final int logLevel = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    switch (logLevel) {
//                    case 0:
//                        Appodeal.setLogLevel(Log.LogLevel.none);
//                        break;
//                    case 1:
//                        Appodeal.setLogLevel(Log.LogLevel.debug);
//                        break;
//                    case 2:
//                        Appodeal.setLogLevel(Log.LogLevel.verbose);
//                        break;
//                    default:
//                        Appodeal.setLogLevel(Log.LogLevel.none);
//                        break;
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_CHILD_TREATMENT)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setChildDirectedTreatment(value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_DISABLE_NETWORK)) {
//            final String network = args.getString(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.disableNetwork(cordova.getActivity(), network);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_DISABLE_NETWORK_FOR_TYPE)) {
//            final String network = args.getString(0);
//            final int adType = args.getInt(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.disableNetwork(cordova.getActivity(), network, getAdType(adType));
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_ON_LOADED_TRIGGER_BOTH)) {
//            final int adType = args.getInt(0);
//            final boolean setOnTriggerBoth = args.getBoolean(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setTriggerOnLoadedOnPrecache(getAdType(adType), setOnTriggerBoth);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_MUTE_VIDEOS_IF_CALLS_MUTED)) {
//            final boolean value = args.getBoolean(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.muteVideosIfCallsMuted(value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_START_TEST_ACTIVITY)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.startTestActivity(cordova.getActivity());
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_GET_VERSION)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    callback.success(Appodeal.getVersion());
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_CAN_SHOW)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (Appodeal.canShow(getAdType(adType))) {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
//                    } else {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_CAN_SHOW_WITH_PLACEMENT)) {
//            final int adType = args.getInt(0);
//            final String placement = args.getString(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (Appodeal.canShow(getAdType(adType), placement)) {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
//                    } else {
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_CUSTOM_INTEGER_RULE)) {
//            final String name = args.getString(0);
//            final int value = args.getInt(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setExtraData(name, value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_CUSTOM_BOOLEAN_RULE)) {
//            final String name = args.getString(0);
//            final boolean value = args.getBoolean(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setExtraData(name, value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_CUSTOM_DOUBLE_RULE)) {
//            final String name = args.getString(0);
//            final double value = args.getDouble(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setExtraData(name, value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_CUSTOM_STRING_RULE)) {
//            final String name = args.getString(0);
//            final String value = args.getString(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setExtraData(name, value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_EXTRA_DATA)) {
//            final String name = args.getString(0);
//            final String value = args.getString(1);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Appodeal.setExtraData(name, value);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_GET_PREDICTED_ECPM)) {
//            final int adType = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, (float) Appodeal.getPredictedEcpm(adType)));
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_GET_REWARD_PARAMETERS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("amount", Appodeal.getRewardParameters().first);
//                        vals.put("currency", Appodeal.getRewardParameters().second);
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, vals));
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_GET_REWARD_PARAMETERS_FOR_PLACEMENT)) {
//            final String placement = args.getString(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("amount", Appodeal.getRewardParameters(placement).first);
//                        vals.put("currency", Appodeal.getRewardParameters(placement).second);
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, vals));
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_AGE)) {
//            final int age = args.getInt(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    getUserSettings().setAge(age);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_GENDER)) {
//            final String gender = args.getString(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (gender.equals("other".toLowerCase())) {
//                        getUserSettings().setGender(UserSettings.Gender.OTHER);
//                    } else if (gender.equals("female".toLowerCase())) {
//                        getUserSettings().setGender(UserSettings.Gender.FEMALE);
//                    } else if (gender.equals("male".toLowerCase())) {
//                        getUserSettings().setGender(UserSettings.Gender.MALE);
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_USER_ID)) {
//            final String userId = args.getString(0);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    getUserSettings().setUserId(userId);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_NATIVE_CALLBACKS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        nativeCallbacks = callback;
//                        Appodeal.setNativeCallbacks(nativeListener);
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_INIT);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        callback.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_INTERSTITIAL_CALLBACKS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        interstitialCallbacks = callback;
//                        Appodeal.setInterstitialCallbacks(interstitialListener);
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_INIT);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        callback.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_NON_SKIPPABLE_VIDEO_CALLBACKS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        nonSkippableCallbacks = callback;
//                        Appodeal.setNonSkippableVideoCallbacks(nonSkippableVideoListener);
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_INIT);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        callback.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_REWARDED_CALLBACKS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        rewardedCallbacks = callback;
//                        Appodeal.setRewardedVideoCallbacks(rewardedVideoListener);
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_INIT);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        callback.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_BANNER_CALLBACKS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        bannerCallbacks = callback;
//                        Appodeal.setBannerCallbacks(bannerListener);
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_INIT);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        callback.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_GET_NATIVE_ADS)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        final int amount = Appodeal.getAvailableNativeAdsCount();
//                        JSONObject vals = new JSONObject();
//
//                        List<NativeAd> nativeAds = Appodeal.getNativeAds(amount);
//                        if (!nativeAds.isEmpty()) {
//                            //HashMap<String, Map<String, String>> returnAds = new HashMap<String, Map<String, String>>();
//                            JSONObject returnAds = new JSONObject();
//                            for (NativeAd ad : nativeAds) {
//                                Map<String, String> adObject = new HashMap<String, String>();
//                                adObject.put("title", ad.getTitle());
//                                adObject.put("callToAction", ad.getCallToAction());
//                                adObject.put("description", ad.getDescription());
//                                adObject.put("rating", Float.toString(ad.getRating()));
//                                adObject.put("ageRestrictions", ad.getAgeRestrictions());
//                                adObject.put("adProvider", ad.getAdProvider());
//                                if (ad.getProviderView(cordova.getActivity()) != null) {
//                                    adObject.put("hasProviderView", "true");
//                                } else {
//                                    adObject.put("hasProviderView", "false");
//                                }
//                                adObject.put("containsVideo", Boolean.toString(ad.containsVideo()));
//                                adObject.put("isPrecache", Boolean.toString(ad.isPrecache()));
//                                adObject.put("predictedEcpm", Double.toString(ad.getPredictedEcpm()));
//
//                                returnAds.put(ad.toString(), new JSONObject(adObject));
//                            }
//
//                            vals.put("nativeAds", returnAds);
//                        }
//                        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, vals));
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_SET_NATIVEAD_POSITION)) {
//            final float x = (float) args.getDouble(0);
//            final float y = (float) args.getDouble(1);
//            final float w = (float) args.getDouble(2);
//            final float h = (float) args.getDouble(3);
//            final float tabH = (float) args.getDouble(4);
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    setNativeAdPosition(x, y, w, h, tabH);
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_HIDE_NATIVEAD)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    boolean isShowing = false;
//                    if (nativeAdPlaceholder != null) {
//                        final NativeAdViewNewsFeed ad = getViewGroup(-1).findViewById(NATIVE_AD_ID);
//                        if (ad != null) {
//                            nativeAdPlaceholder.ad = ad;
//                            ad.setVisibility(View.INVISIBLE);
//                            nativeAdPlaceholder.isShowing = false;
//                        }
//                        isShowing = nativeAdPlaceholder.isShowing;
//                    }
//                    callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, !isShowing));
//                }
//            });
//            return true;
//        }
//
//        if (action.equals(ACTION_REVEAL_HIDDEN_NATIVEAD)) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    boolean isShowing = false;
//                    if (nativeAdPlaceholder != null) {
//                        if (!nativeAdPlaceholder.isShowing) {
//                            showNativeAd();
//                        } else {
//                            final NativeAdViewNewsFeed ad = getViewGroup(-1).findViewById(NATIVE_AD_ID);
//                            if (ad != null) {
//                                nativeAdPlaceholder.ad = ad;
//                                ad.setVisibility(View.VISIBLE);
//                                nativeAdPlaceholder.isShowing = true;
//                            }
//                        }
//                        isShowing = nativeAdPlaceholder.isShowing;
//                    }
//                    callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, isShowing));
//                }
//            });
//            return true;
//        }

        return false;
    }

    //
//    private NativeCallbacks nativeListener = new NativeCallbacks() {
//
//        @Override
//        public void onNativeShown(NativeAd nativeAd) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        if (nativeAdPlaceholder != null) { nativeAdPlaceholder.isShowing = true; }
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOWN);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNativeLoaded() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_LOADED);
//                        vals.put("nativeAdCount", Appodeal.getAvailableNativeAdsCount());
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNativeFailedToLoad() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        /* @Override
//        public void onInterstitialClosed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLOSED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        } */
//
//        @Override
//        public void onNativeExpired() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_EXPIRED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNativeClicked(NativeAd nativeAd) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLICKED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNativeShowFailed(NativeAd nativeAd) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOW_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nativeCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//    };
//
//    private InterstitialCallbacks interstitialListener = new InterstitialCallbacks() {
//
//        @Override
//        public void onInterstitialShown() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOWN);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialLoaded(final boolean arg0) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_LOADED);
//                        vals.put("isPrecache", arg0);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialFailedToLoad() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialClosed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLOSED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialExpired() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_EXPIRED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialClicked() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLICKED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onInterstitialShowFailed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOW_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        interstitialCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//    };
//
//    private NonSkippableVideoCallbacks nonSkippableVideoListener = new NonSkippableVideoCallbacks() {
//
//        @Override
//        public void onNonSkippableVideoClosed(final boolean finished) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLOSED);
//                        vals.put("finished", finished);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoExpired() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_EXPIRED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoFailedToLoad() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoFinished() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FINISHED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoLoaded(boolean loaded) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_LOADED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoShown() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOWN);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onNonSkippableVideoShowFailed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOW_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        nonSkippableCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//    };
//
//    private RewardedVideoCallbacks rewardedVideoListener = new RewardedVideoCallbacks() {
//
//        @Override
//        public void onRewardedVideoClosed(boolean finished) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLOSED);
//                        vals.put("finished", finished);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoExpired() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_EXPIRED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoFailedToLoad() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoFinished(double amount, String name) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FINISHED);
//                        vals.put("amount", amount);
//                        vals.put("name", name);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoLoaded(boolean loaded) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_LOADED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoShown() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOWN);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoClicked() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLICKED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onRewardedVideoShowFailed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOW_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        rewardedCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//    };
//
//    private BannerCallbacks bannerListener = new BannerCallbacks() {
//
//        @Override
//        public void onBannerClicked() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_CLICKED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onBannerExpired() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_EXPIRED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onBannerFailedToLoad() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onBannerLoaded(final int height, final boolean isPrecache) {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_LOADED);
//                        vals.put("height", height);
//                        vals.put("isPrecache", isPrecache);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onBannerShown() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOWN);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//
//        @Override
//        public void onBannerShowFailed() {
//            cordova.getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject vals = new JSONObject();
//                        vals.put("event", CALLBACK_SHOW_FAILED);
//                        PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
//                        result.setKeepCallback(true);
//                        bannerCallbacks.sendPluginResult(result);
//                    } catch (JSONException e) {
//                    }
//                }
//            });
//        }
//    };
//
    private ViewGroup getViewGroup(int child) {
        ViewGroup vg = (ViewGroup) this.cordova.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if (child != -1)
            vg = (ViewGroup) vg.getChildAt(child); // child == 0 is view from setContentView
        return vg;
    }

    //
    private boolean showBanner(int adType, String placement) {

        if (placement == null)
            return Appodeal.show(cordova.getActivity(), adType);

        return Appodeal.show(cordova.getActivity(), adType, placement);
    }

//
//    private boolean showNativeAd() {
//        NativeAdViewNewsFeed nativeAdView;
//        ViewGroup rootView = getViewGroup(-1);
//
//        if (nativeAdPlaceholder == null) {
//            nativeAdPlaceholder = new NativeAdPlaceholder();
//            nativeAdPlaceholder.ad = null;
//        }
//
//        if (nativeAdPlaceholder.ad != null) {
//            nativeAdView = nativeAdPlaceholder.ad;
//            nativeAdView.setVisibility(View.VISIBLE);
//            nativeAdPlaceholder.isShowing = true;
//        } else {
//            if (Appodeal.isLoaded(Appodeal.NATIVE)) {
//                List<NativeAd> nativeAds = Appodeal.getNativeAds(1);
//                nativeAdView = new NativeAdViewNewsFeed(cordova.getActivity(), nativeAds.get(0));
//                nativeAdView.setId(NATIVE_AD_ID);
//                nativeAdView.setBackgroundColor(Color.LTGRAY);
//                nativeAdView.setCallToActionColor("black");
//                List<TextView> list = getAllChildTextViews(nativeAdView);
//                for (TextView v : list) {
//                    v.setTextColor(Color.BLACK);
//                }
//                nativeAdView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                rootView.addView(nativeAdView, 1);
//                nativeAdPlaceholder.ad = nativeAdView;
//                nativeAdPlaceholder.isShowing = true;
//                nativeAdPlaceholder.contentHdp = rootView.getHeight();
//            } else {
//                return false;
//            }
//        }
//        setNativeAdPosition(
//            nativeAdPlaceholder.x, nativeAdPlaceholder.y, nativeAdPlaceholder.w,
//            nativeAdPlaceholder.h, nativeAdPlaceholder.contentHdp
//        );
//        rootView.requestLayout();
//        return true;
//    }
//
//    private List<TextView> getAllChildTextViews(NativeAdViewNewsFeed view) {
//        List<TextView> visited = new java.util.ArrayList<>();
//        List<View> toVisit = new java.util.ArrayList<>();
//        toVisit.add(view);
//
//        while(!toVisit.isEmpty()) {
//            View child = toVisit.remove(0);
//            if (child instanceof TextView) {
//                CharSequence text = ((TextView)child).getText();
//                if (text != null) {
//                    if (text.toString().trim().equals("Ad") || text.toString().trim().equals("Sponsored")) {
//                        View parent = (View) child.getParent();
//                        parent.setX(parent.getX() - 30); // shift the "Ad" box to the left
//                    }
//                }
//                visited.add((TextView)child);
//            }
//            if (child instanceof RatingBar) {
//                android.graphics.drawable.Drawable stars = ((RatingBar) child).getProgressDrawable();
//                stars.setColorFilter(Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_ATOP);
//            }
//            if (!(child instanceof ViewGroup)) continue;
//            ViewGroup group = (ViewGroup) child;
//            final int childCount = group.getChildCount();
//            for (int i=0; i<childCount; i++)
//                toVisit.add(group.getChildAt(i));
//        }
//
//        return visited;
//    }
//
//    private boolean setNativeAdPosition(float x, float y, float w, float h, float tabH) {
//        if (nativeAdPlaceholder == null) {
//            nativeAdPlaceholder = new NativeAdPlaceholder();
//            nativeAdPlaceholder.ad = null;
//        }
//
//        NativeAdViewNewsFeed nativeAdv;
//        // try to get the nativeAd view that got created before and is showing.
//        if (nativeAdPlaceholder.ad == null) {
//            nativeAdv = getViewGroup(-1).findViewById(NATIVE_AD_ID);
//            nativeAdPlaceholder.ad = nativeAdv;
//        } else {
//            nativeAdv = nativeAdPlaceholder.ad;
//        }
//
//        nativeAdPlaceholder.x = x;
//        nativeAdPlaceholder.y = y;
//        nativeAdPlaceholder.w = w;
//        nativeAdPlaceholder.h = h;
//
//        if (nativeAdv != null) {
//            DisplayMetrics metrics = cordova.getActivity().getResources().getDisplayMetrics();
//            int xdp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, metrics);
//            int ydp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, y, metrics);
//            int wdp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, metrics);
//            int hdp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, h, metrics);
//            int headerdp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, metrics);
//            int tabMenudp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabH, metrics);
//            ViewGroup contentView = getViewGroup(-1);
//            nativeAdPlaceholder.contentHdp = contentView.getHeight();
//
//            nativeAdv.setLeft(xdp);
//            nativeAdv.setRight(xdp + wdp);
//
//            if (ydp <= headerdp) {
//                nativeAdv.setTop(headerdp);
//                nativeAdv.setBottom(ydp + hdp);
//            } else {
//                // this works even with a TOP banner showing. Not considering a BOTTOM banner in the calculation.
//                if ((ydp + hdp) >= (nativeAdPlaceholder.contentHdp - tabMenudp)) {
//                    nativeAdv.setTop(ydp);
//                    nativeAdv.setBottom((int) (nativeAdPlaceholder.contentHdp - tabMenudp));
//                } else {
//                    nativeAdv.setTop(ydp);
//                    nativeAdv.setBottom(ydp + hdp);
//                }
//            }
//        }
//        return true;
//    }

    private static void log(String message) {
//        if (Appodeal.getLogLevel().equals(Log.LogLevel.debug) || Appodeal.getLogLevel().equals(Log.LogLevel.verbose)) {
        android.util.Log.d(TAG, message);
//        }
    }
}
