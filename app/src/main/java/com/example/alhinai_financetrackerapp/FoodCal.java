package com.example.alhinai_financetrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodCal extends AppCompatActivity {

    TextView balance;
    Button up_balance;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference ref;
    String user_balance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_cal);

        balance= findViewById(R.id.c_balance3);
        up_balance= findViewById(R.id.up_c_balance3);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("Balance");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserAccount userAccount = dataSnapshot.getValue(UserAccount.class);
                    balance.setText(userAccount.getAccFood() + " OMR");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FoodCal.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        up_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodCal.this, Calculator3.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FoodCal.this, AddExpenses.class));
        finish();
    }
}