package com.example.android.smartgovernance;

/**
 * Created by Riddhi on 22-Mar-18.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 3/21/2018.
 */
public class RecyclerviewAdapter1 extends RecyclerView.Adapter<RecyclerviewAdapter1.MyHolder>{

    List<WorkerContact> listdata;
    android.content.Context context;
    public RecyclerviewAdapter1(List<WorkerContact> listdata, android.content.Context applicationContext) {
        this.listdata = listdata;
        this.context =applicationContext;

    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview1,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        WorkerContact data = listdata.get(position);
        holder.uid.setText((data.getUid()));
        holder.vname.setText(data.getName());
        holder.vemail.setText(data.getEmail());
        holder.vaddress.setText(data.getArea());
        holder.vdate.setText((CharSequence) data.getIssued_date());
        holder.vcategory.setText(data.getCategory());
        holder.vcomplain.setText(data.getComplain());
        //holder.vurl.setText(data.getImageURL());
        holder.vpno.setText(data.getPno());
        holder.vassigned_date.setText(data.getAssigned_date());
        holder.vuid.setText(data.getUid());
        holder.vstatus.setText(data.getStatus());
        holder.vno_of_days.setText(data.getNo_of_days());
        holder.vcomplain_id.setText(data.getComplainid());


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vaddress,vemail,vpno,vurl,vcategory,vdate,vcomplain,uid,vassigned_date,vcomplain_id,vno_of_days,vstatus,vuid;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            uid=(TextView)itemView.findViewById(R.id.uid);
            vname = (TextView) itemView.findViewById(R.id.vname);
            vemail = (TextView) itemView.findViewById(R.id.vemail);
            vaddress = (TextView) itemView.findViewById(R.id.vaddress);
            vpno = (TextView)itemView.findViewById(R.id.vpno);
            vurl= (TextView) itemView.findViewById(R.id.vurl);
            vcategory = (TextView) itemView.findViewById(R.id.vcategory);
            vcomplain= (TextView) itemView.findViewById(R.id.vcomplain);
            vdate = (TextView)itemView.findViewById(R.id.vdate);
            vassigned_date = (TextView) itemView.findViewById(R.id.vassigndt);
            vcomplain_id = (TextView) itemView.findViewById(R.id.vcomplainid);
            vno_of_days = (TextView) itemView.findViewById(R.id.vno_of_days);
            vstatus = (TextView) itemView.findViewById(R.id.vstatus);
            vuid = (TextView) itemView.findViewById(R.id.vuid);
        }
    }


}