package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LeaderboardInfoActivity extends AppCompatActivity {

    // a TAG to identify logcat events
    private static final String TAG = "Leaderboard";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( TAG, "LeaderboardInfoActivity.onCreate()" );

        super.onCreate( savedInstanceState );

        // if this call is not for a change in the configuration, do nothing and return
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            finish();
            return;
        }

        // if not in 2 fragment mode ie in portrait
        if( savedInstanceState == null ) {

            LeaderboardInfoFragment leaderboardInfoFragment = new LeaderboardInfoFragment();

            // pass on any saved data, i.e., Android version no (list index)
            leaderboardInfoFragment.setArguments(getIntent().getExtras());

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // add the fragment within a transaction
            getFragmentManager().beginTransaction().add(android.R.id.content, leaderboardInfoFragment).commit();
        }
    }

}
