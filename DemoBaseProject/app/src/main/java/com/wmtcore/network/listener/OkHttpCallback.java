package com.wmtcore.network.listener;

import android.os.Bundle;

import org.json.JSONObject;

public interface OkHttpCallback {

    void onResponse(JSONObject joResponse, int requestId, Bundle extra);

    void onError(Throwable error, int requestId, Bundle extra);
}
