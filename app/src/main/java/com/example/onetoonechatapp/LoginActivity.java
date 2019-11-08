package com.example.onetoonechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email_editText;
    EditText password_editText;
    Button button;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_editText=findViewById(R.id.email);
        password_editText=findViewById(R.id.password);
        button=findViewById(R.id.button);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=email_editText.getText().toString();
                String password=password_editText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    email_editText.setError("Please enter your email id");
                }
                else if(TextUtils.isEmpty(password) || password.length()<=6){
                    password_editText.setText("");
                    password_editText.setError("Please enter your password id");
                }

                else{
                    SignInWIthFirebase(email,password);

                }
            }
        });


    }

    private void SignInWIthFirebase(String email,String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
