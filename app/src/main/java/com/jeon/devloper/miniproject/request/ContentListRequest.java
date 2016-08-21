package com.jeon.devloper.miniproject.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jeon.devloper.miniproject.Data.ContentData;
import com.jeon.devloper.miniproject.Data.NetworkResult;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Administrator on 2016-08-12.
 */
public class ContentListRequest extends AbstractRequest<NetworkResult<List<ContentData>>> {
    Request request;
    public ContentListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("contents")
                .build();
        request = new Request.Builder()
                .url(url)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<ContentData>>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
