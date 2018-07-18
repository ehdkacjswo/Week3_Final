package com.example.q.week3;

import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private static UsageStatsManager usageStatsManager;
    private static PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);

        if (!isAccessGranted()) {
            //어플을 사용하기 위해서는 ~를 ~로 설정해줘야 한다 띄우기
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);

            if(!isAccessGranted()) {
                //toast 메시지로 설명
                finish();
            }
        }

        usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        packageManager = getPackageManager();

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("EVALUATION");
        tabLayout.getTabAt(1).setText("REPORT");
        tabLayout.getTabAt(2).setText("CHART");
        tabLayout.getTabAt(3).setText("GRAPH");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tag_faces_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_description_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_pie_chart_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_timeline_black_24dp);

        Intent backgroundService = new Intent(getApplicationContext(), MyService.class);
        startService(backgroundService);
    }

    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private static long back_pressed;

    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "앱을 종료하시려면 뒤로가기 한번 더!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }


    public static class UsageDataControl{
        private static ArrayList<AppUsageInfo> usageInfos;
        private static long phoneUsageToday;

        public static class AppUsageInfo {
            Drawable appIcon;
            String appName, packageName;
            long timeInForeground;
            int launchCount;

            AppUsageInfo(String packageName) {
                this.packageName = packageName;
            }
        }

        private static boolean AppFilter(ApplicationInfo info) {
            if((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) return true;
            if((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) return true;
            return false;
        }

        private static void Daily_data(int date_past) {
            UsageEvents.Event currentEvent;
            List<UsageEvents.Event> allEvents = new ArrayList<>();
            HashMap<String, AppUsageInfo> map = new HashMap<>();
            phoneUsageToday = 0;

            Calendar begin_cal = Calendar.getInstance();
            Calendar end_cal = Calendar.getInstance();

            begin_cal.add(Calendar.DAY_OF_MONTH, - date_past);
            begin_cal.set(Calendar.HOUR_OF_DAY, 0);

            end_cal.add(Calendar.DAY_OF_MONTH, - date_past);
            end_cal.set(Calendar.HOUR_OF_DAY, 23);

            long starttime = (begin_cal.getTimeInMillis() / 3600000) * 3600000;
            long endtime = (end_cal.getTimeInMillis() / 3600000) * 3600000 + 3599999;

            UsageEvents usageEvents = usageStatsManager.queryEvents(starttime, endtime);

            while (usageEvents.hasNextEvent()) {
                currentEvent = new UsageEvents.Event();
                usageEvents.getNextEvent(currentEvent);
                if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                        currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                    allEvents.add(currentEvent);
                    String key = currentEvent.getPackageName();
// taking it into a collection to access by package name
                    if (map.get(key)==null){
                        map.put(key, new AppUsageInfo(key));
                    }
                }
            }

            for (int i = 0; i < allEvents.size() - 1; i++){
                UsageEvents.Event E0 = allEvents.get(i);
                UsageEvents.Event E1 = allEvents.get(i+1);

                if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND){
                    map.get(E1.getPackageName()).launchCount++;
                }

                if (E0.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND && E1.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND
                        && E0.getClassName().equals(E1.getClassName())){
                    long diff = E1.getTimeStamp() - E0.getTimeStamp();
                    phoneUsageToday += diff;
                    map.get(E0.getPackageName()).timeInForeground += diff;
                    //i++;
                }
            }

            usageInfos = new ArrayList<>(map.values());

            for(int i = 0; i < usageInfos.size(); i++) {
                String packageName = usageInfos.get(i).packageName;
                ApplicationInfo applicationInfo;

                try{
                    applicationInfo = packageManager.getApplicationInfo(packageName, 0);
                } catch (PackageManager.NameNotFoundException e) {continue;}

                if(!AppFilter(applicationInfo)) {
                    usageInfos.remove(i);
                    i--;
                    continue;
                }


                Drawable icon = applicationInfo.loadIcon(packageManager);
                String appname = applicationInfo.loadLabel(packageManager).toString();

                usageInfos.get(i).appIcon = icon;
                usageInfos.get(i).appName = appname;
            }
        }

        public static ArrayList<Float> graph_data() {
            ArrayList<Float> rt = new ArrayList<>();

            for(int i = 7; i > 0 ; i--) {
                Daily_data(i);
                rt.add((float)phoneUsageToday / 3600000);
            }

            return rt;
        }

        public static ArrayList<AppUsageInfo> daily_data() {
            Daily_data(0);
            Collections.sort(usageInfos, new Comparator<AppUsageInfo>() {
                @Override
                public int compare(AppUsageInfo info1, AppUsageInfo info2) {
                    if(info1.timeInForeground > info2.timeInForeground) return -1;
                    else if(info1.timeInForeground < info2.timeInForeground) return 1;
                    else return 0;
                }
            });

            return usageInfos;
        }

        public static long eval_data() {
            Daily_data(1);
            return phoneUsageToday;
        }
    }
}
