package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_adapters.*;
import in.co.okservices.nidanhospitaapp3.data_models.patient_model;

public class EditPreviousDayRecord extends AppCompatActivity {

    public String date;
    private RecyclerView recycler_view;
    private TextView date_txt;
    Cursor cursor;
    MyDatabaseHelper myDB;
    ArrayList<patient_model> dataHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_previous_day_record);
        initViews();
        cursor = new MyDatabaseHelper(this).readDataByDate(date);
        dataHolder = new ArrayList<>();
        // arraylist block
        {
            try {
                while (cursor.moveToNext()) {
                    patient_model obj = new patient_model(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    );
                    dataHolder.add(obj);
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage().trim(), Toast.LENGTH_SHORT).show();
            }
            edit_previous_record_adapter adapter = new edit_previous_record_adapter(dataHolder, this);
            recycler_view.setAdapter(adapter);
        }
    }

    private void initViews(){
        date = getIntent().getStringExtra("date");
        date_txt = (TextView)findViewById(R.id.date_txt);
        date_txt.setText(date);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
    }
}

/* in this activity I am using the same model and the same db helper as the Main Activity*/