package com.william.quizapp;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private long backpressTime;
    private Toast backToast;
    private TextView text_quote, text_author;
    private String url = "https://type.fit/api/quotes";
    private int randint;
    ArrayList<Quotes> quote = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCardView = findViewById(R.id.btn_quiz);
        text_quote = findViewById(R.id.text_quote);
        text_author = findViewById(R.id.text_author);

        btnCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(i);
            }
        });



        quote = getQuotes(new ResponseFinished() {
            @Override
            public void processFinished(ArrayList<Quotes> quotes) {
                Random rand = new Random();
                randint = rand.nextInt(quotes.size());
                text_quote.setText(quotes.get(randint).getQuote());
                if (quotes.get(randint).getPerson() != null) {
                    text_author.setText(quotes.get(randint).getPerson());
                } else {
                    text_author.setText("Some People");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        
        if (backpressTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Klik kembali lagi untuk keluar", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressTime = System.currentTimeMillis();
    }

    public ArrayList<Quotes> getQuotes(final ResponseFinished callback) {
        ArrayList<Quotes> quotesArrayList = new ArrayList<>();
        JsonArrayRequest quotesRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Quotes quote = new Quotes();
                        quote.setQuote(response.getJSONObject(i).getString("text"));
                        quote.setPerson(response.getJSONObject(i).getString("author"));
                        quotesArrayList.add(quote);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (callback != null) callback.processFinished(quotesArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JSON Error", "onErrorResponse: " + error);
            }
        });
        AppRequest.getInstance(MainActivity.this).addToRequestQueue(quotesRequest);
        return quotesArrayList;
    }
}