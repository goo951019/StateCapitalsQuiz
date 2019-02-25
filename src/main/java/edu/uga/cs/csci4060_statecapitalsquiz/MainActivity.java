package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*  US Capitals Quiz App
    by Jerry Jang
        811554128
    and Tanner McClure
        811730580
 */


public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "MainActivity";
    private static final String TAG = "MainActivity";
    private StateData stateData = null;
    private List<State> states = new ArrayList<>();
    private Button goToQuiz, scores;

    private QuizData quizData = null;
    private List<Quiz> quizList = new ArrayList<>();;
    public static List<Quiz> recentQuiz = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve Quiz from DB
        quizData = new QuizData( this );
        new RetrieveQuizTask().execute();

        goToQuiz = (Button) findViewById(R.id.button);
        scores = (Button) findViewById(R.id.button2);

        // retrieve States from database.
        stateData = new StateData(this);
        stateData.open();
        states = stateData.retrieveAllStates();

        // if states DB is empty, put state_capitals.cvs to State.db
        // if states DB already exists, resue it.
        if (states == null || states.isEmpty()) {
            System.out.println("DB not Found. Reading CVS and Generating DB...");
            readCVS();
            states = stateData.retrieveAllStates();
        } else {
            System.out.println("DB already exists! Reusing previous DB...");
        }

        goToQuiz.setOnClickListener(new quizClickListener());
        scores.setOnClickListener(new scoresClickListener());
    }

    public class RetrieveQuizTask extends AsyncTask<Void, Void, List<Quiz>> {
        // This method will run as a background process to read from db.
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            quizData.open();
            quizList = quizData.retrieveAllQuizzes();
            System.out.println(quizList);

            Log.d( DEBUG_TAG, "RetrieveQuizTask: Quizzes retrieved: " + quizList.size() );

            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        @Override
        protected void onPostExecute( List<Quiz> quizList ) {
            super.onPostExecute(quizList);
            //recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
            //recyclerView.setAdapter( recyclerAdapter );
            int i = 0;
            recentQuiz = new ArrayList<>();;
            while(quizList.size() != 0 && recentQuiz.size() < 5){
                recentQuiz.add(quizList.get(quizList.size()-1));
                quizList.remove(quizList.size()-1);
            }
        }
    }

    // Read CVS file and put each state to DB.
    public void readCVS() {
        InputStream is = getResources().openRawResource(R.raw.state_capitals);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            reader.readLine(); /* Skip line */
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(","); /* Split by comma*/

                if (tokens[2].length() <= 0) {tokens[2] = "";}
                if (tokens[3].length() <= 0) {tokens[3] = "";}
                State s = new State(tokens[0], tokens[1], tokens[2], tokens[3],
                        Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                new CreateStateTask().execute(s);
            }
        } catch (IOException e) {
            Log.wtf("MainActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing asynchronously.
    private class CreateStateTask extends AsyncTask<State, Void, State> {
        // This method will run as a background process to write into db.
        @Override
        protected State doInBackground(State... s) {
            stateData.storeState(s[0]);
            return s[0];
        }
        // This method will be automatically called by Android once the db writing
        // background process is finished.
        @Override
        protected void onPostExecute(State s) {
            super.onPostExecute(s);
            Log.d(DEBUG_TAG, "State saved: " + s);
        }
    }

    public class quizClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            QuizActivity.score = 0;
            Intent intent = new Intent(v.getContext(), QuizActivity.class);
            v.getContext().startActivity(intent);
        }
    }
    public class scoresClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(recentQuiz.size() == 0){
                Toast.makeText(v.getContext(), "Quiz not Found", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(v.getContext(), LeaderboardActivity.class);
                v.getContext().startActivity(intent);
            }
        }
    }
    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( TAG, "MainActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( TAG, "MainActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( TAG, "MainActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( TAG, "MainActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( TAG, "MainActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( TAG, "MainActivity.onRestart()" );
        super.onRestart();
    }
}