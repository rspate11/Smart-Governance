package com.example.android.smartgovernance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.TextView;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;



public class departments extends Fragment implements OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5;

    TextView streetlight,watersupply,drainage,animal,phone,email;
    String ws,sl,dn,sa;
    View view;
    Button ex1,ex2,ex3,ex4;

    public departments()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.fragment_departments, container, false);
        super.onCreate(savedInstanceState);
        watersupply=(TextView)view.findViewById(R.id.watersupply);
        streetlight=(TextView)view.findViewById(R.id.streetlight);
        drainage=(TextView)view.findViewById(R.id.drainage);
        animal=(TextView)view.findViewById(R.id.animal);
        phone=(TextView)view.findViewById(R.id.phone);
        email=(TextView)view.findViewById(R.id.email);
         ex1=(Button)view.findViewById(R.id.ex1);
        ex2=(Button)view.findViewById(R.id.ex2);
        ex3=(Button)view.findViewById(R.id.ex3);
        ex4=(Button)view.findViewById(R.id.ex4);

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);


        ws="1. The approximate population of the city is 14,00,000. The city is supplied on an average 53.2 million gallon (240 million Ltrs) of water per day. Accordingly the present water supply is 38 gallon (190 Ltrs) per person per day. Generally this supply can be termed satisfactory. \n" +
                "\n" +
                "2. Water Treatment \n" +
                "\n" +
                "A. Water received from the French well of the river Mahi needs no special treatment as it comes through the natural layers of sand, which purifies it. The quality of such water is considered very good.\n" +
                "B. Water received from Ajwa Sarovar is treated and filtered at Nimeta Water Purification Plant. The capacity of the Nimeta treatment plant is 10 million gallon per day. The plant can also be run at a 20% additional capacity.\n" +
                "C. Chlorination.\n" +
                "Post chlorination is practiced at various water distribution centers (water tanks) with a view to providing high quality potable water to the consumers. This facilitates to provide necessary residual chlorine to the consumers.\n";
        watersupply.setText(ws);
        sl="April to September\t1:00 PM to 9:00 PM\n" +
                "October to March\t12:00 AM to 8:00 PM\n" +
                "Administration \n" +
                "Ward\tContact Number \n" +
                "(Land Line)\tForeman\n" +

                "Ward-1/2:\t2462367\t9978958596\t9879613683\n" +
                "Ward-3:\t2582487\t9909915259\t9879613684\n" +
                "Ward-4:\t2651020\t9879615034\t9687606304\n" +
                "Ward-6:\t2310569\t9978958597\t9879613681\n" +
                "Ward-7:\t2794882 (Privatization)\t9824403188\t9879613682";
        streetlight.setText(sl);

        dn="Till 2001 out of the total city area 108.26 Sq.Km., 25% area present had been\n" +
                "covered with SWD systems. In 2012, With the increase in the VMSS limits\n" +
                "from 108.26 Sq.Km. to 158.70 Sq.Km. the coverage of SWD systems had\n" +
                "gone down from 25% to 10% with respect to area of 158.70 Sq.Km.\n" +
                "To provide a better quality of life and to make Vadodara a self reliant and\n" +
                "sustainable city with all basic amenities. Our commitment was for 100%\n" +
                "coverage in terms of geographical developed area of 108.26 Sq.Km. of the city\n" +
                "by providing Comprehensive Storm Water Drainage System by the end of\n" +
                "December 2012 which has been achieved.\n" +
                "The functions of the Department include SWD Planning, designing, Estimating,\n" +
                "tendering, executing and Operating as well as maintaining the SWD Systems\n" +
                "(maintenance works are carried out by Zone Offices) which consists of SWD\n" +
                "Network.\n";
        drainage.setText(dn);
        sa="";

        setListeners();



        return view;
    }

    private void setListeners() {

        ex1.setOnClickListener(this);
        ex2.setOnClickListener(this);
        ex3.setOnClickListener(this);
        ex4.setOnClickListener(this);
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ex1:


                expandableLayout1 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout1);

                expandableLayout1.toggle(); // toggle expand and collapse
                break;

            case R.id.ex2:

                expandableLayout2 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout2);

                expandableLayout2.toggle(); // toggle expand and collapse
                break;
            case R.id.ex3:

                expandableLayout3 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout3);

                expandableLayout3.toggle(); // toggle expand and collapse
                break;

            case R.id.ex4:

                expandableLayout4 = (ExpandableRelativeLayout)view.findViewById(R.id.expandableLayout4);

                expandableLayout4.toggle(); // toggle expand and collapse
                break;



            case R.id.phone:

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9998216886"));
                startActivity(callIntent);
                break;
            case R.id.email:
                String tos = email.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{tos});
                // email.putExtra(Intent.EXTRA_SU BJECT, subs);
               // email.putExtra(Intent.EXTRA_TEXT, msgtxt);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose app to send"));

            break;
        }


    }

   // private class PhoneCallListener {
//monitor phone call activities
   public class PhoneCallListener extends PhoneStateListener {

       private boolean isPhoneCalling = false;

       String LOG_TAG = "LOGGING 123";

       @Override
       public void onCallStateChanged(int state, String incomingNumber) {

           if (TelephonyManager.CALL_STATE_RINGING == state) {
               // phone ringing
               Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
           }

           if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
               // active
               Log.i(LOG_TAG, "OFFHOOK");

               isPhoneCalling = true;
           }

           if (TelephonyManager.CALL_STATE_IDLE == state) {
               // run when class initial and phone call ended,
               // need detect flag from CALL_STATE_OFFHOOK
               Log.i(LOG_TAG, "IDLE");

               if (isPhoneCalling) {

                   Log.i(LOG_TAG, "restart app");

                   // restart app
                   Intent i = getActivity().getBaseContext().getPackageManager()
                           .getLaunchIntentForPackage(
                                   getActivity().getBaseContext().getPackageName());
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(i);

                   isPhoneCalling = false;
               }

           }
       }
   }





}