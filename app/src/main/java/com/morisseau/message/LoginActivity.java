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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button Login,phone;
    private EditText useremail,userpassword;
    private TextView forget,linknewaccount;

    private ProgressDialog loadingBar;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        linknewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });
    }

    private void AllowUserToLogin() {
        String email = useremail.getText().toString();
        String password = userpassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }else {

            loadingBar.setTitle("Signing ");
            loadingBar.setTitle("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                SendUserToMainActivity();
                                Toast.makeText(LoginActivity.this, "Logged Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String message =task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error "+message, Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }

    private void initialize() {
        Login = (Button) findViewById(R.id.btnlogin);
        phone = (Button) findViewById(R.id.btnphonelogin);
        useremail = findViewById(R.id.loginemail);
        userpassword = findViewById(R.id.loginpassword);
        forget = (TextView) findViewById(R.id.forget);
        linknewaccount = (TextView) findViewById(R.id.newaccount);
        loadingBar = new ProgressDialog(this);
    }


    @Override
        protected void onStart() {
            super.onStart();
            if (currentUser !=null){
                SendUserToMainActivity();
            }
        }

    private void SendUserToMainActivity() {
        Intent loginIntent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);

    }
}