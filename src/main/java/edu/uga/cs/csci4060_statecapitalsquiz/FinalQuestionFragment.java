package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalQuestionFragment extends Fragment {
    private static final String DEBUG_TAG = "Quiz Table";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STATE = "state";
    private static final String CITY1 = "city1";
    private static final String CITY2 = "city2";
    private static final String CITY3 = "city3";
    private static final String KEY = "key";

    // TODO: Rename and change types of parameters
    private String mState;
    private String mCity1;
    private String mCity2;
    private String mCity3;
    public static String mKey;
    private OnFragmentInteractionListener mListener;
    private QuizData quizData = null;

    public FinalQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalQuestionFragment newInstance(String state, String city1, String city2, String city3, String key) {
        FinalQuestionFragment fragment = new FinalQuestionFragment();
        Bundle args = new Bundle();
        args.putString(STATE, state);
        args.putString(CITY1, city1);
        args.putString(CITY2, city2);
        args.putString(CITY3, city3);
        args.putString(KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mState = getArguments().getString(STATE);
            mCity1 = getArguments().getString(CITY1);
            mCity2 = getArguments().getString(CITY2);
            mCity3 = getArguments().getString(CITY3);
            mKey = getArguments().getString(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        int orientation = getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT) {
            v = inflater.inflate(R.layout.fragment_final_question, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_horizontal_final_question, container, false);
        }

        TextView finalQuestText = (TextView) v.findViewById(R.id.finalQuestion);
        RadioButton answer1 = (RadioButton) v.findViewById(R.id.radioButton6);
        RadioButton answer2 = (RadioButton) v.findViewById(R.id.radioButton5);
        RadioButton answer3 = (RadioButton) v.findViewById(R.id.radioButton4);
        final Button submit = (Button) v.findViewById(R.id.submit);
        final Button prev = (Button) v.findViewById(R.id.prev);
        final RadioGroup radGroup = (RadioGroup) v.findViewById(R.id.sixthQRadGroup);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.linLayout);

        finalQuestText.setText("What is the capital of "+mState+"?");
        answer1.setText(mCity1);
        answer2.setText(mCity2);
        answer3.setText(mCity3);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pickedID = radGroup.getCheckedRadioButtonId();
                RadioButton rB = (RadioButton) radGroup.findViewById(pickedID);

                if(pickedID != -1) {
                    QuizActivity.q6Selected = rB.getText().toString();
                    if (QuizActivity.q1Key.equals(QuizActivity.q1Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[0] = "Right";
                    }
                    if (QuizActivity.q2Key.equals(QuizActivity.q2Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[1] = "Right";
                    }
                    if (QuizActivity.q3Key.equals(QuizActivity.q3Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[2] = "Right";
                    }
                    if (QuizActivity.q4Key.equals(QuizActivity.q4Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[3] = "Right";
                    }
                    if (QuizActivity.q5Key.equals(QuizActivity.q5Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[4] = "Right";
                    }
                    if (QuizActivity.q6Key.equals(QuizActivity.q6Selected)) {
                        QuizActivity.score += 1;
                        QuizActivity.correct[5] = "Right";
                    }

                    QuizActivity.insertQuizToDB();
                    Toast.makeText(v.getContext(), "Score = " + QuizActivity.score, Toast.LENGTH_LONG + 100).show();

                    QuizActivity.pos = 0;
                    Intent intent = new Intent(v.getContext(), QuizResult.class);
                    v.getContext().startActivity(intent);
                }else{
                    Toast.makeText(v.getContext(), "Please select your answer", Toast.LENGTH_LONG).show();
                }

                //Write score (QuizActivity.score), date, etc to database and then to R.id.lastQuiz
                //in activity_leaderboard.xml
                //Write table of high scores from db to R.id.highScores


            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizActivity.pos-=1;
                FragmentTransaction ft = QuizActivity.frags[QuizActivity.pos+1].getFragmentManager().beginTransaction();
                ft.replace(R.id.questionView, QuizActivity.frags[QuizActivity.pos]);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        layout.setOnTouchListener(new QuizActivity.OnSwipeListener(layout.getContext()){
            public void onSwipeLeft(){
                submit.callOnClick();
            }
            public void onSwipeRight(){
                prev.callOnClick();
            }
        });

        // Inflate the layout for this fragment
        // inflate as a new layout, modify, then return the layout
        //add mState...mCity3 into text views
        //basically throw this stuff into first/last question fragments
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

