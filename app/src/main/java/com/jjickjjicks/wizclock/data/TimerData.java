package com.jjickjjicks.wizclock.data;

import java.util.Locale;

public class TimerData {
    public int timeCnt;
    public long mTimeLeftInMillis;

    public int getTimeCnt(){
        return timeCnt;
    }

    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    public int getHour(){
        return (int) (mTimeLeftInMillis / 1000) / 3600;
    }

    public int getMin(){
        return (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
    }

    public int getSec(){
        return (int) (mTimeLeftInMillis / 1000) % 60;
    }

    public String getTime(){
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour(), getMin(), getSec());
    }
}
