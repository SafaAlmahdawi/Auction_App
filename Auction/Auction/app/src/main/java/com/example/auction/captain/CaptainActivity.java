package com.example.auction.captain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auction.CommonVars;
import com.example.auction.MainActivity;
import com.example.auction.R;

public class CaptainActivity extends AppCompatActivity {

    ImageView imgLogout, imgEditProfile, imgOrders, imgLogo;
    TextView txtLogout, txtEditProfile, txtOrders;
    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_captain);

        Intent i = getIntent();

        imgLogout = findViewById(R.id.imgLogout);
        imgEditProfile = findViewById(R.id.imgEdit);
        imgOrders = findViewById(R.id.imgView);
        imgLogo = findViewById(R.id.imgLogo);
        txtLogout = findViewById(R.id.txtLogout);
        txtOrders = findViewById(R.id.txtView);
        txtEditProfile = findViewById(R.id.txtEditProfile);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileCaptainActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_MODEL, i.getStringExtra(vars.TAG_MODEL));
                intent.putExtra(vars.TAG_YEAR, i.getStringExtra(vars.TAG_YEAR));
                intent.putExtra(vars.TAG_CITY, i.getStringExtra(vars.TAG_CITY));
                intent.putExtra(vars.TAG_WORKING, i.getStringExtra(vars.TAG_WORKING));
                startActivityForResult(intent, 100);
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileCaptainActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_MODEL, i.getStringExtra(vars.TAG_MODEL));
                intent.putExtra(vars.TAG_YEAR, i.getStringExtra(vars.TAG_YEAR));
                intent.putExtra(vars.TAG_CITY, i.getStringExtra(vars.TAG_CITY));
                intent.putExtra(vars.TAG_WORKING, i.getStringExtra(vars.TAG_WORKING));
                startActivityForResult(intent, 100);
            }
        });
        txtOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_MODEL, i.getStringExtra(vars.TAG_MODEL));
                intent.putExtra(vars.TAG_YEAR, i.getStringExtra(vars.TAG_YEAR));
                intent.putExtra(vars.TAG_CITY, i.getStringExtra(vars.TAG_CITY));
                intent.putExtra(vars.TAG_WORKING, i.getStringExtra(vars.TAG_WORKING));
                startActivityForResult(intent, 100);
            }
        });
        imgOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_MODEL, i.getStringExtra(vars.TAG_MODEL));
                intent.putExtra(vars.TAG_YEAR, i.getStringExtra(vars.TAG_YEAR));
                intent.putExtra(vars.TAG_CITY, i.getStringExtra(vars.TAG_CITY));
                intent.putExtra(vars.TAG_WORKING, i.getStringExtra(vars.TAG_WORKING));
                startActivityForResult(intent, 100);
            }
        });
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPrefs = getSharedPreferences("MY",MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Logout.getSingleInstance().setLoggingOut(true);
                Intent intent = new Intent(CaptainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPrefs = getSharedPreferences("MY",MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Logout.getSingleInstance().setLoggingOut(true);
                Intent intent = new Intent(CaptainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public static class Logout {
        private static Logout singleInstance;
        private boolean isLoggingOut;
        private Logout() {
        }
        public static Logout getSingleInstance()
        {
            if (singleInstance == null) {
                singleInstance = new Logout();
            }
            return singleInstance;
        }
        public void setLoggingOut(boolean isLoggingOut) {
            this.isLoggingOut = isLoggingOut;
        }
    }
}