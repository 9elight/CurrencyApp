package com.delight.currencyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delight.currencyapp.data.RetrofitBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;
import static com.delight.currencyapp.BuildConfig.API_KEY;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> ratesValues;
    private Spinner spinner1,spinner2;
    private EditText editText;
    private TextView textView;
    private double firstCurrency,secondCurrency;
    private int i;
    private Object[] ratesTitles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getCurrency();
        spinnerListeners();
        showResult();


    }

    private void spinnerListeners(){
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstCurrency = Double.parseDouble(ratesValues.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                i = spinner1.getSelectedItemPosition();
                secondCurrency = Double.parseDouble(ratesValues.get(position));

//                Toast.makeText(getApplicationContext(),"position " + position +" " + i, LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
    private void initView(){
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        editText = findViewById(R.id.edit_text);
        textView = findViewById(R.id.text_view);
    }





    private void getCurrency(){
        RetrofitBuilder.getService().exchangeRates(API_KEY)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject body = response.body();
                        JsonObject rates = body.getAsJsonObject("rates");
                        ratesTitles = rates.keySet().toArray();
                        ratesValues = new ArrayList<>();
                        for (Object ratesTitle : ratesTitles) {
                            ratesValues.add(String.valueOf(rates.getAsJsonPrimitive(ratesTitle.toString())));
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext()
                                ,android.R.layout.simple_list_item_1,ratesTitles);
                        spinner1.setAdapter(arrayAdapter);
                        spinner2.setAdapter(arrayAdapter);
                        Log.e("tag", "onResponse: " + response.code() );



                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getLocalizedMessage(), LENGTH_LONG).show();
                    }
                });
    }

    private void showResult(){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && s.length() > 0) {
                        textView.setText(calcRates(String.valueOf(s), firstCurrency, secondCurrency));
                    }else {
                        textView.setText("");
                    }
                }
            });
    }

    private String calcRates(String values,double fc,double sc){
      double result = ((Double.parseDouble(values)/fc)*sc);

      return String.valueOf(result);
    }


}
