package com.morisseau.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText useremail,userpassword;
    private Button register;
    private TextView linktogologin;
    FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        loadingBar.setTitle("Creating New Account");
//        loadingBar.setTitle("Please wait, while we are creating new account");
//        loadingBar.setCanceledOnTouchOutside(true);
//        loadingBar.show();

        mAuth = FirebaseAuth.getInstance();
        initialize();

        linktogologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String email = useremail.getText().toString();
        String password = userpassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }else {
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendertologinemail();
                                    Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                }else {
                                    String message =task.getException().toString();
                                    Toast.makeText(RegisterActivity.this, "Error "+message, Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                }
                            }
                        });
        }
    }

    private void sendertologinemail() {
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void initialize() {
        linktogologin = (TextView) findViewById(R.id.linklogin);
        register=(Button) findViewById(R.id.btnregister);
        useremail=(EditText) findViewById(R.id.registeremail);
        userpassword=(EditText) findViewById(R.id.registerpassword);

        loadingBar = new ProgressDialog(this);

    }
}