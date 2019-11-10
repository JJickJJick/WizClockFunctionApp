package com.jjickjjicks.wizclock.ui.item;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import com.jjickjjicks.wizclock.R;

public class InsertTimeDialog extends Dialog implements View.OnClickListener {
    private InsertTimeDialogListener insertTimeDialogListener;
    private Context context;
    private Button positiveButton, negativeButton;
    private NumberPicker hourPicker, minPicker, secPicker;

    public InsertTimeDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setDialogListener(InsertTimeDialogListener insertTimeDialogListener) {
        this.insertTimeDialogListener = insertTimeDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.insert_time_dialog);

        hourPicker = findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(99);
        hourPicker.setMinValue(0);
        hourPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        minPicker = findViewById(R.id.minPicker);
        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);
        minPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        secPicker = findViewById(R.id.secPicker);
        secPicker.setMaxValue(59);
        secPicker.setMinValue(0);
        secPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        //init
        positiveButton = (Button) findViewById(R.id.btnPositive);
        negativeButton = (Button) findViewById(R.id.btnNegative);


        //버튼 클릭 리스너 등록
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPositive: // confirm (send data to activtiy)
                long time = hourPicker.getValue() * 3600000 + minPicker.getValue() * 60000 + secPicker.getValue() * 1000;
                if (time == 0) // prevent zero time error
                    break;
                insertTimeDialogListener.onPositiveClicked(time);
                dismiss();
                break;
            case R.id.btnNegative: // cancel btn clicked
                cancel();
                break;
        }
    }

    public interface InsertTimeDialogListener {
        void onPositiveClicked(long time);

        void onNegativeClicked();
    }
}
