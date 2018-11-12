package com.example.android.studdyglucoz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivityForResult(intent,0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signupclick = (Button) findViewById(R.id.signup);
        signupclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),signup.class);
                startActivityForResult(intent,0);
            }
        });
        Button loginclick = (Button) findViewById(R.id.login);
        loginclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),login.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
