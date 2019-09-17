package com.jjickjjicks.wizclock.ui.function;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jjickjjicks.wizclock.R;
import com.jjickjjicks.wizclock.data.TimerAdapter;
import com.jjickjjicks.wizclock.service.CountdownTimerService;
import com.jjickjjicks.wizclock.ui.item.InsertTimeDialog;

public class AdvanceTimerActivity extends AppCompatActivity {
    private TextView mTextViewCountDown;
    private Button mButtonStartPause, mButtonReset;
    private ListView listView;
    private TimerAdapter adapter;
    private Spinner repeatCnt;
    private InsertTimeDialog insertTimeDialog;
    private CountdownTimerService timer;

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

        timer = new CountdownTimerService(mButtonStartPause, mButtonReset, mTextViewCountDown, adapter, Integer.parseInt(repeatCnt.getSelectedItem().toString()), getApplicationContext());

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setInformation(mButtonStartPause, mButtonReset, mTextViewCountDown, adapter, Integer.parseInt(repeatCnt.getSelectedItem().toString()), getApplicationContext());
                timer.activation();
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
                timer.resetTimer();
            }
        });
        timer.updateCountDownText();
    }

}
