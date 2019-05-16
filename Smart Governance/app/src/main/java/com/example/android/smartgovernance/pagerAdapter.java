package com.example.android.smartgovernance;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Riddhi on 17-Feb-18.
 */

public class pagerAdapter extends FragmentPagerAdapter{


    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return null;
    }

    private int[] imgs = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    private Context context;
    private LayoutInflater layoutInflater;


  /*  public android.support.v4.app.Fragment getItem(int position) {
        return imgs.get(position);
    }*/

    @Override
    public int getCount() {
        return imgs.length;
    }

    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView=(ImageView)item_view.findViewById(R.id.image_view);
        TextView textView=(TextView)item_view.findViewById(R.id.image_count);
        imageView.setImageResource(imgs[position]);
        textView.setText("image : "+position);
        container.addView(item_view);

        return item_view;
    }

}
