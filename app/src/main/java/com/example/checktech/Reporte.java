package com.example.checktech;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checktech.databinding.ActivityAbcAulaBinding;
import com.example.checktech.databinding.ActivityReporteBinding;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
public class Reporte extends menu {

    ActivityReporteBinding activityReporteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReporteBinding = ActivityReporteBinding.inflate(getLayoutInflater());
        setContentView(activityReporteBinding.getRoot());

    }

}