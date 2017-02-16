package tutorial.cs5551.com.translateapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class TranslateActivity extends AppCompatActivity {

    String API_URL = "https://api.uclassify.com/v1/uClassify/genderAnalyzer_v5/classify/?";
    String API_KEY = "ipBq6rpsTwA3";
    String sourceText;
    TextView outputTextView;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        outputTextView = (TextView) findViewById(R.id.txt_Result);
        //outputTextView1 = (TextView) findViewById(R.id.txt_Result1);//
    }
    public void logout(View v) {
        Intent redirect = new Intent(TranslateActivity.this, LoginActivity.class);
        startActivity(redirect);
    }
    public void translateText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.txt_Email);

        sourceText = sourceTextView.getText().toString();
        String getURL = "https://api.uclassify.com/v1/uClassify/genderAnalyzer_v5/classify/?"
                + "readKey=ipBq6rpsTwA3&text=" + sourceText;//The API service URL
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
                        Double convertedTextArray = jsonResult.getDouble("female");


                        Double convertedTextArray1 = jsonResult.getDouble("male");

                        if(convertedTextArray>convertedTextArray1) {
                            final String convertedText = String.valueOf(convertedTextArray);


                            Log.d("okHttp", jsonResult.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    outputTextView.setText("female :" +""+convertedText);

                                }


                            });
                        }
                        else{
                            final String convertedText = String.valueOf(convertedTextArray1);


                            Log.d("okHttp", jsonResult.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    outputTextView.setText("male :"+""+convertedText);

                                }


                            });
                        }
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
