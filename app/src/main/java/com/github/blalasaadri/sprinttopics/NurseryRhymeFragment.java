package com.github.blalasaadri.sprinttopics;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.blalasaadri.sprinttopics.NurseryRhymeFragment.NurseryRhymeSelection.UNKNOWN;

public class NurseryRhymeFragment extends Fragment {

    final static String NURSERY_RHYME_ARGUMENT = "nursery_rhyme";

    @BindView(R.id.nursery_rhyme_text)
    TextView nurseryRhymeText;

    public NurseryRhymeFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nursery_rhyme, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();
        NurseryRhymeSelection nurseryRhyme = (NurseryRhymeSelection) arguments.getSerializable(NURSERY_RHYME_ARGUMENT);
        if (nurseryRhyme == null) {
            nurseryRhyme = UNKNOWN;
        }
        switch (nurseryRhyme) {
            case NEW:
                nurseryRhymeText.setText(R.string.nursery_rhyme_new);
                break;
            case OLD:
                nurseryRhymeText.setText(R.string.nursery_rhyme_old);
                break;
            default:
                nurseryRhymeText.setText(R.string.nursery_rhyme_unknown);
        }

        return rootView;
    }

    enum NurseryRhymeSelection {
        NEW, OLD, UNKNOWN
    }
}
