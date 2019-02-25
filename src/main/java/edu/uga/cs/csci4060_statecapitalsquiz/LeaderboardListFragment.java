package edu.uga.cs.csci4060_statecapitalsquiz;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LeaderboardListFragment extends ListFragment {
    // a TAG string for logcat messages identification
    private static final String TAG = "Leaderboard";

    // Array of Android versions strings for the list fragment
    private String[] recentQuizzes = {"N/A"};

    // indicate if this is a landscape mode with both fragments present or not
    boolean twoFragmentsActivity = false;

    // list selection index
    int versionIndex = 0;

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        Log.d( TAG, "LeaderboardListFragment.onActivityCreated()" );

        LeaderboardActivity la = (LeaderboardActivity) getActivity();

        recentQuizzes = new String[MainActivity.recentQuiz.size()];
        for(int i=0;i<MainActivity.recentQuiz.size();i++)
            recentQuizzes[i] = "Quiz on " + MainActivity.recentQuiz.get(i).getDate();


        // create a new ArrayAdapter for the list
        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1, recentQuizzes ) );

        // set the twoFragmentsActivity variable to true iff we are in 2 fragment (landscape) view
        View detailsFrame = getActivity().findViewById( R.id.leaderboard_info );
        twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // restore the saved list index selection (Android version no), if available
        if( savedInstanceState != null ) {
            // Restore last state for checked position.
            versionIndex = savedInstanceState.getInt("quizSelection", 0 );
        }

        // set the list mode as single choice and
        // if we are in 2 fragment (landscape) orientation, show the Android version information
        if( twoFragmentsActivity ) {
            getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
            leaderboardInfo(versionIndex);
        }
        else {
            getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
            getListView().setItemChecked(versionIndex, true );
        }
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);

        // save the list index selection
        outState.putInt( "quizSelection", versionIndex);
    }

    @Override
    public void onListItemClick( ListView l, View v, int position, long id ) {
        // on a click on a list item, show the selected Android version info
        leaderboardInfo( position );
    }

    void leaderboardInfo( int versionIndex ) {

        this.versionIndex = versionIndex;

        if( twoFragmentsActivity ) {  // only in the 2 fragment (landscape) orientation

            getListView().setItemChecked( versionIndex, true );

            // get the placeholder element (FrameLayout) in a 2 fragment (landscape) orientation layout
            LeaderboardInfoFragment details =
                    (LeaderboardInfoFragment) getFragmentManager().findFragmentById(R.id.leaderboard_info);

            if( details == null || details.getLeaderboardIndex() != versionIndex ) {

                // obtain a new Android info fragment instance
                details = LeaderboardInfoFragment.newInstance( versionIndex );
                // replace the placeholder with the new fragment stance within a transaction
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace( R.id.leaderboard_info, details );

                // TRANSIT_FRAGMENT_FADE means that the fragment should fade in or fade out
                fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );

                // commit the transaction, i.e. make the changes
                fragmentTransaction.commit();
            }
        }
        else {
            // In a 1 fragment orientation (portrait), start a new activity using an Intent, as in the previous demo app
            Intent intent = new Intent();
            intent.setClass( getActivity(), LeaderboardInfoActivity.class );
            intent.putExtra("versionIndex", versionIndex);
            startActivity( intent );
        }
    }


}
