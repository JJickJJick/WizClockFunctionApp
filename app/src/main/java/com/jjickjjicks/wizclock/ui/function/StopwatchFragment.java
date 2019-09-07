package com.jjickjjicks.wizclock.ui.function;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jjickjjicks.wizclock.R;

public class StopwatchFragment extends Fragment {
    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;
    private TextView myOutput, myRec, advanceFunction;
    private Button myBtnStart, myBtnRec;
    private int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    private int myCount = 1;
    private long myBaseTime, myPauseTime;
    Handler myTimer = new Handler() {
        public void handleMessage(Message msg) {
            myOutput.setText(getTimeOut());

            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
            myTimer.sendEmptyMessage(0);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        myOutput = root.findViewById(R.id.time_out);
        myRec = root.findViewById(R.id.record);
        myRec.setMovementMethod(new ScrollingMovementMethod());
        myBtnStart = root.findViewById(R.id.btn_start);
        myBtnRec = root.findViewById(R.id.btn_rec);

        myBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur_Status) {
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime); //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        myTimer.sendEmptyMessage(0);
                        myBtnStart.setText(R.string.pause); //버튼의 문자"시작"을 "멈춤"으로 변경
                        myBtnRec.setEnabled(true); //기록버튼 활성
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnStart.setText(R.string.start);
                        myBtnRec.setText(R.string.reset);
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now - myPauseTime);
                        myBtnStart.setText(R.string.cancel);
                        myBtnRec.setText(R.string.record);
                        cur_Status = Run;
                        break;


                }
            }
        });

        myBtnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur_Status) {
                    case Run:
                        String str = myRec.getText().toString();
                        str = String.format("%d \t %s\n", myCount, getTimeOut()) + str;
                        myRec.setText(str);
                        myCount++; //카운트 증가

                        break;
                    case Pause:
                        //핸들러를 멈춤
                        myTimer.removeMessages(0);

                        myBtnStart.setText(R.string.start);
                        myBtnRec.setText(R.string.record);
                        myOutput.setText("00:00:00");

                        cur_Status = Init;
                        myCount = 1;
                        myRec.setText("");
                        myBtnRec.setEnabled(false);

                        break;
                }
            }
        });


        advanceFunction = root.findViewById(R.id.advance);
        advanceFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.unsupport, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    //현재시간을 계속 구해서 출력하는 메소드
    String getTimeOut() {
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime / 1000 / 60, (outTime / 1000) % 60, (outTime % 1000) / 10);
        return easy_outTime;

    }
}