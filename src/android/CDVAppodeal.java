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

    private static ConsentForm consentForm;


    private boolean isInitialized = false;

    @Override
    public boolean execute(String actionName, JSONArray args, CallbackContext callback) throws JSONException {

        log("execute action:" + actionName);
        Action action = Action.getAction(actionName);

        if (action == null) {
            log("action not found");
            return false;
        }

        if (action == Action.initialize)
            return actionInitialize(args, callback);

        if (action == Action.isInitialized)
            sendPluginResOK(callback, isInitialized);

        if (action == Action.show)
            return actionShow(args, callback);

        if (action == Action.hide)
            Appodeal.hide(cordova.getActivity(), args.getInt(0));

        if (action == Action.showTestScreen)
            Appodeal.startTestActivity(cordova.getActivity());

        if (action == Action.setTesting)
            Appodeal.setTesting(args.getBoolean(0));

        if (action == Action.isLoaded)
            sendPluginResOK(callback, Appodeal.isLoaded(args.getInt(0)));

        if (action == Action.cache)
            Appodeal.cache(cordova.getActivity(), args.getInt(0));

        if (action == Action.destroy)
            Appodeal.destroy(args.getInt(0));

        if (action == Action.setAutoCache)
            Appodeal.setAutoCache(args.getInt(0), args.getBoolean(1));

        if (action == Action.isPrecache)
            sendPluginResOK(callback, Appodeal.isPrecache(args.getInt(0)));

        if (action == Action.setBannerAnimation)
            Appodeal.setBannerAnimation(args.getBoolean(0));

        if (action == Action.setSmartBanners)
            Appodeal.setSmartBanners(args.getBoolean(0));

        if (action == Action.set728x90Banners)
            Appodeal.set728x90Banners(args.getBoolean(0));

        if (action == Action.setLogLevel)
            actionSetLogLevel(args, callback);

        if (action == Action.setChildDirectedTreatment)
            Appodeal.setChildDirectedTreatment(args.getBoolean(0));

        if (action == Action.disableNetwork)
            Appodeal.disableNetwork(args.getString(0), args.getInt(1));

        if (action == Action.setTriggerOnLoadedOnPrecache)
            Appodeal.setTriggerOnLoadedOnPrecache(args.getInt(0), args.getBoolean(1));

        if (action == Action.muteVideosIfCallsMuted)
            Appodeal.muteVideosIfCallsMuted(args.getBoolean(0));

        if (action == Action.getVersion)
            sendPluginResOK(callback, Appodeal.getVersion());

        if (action == Action.canShow)
            actionCanShow(args, callback);

        if (action == Action.setCustomIntegerRule)
            Appodeal.setExtraData(args.getString(0), args.getInt(1));

        if (action == Action.setCustomBooleanRule)
            Appodeal.setExtraData(args.getString(0), args.getBoolean(1));

        if (action == Action.setCustomDoubleRule)
            Appodeal.setExtraData(args.getString(0), args.getDouble(1));

        if (action == Action.setCustomStringRule)
            Appodeal.setExtraData(args.getString(0), args.getString(1));

        if (action == Action.getPredictedEcpm)
            sendPluginResOK(callback, (float) Appodeal.getPredictedEcpm(args.getInt(0)));

        if (action == Action.getRewardParameters)
            actionGetRewardParameters(args, callback);

        if (action == Action.setUserId)
            Appodeal.setUserId(args.getString(0));

        if (action == Action.setBannerCallbacks) {
            Appodeal.setBannerCallbacks(new BannerCallbacksHandler(this, callback));
            return true;
        }

        if (action == Action.setInterstitialCallbacks) {
            Appodeal.setInterstitialCallbacks(new InterstitialCallbacksHandler(this, callback));
            return true;
        }

        if (action == Action.setRewardedVideoCallbacks) {
            Appodeal.setRewardedVideoCallbacks(new RewardedVideoCallbacksHandler(this, callback));
            return true;
        }


        if (!callback.isFinished()) sendPluginResOK(callback);
        return true;
    }

    private boolean actionInitialize(JSONArray args, final CallbackContext callback) throws JSONException {
        final String appKey = args.getString(0);
        final int adType = args.getInt(1);

        log("Initializing SDK");
        Appodeal.initialize(cordova.getActivity(), appKey, adType, errors -> {
            isInitialized = true;
            log("SDK initialized");
            sendPluginResOK(callback);
        });

        return true;
    }

    private boolean actionShow(JSONArray args, CallbackContext callback) throws JSONException {
        final int adType = args.getInt(0);
        final String placement = args.optString(1, null);

        boolean res;
        if (placement == null)
            res = Appodeal.show(cordova.getActivity(), adType);
        else
            res = Appodeal.show(cordova.getActivity(), adType, placement);

        sendPluginResOK(callback, res);
        return true;
    }

    private void actionSetLogLevel(JSONArray args, CallbackContext callback) throws JSONException {
        try {
            Log.LogLevel logLevel = Log.LogLevel.valueOf(args.getString(0));
            Appodeal.setLogLevel(logLevel);
            sendPluginResOK(callback, true);
        } catch (IllegalArgumentException e) {
            sendPluginResOK(callback, false);
        }
    }

    private void actionCanShow(JSONArray args, CallbackContext callback) throws JSONException {
        int adType = args.getInt(0);

        boolean result;
        try {
            String placement = args.getString(1);
            result = Appodeal.canShow(adType, placement);
        } catch (JSONException e) {
            result = Appodeal.canShow(adType);
        }

        sendPluginResOK(callback, result);
    }

    private void actionGetRewardParameters(JSONArray args, CallbackContext callback) throws JSONException {
        String placement;

        try {
            placement = args.getString(0);
        } catch (JSONException e) {
            placement = null;
        }

        Reward reward;
        if (placement == null)
            reward = Appodeal.getReward();
        else
            reward = Appodeal.getReward(placement);

        JSONObject vals = new JSONObject();
        vals.put("amount", reward.getAmount());
        vals.put("currency", reward.getCurrency());

        sendPluginResOK(callback, vals);
    }

    private static void log(String message) {
        if (Appodeal.getLogLevel().equals(Log.LogLevel.debug) || Appodeal.getLogLevel().equals(Log.LogLevel.verbose)) {
            android.util.Log.d(TAG, message);
        }
    }

    private void sendPluginResOK(CallbackContext callback) {
        callback.sendPluginResult(new PluginResult(PluginResult.Status.OK));
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
