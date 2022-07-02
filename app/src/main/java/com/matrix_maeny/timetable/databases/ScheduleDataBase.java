package com.matrix_maeny.timetable.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScheduleDataBase extends SQLiteOpenHelper {

    private final SQLiteDatabase database;

    public ScheduleDataBase(@Nullable Context context) {
        super(context, "Schedule.db", null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        db.execSQL("Create Table Monday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Tuesday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Wednesday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Thursday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Friday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Saturday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");
        db.execSQL("Create Table Sunday(serialNo INT primary key, taskName TEXT, taskTime TEXT)");


    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop Table if exists Monday");
        db.execSQL("drop Table if exists Tuesday");
        db.execSQL("drop Table if exists Wednesday");
        db.execSQL("drop Table if exists Thursday");
        db.execSQL("drop Table if exists Friday");
        db.execSQL("drop Table if exists Saturday");
        db.execSQL("drop Table if exists Sunday");

    }


    public boolean insertData(int dayCode,int serialNo, String taskName, String taskTime) {
        ContentValues cv = new ContentValues();


        cv.put("serialNo",serialNo);
        cv.put("taskName", taskName);
        cv.put("taskTime", taskTime);


        long result;// = database.insert()

        switch (dayCode) {
            case 0: // monday
                result = database.insert("Monday", null, cv);
                break;
            case 1:
                result = database.insert("Tuesday", null, cv);
                break;
            case 2:
                result = database.insert("Wednesday", null, cv);
                break;
            case 3:
                result = database.insert("Thursday", null, cv);
                break;
            case 4:
                result = database.insert("Friday", null, cv);
                break;
            case 5:
                result = database.insert("Saturday", null, cv);
                break;
            case 6:
                result = database.insert("Sunday", null, cv);
                break;
            default:
                result = -1;
        }
        return result != -1;
    }

    public boolean updateData(int dayCode, int serialNo, String taskName, String taskTime) {
        ContentValues cv = new ContentValues();

        cv.put("taskName", taskName);
        cv.put("taskTime", taskTime);

        long result;// = database.insert()

        switch (dayCode) {
            case 0: // monday
                result = database.update("Monday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 1:
                result = database.update("Tuesday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 2:
                result = database.update("Wednesday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 3:
                result = database.update("Thursday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 4:
                result = database.update("Friday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 5:
                result = database.update("Saturday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 6:
                result = database.update("Sunday", cv, "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            default:
                result = -1;
        }
        return result != -1;
    }

    public boolean deleteData(int dayCode, int serialNo) {

        long result;// = database.insert()

        switch (dayCode) {
            case 0: // monday
                result = database.delete("Monday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 1:
                result = database.delete("Tuesday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 2:
                result = database.delete("Wednesday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 3:
                result = database.delete("Thursday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 4:
                result = database.delete("Friday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 5:
                result = database.delete("Saturday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            case 6:
                result = database.delete("Sunday", "serialNo=?", new String[]{String.valueOf(serialNo)});
                break;
            default:
                result = -1;
        }
        return result != -1;
    }

    public boolean deleteAllData(int dayCode) {

        long result;// = database.insert()

        switch (dayCode) {
            case 0: // monday
                result = database.delete("Monday", null, null);
                break;
            case 1:
                result = database.delete("Tuesday", null, null);
                break;
            case 2:
                result = database.delete("Wednesday", null, null);
                break;
            case 3:
                result = database.delete("Thursday", null, null);
                break;
            case 4:
                result = database.delete("Friday", null, null);
                break;
            case 5:
                result = database.delete("Saturday", null, null);
                break;
            case 6:
                result = database.delete("Sunday", null, null);
                break;
            default:
                result = -1;
        }
        return result != -1;
    }

    @SuppressLint("Recycle")
    public Cursor getData(int dayCode) {

        Cursor cursor;// = database.insert()

        switch (dayCode) {
            case 0: // monday
                cursor = database.rawQuery("Select * from Monday", null);
                break;
            case 1:
                cursor = database.rawQuery("Select * from Tuesday", null);
                break;
            case 2:
                cursor = database.rawQuery("Select * from Wednesday", null);
                break;
            case 3:
                cursor = database.rawQuery("Select * from Thursday", null);
                break;
            case 4:
                cursor = database.rawQuery("Select * from Friday", null);
                break;
            case 5:
                cursor = database.rawQuery("Select * from Saturday", null);
                break;
            case 6:
                cursor = database.rawQuery("Select * from Sunday", null);
                break;
            default:
                cursor = null;
        }
        return cursor;
    }
}
