package com.william.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class hasilTestAct extends AppCompatActivity {

    private TextView textHasil;
    private Button btnHasil;
    private Animation animate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_test);

        textHasil = findViewById(R.id.text_hasil);
        btnHasil = findViewById(R.id.btn_kembali);
        animate = AnimationUtils.loadAnimation(this, R.anim.splash);


        int jumlahTotal = getIntent().getExtras().getInt("hasil", 0) * 100 / 80;
        textHasil.setText(String.valueOf(jumlahTotal));
        textHasil.setAnimation(animate);

        btnHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(hasilTestAct.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}