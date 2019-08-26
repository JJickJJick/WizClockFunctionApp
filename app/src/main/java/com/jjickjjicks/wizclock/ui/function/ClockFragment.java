package com.jjickjjicks.wizclock.ui.function;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jjickjjicks.wizclock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ClockFragment extends Fragment {
    private TextView timezone;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clock, container, false);

        timezone = root.findViewById(R.id.timezone);

        TimeZone tz = TimeZone.getDefault();
        timezone.setText(tz.getID());

        return root;
    }
}