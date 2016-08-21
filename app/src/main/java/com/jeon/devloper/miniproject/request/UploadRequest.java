package com.jeon.devloper.miniproject.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jeon.devloper.miniproject.Data.ContentData;
import com.jeon.devloper.miniproject.Data.NetworkResult;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016-08-12.
 */
public class UploadRequest extends AbstractRequest<NetworkResult<ContentData>> {
    MediaType jpeg = MediaType.parse("image/jpeg");
    Request request;
    public UploadRequest(Context context, String content, File file) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("upload")
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", content);
        if (file != null) {
            builder.addFormDataPart("myFile", file.getName(),
                    RequestBody.create(jpeg, file));
        }
        RequestBody body = builder.build();
        request = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<ContentData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
