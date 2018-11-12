package com.example.android.studdyglucoz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLngBounds;


public class college_school_name extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String college;
    private ProgressBar progressBar;
    //private PlaceAutocompleteFragment mCollege;

    private static final String TAG = "college_school_name";

    /*protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_school_name);

        mAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mProgress=new ProgressDialog(this);

        final PlaceAutocompleteFragment mCollege = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.college_autocomplete);
        mCollege.setHint("Enter School/College Name");
        mCollege.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                // TODO: Get info about the selected place.
                college=place.getName().toString().trim();
                if(!TextUtils.isEmpty(college))
                Log.i(TAG, college);


                Button proceed = (Button) findViewById(R.id.proceed);
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user_id=mAuth.getCurrentUser().getUid();
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("colleges").child(place.getId());
                        databaseReference.setValue(college);
                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("users").child(user_id).child("colleges").child(place.getId());
                        databaseReference1.setValue(college);
                        Intent intent = new Intent(view.getContext(),Main2Activity.class);
                        startActivityForResult(intent,0);
                    }
                });
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}
