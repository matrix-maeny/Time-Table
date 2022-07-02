package com.matrix_maeny.timetable.Weeks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrix_maeny.timetable.R;
import com.matrix_maeny.timetable.databases.CentralSharedData;

import java.util.ArrayList;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.viewHolder> {

    Context context;

    ArrayList<WeeksModel> list;
    WeeksFunctions listener;

    public WeeksAdapter(Context context, ArrayList<WeeksModel> list) {
        this.context = context;
        this.list = list;
        try {
            listener = (WeeksFunctions) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weeks_model, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        WeeksModel model = list.get(position);
        holder.weekName.setText(model.getWeekName());


        holder.weekName.setOnClickListener(v -> {

            CentralSharedData.weekPos = holder.getAdapterPosition();

            if(listener != null){
                listener.changeNameAndLoadData(model.getWeekName(),holder.getAdapterPosition());

            }

        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface WeeksFunctions {
        void changeNameAndLoadData(String name, int dayCode);
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView weekName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            weekName = itemView.findViewById(R.id.weekName);
        }
    }
}
