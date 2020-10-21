package com.example.e_covid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList startList;
    private ArrayList endList;
    private ArrayList matakuliahList;
    private ArrayList dosenList;
    RecyclerViewAdapter(ArrayList startList,ArrayList endList,ArrayList matakuliahList,ArrayList dosenList){
        this.startList = startList;
        this.endList = endList;
        this.matakuliahList = matakuliahList;
        this.dosenList = dosenList;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mstart,mend,mmatakuliah,mdosen;

        ViewHolder (View itemView){
            super(itemView);
            mstart = itemView.findViewById(R.id.startShowjadwal);
            mend = itemView.findViewById(R.id.endShowjadwal);
            mmatakuliah = itemView.findViewById(R.id.matakuliahShowjadwal);
            mdosen = itemView.findViewById(R.id.dosenShowjadwal);
        }

    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    }

    @Override
    public int getItemCount() {
        return startList.size();
    }
}
