package com.jjickjjicks.wizclock.ui.function;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jjickjjicks.wizclock.R;
import com.jjickjjicks.wizclock.data.TimerAdapter;
import com.jjickjjicks.wizclock.service.CountdownTimerService;

public class TimerFragment extends Fragment {
    private TextView mTextViewCountDown, advanceFunction;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private RelativeLayout timePicker;
    private TimerAdapter adapter;
    private NumberPicker hourPicker, minPicker, secPicker;
    private CountdownTimerService timer;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timer, container, false);

        mTextViewCountDown = root.findViewById(R.id.text_view_countdown);
        mButtonStartPause = root.findViewById(R.id.button_start_pause);
        mButtonReset = root.findViewById(R.id.button_reset);

        timePicker = root.findViewById(R.id.timePicker);

        hourPicker = root.findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(99);
        hourPicker.setMinValue(0);
        hourPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        minPicker = root.findViewById(R.id.minPicker);
        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);
        minPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        secPicker = root.findViewById(R.id.secPicker);
        secPicker.setMaxValue(59);
        secPicker.setMinValue(0);
        secPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        adapter = new TimerAdapter();
        timer = new CountdownTimerService(mButtonStartPause, mButtonReset, mTextViewCountDown, adapter, 1, getActivity());

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.INVISIBLE);
                mTextViewCountDown.setVisibility(View.VISIBLE);
                adapter.addTimer(1, (hourPicker.getValue() * 3600000 + minPicker.getValue() * 60000 + secPicker.getValue() * 1000));
                timer.setInformation(mButtonStartPause, mButtonReset, mTextViewCountDown, adapter, 1, getActivity());
                timer.activation();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                mTextViewCountDown.setVisibility(View.INVISIBLE);
                timer.resetTimer();
            }
        });
        timer.updateCountDownText();

        advanceFunction = root.findViewById(R.id.advance);
        advanceFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdvanceTimerActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


}