package com.example.android.smartgovernance;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class adminmain extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    viewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmain);

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);

        adapter=new viewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new WaterSupply(),"WaterSupply");
        adapter.addFragments(new StreetLight(),"StreetLight");
        adapter.addFragments(new StrayAnimal(),"StrayAnimal");
        adapter.addFragments(new Drainage(),"Drainage");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);




    }


    public void onBackPressed() {
        Intent in = new Intent(this,NavigationActivity.class);
        startActivity(in);
    }
}
