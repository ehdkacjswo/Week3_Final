package com.example.q.week3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.week3.Daily.RecyclerAdapter;
import com.example.q.week3.Daily.SubTitle;
import com.example.q.week3.Daily.Title;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);

        List<Title> list = getList();
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        return v;
    }

    private String timeFormat(long time) {
        int h , m , s;
        time /= 1000;
        s = (int)(time % 60);

        time /= 60;
        m = (int)(time % 60);

        time /= 60;
        h = (int)(time % 60);

        return h + "시간 " + m + "분 " + s + "초";
    }

    private List<Title> getList() {
        List<Title> list = new ArrayList<>();
        ArrayList<MainActivity.UsageDataControl.AppUsageInfo> daily_data = MainActivity.UsageDataControl.daily_data();

        for (int i = 0; i < daily_data.size(); i++) {
            List<SubTitle> subTitles = new ArrayList<>();

            SubTitle subTitle = new SubTitle("사용시간 : " + timeFormat(daily_data.get(i).timeInForeground));
            subTitles.add(subTitle);

            subTitle = new SubTitle("사용횟수 : " + daily_data.get(i).launchCount + "회");
            subTitles.add(subTitle);

            Title model = new Title(daily_data.get(i).appName, subTitles, daily_data.get(i).appIcon);
            list.add(model);
        }
        return list;
    }
}
