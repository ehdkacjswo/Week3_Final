package com.example.q.week3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class EvaluationFragment extends Fragment {
    GifImageView rabbit;
    GifImageView g1;
    GifImageView g2;
    TextView textView;
    Integer x;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evaluation, container, false);
        // Inflate the layout for this fragment


        rabbit = (GifImageView)v.findViewById(R.id.rabbit);
        textView = (TextView)v.findViewById(R.id.evtext);
        g1 = (GifImageView)v.findViewById(R.id.g1);
        g2 = (GifImageView)v.findViewById(R.id.g2);

        Random rnd = new Random();
        x = rnd.nextInt(10);
        //x = MainActivity.UsageDataControl.eval_data();
        //x = 1;
        if (x<=1){

            rabbit.setImageResource(R.drawable.aa);
            g1.setImageResource(R.drawable.aaa);
            g2.setImageResource(R.drawable.happy);
            textView.setText("[ 와 대단해요!이상태를 계속 유지하세요! ]");
        }
        else if (x>1 && x<=3){

            rabbit.setImageResource(R.drawable.bb);
            g1.setImageResource(R.drawable.bbb);
            g2.setImageResource(R.drawable.step2);
            textView.setText("[ 아주 좋아요!더 줄이면 더 좋겠죠?? ]");
        }
        else if (x>3 && x<=5){

            rabbit.setImageResource(R.drawable.cc);
            g1.setImageResource(R.drawable.ccc);
            g2.setImageResource(R.drawable.step3);
            textView.setText("[ 음..더 노력하세요! ]");
        }
        else if (x>5 && x<=7){

            rabbit.setImageResource(R.drawable.dd);
            g1.setImageResource(R.drawable.ddd);
            g2.setImageResource(R.drawable.giphy);
            textView.setText("[ 이만큼이나??정신 차리세요!! ]");
        }
        else if (x>7){

            rabbit.setImageResource(R.drawable.ee);
            g1.setImageResource(R.drawable.throwaway);
            g2.setImageResource(R.drawable.step5);
            textView.setText("[ 심각하군요.지금 당장 끄세요! ]");
        }
        return v;
    }
}
