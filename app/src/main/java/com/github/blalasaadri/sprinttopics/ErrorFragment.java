package com.github.blalasaadri.sprinttopics;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.bind;

public class ErrorFragment extends Fragment {

    static final String ERROR_MESSAGE_ARG = "errorMsg";

    @BindView(R.id.error_text)
    TextView errorText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.error, container, false);
        bind(this, rootView);

        Bundle args = getArguments();
        int errorMessage = args.getInt(ERROR_MESSAGE_ARG);
        errorText.setText(errorMessage);

        return rootView;
    }
}
