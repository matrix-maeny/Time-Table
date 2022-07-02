package com.matrix_maeny.timetable.schedules;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.matrix_maeny.timetable.R;
import com.matrix_maeny.timetable.databases.CentralSharedData;
import com.matrix_maeny.timetable.databases.ScheduleDataBase;
import com.matrix_maeny.timetable.dialogs.TimePickerFragment;

import java.util.Objects;

public class AddScheduleActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText taskName;
    TextView selectedTaskTime;
    AppCompatButton selectTimeBtn;
    String taskNameTxt = null;
    String taskTimeTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        initialize();

        if (CentralSharedData.model == null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("New schedule");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Edit");
            loadContent();
        }



    }

    private void loadContent() {
        taskName.setText(CentralSharedData.model.getTaskName());
        selectedTaskTime.setText(CentralSharedData.model.getTaskTime());
    }

    private void initialize() {
        taskName = findViewById(R.id.enteredTaskName);
        selectedTaskTime = findViewById(R.id.selectedTaskTime);
        selectTimeBtn = findViewById(R.id.selectTimeBtn);

        selectTimeBtn.setOnClickListener(selectBtnListener);
    }


    View.OnClickListener selectBtnListener = v -> {
        TimePickerFragment dialog = new TimePickerFragment();
        dialog.show(getSupportFragmentManager(), "time fragment");
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // save data

        if (saveData()) {
            finish();
            CentralSharedData.model = null;
        }

        return true;
    }

    private boolean saveData() {

        if (!checkTaskName()) {
            Toast.makeText(this, "please enter task name", Toast.LENGTH_SHORT).show();
            return false;

        }
        if (!checkTaskTime()) {
            Toast.makeText(this, "please set task time", Toast.LENGTH_SHORT).show();
            return false;

        }

        ScheduleDataBase dataBase = new ScheduleDataBase(AddScheduleActivity.this);

        if (CentralSharedData.model == null && !dataBase.insertData(CentralSharedData.weekPos,CentralSharedData.serialNo, taskNameTxt, taskTimeTxt)) {
            Toast.makeText(this, "can't save data", Toast.LENGTH_SHORT).show();
            return false;
        }else if(CentralSharedData.model != null) {

            if(dataBase.updateData(CentralSharedData.weekPos,CentralSharedData.currentSerialNo,taskNameTxt,taskTimeTxt)){
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "can't save data", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private boolean checkTaskTime() {
        try {
            if (selectedTaskTime.getText().toString().trim().equals("")) {
                return false;
            } else taskTimeTxt = selectedTaskTime.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean checkTaskName() {
        try {
            if (taskName.getText().toString().trim().equals("")) {
                return false;
            } else taskNameTxt = taskName.getText().toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String time = "" + hourOfDay + " : " + minute;
        CentralSharedData.time = time;
        selectedTaskTime.setText(time);
    }
}