package com.in.kistec.Retrofit;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.kistec.Retrofit.RetroifitErrorResponse.LenientGsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://kistec.org/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

           /* Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();*/

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .disableHtmlEscaping()
                    .disableInnerClassSerialization()
                    .create();

          /*  OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.
                    addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            if (response.code() == 403) {
                                handleForbiddenResponse();
                            }
                            return response;
                        }
                    });*/

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mainss();
        }
        return retrofit;
    }

    public static void  mainss()
    {
        Log.e("Adminddd","   Boiss");
    }


}
