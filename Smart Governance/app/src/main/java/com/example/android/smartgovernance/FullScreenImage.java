package com.example.android.smartgovernance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenImage  extends Activity {




    Bundle bundle ,bundle1;
    ImageView imgDisplay;
    Button btnClose;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_image);

        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        btnClose = (Button) findViewById(R.id.btnClose);

        bundle = getIntent().getExtras();
        if (bundle != null) {

            bundle1 = bundle.getBundle("u");
            url = bundle1.getString("url");
        }

        Glide.with(this).load(url).into(imgDisplay);






        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FullScreenImage.this.finish();
            }
        });



    }


}

