package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ScrollView;

public class LeaderboardInfoFragment extends Fragment {
    private static String strSeparator = ", ";
    private static final String TAG = "Leaderboard";
    private String[] recentQuizzes;
    // This method is similar to the factory method design pattern
    // to create new instances of this fragment.
    // There is a specific reason for having this method, though.  We want to send some data (VersionIndex, here) into the
    // new fragment.  Android disallows overloaded constructors for fragments, and so we can't create a Fragment with
    // the versionIndex as argument.  But we can use the Bundle and send the data this way.  Also, the setArguments call
    // must happen BEFORE the fragment is attached an activity.
    public static LeaderboardInfoFragment newInstance(int versionIndex) {

        Log.d( TAG, "LeaderboardInfoFragment.newInstance(): versionIndex: " + versionIndex );
        // this uses the default constructor (not defined in this class, but Java-supplied)
        LeaderboardInfoFragment fragment = new LeaderboardInfoFragment();

        // save the selected list versionIndex in the new fragment's Bundle data
        // the AndroidVersionInfoFragment needs to know the version to display
        Bundle args = new Bundle();
        args.putInt( "versionIndex", versionIndex );
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        Log.d( TAG, "LeaderboardInfoFragment.onCreateView()" );

        // Programmatically, create a scrollable TextView to show the Android version information
        ScrollView scroller = new ScrollView( getActivity()) ;

        // Set the padding for the new TextView
        // these padding attributes are normally defined in the XML file
        // here, they are set programmatically
        int padding = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 12, getActivity().getResources().getDisplayMetrics() );
        recentQuizzes = new String[MainActivity.recentQuiz.size()];
        String[] q;
        String[] a;
        String[] c;
        for(int i=0;i<MainActivity.recentQuiz.size();i++) {
            q = MainActivity.recentQuiz.get(i).getQuestion();
            a = MainActivity.recentQuiz.get(i).getAnswer();
            c = MainActivity.recentQuiz.get(i).getCorrect();
            recentQuizzes[i] = "Quiz on " + MainActivity.recentQuiz.get(i).getDate() +
                    "\n Question 1: What is capital of "+q[0]+"?\nAnswer: "+a[0]+"\nYou Got Q1 "+c[0]+"!"+
                    "\n\n Question 2: What is capital of "+q[1]+"?\nAnswer: "+a[1]+"\nYou Got Q2 "+c[1]+"!"+
                    "\n\n Question 3: What is capital of "+q[2]+"?\nAnswer: "+a[2]+"\nYou Got Q2 "+c[2]+"!"+
                    "\n\n Question 4: What is capital of "+q[3]+"?\nAnswer: "+a[3]+"\nYou Got Q2 "+c[3]+"!"+
                    "\n\n Question 5: What is capital of "+q[4]+"?\nAnswer: "+a[4]+"\nYou Got Q2 "+c[4]+"!"+
                    "\n\n Question 6: What is capital of "+q[5]+"?\nAnswer: "+a[5]+"\nYou Got Q2 "+c[5]+"!"+
                    "\n\n Total Score is: " + MainActivity.recentQuiz.get(i).getScore();
        }
        TextView textView = new TextView( getActivity() );

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        // show the android version info
        scroller.addView( textView );
        textView.setText(recentQuizzes[getLeaderboardIndex()] );
        textView.setTextColor(Color.BLACK);

        return scroller;
    }

    public int getLeaderboardIndex() {
        return getArguments().getInt("versionIndex", 0 );
    }
}

