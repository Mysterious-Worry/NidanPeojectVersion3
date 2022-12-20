package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import in.co.okservices.nidanhospitaapp3.costom_packages.MyDatabaseHelper;
import in.co.okservices.nidanhospitaapp3.data_models.patient_model;
import in.co.okservices.nidanhospitaapp3.data_adapters.patient_adapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    Button _Refresh_Btn;
    EditText discount_percent_txt;
    ImageButton user_setting_btn, day_record_btn;
    CheckBox normal_cbx, emergency_cbx, normal_paper_valid_cbx,
            paper_valid_emergency_cbx, discount_cbx, cancel_cbx;
    ArrayList<patient_model> dataHolder;
    TextView selected_patient, date_txt, patient_count_txt, total_amount_txt,
            normal_count_txt, emergency_count_txt, normal_paper_valid_count_txt,
            paper_valid_emergency_txt, discount_count_txt, cancel_txt;
    Cursor cursor;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        insertDataOfTheDay();
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new GridLayoutManager(this, 9));
        cursor = new MyDatabaseHelper(this).readalldata();
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
            patient_adapter adapter = new patient_adapter(dataHolder, this, recycler_view);
            recycler_view.setAdapter(adapter);
        }

        _Refresh_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        user_setting_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RotateAnimation rotateAnimation = (RotateAnimation)AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_anim);
//                view.startAnimation(rotateAnimation);
//            }
//        });

        day_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dayIntent = new Intent(MainActivity.this, DayRecordActivity.class);
                startActivity(dayIntent);
                finish();
            }
        });
    }

    private void initViews(){
        myDB = new MyDatabaseHelper(MainActivity.this);
        selected_patient = (TextView)findViewById(R.id.selected_patient);
        date_txt = (TextView)findViewById(R.id.date_txt);
        patient_count_txt = (TextView)findViewById(R.id.patient_count_txt);
        total_amount_txt = (TextView)findViewById(R.id.total_amount_txt);
        normal_count_txt = (TextView)findViewById(R.id.normal_count_txt);
        emergency_count_txt = (TextView)findViewById(R.id.emergency_count_txt);
        normal_paper_valid_count_txt = (TextView)findViewById(R.id.normal_paper_valid_count_txt);
        paper_valid_emergency_txt = (TextView)findViewById(R.id.paper_valid_emergency_txt);
        discount_count_txt = (TextView)findViewById(R.id.discount_count_txt);
        cancel_txt = (TextView)findViewById(R.id.cancel_txt);
//        user_setting_btn = (ImageButton)findViewById(R.id.user_setting_btn);
        day_record_btn = (ImageButton)findViewById(R.id.day_record_btn);

        normal_cbx = (CheckBox)findViewById(R.id.normal_cbx);
        emergency_cbx = (CheckBox)findViewById(R.id.emergency_cbx);
        normal_paper_valid_cbx = (CheckBox)findViewById(R.id.normal_paper_valid_cbx);
        paper_valid_emergency_cbx = (CheckBox)findViewById(R.id.paper_valid_emergency_cbx);
        discount_cbx = (CheckBox)findViewById(R.id.discount_cbx);
        cancel_cbx = (CheckBox)findViewById(R.id.cancel_cbx);
        _Refresh_Btn = (Button)findViewById(R.id._Refresh_Btn);

        discount_percent_txt = (EditText)findViewById(R.id.discount_percent_txt);

        try{
            date_txt.setText("Date="+myDB.getDate());
            patient_count_txt.setText("TSP="+String.valueOf(myDB.senderCell("patient_count")));
            total_amount_txt.setText("A="+String.valueOf(myDB.senderCell("collected_money")));
            normal_count_txt.setText("N="+String.valueOf(myDB.senderCell("normal_count")));
            emergency_count_txt.setText("E="+String.valueOf(myDB.senderCell("emergency_count")));
            normal_paper_valid_count_txt.setText("NPV="+String.valueOf(myDB.senderCell("normal_paper_valid_count")));
            paper_valid_emergency_txt.setText("EPV="+String.valueOf(myDB.senderCell("paper_valid_emergency_count")));
            discount_count_txt.setText("D="+String.valueOf(myDB.senderCell("discount_count")));
            cancel_txt.setText("C="+String.valueOf(myDB.senderCell("cancel_count")));
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void insertDataOfTheDay(){
        boolean ifRecordExists = myDB.checkIfRecordExist(myDB.getDate());

        if (!ifRecordExists){
            String dataAdded = myDB.addRawData2();
            if(Objects.equals(dataAdded, "Failed")){
                Toast.makeText(this, "Failed, adding patient data.", Toast.LENGTH_SHORT).show();
            } else if(Objects.equals(dataAdded, "Successfully inserted")){
                Toast.makeText(this, "Data added successfully.", Toast.LENGTH_SHORT).show();
            }

            try {
                String dayDataAdded = myDB.addDayRawData();
                if(Objects.equals(dayDataAdded, "Failed")){
                    Toast.makeText(this, "Failed, adding day data.", Toast.LENGTH_SHORT).show();
                } else if(Objects.equals(dayDataAdded, "Successfully inserted")){
                    Toast.makeText(this, "Data added successfully.", Toast.LENGTH_SHORT).show();

                    date_txt.setText("Date="+myDB.getDate());
                    patient_count_txt.setText("TSP="+String.valueOf(myDB.senderCell("patient_count")));
                    total_amount_txt.setText("A="+String.valueOf(myDB.senderCell("collected_money")));
                    normal_count_txt.setText("N="+String.valueOf(myDB.senderCell("normal_count")));
                    emergency_count_txt.setText("E="+String.valueOf(myDB.senderCell("emergency_count")));
                    normal_paper_valid_count_txt.setText("NPV="+String.valueOf(myDB.senderCell("normal_paper_valid_count")));
                    paper_valid_emergency_txt.setText("EPV="+String.valueOf(myDB.senderCell("paper_valid_emergency_count")));
                    discount_count_txt.setText("D="+String.valueOf(myDB.senderCell("discount_count")));
                    cancel_txt.setText("C="+String.valueOf(myDB.senderCell("cancel_count")));
                }
            } catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Data already exists", Toast.LENGTH_SHORT).show();
        }
    }
}