package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;

public class QuizActivity extends AppCompatActivity implements FirstQuestionFragment.OnFragmentInteractionListener,
        QuestionFragment.OnFragmentInteractionListener, FinalQuestionFragment.OnFragmentInteractionListener{
    public static Fragment[] frags = new Fragment[6];

    public static int pos = 0;

    public static String q1Key = "";
    public static String q2Key = "";
    public static String q3Key = "";
    public static String q4Key = "";
    public static String q5Key = "";
    public static String q6Key = "";

    public static String q1Selected = "";
    public static String q2Selected = "";
    public static String q3Selected = "";
    public static String q4Selected = "";
    public static String q5Selected = "";
    public static String q6Selected = "";

    public static String d;
    public static String[] pickedstates;
    public static String[] correct;
    public static String[] answers;

    public static int score = 0;

    private static final String DEBUG_TAG = "Quiz Table";
    private List<State> states = new ArrayList<>();
    private List<State> questions = new ArrayList<>();
    private StateData stateData = null;

    private List<Quiz> quizzes = new ArrayList<>();
    private static QuizData quizData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            System.out.println("pos= "+pos);

            return;
        }

        FrameLayout quizFrame = (FrameLayout) findViewById(R.id.questionView);

        stateData = new StateData(this);
        stateData.open();
        states = stateData.retrieveAllStates();

        Random rand = new Random();
        int i = 50;
        /* pick random 6 states from the list
        * Removes picked state from the original list
        */
        while(questions.size() < 6) {
            int n = rand.nextInt(i); // Gives n such that 0 <= n < i
            questions.add(states.get(n));
            states.remove(n);
            i = i - 1;
        }
        System.out.println(states.size());
        System.out.println("Picked States: " + questions);
        //stateData.close();

        // Picked states and capitals into string array
        d = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        pickedstates = new String[]{questions.get(0).getState(), questions.get(1).getState(),
                questions.get(2).getState(), questions.get(3).getState(),
                questions.get(4).getState(), questions.get(5).getState()};
        correct = new String[]{"Wrong", "Wrong", "Wrong", "Wrong", "Wrong", "Wrong"};
        answers = new String[]{questions.get(0).getCapital(), questions.get(1).getCapital(),
                questions.get(2).getCapital(), questions.get(3).getCapital(),
                questions.get(4).getCapital(), questions.get(5).getCapital()};

        // random capitals
        String[] rc0 = getRandomCapitals(states, questions, 0);
        String[] rc1 = getRandomCapitals(states, questions, 1);
        String[] rc2 = getRandomCapitals(states, questions, 2);
        String[] rc3 = getRandomCapitals(states, questions, 3);
        String[] rc4 = getRandomCapitals(states, questions, 4);
        String[] rc5 = getRandomCapitals(states, questions, 5);

        //in each of these declarations pull random question values from the database
        //and insert them into the parameters. The format for each is:
        // newInstance(state, city1, city2, city3, correct answer)
        FirstQuestionFragment q1 = FirstQuestionFragment.newInstance(pickedstates[0], rc0[0], rc0[1], rc0[2], answers[0]);
        q1Key = answers[0];
        QuestionFragment q2 = QuestionFragment.newInstance(pickedstates[1], rc1[0], rc1[1], rc1[2], answers[1]);
        q2Key = answers[1];
        QuestionFragment q3 = QuestionFragment.newInstance(pickedstates[2], rc2[0], rc2[1], rc2[2], answers[2]);
        q3Key = answers[2];
        QuestionFragment q4 = QuestionFragment.newInstance(pickedstates[3], rc3[0], rc3[1], rc3[2], answers[3]);
        q4Key = answers[3];
        QuestionFragment q5 = QuestionFragment.newInstance(pickedstates[4], rc4[0], rc4[1], rc4[2], answers[4]);
        q5Key = answers[4];
        FinalQuestionFragment q6 = FinalQuestionFragment.newInstance(pickedstates[5], rc5[0], rc5[1], rc5[2], answers[5]);
        q6Key = answers[5];

        frags[0] = q1;
        frags[1] = q2;
        frags[2] = q3;
        frags[3] = q4;
        frags[4] = q5;
        frags[5] = q6;

        quizData = new QuizData(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.questionView, q1, "fragment");
        ft.commit();

    }

    public static void insertQuizToDB(){
        quizData.open();
        Quiz q = new Quiz(d, pickedstates, correct, answers, score);
        q.toString();
        new CreateQuizTask().execute(q);
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing asynchronously.
    private static class CreateQuizTask extends AsyncTask<Quiz, Void, Quiz> {
        // This method will run as a background process to write into db.
        @Override
        protected Quiz doInBackground(Quiz... q) {
            quizData.storeQuiz(q[0]);
            return q[0];
        }

        // This method will be automatically called by Android once the db writing
        // background process is finished.
        @Override
        protected void onPostExecute(Quiz q) {
            super.onPostExecute(q);
            Log.d(DEBUG_TAG, "Quiz saved: " + q);
        }
    }

    // return random ordered String array that has 2 random capitals from unpicked states and 1 capital from picked state
    private String[] getRandomCapitals(List<State> unpicked, List<State> picked, int index){
        int count = 0;
        List<Integer> intList = new ArrayList<>();;
        List<String> tempCap = new ArrayList<>();;
        Random rand = new Random();
        while(count < 2){
            int n = rand.nextInt(unpicked.size()); // Gives n such that 0 <= n < unpicked.size();
            if(intList.size() == 0 || intList.get(0) != n){
                intList.add(n);
                count++;
            }
        }

        tempCap.add(picked.get(index).getCapital());
        tempCap.add(unpicked.get(intList.get(0)).getCapital());
        tempCap.add(unpicked.get(intList.get(1)).getCapital());
        Collections.shuffle(tempCap);
        String[] temp = new String[]{tempCap.get(0),tempCap.get(1),tempCap.get(2)};
        return temp;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public static class OnSwipeListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        public OnSwipeListener(Context c) {
            gestureDetector = new GestureDetector(c, new GestureListener());
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {

        }

        public void onSwipeLeft() {

        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }//OnSwipeListener
}
