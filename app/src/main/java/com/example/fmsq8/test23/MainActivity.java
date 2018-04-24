package com.example.fmsq8.test23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fmsq8.test23.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity {

    // Firebase
    FirebaseDatabase database;
    DatabaseReference users;


    EditText edtUsername, edtPwd;
    Button btnSingIn, btntoSingUp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPwd = (EditText) findViewById(R.id.edtPwd);

        btnSingIn = (Button) findViewById(R.id.btnSingIn);
        btntoSingUp = (Button) findViewById(R.id.btntoSingUp);

        btntoSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(s);
            }
        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUsername.getText().toString(), edtPwd.getText().toString());
            }
        });
    }

    private void signIn( final String user, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()) {
                    if (!user.isEmpty()) {
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if (login.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Log in Success", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(s);
                        }
                        else
                        if (!password.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Password is wrong", Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(MainActivity.this, "Username in not registered", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            // costume code here
            }
        });
    }
}