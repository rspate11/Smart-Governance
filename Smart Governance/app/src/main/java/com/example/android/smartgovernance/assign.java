package com.example.android.smartgovernance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public  class assign extends AppCompatActivity {

     TextView text1, text2, text3, text4, text5, text6, text7, text8,textView;
    List<String> list;
    ListView listView;
    ImageView im;
    ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> array1 = new ArrayList<String>();
    ArrayList<String> array2 = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Bundle bundle,bundle2,bundlecomplain;
    Bundle bundle1,bundle3,bundleem,bundle4;
    Toolbar toolbar;
    boolean isImageFitToScreen;


    String name1,email1,area1,date1,category1,uid1,pno1,complain1,complainid1,status1,url;
    Spinner spinner;

    List<String> names ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        listView = (ListView) findViewById(R.id.listview);
        spinner = (Spinner)findViewById(R.id.spinner);
        textView=(TextView)findViewById(R.id.textView);
        im=(ImageView) findViewById(R.id.im);
        list = new ArrayList<String>();
        names = new ArrayList<String>();
        bundle = getIntent().getExtras();

        bundleem = getIntent().getExtras();

       //long r= listView.getItemIdAtPosition(0);
     //  textView.setText((int) r);

        if (bundle != null && bundleem!= null  ) {
            bundle1 = bundle.getBundle("some");
           bundlecomplain = bundleem.getBundle("some");
            array = bundle1.getStringArrayList("array");
           array2 = bundlecomplain.getStringArrayList("array");
            adapter = new ArrayAdapter<String>(assign.this, android.R.layout.simple_list_item_1, array);
            listView.setAdapter(adapter);
            uid1= (String) listView.getItemAtPosition(0);
            complainid1=array2.get(1);
            name1= array2.get(2);
            email1= array2.get(3);
            pno1=array2.get(4);
            area1= array2.get(5);
            complain1=array2.get(6);
            date1= array2.get(7);
            status1 =array2.get(8);
            array1.add(uid1);
            array1.add(complainid1);
            array1.add(name1);
            array1.add(email1);
            array1.add(pno1);
            array1.add(area1);
            //list1.add(category1);
            array1.add(complain1);
            array1.add(date1);
            array1.add(status1);

            bundle2=bundle.getBundle("ws");
          String  type = bundle2.getString("key");
          bundle4 =bundle.getBundle("u");
           url = bundle4.getString("url");

            Glide.with(this).load(url).into(im);


            if(type.equals("WaterSupply")){
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(assign.this, R.array.worker,
                android.R.layout.simple_spinner_item);
             adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
             spinner.setAdapter(adapter1);
         }

                else if (type.equals("StreetLight"))
         {

             final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(assign.this, R.array.workerstreet,
                     android.R.layout.simple_spinner_item);
             adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);

             spinner.setAdapter(adapter1);
         }
         else if(type.equals("StrayAnimal")){

             final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(assign.this, R.array.workeranimal,
                     android.R.layout.simple_spinner_item);
             adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
             spinner.setAdapter(adapter1);
         }
        else {

             final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(assign.this, R.array.workerdrainage,
                     android.R.layout.simple_spinner_item);
             adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
             spinner.setAdapter(adapter1);
         }


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String test=(String)adapterView.getItemAtPosition(i);
                    textView.setText( test);
                    if(test.equals("Worker Name")){

                    }
                    else {

                        bundle2=bundle.getBundle("ws");
                       String type1 = bundle2.getString("key");

                        Bundle bundle4 = new Bundle();
                        bundle4.putString("name", test);
                        bundle4.putString("url",url);
                        bundle4.putString("type" ,type1);
                        bundle4.putStringArrayList("complaininfo", array1);
                        Intent in = new Intent(assign.this, vrundainfo.class);
                        in.putExtra("complain", bundle4);
                        in.putExtra("u",bundle4);
                       in.putExtra("worker", bundle4);
                        in.putExtra("category", bundle4);

                        startActivity(in);
                        spinner.setSelection(0);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });


            //adapter.notifyDataSetChanged();
          /*  bundle.clear();
            bundle1.clear();
            bundle.remove("some");
            bundle2.clear();
            bundle.remove("ws");*/
           }

        im.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*if(isImageFitToScreen) {
                isImageFitToScreen=false;
                im.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                im.setAdjustViewBounds(true);
            }else{
                isImageFitToScreen=true;
                im.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                im.setScaleType(ImageView.ScaleType.FIT_XY);
            }*/


            Intent in = new Intent(assign.this, FullScreenImage.class);
             bundle3 = new Bundle();
            bundle3.putString("url",url);
            in.putExtra("u",bundle3);
            startActivity(in);

        }
    });



    }



    public void onBackPressed() {
        Intent intent = new Intent(this.getApplicationContext(), adminmain.class);
        array.clear();
       adapter.notifyDataSetChanged();
        finish();
       bundle.clear();
       bundle1.clear();
        bundle.remove("some");
        bundle2.clear();
        bundle.remove("ws");
        bundle.remove("u");


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}