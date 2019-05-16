package com.example.android.smartgovernance;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Riddhi on 05-Feb-18.
 */

public class SwipeAdapter extends PagerAdapter {
    private int[] imgs = {R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    private Context context;
    private LayoutInflater layoutInflater;


    public SwipeAdapter(Context context)
    {
        this.context=context;
    }



    @Override
    public int getCount() {
        return imgs.length;
    }


    @Override
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }
}

