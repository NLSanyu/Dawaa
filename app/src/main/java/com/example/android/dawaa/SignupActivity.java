package com.example.android.dawaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    EditText etUName, etUName2, etEmail2, etUPassword2;
    CardView cvSignup;
    String UEmail, UPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Firebase.setAndroidContext(this);
        mFirebaseAuth = FirebaseAuth.getInstance();

        etUName = (EditText) findViewById(R.id.etUName);
        etUName2 = (EditText) findViewById(R.id.etUName2);
        etEmail2 = findViewById(R.id.etEmail2);
        etUPassword2 = findViewById(R.id.etPwd2);
        cvSignup = findViewById(R.id.cardViewSignUp);
        progressBar = findViewById(R.id.progress_bar2);




        cvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UEmail = etEmail2.getText().toString().trim();
                UPassword = etUPassword2.getText().toString().trim();

                if(TextUtils.isEmpty(UEmail) || TextUtils.isEmpty(UPassword)){
                    Toast.makeText(SignupActivity.this, R.string.Empty_name_pwd, Toast.LENGTH_LONG).show();
                    return;
                }

                if(UPassword.length()>6){
                    Toast.makeText(SignupActivity.this, R.string.Pwd_too_long, Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                createNewUser2();

            }
        });

    }

    private void createNewUser(){
        mFirebaseAuth.createUserWithEmailAndPassword(UEmail, UPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, R.string.Registration_successful, Toast.LENGTH_LONG).show();

                    Intent SignupIntent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(SignupIntent);
                    finish();
                }
                else{
                    Toast.makeText(SignupActivity.this, R.string.Registration_failed, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createNewUser2(){
        mFirebaseAuth.createUserWithEmailAndPassword(UEmail, UPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Intent SignupIntent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(SignupIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, R.string.Registration_failed, Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

}