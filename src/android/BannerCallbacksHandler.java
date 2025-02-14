package com.appodeal.plugin;

import com.appodeal.ads.BannerCallbacks;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerCallbacksHandler extends CallbackHandler implements BannerCallbacks {

    public BannerCallbacksHandler(CDVAppodeal plugin, CallbackContext callback) {
        super(plugin, callback);
    }

    @Override
    public void onBannerClicked() {
        sendEventResult(CALLBACK_CLICKED);
    }

    @Override
    public void onBannerExpired() {
        sendEventResult(CALLBACK_EXPIRED);
    }

    @Override
    public void onBannerFailedToLoad() {
        sendEventResult(CALLBACK_LOAD_FAILED);
    }

    @Override
    public void onBannerLoaded(final int height, final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("height", height);
            vals.put("isPrecache", isPrecache);
            sendEventResult(CALLBACK_LOADED, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onBannerShown() {
        sendEventResult(CALLBACK_SHOWN);
    }

    @Override
    public void onBannerShowFailed() {
        sendEventResult(CALLBACK_SHOW_FAILED);
    }
}
