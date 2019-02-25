package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class FirstQuestionFragment extends Fragment {
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

    public FirstQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstQuestionFragment newInstance(String state, String city1, String city2, String city3, String key) {
        FirstQuestionFragment fragment = new FirstQuestionFragment();
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
            v = inflater.inflate(R.layout.fragment_first_question, container, false);
        }
        else{
            v = inflater.inflate(R.layout.fragment_horizontal_first_question, container, false);
        }

        TextView finalQuestText = (TextView) v.findViewById(R.id.firstQuestion);
        RadioButton answer1 = (RadioButton) v.findViewById(R.id.radioButton8);
        RadioButton answer2 = (RadioButton) v.findViewById(R.id.radioButton9);
        RadioButton answer3 = (RadioButton) v.findViewById(R.id.radioButton7);
        final RadioGroup radGroup = (RadioGroup) v.findViewById(R.id.firstQRadGroup);
        final Button next = (Button) v.findViewById(R.id.next);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.linLayout);

        finalQuestText.setText("What is the capital of "+mState+"?");
        answer1.setText(mCity1);
        answer2.setText(mCity2);
        answer3.setText(mCity3);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pickedID = radGroup.getCheckedRadioButtonId();
                RadioButton rB = (RadioButton) radGroup.findViewById(pickedID);
                if(pickedID != -1) {
                    QuizActivity.q1Selected = rB.getText().toString();

                    QuizActivity.pos += 1;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.questionView, QuizActivity.frags[QuizActivity.pos]);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    Toast.makeText(v.getContext(), "Please select your answer", Toast.LENGTH_LONG).show();
                }
            }
        });

        layout.setOnTouchListener(new QuizActivity.OnSwipeListener(layout.getContext()){
            public void onSwipeLeft(){
                next.callOnClick();
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

