package com.delight.currencyapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.delight.currencyapp.BuildConfig.BASE_URL;

public class RetrofitBuilder {
    private static CurrencyService currencyService;

    public static CurrencyService getService(){
        if (currencyService == null){
            currencyService = buildRetrofit();
        }
        return currencyService;
    }

    private static CurrencyService buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(CurrencyService.class);
    }
}
