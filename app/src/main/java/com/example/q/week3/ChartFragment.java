package com.example.q.week3;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    public ChartFragment() {
    }

    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        super.onCreate(savedInstanceState);

        pieChart = (PieChart) v.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        ArrayList<MainActivity.UsageDataControl.AppUsageInfo> chartdata = MainActivity.UsageDataControl.daily_data();

        for(int i = 0; i < Math.min(4, chartdata.size()); i++) yValues.add(new PieEntry(chartdata.get(i).timeInForeground, chartdata.get(i).appName));
        if(chartdata.size() > 4) {
            long etc = 0;
            for(int i = 4; i < chartdata.size(); i++) etc += chartdata.get(i).timeInForeground;
            yValues.add(new PieEntry(etc, "etc"));
        }

        Description description = new Description();
        description.setText("aaaaaa");
        description.setTextSize(15);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues,"Apps");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(159,138,209),Color.rgb(233,145,183),Color.rgb(244,185,111),Color.rgb(119,198,153),Color.rgb(130,183,227));

        PieData data = new PieData((dataSet));
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


        return v;
    }
}
