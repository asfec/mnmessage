package com.example.mnmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class loginphonenumber extends AppCompatActivity {

    Button btn_send_otp;
    ProgressBar pb_send_otp;
    EditText input_phonenumber;
    ImageButton btn_backphonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginphonenumber);
        input_phonenumber = findViewById(R.id.input_phonenumber);
        btn_send_otp = findViewById(R.id.btn_send_otp);
        pb_send_otp = findViewById(R.id.pb_send_otp);
        pb_send_otp.setVisibility(View.GONE);

        btn_send_otp.setOnClickListener((v -> {
            if (input_phonenumber.getText().toString().length() != 10) {
                input_phonenumber.setError("Số điện thoại không hợp lệ");
                return;
  
            }
            Intent intent = new Intent(loginphonenumber.this, login_otp.class);
            intent.putExtra(
                    "input_phonenumber",
                    input_phonenumber.getText().toString()
            );
            startActivity(intent);
        }));

    }
}