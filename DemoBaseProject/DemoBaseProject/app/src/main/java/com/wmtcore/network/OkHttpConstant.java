package com.wmtcore.network;

import okhttp3.MediaType;

public class OkHttpConstant {

	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
    public static final MediaType MEDIA_TYPE_MULTIPART = MediaType.parse("multipart/form-data");

    public static final int REQUEST_JSON = 1;
    public static final int REQUEST_FORM = 2;
    public static final int REQUEST_MULTIPART = 3;

	public static class Response{
		public static final int HTTP_200 = 200;
		public static final int HTTP_400 = 400;
		public static final int HTTP_401 = 401;
		public static final int HTTP_402 = 402;
		public static final int HTTP_404 = 404;
		public static final int HTTP_500 = 500;
	}
}
