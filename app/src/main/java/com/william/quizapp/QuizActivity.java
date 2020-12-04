package com.william.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton prevButton;
    private ImageButton nextButton;
    private ImageButton[] buttonNomor = new ImageButton[8];
    private Button jwbn1;
    private Button jwbn2;
    private Button jwbn3;
    private Button jwbn4;
    private Button btnSubmit;
    private int countScore = 0;

    private String[] arrJawaban = new String[8];

    private TextView questionText;
    private int currentIndex = 0;
    private Question[] questionBank = new Question[]{
            new Question(R.string.question1, "Google", "Facebook", "Google", "Amazon", "Twitter"),
            new Question(R.string.question2, "Rails", "React", "Laravel", "Spring", "Rails"),
            new Question(R.string.question3, "Dart", "Java", "Kotlin", "Dart", "Javascript"),
            new Question(R.string.question4, "Python", "Python", "HTML", "CSS", "Javascript"),
            new Question(R.string.question5, "Inheritance", "Abstraction", "Inheritance", "Interface", "Polymorph"),
            new Question(R.string.question6, "Docker", "VS code", "Sublime", "Atom", "Docker"),
            new Question(R.string.question7, "Kali Linux", "Arch Linux", "Kali Linux", "Whonix", "Qubes"),
            new Question(R.string.question8, "val", "val", "var", "let", "const"),

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        jwbn1 = findViewById(R.id.jawaban1);
        jwbn2 = findViewById(R.id.jawaban2);
        jwbn3 = findViewById(R.id.jawaban3);
        jwbn4 = findViewById(R.id.jawaban4);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        questionText = findViewById(R.id.questionText);

        btnSubmit = findViewById(R.id.btn_submit);

        buttonNomor[0] = (ImageButton) findViewById(R.id.nomorSatu);
        buttonNomor[1] = (ImageButton) findViewById(R.id.nomorDua);
        buttonNomor[2] = (ImageButton) findViewById(R.id.nomorTiga);
        buttonNomor[3] = (ImageButton) findViewById(R.id.nomorEmpat);
        buttonNomor[4] = (ImageButton) findViewById(R.id.nomorLima);
        buttonNomor[5] = (ImageButton) findViewById(R.id.nomorEnam);
        buttonNomor[6] = (ImageButton) findViewById(R.id.nomorTujuh);
        buttonNomor[7] = (ImageButton) findViewById(R.id.nomorDelapan);

        jwbn1.setOnClickListener(this);
        jwbn2.setOnClickListener(this);
        jwbn3.setOnClickListener(this);
        jwbn4.setOnClickListener(this);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        updateQuestion();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                pindah();
                break;
            case R.id.prev_button:
                if (currentIndex > 0) {
                    deleteSelected();
                    currentIndex = (currentIndex - 1);
                    updateQuestion();
                }
                break;
            case R.id.jawaban1:
                arrJawaban[currentIndex] = (questionBank[currentIndex].getAnswer1());
                pindah();
                break;
            case R.id.jawaban2:
                arrJawaban[currentIndex] = (questionBank[currentIndex].getAnswer2());
                pindah();
                break;
            case R.id.jawaban3:
                arrJawaban[currentIndex] = (questionBank[currentIndex].getAnswer3());
                pindah();
                break;
            case R.id.jawaban4:
                arrJawaban[currentIndex] = (questionBank[currentIndex].getAnswer4());
                pindah();
                break;
            case R.id.btn_submit:
                hasil();
                if (countScore > 0) {
                    Intent i = new Intent(QuizActivity.this, hasilTestAct.class);
                    i.putExtra("hasil", countScore);
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(), "Jawab Minimal 1 soal", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void pindah() {
        if (currentIndex < questionBank.length - 1) {
            deleteSelected();
            currentIndex = (currentIndex + 1);
            updateQuestion();
        }

    }

    public void deleteSelected() {
        buttonNomor[currentIndex].setBackgroundResource(R.drawable.button_number);
    }

    public void updateQuestion() {
        buttonNomor[currentIndex].setBackgroundResource(R.drawable.button_selected);
        questionText.setText(questionBank[currentIndex].getAnswerResId());
        jwbn1.setText(questionBank[currentIndex].getAnswer1());
        jwbn2.setText(questionBank[currentIndex].getAnswer2());
        jwbn3.setText(questionBank[currentIndex].getAnswer3());
        jwbn4.setText(questionBank[currentIndex].getAnswer4());

    }

    public void hasil() {

        for (int i = 0; i < arrJawaban.length; i++) {
            if (arrJawaban[i] == questionBank[i].getCorrectAnswer()) {
                countScore = countScore + 10;
            } else {
                countScore += 0;
            }
        }
    }
}