package com.example.e_covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    private ArrayList daydatelist;
    private ArrayList matakuliahlist;
    private ArrayList dosenlist;
    private ArrayList waktukehadiranlist;
    private ArrayList keteranganlist;
    Context context;

    RecyclerViewAdapter2(ArrayList daydatelist,ArrayList matakuliahlist,ArrayList dosenlist,ArrayList waktukehadiranlist,ArrayList keteranganlist,Context context){
        this.daydatelist = daydatelist;
        this.matakuliahlist = matakuliahlist;
        this.dosenlist = dosenlist;
        this.waktukehadiranlist = waktukehadiranlist;
        this.keteranganlist = keteranganlist;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mdaydate,mmatakuliah,mdosen,mwaktukehadiran,mketeranganlist;
        RelativeLayout relativeLayout;
        ViewHolder(View view){
            super(view);
            mdaydate = view.findViewById(R.id.dayanddatehistory);
            mmatakuliah = view.findViewById(R.id.matakuliahhistory);
            mdosen = view.findViewById(R.id.dosenhistory);
            mwaktukehadiran = view.findViewById(R.id.waktukehadiranhistory);
            mketeranganlist = view.findViewById(R.id.keteranganhistory);
            relativeLayout = view.findViewById(R.id.constraint2);
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showhistory,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter2.ViewHolder holder, int position) {
        final String daydate = (String)daydatelist.get(position);
        final String matakuliah = (String)matakuliahlist.get(position);
        final String dosen = (String)dosenlist.get(position);
        final String waktukehadiran = (String)waktukehadiranlist.get(position);
        final String keterangan = (String)keteranganlist.get(position);
        holder.mdaydate.setText(daydate);
        holder.mmatakuliah.setText(matakuliah);
        holder.mdosen.setText(dosen);
        holder.mwaktukehadiran.setText(waktukehadiran);
        holder.mketeranganlist.setText(keterangan);
    }

    @Override
    public int getItemCount() {
        return daydatelist.size();
    }
}
