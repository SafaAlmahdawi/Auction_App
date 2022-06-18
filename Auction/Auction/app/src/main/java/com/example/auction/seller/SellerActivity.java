package com.example.auction.seller;

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

public class SellerActivity extends AppCompatActivity {

    ImageView imgLogout, imgEditProfile, imgAddProduct, imgManageProduct, imgLogo;
    TextView txtLogout, txtEditProfile, txtAddProduct, txtManageProduct;

    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_seller1);

        Intent i = getIntent();

        imgLogout = findViewById(R.id.imgLogout);
        imgEditProfile = findViewById(R.id.imgEdit);
        imgAddProduct = findViewById(R.id.imgAdd);
        imgManageProduct = findViewById(R.id.imgManage);
        imgLogo = findViewById(R.id.imgLogo);
        txtLogout = findViewById(R.id.txtLogout);
        txtEditProfile = findViewById(R.id.txtEditProfile);
        txtAddProduct = findViewById(R.id.txtAddProduct);
        txtManageProduct = findViewById(R.id.txtManage);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        imgAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgManageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtManageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileSellerActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
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
                Intent intent = new Intent(SellerActivity.this, MainActivity.class);
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
                Intent intent = new Intent(SellerActivity.this, MainActivity.class);
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