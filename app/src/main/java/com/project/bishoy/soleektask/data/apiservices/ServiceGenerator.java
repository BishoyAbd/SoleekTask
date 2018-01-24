package com.project.bishoy.soleektask.data.apiservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private static final String BASE_URL = "http://www.thejerb.com/jerb/public/api/";


    private static Retrofit retrofit;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(createOkHttpInterceptor())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {

        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient createOkHttpInterceptor() {


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

//        okHttpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
////                Timber.d("header before removing -->"+chain.proceed(chain.request()).headers().toString());
//////
//////                request = request.newBuilder()
//////                        .removeHeader("Content-Type")
//////                        .build();
//////                Response response = chain.proceed(request);
//////                Timber.d(response.header("Content-Type"));
////
////                return chain.proceed(request);
//            }
//        });

        return okHttpClientBuilder.build();


    }


    public static RequestBody createFromString(String string) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text"), string);
        return requestBody;
    }


}