package com.example.android.studdyglucoz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.android.studdyglucoz.R.id.logout;


public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=(Toolbar)findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle( "STUDDy GLUcoz" );

        Button new_sem = (Button) findViewById(R.id.new_sem);
        new_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),add_new_sem_year.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_main,menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Toast.makeText( this,"Settings",Toast.LENGTH_SHORT ).show();
        }
        if(id== logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent( getApplicationContext(), login.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
            Toast.makeText( getApplicationContext(), "Successfully Logged Out!", Toast.LENGTH_LONG ).show();
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            FirebaseAuth.getInstance().removeAuthStateListener( mAuthStateListener );
        }
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {

        } else {
            Toast.makeText(getBaseContext(),"Tap back button again to close the App", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }

    }
}
