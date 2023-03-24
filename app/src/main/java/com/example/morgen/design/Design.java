package com.example.morgen.design;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.morgen.R;

public class Design {

    private ImageView view;
    private ImageButton button;

    public Design(ImageView view, ImageButton button) {
        this.view = view;
        this.button = button;
    }
    public void setLockedDesign() {
        view.setColorFilter(Color.argb(100, 0, 0, 0));
        view.getBackground().setTint(Color.argb(255, 222, 26, 26));
        button.setImageResource(R.drawable.locked_long_video_button);
    }
    public void setUnlockedDesign() {
        view.setColorFilter(Color.argb(0, 255, 255, 255));
        view.getBackground().setTint(Color.argb(255, 140, 140, 140));
        button.setImageResource(R.drawable.not_selected_button);
    }
    public void setAppliedDesign() {
        view.setColorFilter(Color.argb(0, 255, 255, 255));
        view.getBackground().setTint(Color.argb(255, 47, 207, 53));
        button.setImageResource(R.drawable.selected_button);
    } //0 150 136
}
