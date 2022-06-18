package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auction.CommonVars;
import com.example.auction.MainActivity;
import com.example.auction.R;

public class BuyerActivity extends AppCompatActivity {

    ImageView imgLogout, imgEditProfile, imgViewProducts, imgSearch, imgOperations, imgBidding, imgCart, imgWin, imgContact;
    TextView txtLogout, txtEditProfile, txtViewProducts, txtSearch, txtOperations, txtBidding, txtCart, txtWin;

    Dialog card_contact;

    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_buyer);

       Intent i = getIntent();

       imgLogout = findViewById(R.id.imgLogout);
       imgEditProfile = findViewById(R.id.imgEdit);
       imgViewProducts = findViewById(R.id.imgViewProducts);
       imgSearch = findViewById(R.id.imgSearchProduct);
       imgOperations = findViewById(R.id.imgOperations);
       imgBidding = findViewById(R.id.imgBidding);
       imgCart = findViewById(R.id.imgCart);
       imgWin = findViewById(R.id.imgWin);
       imgContact = findViewById(R.id.imgContact);
       txtLogout = findViewById(R.id.txtLogout);
       txtEditProfile = findViewById(R.id.txtEditProfile);
       txtViewProducts = findViewById(R.id.txtViewProducts);
       txtOperations = findViewById(R.id.txtOperations);
       txtSearch = findViewById(R.id.txtSearch);
       txtBidding = findViewById(R.id.txtBidding);
       txtWin = findViewById(R.id.txtWin);
       txtCart = findViewById(R.id.txtCart);

        //// rate dialog /////////////
        card_contact = new Dialog(this);
        card_contact.setContentView(R.layout.card_contact);
        AppCompatButton btn_Ok = card_contact.findViewById(R.id.btn_yes);

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               card_contact.show();
            }
        });
        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_contact.dismiss();
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileBuyerActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProductsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchProductActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LiveBiddingActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LiveBiddingActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCartActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyCartActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WinBiddingActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WinBiddingActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        txtOperations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductOperationsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgOperations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductOperationsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProductsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
                startActivityForResult(intent, 100);
            }
        });
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileBuyerActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_PHONE, i.getStringExtra(vars.TAG_PHONE));
                intent.putExtra(vars.TAG_ADDRESS, i.getStringExtra(vars.TAG_ADDRESS));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                intent.putExtra(vars.TAG_PAYMENT, i.getStringExtra(vars.TAG_PAYMENT));
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
                Intent intent = new Intent(BuyerActivity.this, MainActivity.class);
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
                Intent intent = new Intent(BuyerActivity.this, MainActivity.class);
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