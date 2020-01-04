package com.delight.currencyapp.data;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyService {
    @GET("latest")
    Call<JsonObject> exchangeRates (@Query("access_key")String apiKey);
}