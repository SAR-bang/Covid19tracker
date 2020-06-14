package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    EditText emailadd;
    Button resetbtn;
    TextView message;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        emailadd = findViewById(R.id.tvemail);
        resetbtn = findViewById(R.id.reset);
        firebaseAuth = FirebaseAuth.getInstance();
        message = findViewById(R.id.resettext);

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // method to send the password reset email

                firebaseAuth.sendPasswordResetEmail(emailadd.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // send message is displayed
                                    message.setVisibility(View.VISIBLE);
                                } else {
                                    // error loading phase
                                    message.setText("Error found sending the link you are not registered");
                                    message.setVisibility(View.VISIBLE);
                                }
                            }
                        });

            }
        });
    }
}
