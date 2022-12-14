package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditPreviousDayRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_previous_day_record);
    }
}

/* in this activity I am using the same model, adapter, and the same db helper as the Main Activity*/