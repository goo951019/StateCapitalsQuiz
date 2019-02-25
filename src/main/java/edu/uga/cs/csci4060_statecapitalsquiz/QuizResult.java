package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizResult extends AppCompatActivity {
    private static String strSeparator = ", ";
    private QuizData quizData = null;
    private List<Quiz> quizList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Button home = (Button) findViewById(R.id.home);

        quizData = new QuizData(this);
        quizData.open();
        quizList = quizData.retrieveAllQuizzes();

        String[] q = quizList.get(quizList.size()-1).getQuestion();
        String[] a = quizList.get(quizList.size()-1).getAnswer();
        String[] c = quizList.get(quizList.size()-1).getCorrect();
        String result = "Quiz on " + quizList.get(quizList.size()-1).getDate() +
                "\n Question 1: What is capital of "+q[0]+"?\nAnswer: "+a[0]+"\nYou Got Q1 "+c[0]+"!"+
                "\n\n Question 2: What is capital of "+q[1]+"?\nAnswer: "+a[1]+"\nYou Got Q2 "+c[1]+"!"+
                "\n\n Question 3: What is capital of "+q[2]+"?\nAnswer: "+a[2]+"\nYou Got Q3 "+c[2]+"!"+
                "\n\n Question 4: What is capital of "+q[3]+"?\nAnswer: "+a[3]+"\nYou Got Q4 "+c[3]+"!"+
                "\n\n Question 5: What is capital of "+q[4]+"?\nAnswer: "+a[4]+"\nYou Got Q5 "+c[4]+"!"+
                "\n\n Question 6: What is capital of "+q[5]+"?\nAnswer: "+a[5]+"\nYou Got Q6 "+c[5]+"!"+
                "\n\n Total Score is: " + quizList.get(quizList.size()-1).getScore();
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(result);

        home.setOnClickListener(new homeClickListener());

    }

    public class homeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        }
    }
}
