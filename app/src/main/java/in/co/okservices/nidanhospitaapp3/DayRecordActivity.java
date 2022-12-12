package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.*;
import in.co.okservices.nidanhospitaapp3.data_adapters.*;
import java.util.ArrayList;

public class DayRecordActivity extends AppCompatActivity {

    ArrayList<day_record_madel> dataHolder;
    RecyclerView recyclerView;

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
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
    }
}