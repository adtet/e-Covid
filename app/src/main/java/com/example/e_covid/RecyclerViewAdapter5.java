package com.example.e_covid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter5 extends RecyclerView.Adapter<RecyclerViewAdapter5.ViewHolder> {
    private ArrayList startList;
    private ArrayList endList;
    private ArrayList matakuliahList;
    private ArrayList dosenList;
    Context context;

    RecyclerViewAdapter5(ArrayList startList,ArrayList endList,ArrayList matakuliahList,ArrayList dosenList,Context context){
        this.startList = startList;
        this.endList = endList;
        this.matakuliahList = matakuliahList;
        this.dosenList = dosenList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showjadwal1,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String start = (String)startList.get(position);
        final String  end = (String)endList.get(position);
        final String matakuliah = (String)matakuliahList.get(position);
        final String dosen = (String) dosenList.get(position);
        holder.mstart.setText(start);
        holder.mend.setText(end);
        holder.mmatakuliah.setText(matakuliah);
        holder.mdosen.setText(dosen);
        holder.layoutShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,take_pict_surat2.class);
                i.putExtra("matakuliah",matakuliah);
                i.putExtra("dosen",dosen);
                context.startActivity(i);
                ((showJadwal4)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return startList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mstart,mend,mmatakuliah,mdosen;
        RelativeLayout layoutShow;
        ViewHolder (View itemView){
            super(itemView);
            mstart = itemView.findViewById(R.id.startShowjadwal);
            mend = itemView.findViewById(R.id.endShowjadwal);
            mmatakuliah = itemView.findViewById(R.id.matakuliahShowjadwal);
            mdosen = itemView.findViewById(R.id.dosenShowjadwal);
            layoutShow = itemView.findViewById(R.id.layoutShow);
        }
    }


}
