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
        sendEventResult(Event.onClick);
    }

    @Override
    public void onInterstitialExpired() {
        sendEventResult(Event.onExpired);
    }

    @Override
    public void onInterstitialFailedToLoad() {
        sendEventResult(Event.onLoadFailed);
    }

    @Override
    public void onInterstitialLoaded(final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("isPrecache", isPrecache);
            sendEventResult(Event.onLoaded, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onInterstitialShown() {
        sendEventResult(Event.onShown);
    }

    @Override
    public void onInterstitialShowFailed() {
        sendEventResult(Event.onShowFailed);
    }

    @Override
    public void onInterstitialClosed() {
        sendEventResult(Event.onClosed);
    }
}
