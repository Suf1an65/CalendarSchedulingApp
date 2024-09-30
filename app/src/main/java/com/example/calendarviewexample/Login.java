package com.example.calendarviewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendarviewexample.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;


public class Login extends AppCompatActivity { // This screen is where the user creates their account and logs in.


    private Button loginButton;
    private EditText EditUsername;
    public static FirebaseDatabase db;
    public static DatabaseReference reference;
    Users users;
    public static String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_login);
        EditUsername = findViewById(R.id.Username);

        db = FirebaseDatabase.getInstance(); //variables to access firebase project
        reference = db.getReference("Users"); // uses Users as the root child node

        users = new Users();






       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               username = EditUsername.getText().toString();




                if (username.isEmpty() ) //checks to see if nothing was entered into the calendar view
                {
                    Toast.makeText(Login.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                    // if so sends error message to the user

               }
                else
               {
                  Toast.makeText(Login.this, "it recognizes the field isn't empty",
                          Toast.LENGTH_SHORT).show();
                    /// addDataToFirebase(username);
                   //reference.child(username).child(String.valueOf(LocalDate.now())).setValue("yes");
                   Day.setDayClass(); //This function will create the array of day objects
                   for (int i = 0; i < Day.listOfDayObjects.size(); i++) //This creates an array of day objects for the first 200 hundred days
                       // in the user's schedule
                   {
                       LocalDate DateBeingAdded = Day.listOfDayObjects.get(i).getDate();
                       int InitialSetDayTime = Day.listOfDayObjects.get(i).getTotalTime();

                       reference.child(username).child(String.valueOf(DateBeingAdded)).child("Time for day").setValue(InitialSetDayTime);
                       GoToMain();
                   }

               }

           }
        });
    }

    private void GoToMain() {
        startActivity(new Intent(this, MainActivity.class));
    }
    //Sends user to the MainActivity screen



}