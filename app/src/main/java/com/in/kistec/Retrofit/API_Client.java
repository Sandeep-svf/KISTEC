package com.in.kistec.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.kistec.Retrofit.RetroifitErrorResponse.LenientGsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class API_Client {

   /* public static final String BASE_URL = "https://itdevelopmentservices.com/creditRating/api/";
    public static final String BASE_IMAGE = "https://itdevelopmentservices.com/creditRating/upload/images/";
    public static final String BASE_IMAGE_DOC = "https://itdevelopmentservices.com/creditRating/upload/nationalid/";*/


    public static final String BASE_URL = "https://kistec.org/api/";
    public static final String BASE_IMAGE = "https://kistec.org/upload/images/";
    public static final String BASE_IMAGE_DOC = "https://kistec.org/upload/nationalid/";

    private static Retrofit retrofit = null;

    private static Api api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Api getClient() {
        if (api == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api api = retrofit.create(Api.class);
            return api;
        } else
            return api;
    }

//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .build();
//    private static Retrofit retrofit = null;
//    public static Retrofit getClient()
//    {
//        if (retrofit==null)
//        {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(ServiceUrlList.RootIpUrl)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }
}


