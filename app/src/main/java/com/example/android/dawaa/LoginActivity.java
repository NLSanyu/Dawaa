package com.example.android.dawaa;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    EditText etUEmail, etUPassword;
    CardView cvLogin, cvSignup;
    Button btnLang;
    String UEmail, UPassword;
    int nb=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();

        etUEmail = findViewById(R.id.etUEmail);
        etUPassword = findViewById(R.id.etPwd);
        cvLogin = findViewById(R.id.cardView1);
        cvSignup = findViewById(R.id.cardView2);
        btnLang = findViewById(R.id.btnLang);

        btnLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Changer la langue ici

            }
        });

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UEmail = etUEmail.getText().toString();
                UPassword = etUPassword.getText().toString();

                nb--; //pour ne pas accepter plus de 5 essaies
                if(nb==0){
                    Toast.makeText(LoginActivity.this, "Nombre d'essais atteint", Toast.LENGTH_LONG).show();
                    //cvLogin.setClickable(false);
                }

                Intent HomeIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(HomeIntent);

                //Intent HomeIntent = new Intent(LoginActivity.this, AddMedicineActivity.class);
                //startActivity(HomeIntent);


            }
        });

        cvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(SignupIntent);
            }
        });
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }*/
}
