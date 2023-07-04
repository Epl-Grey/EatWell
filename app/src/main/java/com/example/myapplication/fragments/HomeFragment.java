package com.example.myapplication.fragments;

import static com.example.myapplication.calendar.CalendarUtils.daysInWeekArray;
import static com.example.myapplication.calendar.CalendarUtils.monthYearFromDate;
import static com.example.myapplication.calendar.CalendarUtils.numberOfDays;
import static com.example.myapplication.calendar.CalendarUtils.selectedDate;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.DishManager;
import com.example.myapplication.R;
import com.example.myapplication.calendar.CalendarAdapter;
import com.example.myapplication.calendar.CalendarUtils;
import com.example.myapplication.dishSimpleAdapter;
import com.example.myapplication.dishView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    ListView dishList;
    TextView calendar;
    LocalDate date;
    public SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    dishSimpleAdapter pillAdapter;
    public ArrayList<LocalDate> days;
    public ArrayList<String> numberWeek;
    Cursor dataCursor;
    Cursor testCursor;
    Cursor dishCursor;
    private TextView monthYearText;
    DatePickerDialog datePickerDialog;
    private RecyclerView calendarRecyclerView;
    private DishManager dishManager;
    LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewP = inflater.inflate(R.layout.fragment_home, container, false);
        monthYearText = viewP.findViewById(R.id.monthYearTV);
        calendar= viewP.findViewById(R.id.monthYearTV);
        calendarRecyclerView = viewP.findViewById(R.id.calendarRecyclerView);
        dishList = viewP.findViewById(R.id.list);
        databaseHelper = new DatabaseHelper(getActivity());
        db = databaseHelper.getReadableDatabase();
        dishList.setOnItemClickListener((parent, view, position, id) -> {
     //       TextView idTextView = viewP.findViewById(R.id.id_storage); // Yeah, TextView for storing data :)
     //       Intent intent = new Intent(getContext(), InformActivity.class);
       //     intent.putExtra("id", idTextView.getText().toString());
         //   startActivity(intent);
        });

        calendar.setOnClickListener(v -> {
            openDatePickerAfter();
            readFromDb();
        });

        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        date = CalendarUtils.selectedDate;
        monthYearText.setOnClickListener(v -> openDatePickerAfter());
        onCalendarItem(db);
        dishManager = new DishManager(getContext());
        dishManager.setListener((pills) -> {
            System.out.println("PILLS CHANGED");
            pillAdapter.notifyDataSetChanged();
            onCalendarItem(db);
            pillAdapter.notifyDataSetChanged();
            return null;
        });

        return viewP;
    }
    public void onCalendarItem(SQLiteDatabase db) {
        testCursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_DATE + ", " + DatabaseHelper.COLUMN_NAME + ", " + DatabaseHelper.COLUMN_TIME + ", " + DatabaseHelper.COLUMN_TIMEDAY + " from " + DatabaseHelper.TABLE, null);
        testCursor.moveToFirst();

        int length = testCursor.getCount();
        System.out.println("selectedDate " + selectedDate);
        ArrayList<dishView> arrayList = new ArrayList<dishView>();
        pillAdapter = new dishSimpleAdapter(getActivity(), arrayList);
        dishList.setAdapter(pillAdapter);
        for (int i = 1; i <= length; i++) {

            String id = testCursor.getString(0);
            String data = testCursor.getString(1);
            String data2 = testCursor.getString(2);

            String[] str = data.split("\\.");
            String[] str2 = data2.split("\\.");

            String dataSplit = str[2] + "-" + str[1] + "-" + str[0];
            LocalDate localDate = LocalDate.parse(dataSplit);
            System.out.println("str " + localDate);

            String dateSplit2 = str2[2] + "-" + str2[1] + "-" + str2[0];
            LocalDate localDate2 = LocalDate.parse(dateSplit2);
            System.out.println("str2 " + localDate2);

            if (selectedDate.isAfter(localDate) && selectedDate.isBefore(localDate2) || selectedDate.equals(localDate) || selectedDate.equals(localDate2)) {

                dishCursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_NAME  + ", " + DatabaseHelper.COLUMN_TIMEDAY + ", " + DatabaseHelper.COLUMN_TIME + " FROM " + DatabaseHelper.TABLE + " WHERE " + DatabaseHelper.COLUMN_ID + " =   \""+ id + "\"", null);
                dishCursor.moveToFirst();
                arrayList.add(new dishView(dishCursor.getString(1), dishCursor.getString(2), dishCursor.getString(3), dishCursor.getString(0)));

                pillAdapter = new dishSimpleAdapter(getContext(), arrayList);
                dishList.setAdapter(pillAdapter);

            }


            testCursor.moveToNext();

        }
    }
    public void onItemClick(int position, LocalDate date)
    {
        selectedDate = date;
        setWeekView();
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            date = LocalDate.of(year, month, day);
            onItemClick(1, date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        days = daysInWeekArray(CalendarUtils.selectedDate);
        numberWeek = numberOfDays(CalendarUtils.selectedDate);
        System.out.println("numberWeek " + numberWeek);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this::onItemClick, days, numberWeek,  this);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(linearLayoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }
    public void readFromDb() {
        db = databaseHelper.getReadableDatabase();

        dataCursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);
        String strDate = "01 11 2022";
        String[] words = strDate.split(" ");
        String date = words[2] + "-" + words[1] + "-" + words[0];
        System.out.println("words " + date);
        if (dataCursor.moveToFirst()) {
            int id = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int d1 = dataCursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);


            do {


            } while (dataCursor.moveToNext());
        } else{
            System.out.println("тd");
        }
        dataCursor.close();
    }
    public void openDatePickerAfter() {
        initDatePicker();
        datePickerDialog.show();
    }

}