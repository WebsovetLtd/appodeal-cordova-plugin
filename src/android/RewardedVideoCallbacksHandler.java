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
        sendEventResult(CALLBACK_CLICKED);
    }

    @Override
    public void onRewardedVideoExpired() {
        sendEventResult(CALLBACK_EXPIRED);
    }

    @Override
    public void onRewardedVideoFailedToLoad() {
        sendEventResult(CALLBACK_LOAD_FAILED);
    }

    @Override
    public void onRewardedVideoLoaded(final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("isPrecache", isPrecache);
            sendEventResult(CALLBACK_LOADED, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onRewardedVideoShown() {
        sendEventResult(CALLBACK_SHOWN);
    }

    @Override
    public void onRewardedVideoShowFailed() {
        sendEventResult(CALLBACK_SHOW_FAILED);
    }

    @Override
    public void onRewardedVideoClosed(boolean finished) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("finished", finished);
            sendEventResult(CALLBACK_CLOSED, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onRewardedVideoFinished(double amount, String currency) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("amount", amount);
            vals.put("currency", currency);
            sendEventResult(CALLBACK_FINISHED, vals);
        } catch (JSONException ignored) {
        }
    }
}
