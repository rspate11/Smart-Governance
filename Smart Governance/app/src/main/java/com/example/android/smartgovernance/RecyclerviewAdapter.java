package com.example.android.smartgovernance;

/**
 * Created by Riddhi on 22-Feb-18.
 */

import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder>{

    List<Contact> listdata;

    android.content.Context context;
    public RecyclerviewAdapter(List<Contact> listdata, android.content.Context applicationContext) {
        this.listdata = listdata;
        this.context =applicationContext;

    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Contact data = listdata.get(position);
        holder.uid.setText((data.getUid()));
        holder.vst.setText((data.getStatus()));
        holder.cid.setText((data.getComplainid()));
        holder.vname.setText(data.getName());
        holder.vemail.setText(data.getEmail());
        holder.vaddress.setText(data.getArea());
        holder.vdate.setText((CharSequence) data.getDate());
        holder.vcategory.setText(data.getCategory());
        holder.vcomplain.setText(data.getComplain());
        //holder.vurl.setText(data.getImageURL());
        holder.vpno.setText(data.getPno());
        Glide.with(context).load(data.getImageURL()).into(holder.imageView);
        //String r="Submitted";
          /*  if(data.getStatus().equals(r)){
                holder.rootView.setBackgroundResource(R.color.black);
            } else {
                holder.rootView.setBackgroundResource(R.color.white);
            }*/
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder{
        TextView vname , vaddress,vemail,vpno,vurl,vcategory,vdate,vcomplain,uid,cid,vst;
        ImageView imageView;
        LinearLayout rootView;



        public MyHolder(View itemView) {
            super(itemView);
            rootView=(LinearLayout)itemView.findViewById(R.id.rootView);
            uid=(TextView)itemView.findViewById(R.id.uid);
            vst=(TextView)itemView.findViewById(R.id.vst);
            cid=(TextView)itemView.findViewById(R.id.cid);
            vname = (TextView) itemView.findViewById(R.id.vname);
            vemail = (TextView) itemView.findViewById(R.id.vemail);
            vaddress = (TextView) itemView.findViewById(R.id.vaddress);
            vpno = (TextView)itemView.findViewById(R.id.vpno);
            vurl= (TextView) itemView.findViewById(R.id.vurl);
            vcategory = (TextView) itemView.findViewById(R.id.vcategory);
            vcomplain= (TextView) itemView.findViewById(R.id.vcomplain);
            vdate = (TextView)itemView.findViewById(R.id.vdate);
            imageView=(ImageView)itemView.findViewById(R.id.ShowImageView);

        }
    }



}