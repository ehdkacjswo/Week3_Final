package com.example.q.week3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import co.blankkeys.animatedlinegraphview.AnimatedLineGraphView;

public class GraphFragment extends Fragment {
    public GraphFragment() {
    }

    private LineChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        super.onCreate(savedInstanceState);

        mChart = (LineChart) v.findViewById(R.id.linechart);


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getAxisLeft().setDrawLabels(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.setTouchEnabled(true);


        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setEnabled(true);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(20f);

        YAxis yAxis2 = mChart.getAxisRight();
        yAxis2.setEnabled(false);

        mChart.setDrawBorders(false);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Float> graph_data = MainActivity.UsageDataControl.graph_data();

        for(int i = 0; i < 7; i++)  yValues.add(new Entry(i, graph_data.get(i)));

        LineDataSet set1 = new LineDataSet(yValues, "");

        set1.setFillAlpha(110);

        set1.setColor(Color.rgb(116,205,199));
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);

        ArrayList<ILineDataSet>dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        set1.isDrawCirclesEnabled();
        set1.setCircleRadius(5f);
        set1.setCircleColor(Color.rgb(255,139,139));
        set1.setDrawValues(false);
        mChart.getLegend().setEnabled(false);

        mChart.setData(data);
        return v;
    }
}
