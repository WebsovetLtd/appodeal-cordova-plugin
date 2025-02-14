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
        sendEventResult(Event.onClick);
    }

    @Override
    public void onBannerExpired() {
        sendEventResult(Event.onExpired);
    }

    @Override
    public void onBannerFailedToLoad() {
        sendEventResult(Event.onLoadFailed);
    }

    @Override
    public void onBannerLoaded(final int height, final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("height", height);
            vals.put("isPrecache", isPrecache);
            sendEventResult(Event.onLoaded, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onBannerShown() {
        sendEventResult(Event.onShown);
    }

    @Override
    public void onBannerShowFailed() {
        sendEventResult(Event.onShowFailed);
    }
}
