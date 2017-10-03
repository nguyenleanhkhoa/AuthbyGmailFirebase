package com.example.admin.authfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtemail,edtpass;
    private Button btnregister;
    private TextView txtsign;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFirebase();
        Addcontrol();
        AddEvent();
    }

    private void addFirebase() {
        firebaseauth=FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
    }

    private void AddEvent() {
        progressDialog=new ProgressDialog(this);
        btnregister.setOnClickListener(this);
        txtsign.setOnClickListener(this);
    }

    private void Addcontrol() {


        edtemail= (EditText) findViewById(R.id.edtenteremail);
        edtpass= (EditText) findViewById(R.id.edtenterpass);
        btnregister= (Button) findViewById(R.id.btnregister);
        txtsign= (TextView) findViewById(R.id.txtsignin);
    }

    @Override
    public void onClick(View view) {
        if(view==btnregister){
            register();
        }
        if(view==txtsign){
            //open login activity
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }

    private void register() {
        String email=edtemail.getText().toString().trim();
        String password=edtpass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
        }

        progressDialog.show();
        progressDialog.setMessage("Registering.....");
        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start profile activity here
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Could not Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
