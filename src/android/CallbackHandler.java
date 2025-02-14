package com.appodeal.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class CallbackHandler {
    protected enum Event {
        onInit,
        onLoaded,
        onLoadFailed,
        onClick,
        onShown,
        onClosed,
        onFinished,
        onExpired,
        onShowFailed
    }

    protected final CDVAppodeal plugin;

    protected final CallbackContext callback;

    public CallbackHandler(CDVAppodeal plugin, CallbackContext callback) {
        this.plugin = plugin;
        this.callback = callback;

        sendEventResult(Event.onInit);
    }

    protected void sendEventResult(Event event) {
        sendEventResult(event, new JSONObject());
    }

    protected void sendEventResult(Event event, JSONObject vals) {
        try {
            vals.put("event", event.toString());
            PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
            result.setKeepCallback(true);
            callback.sendPluginResult(result);
        } catch (JSONException ignored) {
        }
    }
}
