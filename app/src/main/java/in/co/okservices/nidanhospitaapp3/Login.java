package in.co.okservices.nidanhospitaapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText _Username_Txt, _Password_Txt;
    Button _Login_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _Username_Txt = (EditText)findViewById(R.id._Username_Txt);
        _Password_Txt = (EditText)findViewById(R.id._Password_Txt);
        _Login_Btn = (Button) findViewById(R.id._Login_Btn);

        _Login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Username_Txt.getText().toString().trim().equals("admin") &&
                        _Password_Txt.getText().toString().trim().equals("admin") ){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Login credentials are incorrect, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}