package com.wmtcore.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

public class OkHttpUtils {

    public static final String TAG = OkHttpUtils.class.getSimpleName();

    private static OkHttpClient mOkHttpClient;
    private static final int TIMEOUT = 25;

    public static void init(Context context, boolean useSSL) {

        if (mOkHttpClient != null)
            return;

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 20, TimeUnit.SECONDS))
                .retryOnConnectionFailure(false)
                .cache(cache)
                .build();

        if (useSSL)
            mOkHttpClient.sslSocketFactory();
    }

    public static OkHttpClient getClient() {
        if (mOkHttpClient == null)
            throw new NullPointerException("You must initialize OkHttpClient before using it");
        return mOkHttpClient;
    }

    public static void cancel(Object tag) {
        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) call.cancel();
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) call.cancel();
        }
    }

    public static void cancelContainsTag(Object tag) {
        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (call.request().tag().toString().contains(tag.toString())) call.cancel();
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (call.request().tag().toString().contains(tag.toString())) call.cancel();
        }
    }
}
