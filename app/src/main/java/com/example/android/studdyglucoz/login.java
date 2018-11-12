package com.example.android.studdyglucoz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.text.Html;

public class login extends AppCompatActivity {

    private Button mSigninButton;
    private EditText mEmailField;
    private EditText mPasswordField;
    private ProgressDialog mProgress;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signupclick = (Button) findViewById(R.id.signup);
        signupclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),signup.class);
                startActivityForResult(intent,0);
            }
        });

        mSigninButton= (Button) findViewById(R.id.login);
        mEmailField=(EditText) findViewById(R.id.email);
        mPasswordField=(EditText) findViewById(R.id.password);

        mProgress=new ProgressDialog(this);

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    Intent intent=new Intent(login.this,signup.class);
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

    private void startLogin(){
        final String email=mEmailField.getText().toString().trim();
        final String password=mPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Logging In... Please Wait...");
            mProgress.show();
            mAuth=FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mProgress.dismiss();
                    if(task.isSuccessful()){
                        //mAuth.signInWithEmailAndPassword(email,password);
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(login.this, Html.fromHtml("<background color='#000000' >" + "Welcome!" + "</background>"),Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(login.this,Main2Activity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                        Toast.makeText(login.this, Html.fromHtml("<background color='#000000' >" + "Error Logging In... Try Again Later" + "</background>"),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(login.this, Html.fromHtml("<background color='#000000' >" + "All the fields are necessary" + "</background>"),Toast.LENGTH_SHORT).show();
        }

    }
}
