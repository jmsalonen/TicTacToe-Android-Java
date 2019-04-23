package org.jmsalonen.tictactoedemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements View.OnClickListener {

    private AlertDialog mDialog;
    Button continueButton;
    Button newButton;
    Button aboutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        continueButton = rootView.findViewById(R.id.continue_button);
        newButton = rootView.findViewById(R.id.new_button);
        aboutButton = rootView.findViewById(R.id.about_button);

        continueButton.setOnClickListener(this);
        newButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Intent intent;

        switch (view.getId()) {
            case R.id.new_button:       // start new game
                intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.continue_button:  // continue old game
                intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
                break;
            case R.id.about_button:     // show popup window
                builder.setTitle(R.string.aboutTitle);
                builder.setMessage(R.string.aboutText);
                mDialog = builder.show();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // get rid of the popup dialog
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
