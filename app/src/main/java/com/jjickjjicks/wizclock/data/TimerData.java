package com.jjickjjicks.wizclock.data;

import java.util.Locale;

public class TimerData {
    private int timeCnt;
    private long mTimeLeftInMillis;

    public void setTimeCnt(int timeCnt) {
        this.timeCnt = timeCnt;
    }

    public void setmTimeLeftInMillis(long mTimeLeftInMillis) {
        this.mTimeLeftInMillis = mTimeLeftInMillis;
    }

    public int getTimeCnt() {
        return timeCnt;
    }

    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    public int getHour() {
        return (int) (mTimeLeftInMillis / 1000) / 3600;
    }

    public int getMin() {
        return (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
    }

    public int getSec() {
        return (int) (mTimeLeftInMillis / 1000) % 60;
    }

    public String getTime() {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", getHour(), getMin(), getSec());
    }
}
