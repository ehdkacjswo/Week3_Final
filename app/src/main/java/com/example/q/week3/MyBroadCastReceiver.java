package com.example.q.week3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        switch (actionName){
            case "android.intent.action.SCREEN_ON":
                SharedPreferences data = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                int screen_on = data.getInt("screen_on", 0);
                screen_on++;

                if(screen_on < 5) Toast.makeText(context, "오늘은 핸드폰을 " + screen_on + "번밖에 켜지 않았군요! 멋져요!", Toast.LENGTH_SHORT).show();
                else if(screen_on < 10) Toast.makeText(context, screen_on + "번 핸드폰을 키셨군요! 이정도면 준수하죠!", Toast.LENGTH_SHORT).show();
                else if(screen_on < 15) Toast.makeText(context, "오늘은 벌써 핸드폰을 " + screen_on + "번 키셨는데, 조금 줄이는게 좋지 않을까요?", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "하루에 핸드폰을 " + screen_on + " 번이나 켜다니, 제정신입니까?", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = data.edit();
                editor.putInt("screen_on", screen_on);
                editor.commit();
                break;
            case "android.intent.action.DATE_CHANGED":
                SharedPreferences data1 = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = data1.edit();
                editor1.putInt("screen_on", 0);
                editor1.commit();
                break;
        }
    }
}