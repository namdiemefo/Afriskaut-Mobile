package com.naemo.afriscout.views.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class GlideBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {

        // set request options
        Glide.with(view.getContext()).load(url).into(view);
    }
}
