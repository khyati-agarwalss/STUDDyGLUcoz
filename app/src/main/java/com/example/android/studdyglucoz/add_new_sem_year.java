package com.example.android.studdyglucoz;

import android.content.Intent;
import android.icu.util.DateInterval;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class add_new_sem_year extends AppCompatActivity {

    final Context context = this;
    private EditText result,result1;
    private EditText start_date,end_date,no_of_subjects,no_of_exams,no_of_hours;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_sem_year);

        Button proceed = (Button) findViewById(R.id.proceed);
        start_date=(EditText)findViewById(R.id.start_date);
        end_date=(EditText)findViewById(R.id.end_date);
        no_of_subjects=(EditText)findViewById(R.id.no_of_subjects);
        no_of_exams=(EditText)findViewById(R.id.no_of_exams);
        no_of_hours=(EditText)findViewById(R.id.hrs_of_study);

        //final Date start= (Date) start_date.getText();
        //final Date end=(Date) end_date.getText();
        final int nos=no_of_subjects.getInputType();
        final int noe=no_of_exams.getInputType();
        final int noh=no_of_hours.getInputType();
        mAuth=FirebaseAuth.getInstance();
        proceed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id=mAuth.getCurrentUser().getUid();
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("/colleges");
                final DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference().child( "users" ).child( user_id );

                /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String CollegeId=dataSnapshot.getValue(String.class);
                        databaseReference.child(CollegeId).child("sessions").child(start.toString().trim()).child("no_of_subjects").setValue(nos);
                        databaseReference.child(CollegeId).child("sessions").child(start.toString().trim()).child("no_of_exams").setValue(noe);
                        databaseReference.child(CollegeId).child("sessions").child(start.toString().trim()).child("no_of_hours").setValue(noh);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Main2Activity.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
