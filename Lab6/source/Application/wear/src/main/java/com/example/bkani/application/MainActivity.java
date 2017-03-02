package com.example.bkani.application;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    private TextView mTextView;
    String sourceText;
    TextView outputTextView;
    TextView outputTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }

        });
    }
    public void translateText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.edit1);
        outputTextView = (TextView) findViewById(R.id.first);
        outputTextView2 = (TextView) findViewById(R.id.second);
        sourceText = sourceTextView.getText().toString();
        String getURL = "https://api.uclassify.com/v1/uclassify/genderAnalyzer_v5/Classify?readkey=rOKST7yvkDK8&text=" + sourceText;//The API service URL
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {

                        jsonResult = new JSONObject(result);
                        Double convertedTextArray = jsonResult.getDouble("Female");
                        Double PersonalText = jsonResult.getDouble("Manle");
                        Double Corporate = convertedTextArray*100;
                        Double personal = PersonalText*100;
                        int y = (int)Math.round(Corporate);
                        int Z = (int)Math.round(personal);

                        final String convertedText =String.valueOf(y);
                        final String convertedText1 =String.valueOf(Z);
                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                outputTextView.setText("Female : " +convertedText +"%");
                                outputTextView2.setText("Male : " +convertedText1 +"%");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());

        }

    }
}
