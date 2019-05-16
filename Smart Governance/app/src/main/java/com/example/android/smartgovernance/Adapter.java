package com.example.android.smartgovernance;

/**
 * Created by Riddhi on 03-Apr-18.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyHolder> {

    private List<Main> List;
    android.content.Context context;

    public Adapter(List<Main> List) {
        this.List = List;


    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, title, mail, phn;

        public MyHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            phn = (TextView) view.findViewById(R.id.phn);
            mail = (TextView) view.findViewById(R.id.mail);
            name = (TextView) view.findViewById(R.id.name);


            // phn.setTag(R.integer.btn_plus_view, itemView);
            // btn_minus.setTag(R.integer.btn_minus_view, itemView);
            phn.setOnClickListener(this);
            mail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            if (v.getId() == mail.getId()) {

                String tos = mail.getText().toString();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{tos});
                // email.putExtra(Intent.EXTRA_SU BJECT, subs);
                // email.putExtra(Intent.EXTRA_TEXT, msgtxt);
                email.setType("message/rfc822");
                context.startActivity(Intent.createChooser(email, "Choose app to send"));

            } else if (v.getId() == phn.getId()) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9998216886"));
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    context.startActivity(callIntent);
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                else {
                    Toast.makeText(context,"Call is not permitted.",Toast.LENGTH_SHORT).show();
                }

                    }
            }

        }




    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_row, parent, false);
        Adapter.MyHolder myHolder = new Adapter.MyHolder(itemView);
        return myHolder;

        // add PhoneStateListener
       // PhoneCallListener phoneListener = new PhoneCallListener();
        //TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Main m = List.get(position);
        holder.title.setText(m.getTitle());
        holder.name.setText(m.getName());
        holder.phn.setText(m.getPhn());
        holder.mail.setText(m.getMail());

    }

    @Override
    public int getItemCount() {
        return List.size();
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
                    Intent i =context.getPackageManager()
                            .getLaunchIntentForPackage(context.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}