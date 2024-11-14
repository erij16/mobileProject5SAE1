package com.example.reclamation;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;

public interface TwilioApiService {
    @FormUrlEncoded
    @POST("Accounts/{AccountSid}/Messages.json")
    Call<Void> sendSms(
            @Field("From") String from,
            @Field("To") String to,
            @Field("Body") String body
    );
}
