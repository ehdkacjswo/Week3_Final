package com.example.q.week3.Daily;

import android.graphics.drawable.Drawable;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by kuliza-195 on 11/28/16.
 */

public class Title extends ExpandableGroup<SubTitle> {

    private Drawable image;

    public Title(String title, List<SubTitle> items, Drawable image) {
        super(title, items);
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }
}
