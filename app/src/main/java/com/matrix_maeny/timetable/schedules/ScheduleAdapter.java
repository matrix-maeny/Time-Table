package com.matrix_maeny.timetable.schedules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.matrix_maeny.timetable.R;
import com.matrix_maeny.timetable.databases.CentralSharedData;
import com.matrix_maeny.timetable.databases.ScheduleDataBase;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.viewHolder> {

    Context context;
    ArrayList<ScheduleModel> list;
    Refresh refresh = null;

    public ScheduleAdapter(Context context, ArrayList<ScheduleModel> list) {
        this.context = context;
        this.list = list;
        refresh = (Refresh) context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedules_model, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ScheduleModel model = list.get(position);

        holder.taskName.setText(model.getTaskName());
        holder.taskTime.setText(model.getTaskTime());
        holder.serialNo.setText(model.getSerialNo());

        holder.cardView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), holder.cardView);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()) {
                    case R.id.delete_item:
                        //delete specific item
                        deleteData(holder.getAdapterPosition());
                        break;
                    case R.id.delete_all_items:
                        // delete all items from the data base
                        deleteAllData();
                        break;
                }

                return true;
            });
            popupMenu.show();
            return true;
        });

        holder.cardView.setOnClickListener(v -> {
            CentralSharedData.model = model;
            CentralSharedData.currentSerialNo = holder.getAdapterPosition() + 1;
            context.startActivity(new Intent(context.getApplicationContext(), AddScheduleActivity.class));
        });

    }


    private void deleteData(int serialNo) {
        ScheduleDataBase dataBase = new ScheduleDataBase(context.getApplicationContext());
        if (dataBase.deleteData(CentralSharedData.weekPos, serialNo + 1)) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error deleting data..", Toast.LENGTH_SHORT).show();
        }
        dataBase.close();
        reArrange();

    }

    private void deleteAllData() {
        ScheduleDataBase dataBase = new ScheduleDataBase(context.getApplicationContext());
        if (dataBase.deleteAllData(CentralSharedData.weekPos)) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error deleting data..", Toast.LENGTH_SHORT).show();
        }
        dataBase.close();
        refresh.refreshLayout();
    }

    private void reArrange() {

        ArrayList<ScheduleModel> tempList = new ArrayList<>();
        ScheduleDataBase dataBase = new ScheduleDataBase(context.getApplicationContext());
        Cursor cursor = dataBase.getData(CentralSharedData.weekPos);

        int x = 1;
        while (cursor.moveToNext()) {
            tempList.add(new ScheduleModel(x, cursor.getString(1), cursor.getString(2)));
        }

        deleteAllData();


        x = 1;
        for (ScheduleModel model : tempList) {
            if (!dataBase.insertData(CentralSharedData.weekPos, x, model.getTaskName(), model.getTaskTime())) {
                Toast.makeText(context, "Error re-arranging data", Toast.LENGTH_LONG).show();
                break;
            } else {
                Toast.makeText(context, "succeed", Toast.LENGTH_LONG).show();

            }
            x++;
        }


        refresh.refreshLayout();
    }

//    private boolean re_AddAllData() {
//        ScheduleDataBase dataBase = new ScheduleDataBase(context.getApplicationContext());
//        int x = 1;
//        dataBase.close();
//        return true;
//    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Refresh {
        void refreshLayout();

    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView taskName, taskTime;//,serialNo;
        TextView serialNo;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskTime = itemView.findViewById(R.id.taskTimeTextView);
            serialNo = itemView.findViewById(R.id.serialNoTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
