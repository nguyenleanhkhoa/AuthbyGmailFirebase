package com.example.admin.authfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity{
    private EditText edtmaillogin,edtpasslogin;
    private Button btnlogin;
    private TextView txtsignup;
    private ProgressDialog progessDialog;
    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Addfirebase();
        AddControl();
        AddEvent();
    }

    private void Addfirebase() {
        firebaseauth=FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
    }

    private void AddEvent() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void AddControl() {
        progessDialog=new ProgressDialog(this);
        edtmaillogin= (EditText) findViewById(R.id.edtenteremaillogin);
        edtpasslogin= (EditText) findViewById(R.id.edtenterpasslogin);
        btnlogin= (Button) findViewById(R.id.btnlogin);
        txtsignup= (TextView) findViewById(R.id.txtforgot);
    }


    private void login() {
        String emaillogin=edtmaillogin.getText().toString().trim();
        String passlogin=edtpasslogin.getText().toString().trim();
        if(TextUtils.isEmpty(emaillogin)){
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passlogin)){
            Toast.makeText(this, "please entr your password", Toast.LENGTH_SHORT).show();
            return;
        }
        progessDialog.setMessage("Login.....");
        progessDialog.show();
        firebaseauth.signInWithEmailAndPassword(emaillogin,passlogin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(progessDialog.isShowing()){
                                progessDialog.dismiss();
                            }
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                    }
                });
    }
}
