package com.example.android.studdyglucoz;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity{

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mMobileField;
    private EditText mPasswordField;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private ProgressBar progressBar;

    private static final String TAG = "signup";

    /*protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();
        mNameField=(EditText) findViewById(R.id.name);
        mEmailField=(EditText) findViewById(R.id.email);
        mMobileField=(EditText) findViewById(R.id.phone);
        mPasswordField=(EditText) findViewById(R.id.password);
        mRegisterButton=(Button) findViewById(R.id.signup);

        Button loginclick = (Button) findViewById(R.id.login);

        loginclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),login.class);
                startActivityForResult(intent,0);
            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference();

        mProgress=new ProgressDialog(this);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    Intent intent=new Intent(signup.this,login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void onBackPressed(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivityForResult(intent,0);
    }
    private void startRegister(){
        final String name=mNameField.getText().toString().trim();
        final String email=mEmailField.getText().toString().trim();
        final String phone=mMobileField.getText().toString().trim();
        final String password=mPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) ){
            mProgress.setMessage("Registering... Please Wait...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mProgress.dismiss();
                    if(task.isSuccessful()){

                        String user_id=mAuth.getCurrentUser().getUid();
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                        databaseReference.child("name").setValue(name);
                        databaseReference.child("email").setValue(email);
                        databaseReference.child("phone").setValue(phone);
                        Toast.makeText(signup.this,"Congratulations! You are successfully registered",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(signup.this,college_school_name.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(signup.this,"You are already registered... Log In",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(signup.this,"Error Registering User... Try Again Later",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(signup.this,"All the fields are necessary",Toast.LENGTH_SHORT).show();
        }
    }
}
