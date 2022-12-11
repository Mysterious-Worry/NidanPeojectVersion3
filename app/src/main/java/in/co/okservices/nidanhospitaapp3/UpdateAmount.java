package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateAmount extends AppCompatActivity {

    // Edit Buttons
    Button edit_normal_btn, edit_paper_valid_emergency_btn, edit_emergency_btn,
            edit_discount_btn, edit_normal_paper_valid_btn, edit_cancel_btn;

    // Amount Texts
    TextView normal_amount, paper_valid_emergency_amount, emergency_amount,
            discount_amount, normal_paper_valid_amount, cancel_amount;

    // Update Buttons
    Button update_normal_btn, update_paper_valid_emergency_btn, update_emergency_btn,
            update_discount_btn, update_normal_paper_valid_btn, update_cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_amount);
        initViews();
    }

    private void initViews(){

        // Init Edit Buttons
        edit_normal_btn = (Button)findViewById(R.id.edit_normal_btn);
        edit_paper_valid_emergency_btn = (Button)findViewById(R.id.edit_paper_valid_emergency_btn);
        edit_emergency_btn = (Button)findViewById(R.id.edit_emergency_btn);
        edit_discount_btn = (Button)findViewById(R.id.edit_discount_btn);
        edit_normal_paper_valid_btn = (Button)findViewById(R.id.edit_normal_paper_valid_btn);
        edit_cancel_btn = (Button)findViewById(R.id.edit_cancel_btn);

        // Init Amount Texts
        normal_amount = (EditText)findViewById(R.id.normal_amount);
        paper_valid_emergency_amount = (EditText)findViewById(R.id.paper_valid_emergency_amount);
        emergency_amount = (EditText)findViewById(R.id.emergency_amount);
        discount_amount = (EditText)findViewById(R.id.discount_amount);
        normal_paper_valid_amount = (EditText)findViewById(R.id.normal_paper_valid_amount);
        cancel_amount = (EditText)findViewById(R.id.cancel_amount);

        // Init Update Buttons
        update_normal_btn = (Button)findViewById(R.id.update_normal_btn);
        update_paper_valid_emergency_btn = (Button)findViewById(R.id.update_paper_valid_emergency_btn);
        update_emergency_btn = (Button)findViewById(R.id.update_emergency_btn);
        update_discount_btn = (Button)findViewById(R.id.update_discount_btn);
        update_normal_paper_valid_btn = (Button)findViewById(R.id.update_normal_paper_valid_btn);
        update_cancel_btn = (Button)findViewById(R.id.update_cancel_btn);
    }
}