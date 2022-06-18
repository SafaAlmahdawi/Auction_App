package com.example.auction.admin;

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
import com.example.auction.buyer.BuyerActivity;
import com.example.auction.buyer.EditProfileBuyerActivity;

public class AdminActivity extends AppCompatActivity {

    ImageView imgLogout, imgEditProfile, imgLogo, imgManageUsers, imgAcceptExpert, imgAcceptCaptain, imgManangeBidding, imgManageComments, imgAssignOrders;
    TextView txtLogout, txtEditProfile, txtManageUsers, txtAcceptExpert, txtAcceptCaptain, txtManangeBidding, txtManageComment, txtAssignOrders;

    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin);

        Intent i = getIntent();

        imgLogo = findViewById(R.id.imgLogo);
        imgLogout = findViewById(R.id.imgLogout);
        imgEditProfile = findViewById(R.id.imgEdit);
        imgManageUsers = findViewById(R.id.imgManageUsers);
        imgAcceptCaptain = findViewById(R.id.imgAcceptCaptain);
        imgAcceptExpert = findViewById(R.id.imgAcceptExpert);
        imgManangeBidding = findViewById(R.id.imgManageBidding);
        imgManageComments = findViewById(R.id.imgManageComments);
        imgAssignOrders = findViewById(R.id.imgAssignOrders);
        txtLogout = findViewById(R.id.txtLogout);
        txtEditProfile = findViewById(R.id.txtEditProfile);
        txtManageUsers = findViewById(R.id.txtManageUsers);
        txtAcceptCaptain = findViewById(R.id.txtAcceptCaptain);
        txtAcceptExpert = findViewById(R.id.txtAcceptExpert);
        txtManangeBidding = findViewById(R.id.txtManageBidding);
        txtManageComment = findViewById(R.id.txtManageComment);
        txtAssignOrders = findViewById(R.id.txtAssignOrders);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileAdminActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileAdminActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtAcceptExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectExpertsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgAcceptExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectExpertsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtManangeBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectBidding.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgManangeBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectBidding.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtManageComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageCommentsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgManageComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageCommentsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtAssignOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssignOrdersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgAssignOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssignOrdersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        txtAcceptCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectCaptainsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgAcceptCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcceptRejectCaptainsActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
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
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
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
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageUsersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
            }
        });
        imgManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageUsersActivity.class);
                intent.putExtra(vars.TAG_ID, i.getStringExtra(vars.TAG_ID));
                intent.putExtra(vars.TAG_NAME, i.getStringExtra(vars.TAG_NAME));
                intent.putExtra(vars.TAG_EMAIL, i.getStringExtra(vars.TAG_EMAIL));
                intent.putExtra(vars.TAG_USER, i.getStringExtra(vars.TAG_USER));
                intent.putExtra(vars.TAG_PASS, i.getStringExtra(vars.TAG_PASS));
                startActivityForResult(intent, 100);
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