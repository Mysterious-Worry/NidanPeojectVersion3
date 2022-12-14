package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.*;
import in.co.okservices.nidanhospitaapp3.data_adapters.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DayRecordActivity extends AppCompatActivity {

    ArrayList<day_record_madel> dataHolder;
    RecyclerView recyclerView;
    private Button select_btn, search_btn, search_by_month_btn;
    private int mYear, mMonth, mDay;
    private EditText selected_date_txt;
    MyDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_record);
        initViews();

        Cursor cursor = new MyDatabaseHelper(this).fetchDayData();
        loadData(cursor);

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog( DayRecordActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    String sMonthOfYear, sDayOfMonth;
                                    sMonthOfYear = Integer.toString(monthOfYear+1);
                                    sDayOfMonth = Integer.toString(dayOfMonth);
                                    if (sMonthOfYear.length() == 1 ) {
                                        sMonthOfYear = "0" + String.valueOf(sMonthOfYear);
                                    }
                                    if (sDayOfMonth.length() < 2) {
                                        sDayOfMonth = "0" + dayOfMonth;
                                    }

                                    String date = year + "-" + (sMonthOfYear) + "-" + sDayOfMonth;
                                    selected_date_txt.setText(date);
                                } catch(Exception ex){
                                    Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchDayData(selected_date_txt.getText().toString().trim());
                loadData(cursor);
            }
        });

        search_by_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String month = selected_date_txt.getText().toString().trim();
                    StringBuilder rmv = new StringBuilder(month);
                    rmv = rmv.delete(7, 10);
                    selected_date_txt.setText(rmv.toString());
                    Cursor cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchMonthData(rmv.toString());
                    loadData(cursor);
                } catch (Exception ex){
                    Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(){
        helper = new MyDatabaseHelper(this);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        select_btn = (Button)findViewById(R.id.select_btn);
        search_btn = (Button)findViewById(R.id.search_btn);
        search_by_month_btn = (Button)findViewById(R.id.search_by_month_btn);
        selected_date_txt = (EditText)findViewById(R.id.selected_date_txt);
    }

    private void loadData(Cursor cursor){
        try{
            recyclerView.setLayoutManager(new LinearLayoutManager(DayRecordActivity.this));
            dataHolder = new ArrayList<>();
            while (cursor.moveToNext()){
                day_record_madel madel = new day_record_madel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                dataHolder.add(madel);
            }
            day_record_adapter adapter = new day_record_adapter(dataHolder, DayRecordActivity.this);
            recyclerView.setAdapter(adapter);
        } catch(Exception ex) {
            Toast.makeText(DayRecordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}