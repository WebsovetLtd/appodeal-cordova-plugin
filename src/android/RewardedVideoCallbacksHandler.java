package com.appodeal.plugin;

import com.appodeal.ads.RewardedVideoCallbacks;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

public class RewardedVideoCallbacksHandler extends CallbackHandler implements RewardedVideoCallbacks {

    public RewardedVideoCallbacksHandler(CDVAppodeal plugin, CallbackContext callback) {
        super(plugin, callback);
    }

    @Override
    public void onRewardedVideoClicked() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_CLICKED));
    }

    @Override
    public void onRewardedVideoExpired() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_EXPIRED));
    }

    @Override
    public void onRewardedVideoFailedToLoad() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_LOAD_FAILED));
    }

    @Override
    public void onRewardedVideoLoaded(final boolean isPrecache) {
        plugin.runOnThreadPool(() -> {
            try {
                JSONObject vals = new JSONObject();
                vals.put("isPrecache", isPrecache);
                sendEventResult(CALLBACK_LOADED, vals);
            } catch (JSONException ignored) {
            }
        });
    }

    @Override
    public void onRewardedVideoShown() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_SHOWN));
    }

    @Override
    public void onRewardedVideoShowFailed() {
        plugin.runOnThreadPool(() -> sendEventResult(CALLBACK_SHOW_FAILED));
    }

    @Override
    public void onRewardedVideoClosed(boolean finished) {
        plugin.runOnThreadPool(() -> {
            try {
                JSONObject vals = new JSONObject();
                vals.put("finished", finished);
                sendEventResult(CALLBACK_CLOSED, vals);
            } catch (JSONException ignored) {
            }
        });
    }

    @Override
    public void onRewardedVideoFinished(double amount, String currency) {
        plugin.runOnThreadPool(() -> {
            try {
                JSONObject vals = new JSONObject();
                vals.put("amount", amount);
                vals.put("currency", currency);
                sendEventResult(CALLBACK_FINISHED, vals);
            } catch (JSONException ignored) {
            }
        });
    }
}
