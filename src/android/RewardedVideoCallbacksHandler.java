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
        sendEventResult(Event.onClick);
    }

    @Override
    public void onRewardedVideoExpired() {
        sendEventResult(Event.onExpired);
    }

    @Override
    public void onRewardedVideoFailedToLoad() {
        sendEventResult(Event.onLoadFailed);
    }

    @Override
    public void onRewardedVideoLoaded(final boolean isPrecache) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("isPrecache", isPrecache);
            sendEventResult(Event.onLoaded, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onRewardedVideoShown() {
        sendEventResult(Event.onShown);
    }

    @Override
    public void onRewardedVideoShowFailed() {
        sendEventResult(Event.onShowFailed);
    }

    @Override
    public void onRewardedVideoClosed(boolean finished) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("finished", finished);
            sendEventResult(Event.onClosed, vals);
        } catch (JSONException ignored) {
        }
    }

    @Override
    public void onRewardedVideoFinished(double amount, String currency) {
        try {
            JSONObject vals = new JSONObject();
            vals.put("amount", amount);
            vals.put("currency", currency);
            sendEventResult(Event.onFinished, vals);
        } catch (JSONException ignored) {
        }
    }
}
