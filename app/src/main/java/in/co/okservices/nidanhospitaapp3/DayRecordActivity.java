package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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
    private Button select_btn, search_btn;
    private int mYear, mMonth, mDay;
    private EditText selected_date_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_record);
        initViews();

        try{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Cursor cursor = new MyDatabaseHelper(this).fetchDayData();
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
            day_record_adapter adapter = new day_record_adapter(dataHolder, this);
            recyclerView.setAdapter(adapter);
        } catch(Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

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

                                selected_date_txt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    recyclerView.setLayoutManager(new LinearLayoutManager(DayRecordActivity.this));
                    Cursor cursor = new MyDatabaseHelper(DayRecordActivity.this).fetchDayData(selected_date_txt.getText().toString().trim());
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
        });
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        select_btn = (Button)findViewById(R.id.select_btn);
        search_btn = (Button)findViewById(R.id.search_btn);
        selected_date_txt = (EditText)findViewById(R.id.selected_date_txt);
    }
}