package com.example.android.smartgovernance;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Button scan;
    TextView marquee1;


    GridView gridView;

    static final String[] MOBILE_OS = new String[] {
            "About Us", "Contact Us"};


    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());

        mViewPager = (ViewPager)rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        marquee1 = (TextView)rootView.findViewById(R.id.marquee1);

        marquee1.setSelected(true);


        gridView = (GridView)rootView.findViewById(R.id.gridView1);

        gridView.setAdapter(new ImageAdapter(getActivity(), MOBILE_OS));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String s = (String) ((TextView) v.findViewById(R.id.grid_item_label)).getText();
               if (s.equals("Contact Us")){
                   Intent in = new Intent(getActivity(), MainActivity.class);
                   startActivity(in);
               }

            }
        });


        return rootView;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




}




