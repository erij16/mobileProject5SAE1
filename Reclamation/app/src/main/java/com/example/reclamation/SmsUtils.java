package com.example.reclamation;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import retrofit2.http.Path;

public class SmsUtils {
    private static final String BASE_URL = "https://api.twilio.com/2010-04-01/";
    private static TwilioApiService service = null;
    private static final String ACCOUNT_SID = "ACfdba742da56eb7066ad5318c3de7aa0b";
    private static final String AUTH_TOKEN = "2eb147a659ef976dd4d3642ceb7e4975";

    public interface TwilioApiService {
        @FormUrlEncoded
        @POST("Accounts/{AccountSid}/Messages.json")
        Call<Void> sendSms(@Path("AccountSid") String accountSid, @Field("From") String from, @Field("To") String to, @Field("Body") String body);
    }

    public static TwilioApiService getService() {
        if (service == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(ACCOUNT_SID, AUTH_TOKEN))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(TwilioApiService.class);
        }
        return service;
    }

    public static void sendSms(String from, String to, String body) {
        Call<Void> call = getService().sendSms(ACCOUNT_SID, from, to, body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("SMS sent successfully!");
                } else {
                    try {
                        System.out.println("Failed to send SMS: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Failed to send SMS: " + t.getMessage());
            }
        });
    }

    private static class BasicAuthInterceptor implements Interceptor {
        private String credentials;

        public BasicAuthInterceptor(String accountSid, String authToken) {
            this.credentials = okhttp3.Credentials.basic(accountSid, authToken);
        }

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", this.credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }
}
