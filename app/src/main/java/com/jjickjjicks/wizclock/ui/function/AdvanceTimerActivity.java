package com.jjickjjicks.wizclock.ui.function;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jjickjjicks.wizclock.R;
import com.jjickjjicks.wizclock.data.TimerAdapter;
import com.jjickjjicks.wizclock.ui.item.InsertTimeDialog;

import java.util.Locale;

public class AdvanceTimerActivity extends AppCompatActivity {
    private TextView mTextViewCountDown;
    private Button mButtonStartPause, mButtonReset;
    private CountDownTimer mCountDownTimer;
    private String mTimerStatus;
    private long mTimeLeftInMillis, remainTime;
    private int remainIndex;
    private ListView listView;
    private TimerAdapter adapter;
    private Spinner repeatCnt;
    private InsertTimeDialog insertTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        repeatCnt = findViewById(R.id.repeatCnt);
        listView = (ListView) findViewById(R.id.timeList);

        View footer = getLayoutInflater().inflate(R.layout.listview_add, null, false);
        listView.addFooterView(footer);

        adapter = new TimerAdapter();
        listView.setAdapter(adapter);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerStatus == "run") {
                    pauseTimer();
                } else if (mTimerStatus == "pause") {
                    startTimer(remainIndex, remainTime);
                } else if (adapter.getCount() != 0) {
                    makeRepetition(Integer.parseInt(repeatCnt.getSelectedItem().toString()));
                    remainIndex = 0;
                    startTimer(0, adapter.getItem(0).getmTimeLeftInMillis());
                }
            }
        });

        Button addButton = footer.findViewById(R.id.add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                insertTimeDialog = new InsertTimeDialog(AdvanceTimerActivity.this);
                insertTimeDialog.setDialogListener(new InsertTimeDialog.InsertTimeDialogListener() {
                    @Override
                    public void onPositiveClicked(long time) {
                        adapter.addTimer(adapter.getCount() + 1, time);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                insertTimeDialog.show();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
    }

    private void startTimer(int index, long time) {
        mTimerStatus = "run";
        mButtonStartPause.setText(R.string.pause);
        mButtonReset.setVisibility(View.INVISIBLE);
        actTimer(index, time);
    }

    private void actTimer(final int index, long time) {
        mTimeLeftInMillis = time;
        updateCountDownText();
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (index + 1 != adapter.getCount())
                    actTimer(index + 1, adapter.getItem(index + 1).getmTimeLeftInMillis());
                else {
                    mTimerStatus = "stop";
                    mButtonStartPause.setText(R.string.start);
                    mButtonStartPause.setVisibility(View.INVISIBLE);
                    mButtonReset.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    private void makeRepetition(int cnt) {
        long[] temp = new long[adapter.getCount()];
        for (int i = 0; i < temp.length; i++)
            temp[i] = adapter.getItem(i).getmTimeLeftInMillis();
        for (int i = 0; i < cnt - 1; i++) {
            for (int j = 0; j < temp.length; j++)
                adapter.addTimer(adapter.getCount() + 1, temp[j]);
        }
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerStatus = "pause";
        remainTime = mTimeLeftInMillis;
        mButtonStartPause.setText(R.string.start);
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimerStatus = "stop";
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mTimeLeftInMillis = 0;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
