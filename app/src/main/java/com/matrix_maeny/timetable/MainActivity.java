package com.matrix_maeny.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix_maeny.timetable.Weeks.WeeksAdapter;
import com.matrix_maeny.timetable.Weeks.WeeksModel;
import com.matrix_maeny.timetable.databases.CentralSharedData;
import com.matrix_maeny.timetable.databases.ScheduleDataBase;
import com.matrix_maeny.timetable.schedules.AddScheduleActivity;
import com.matrix_maeny.timetable.schedules.ScheduleAdapter;
import com.matrix_maeny.timetable.schedules.ScheduleModel;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements WeeksAdapter.WeeksFunctions, ScheduleAdapter.Refresh {

    RecyclerView recyclerViewWeeks, recyclerViewSchedules;
    final ArrayList<WeeksModel> listWeeks = new ArrayList<>();
    final ArrayList<ScheduleModel> listSchedules = new ArrayList<>();

    WeeksAdapter adapterWeeks = null;
    ScheduleAdapter adapterSchedules = null;

    final Handler handler = new Handler();
    private ScheduleDataBase dataBase = null;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Monday");

        initialize();
    }

    private void initialize() {
        recyclerViewSchedules = findViewById(R.id.recyclerViewTimings);
        recyclerViewWeeks = findViewById(R.id.recyclerViewWeeks);
        emptyView = findViewById(R.id.emptyTxtView);

        String[] days = {getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday)};

        for (String x : days) listWeeks.add(new WeeksModel(x));

        adapterWeeks = new WeeksAdapter(MainActivity.this, listWeeks);
        adapterSchedules = new ScheduleAdapter(MainActivity.this, listSchedules);

        recyclerViewWeeks.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSchedules.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recyclerViewWeeks.setAdapter(adapterWeeks);
        recyclerViewSchedules.setAdapter(adapterSchedules);

        loadInfo(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadInfo(int dayCode) {
        dataBase = new ScheduleDataBase(MainActivity.this);
        Cursor cursor = dataBase.getData(dayCode);

        int serialNo = 1;
        String taskName = null, taskTime = null;

        if (cursor.getCount() != 0) {
            emptyView.setVisibility(View.GONE);
            listSchedules.clear();

            while (cursor.moveToNext()) {
                serialNo = cursor.getInt(0);
                taskName = cursor.getString(1);
                taskTime = cursor.getString(2);

                listSchedules.add(new ScheduleModel(serialNo, taskName, taskTime));
            }


        } else {
            listSchedules.clear();
            emptyView.setVisibility(View.VISIBLE);

        }
        handler.post(() -> adapterSchedules.notifyDataSetChanged());

        dataBase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addData) {
            // add data here

            addSchedule();

        } else {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }


        return true;
    }

    private void addSchedule() {
        // add schedules here
        CentralSharedData.model = null;
        CentralSharedData.serialNo = listSchedules.size() + 1;
        CentralSharedData.currentSerialNo = -1;
        startActivity(new Intent(MainActivity.this, AddScheduleActivity.class));
    }


    @Override
    public void changeNameAndLoadData(String name, int dayCode) {
        handler.post(() -> Objects.requireNonNull(getSupportActionBar()).setTitle(name));

        loadInfo(dayCode);
    }


    @Override
    protected void onStart() {
        loadInfo(CentralSharedData.weekPos);
        super.onStart();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void refreshLayout() {
        loadInfo(CentralSharedData.weekPos);
    }


}