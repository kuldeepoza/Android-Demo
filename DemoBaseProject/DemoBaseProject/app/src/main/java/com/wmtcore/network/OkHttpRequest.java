package com.wmtcore.network;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.wmtcore.network.listener.OkHttpCallback;
import com.wmtcore.util.BaseUtils;
import com.wmtcore.util.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpRequest implements Callback {

    public static final String TAG = OkHttpRequest.class.getSimpleName();

    private OkHttpCallback mOkHttpCallback;
    private Bundle mRequestExtra;
    private Activity mActivity;

    private String mLoadingMessage;
    private String mRequestTag;
    private int mRequestType;
    private int mRequestId;

    private Request.Builder mRequestBuilder;
    private RequestBody mRequestBody;
    private FormBody.Builder formBodyBuilder;
    private MultipartBody.Builder multipartBodyBuilder;

    public OkHttpRequest() {

    }

    public void doRequest() {
        switch (mRequestType) {
            case OkHttpConstant.REQUEST_JSON:
                break;
            case OkHttpConstant.REQUEST_FORM:
                mRequestBuilder.post(formBodyBuilder.build());
                break;
       /*     case OkHttpConstant.REQUEST_BODY:
                mRequestBuilder.post(mRequestBody);
                break;*/
            case OkHttpConstant.REQUEST_MULTIPART:
                mRequestBuilder.post(multipartBodyBuilder.build())
                        .addHeader("content-type", "multipart/form-data;");
                break;
        }
        try {
            if (!TextUtils.isEmpty(mLoadingMessage))
                BaseUtils.showProgressDialog(mActivity, mLoadingMessage);

            OkHttpUtils.getClient().newCall(mRequestBuilder.build()).enqueue(this);
        } catch (Exception e) {
            Debug.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (!TextUtils.isEmpty(mLoadingMessage))
            BaseUtils.cancelProgressDialog();

        if (mOkHttpCallback == null)
            throw new NullPointerException("You have not set the callback");

        try {
            JSONObject joResponse = new JSONObject(response.body().string());
            sendSuccessResponse(joResponse, mRequestId, mRequestExtra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        if (!TextUtils.isEmpty(mLoadingMessage))
            BaseUtils.cancelProgressDialog();

        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mOkHttpCallback.onError(e, mRequestId, mRequestExtra);
                }
            });
        } else {
            mOkHttpCallback.onError(e, mRequestId, mRequestExtra);
        }
    }

    private void sendSuccessResponse(final JSONObject joResponse, final int requestId,
                                     final Bundle requestExtra) {
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mOkHttpCallback.onResponse(joResponse, requestId, requestExtra);
                }
            });
        } else {
            mOkHttpCallback.onResponse(joResponse, requestId, requestExtra);
        }
    }

    public static class Builder {

        OkHttpRequest okHttpRequest;

        public Builder() {
            okHttpRequest = new OkHttpRequest();
            okHttpRequest.mRequestBuilder = new Request.Builder();
        }

        public Builder setRequestId(int requestId) {
            okHttpRequest.mRequestId = requestId;
            return this;
        }

        public Builder setUrl(String url) {
            okHttpRequest.mRequestBuilder.url(url);
            return this;
        }

        public Builder setJsonParams(JSONObject jsonParams) {
            okHttpRequest.mRequestType = OkHttpConstant.REQUEST_JSON;
            okHttpRequest.mRequestBuilder.post(RequestBody.create(OkHttpConstant.MEDIA_TYPE_JSON, jsonParams.toString()));
            return this;
        }

        public Builder addParams(String key, String value) {
            okHttpRequest.mRequestType = OkHttpConstant.REQUEST_FORM;

            if (okHttpRequest.formBodyBuilder == null)
                okHttpRequest.formBodyBuilder = new FormBody.Builder();

            okHttpRequest.formBodyBuilder.add(key, value);

            return this;
        }

        public Builder addMultiPartParams(String key, String value) {
            okHttpRequest.mRequestType = OkHttpConstant.REQUEST_MULTIPART;

            if (okHttpRequest.multipartBodyBuilder == null)
                okHttpRequest.multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            okHttpRequest.multipartBodyBuilder.addFormDataPart(key, value);

            return this;
        }

        public Builder addMultiPartFiles(String key, String filename, MediaType mediaType) {
            okHttpRequest.mRequestType = OkHttpConstant.REQUEST_MULTIPART;

            if (okHttpRequest.multipartBodyBuilder == null)
                okHttpRequest.multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            File fileUpload = new File(filename);

            if (fileUpload.exists())
                okHttpRequest.multipartBodyBuilder.addFormDataPart(key, filename,
                        RequestBody.create(mediaType, fileUpload));
            return this;
        }

        public Builder addRequestBody(MediaType mediaType, String value) {
        //    okHttpRequest.mRequestType = OkHttpConstant.REQUEST_BODY;
            okHttpRequest.mRequestBody = RequestBody.create(mediaType, value);
            return this;
        }

        public Builder setExtra(Bundle requestExtra) {
            okHttpRequest.mRequestExtra = requestExtra;
            return this;
        }

        public Builder setLoadingMessage(Activity activity, String message) {
            okHttpRequest.mActivity = activity;
            okHttpRequest.mLoadingMessage = message;
            return this;
        }

        public Builder setTag(String tag) {
            okHttpRequest.mRequestTag = tag;
            okHttpRequest.mRequestBuilder.tag(okHttpRequest.mRequestTag);
            return this;
        }

        public Builder setCallback(OkHttpCallback okHttpCallback) {
            okHttpRequest.mOkHttpCallback = okHttpCallback;
            return this;
        }

        public Builder setActivity(Activity activity) {
            okHttpRequest.mActivity = activity;
            return this;
        }

        public Builder cacheOnlyIfCached() {
            okHttpRequest.mRequestBuilder.cacheControl(new CacheControl.Builder().onlyIfCached().build());
            return this;
        }

        public Builder cacheMaxStale(int milliSeconds) {
            okHttpRequest.mRequestBuilder.cacheControl(new CacheControl.Builder().maxStale(milliSeconds, TimeUnit.MILLISECONDS).build());
            return this;
        }

        public Builder noCache() {
            okHttpRequest.mRequestBuilder.cacheControl(new CacheControl.Builder().noCache().build());
            return this;
        }

        public Builder addHeader(String key, String value) {
            okHttpRequest.mRequestBuilder.addHeader(key, value);
            return this;
        }

        public OkHttpRequest build() {
            assert okHttpRequest != null;
            return okHttpRequest;
        }
    }
}
