package org.jmsalonen.tictactoedemo;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private GameFragment mGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        // restore progress
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                                .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.setState(gameData);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save progress
        String gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .apply();
    }
}
