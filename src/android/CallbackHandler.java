package com.appodeal.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class CallbackHandler {
    static final String CALLBACK_INIT = "onInit";
    static final String CALLBACK_LOADED = "onLoaded";
    static final String CALLBACK_LOAD_FAILED = "onLoadFailed";
    static final String CALLBACK_CLICKED = "onClick";
    static final String CALLBACK_SHOWN = "onShown";
    static final String CALLBACK_CLOSED = "onClosed";
    static final String CALLBACK_FINISHED = "onFinished";
    static final String CALLBACK_EXPIRED = "onExpired";
    static final String CALLBACK_SHOW_FAILED = "onShowFailed";

    protected final CDVAppodeal plugin;

    protected final CallbackContext callback;

    public CallbackHandler(CDVAppodeal plugin, CallbackContext callback) {
        this.plugin = plugin;
        this.callback = callback;

        sendEventResult(CALLBACK_INIT);
    }

    protected void sendEventResult(String eventName) {
        sendEventResult(eventName, new JSONObject());
    }

    protected void sendEventResult(String eventName, JSONObject vals) {
        try {
            vals.put("event", eventName);
            PluginResult result = new PluginResult(PluginResult.Status.OK, vals);
            result.setKeepCallback(true);
            callback.sendPluginResult(result);
        } catch (JSONException ignored) {
        }
    }
}
