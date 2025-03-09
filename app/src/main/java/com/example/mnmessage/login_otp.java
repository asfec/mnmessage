package com.example.mnmessage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mnmessage.utils.AndroidUtil;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class login_otp extends AppCompatActivity {

    String input_phonenumber;
    String verificationcode ;

    PhoneAuthProvider.ForceResendingToken resendingToken ;

    Button btn_confirm;
    ImageButton btn_backotp;
    ProgressBar pb_confirm;
    EditText input_otp;
    TextView resend_otp;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    long timeout = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        input_phonenumber = Objects.requireNonNull(getIntent().getExtras()).getString("input_phonenumber");
        Toast.makeText(getApplicationContext(), input_phonenumber, Toast.LENGTH_LONG).show();
        input_otp = findViewById(R.id.input_otp);
        btn_confirm = findViewById(R.id.btn_confirm);
        pb_confirm = findViewById(R.id.pb_confirm);
        resend_otp = findViewById(R.id.resend_otp);
        send_otp(input_phonenumber,false);

    }

    void send_otp(String input_phonenumber, boolean is_resend) {
        set_in_progress(true);
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(input_phonenumber)
                .setTimeout(timeout , TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signin(phoneAuthCredential);
                        set_in_progress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AndroidUtil.showToast(getApplicationContext(),"Verification Failed");
                        set_in_progress(false);
                    }
                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationcode = verificationId;
                        resendingToken = forceResendingToken;
                        AndroidUtil.showToast(getApplicationContext(),"OTP sent");
                        set_in_progress(false);
                    }
                }
                );
        if (is_resend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    void set_in_progress(boolean in_progress) {
        if (in_progress) {
            pb_confirm.setVisibility(ProgressBar.VISIBLE);
            btn_confirm.setVisibility(Button.GONE);

        } else {
            pb_confirm.setVisibility(ProgressBar.GONE);
            btn_confirm.setVisibility(Button.VISIBLE);
        }
    }
    void signin(PhoneAuthCredential phoneAuthCredential) {

    }
}