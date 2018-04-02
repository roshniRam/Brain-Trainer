package com.example.dell.braintrainer;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers;
    Boolean finished = false;
    int correctAnswerLocation;
    int questionCount = 0;
    int correctAnswerCount = 0;
    TextView pointsTextView;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    TextView question;
    TextView correct;
    TextView score;
    Random r = new Random();
    ConstraintLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = (ConstraintLayout) findViewById(R.id.game);
        gameLayout.setVisibility(View.INVISIBLE);

        final TextView startGame = (TextView) findViewById(R.id.start);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame.setVisibility(View.INVISIBLE);
                gameLayout.setVisibility(View.VISIBLE);
            }
        });


        pointsTextView = (TextView) findViewById(R.id.points);
        final Button timerButton = (Button) findViewById(R.id.timer);

        question = (TextView) findViewById(R.id.question);
        correct = (TextView) findViewById(R.id.correction);
        score = (TextView) findViewById(R.id.result);
        score.setVisibility(View.INVISIBLE);

        b1 = (Button) findViewById(R.id.button1);
        b1.setTag(0);
        b2 = (Button) findViewById(R.id.button2);
        b2.setTag(1);
        b3 = (Button) findViewById(R.id.button3);
        b3.setTag(2);
        b4 = (Button) findViewById(R.id.button4);
        b4.setTag(3);

        final CountDownTimer timer = new CountDownTimer(30000+ 1000,1000) {
            @Override
            public void onTick(long l) {
                if(l/1000<=9){
                    timerButton.setTextColor(Color.RED);
                    timerButton.setText("0:0" + String.valueOf(l/1000));
                }
                else{timerButton.setText("0:" + String.valueOf(l/1000));}
            }

            @Override
            public void onFinish() {
                timerButton.setText("0:00");
                question.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                b1.setEnabled(false);
                question.setEnabled(false);
                timerButton.setEnabled(false);
                correct.setVisibility(View.INVISIBLE);
                score.setVisibility(View.VISIBLE);
                score.setText(pointsTextView.getText().toString());
                Log.i("Tag",String.valueOf(finished));
            }
        }.start();

        nextQuestion();
    }

    public void chooseAnswer(View view){

        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation))){
            correctAnswerCount++;
            correct.setText("Correct!");
        }
        else {
            correct.setText("Wrong!");
        }
        questionCount++;

        pointsTextView.setText(Integer.toString(correctAnswerCount)+"/"+questionCount);
        nextQuestion();
    }

    public void nextQuestion(){
        answers = new ArrayList<Integer>();
        int a = r.nextInt(21),b = r.nextInt(21);
        question.setText(a+" + "+b);

        correctAnswerLocation = r.nextInt(4);

        for(int i=0;i<4;i++){
            if(i == correctAnswerLocation){
                answers.add(a+b);
            }

            else{
                int option = r.nextInt(41);

                while(option == a+b){
                    option = r.nextInt(41);
                }

                answers.add(option);
            }
        }

        b1.setText(String.valueOf(answers.get(0)));
        b2.setText(String.valueOf(answers.get(1)));
        b3.setText(Integer.toString(answers.get(2)));
        b4.setText(Integer.toString(answers.get(3)));
    }
}
