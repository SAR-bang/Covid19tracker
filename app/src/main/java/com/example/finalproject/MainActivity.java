package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public  class MainActivity extends AppCompatActivity {

    private Button btn_signin,register_view;;
    private EditText username, password;
    private CheckBox checkbox;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseAuth.getInstance().signOut(); // logging out the user





        btn_signin = findViewById(R.id.btnsignin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fauth = FirebaseAuth.getInstance();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check the valye  in database
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "No null accepted", Toast.LENGTH_SHORT).show();
                } else {

                    fauth.signInWithEmailAndPassword(username.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT);
                                startActivity(new Intent(MainActivity.this,Profile.class));
                                finish();

                            }else{
                                Toast.makeText(MainActivity.this,"Use correct credentials and check connection",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }



            }
        });


        register_view = findViewById(R.id.register);


        register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(signupIntent);
                finish();
            }
        });
    }
}
