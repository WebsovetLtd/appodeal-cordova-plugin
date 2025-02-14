package com.appodeal.plugin;

import com.appodeal.ads.InterstitialCallbacks;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

public class InterstitialCallbacksHandler extends CallbackHandler implements InterstitialCallbacks {

    public InterstitialCallbacksHandler(CDVAppodeal plugin, CallbackContext callback) {
        super(plugin, callback);
    }

    @Override
    public void onInterstitialClicked() {
        sendEventResult(CALLBACK_CLICKED);
    }

    @Override
    public void onInterstitialExpired() {
        sendEventResult(CALLBACK_EXPIRED);
    }

    @Override
    public void onInterstitialFailedToLoad() {
        sendEventResult(CALLBACK_LOAD_FAILED);
    }

    @Override
    public void onInterstitialLoaded(final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("isPrecache", isPrecache);
            sendEventResult(CALLBACK_LOADED, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onInterstitialShown() {
        sendEventResult(CALLBACK_SHOWN);
    }

    @Override
    public void onInterstitialShowFailed() {
        sendEventResult(CALLBACK_SHOW_FAILED);
    }

    @Override
    public void onInterstitialClosed() {
        sendEventResult(CALLBACK_CLOSED);
    }
}
