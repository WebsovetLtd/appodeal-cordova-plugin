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
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_CLICKED));
    }

    @Override
    public void onBannerExpired() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_EXPIRED));
    }

    @Override
    public void onBannerFailedToLoad() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_FAILED));
    }

    @Override
    public void onBannerLoaded(final int height, final boolean isPrecache) {
        plugin.runOnThreadPool(() -> {
            try {
                JSONObject vals = new JSONObject();
                vals.put("height", height);
                vals.put("isPrecache", isPrecache);
                sendEventResult(CALLBACK_LOADED, vals);
            } catch (JSONException ignored) {
            }
        });
    }

    @Override
    public void onBannerShown() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_SHOWN));
    }

    @Override
    public void onBannerShowFailed() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_SHOW_FAILED));
    }
}
