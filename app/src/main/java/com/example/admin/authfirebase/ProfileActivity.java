package com.example.admin.authfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity  {
    private Button btnlogout;
    private FirebaseAuth firebaseAuth;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addControl();
        addEvent();
        addFirebase();
    }

    private void addEvent() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            }
        });
    }

    private void addFirebase() {
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        txt.setText("welcome "+user.getEmail());
    }

    private void addControl() {

        btnlogout= (Button) findViewById(R.id.btnlogout);
        txt= (TextView) findViewById(R.id.txtprofile);
    }


}
