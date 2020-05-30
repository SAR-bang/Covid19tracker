package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    private EditText date, username, password, FirstName, MiddleName, LastName, phone_number;
    private Button signup_btn;
    private FirebaseAuth mAuth;      // user login authentication
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String TAG = "EMailPAssword";
    private String username_save, Name, password_save;
    private String phone_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // date picker activity creating a calendar

        date = findViewById(R.id.dateEdit);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datelsnr = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String format = "MM/DD/YY";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
                date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                new DatePickerDialog(Main2Activity.this, datelsnr, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        username = findViewById(R.id.username);
        signup_btn = findViewById(R.id.signup_btn);
        password = findViewById(R.id.password_btn);
        FirstName = findViewById(R.id.first_name);
        LastName = findViewById(R.id.last_name);




        // Working with registration

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_save = username.getText().toString().trim();
                Name = (FirstName.getText().toString() + LastName.getText().toString()).trim();
                password_save = password.getText().toString().trim();

                if (username_save.isEmpty()) {
                    username.setError("username is required");
                    return;
                }
                if (Name.isEmpty()) {
                    LastName.setError("Name is required");
                    return;
                }
                if (password_save.isEmpty()) {
                    password.setError("password is required");
                    return;
                }
                if ((password_save.replaceAll("[A-Z]", "").length() == password_save.length()) || (password_save.replaceAll("[a-z]", "").length() == password_save.length())) {
                    password.setError("At least one uppercase/lowercase required");
                    return;
                }
                if (password_save.length() < 8) {
                    password.setError("Must be 8 characters long");
                }


                //initializing the FirebaseAuth instance
                try {

                    mAuth = FirebaseAuth.getInstance();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();
                }catch(Exception e){
                    Toast.makeText(Main2Activity.this,"Email already exists",Toast.LENGTH_LONG);
                }

                // creating a user for authentication puropose

                mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success, redirecting to sign in page
                            Toast.makeText(Main2Activity.this, "Sign up complete.",
                                    Toast.LENGTH_SHORT).show();


                            databaseReference.child(mAuth.getCurrentUser().getUid()).child("username/email").setValue(username_save);
                            databaseReference.child(mAuth.getCurrentUser().getUid()).child("Name").setValue(Name);

                            startActivity(new Intent(Main2Activity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Main2Activity.this, task.getException() + " error ", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });


    }


    public void back(View view) {
    startActivity(new Intent(Main2Activity.this,MainActivity.class));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();

    }
}