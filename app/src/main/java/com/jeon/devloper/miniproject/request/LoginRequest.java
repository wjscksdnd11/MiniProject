package com.jeon.devloper.miniproject.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jeon.devloper.miniproject.Data.NetworkResult;
import com.jeon.devloper.miniproject.Data.User;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016-08-09.
 */
public class LoginRequest extends AbstractRequest<NetworkResult<User>> {
    Request request;
    public LoginRequest(Context context,String email, String password, String regId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("signin")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("registrationId", regId)
                .build();

        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }
}
