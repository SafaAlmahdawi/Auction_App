package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.admin.AdminActivity;
import com.example.auction.buyer.BuyerActivity;
import com.example.auction.captain.CaptainActivity;
import com.example.auction.expert.ExpertActivity;
import com.example.auction.seller.SellerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton butLogin, butNewAccount;
    TextView txtForget;
    EditText txtUser, txtPass;
    String strUser, strPass;

    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        butLogin = findViewById(R.id.butLogin);
        butNewAccount = findViewById(R.id.butNewAccount);
        txtForget = findViewById(R.id.txtForget);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);

        butLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (txtUser.getText().toString().equals("") || txtPass.getText().toString().equals("")) {
                                                Toast.makeText(getApplicationContext(), "Missing required data", Toast.LENGTH_SHORT).show();
                                            } else {
                                                strUser = txtUser.getText().toString().trim();
                                                strPass = txtPass.getText().toString().trim();
                                                new UserLogin().execute();
                                            }
                                        }
                                    }
        );
        butNewAccount.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                                                 startActivityForResult(intent, 100);
                                             }
                                         }
        );
        txtForget.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
                                                 startActivityForResult(intent, 100);
                                             }
                                         }
        );
    }

    class UserLogin extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(LoginActivity.this);
            dialogBox.setMessage("Check for user .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("pass", strPass));
                parameters.add(new BasicNameValuePair("user_name", strUser));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_login, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    //// read the user data
                    String[] data = parser.getString(TAG_MESSAGE).split("##");
                    Intent intent = new Intent(getApplicationContext(), BuyerActivity.class);
                    intent.putExtra(vars.TAG_ID, data[0]);
                    intent.putExtra(vars.TAG_NAME, data[1]);
                    intent.putExtra(vars.TAG_EMAIL, data[2]);
                    intent.putExtra(vars.TAG_PHONE, data[3]);
                    intent.putExtra(vars.TAG_ADDRESS, data[4]);
                    intent.putExtra(vars.TAG_USER, data[5]);
                    intent.putExtra(vars.TAG_PASS, data[6]);
                    intent.putExtra(vars.TAG_PAYMENT, data[7]);
                    startActivityForResult(intent, 100);
                    return  "Success Buyer Login";
                }else if (success == 2) {
                    //// read the user data
                    String[] data = parser.getString(TAG_MESSAGE).split("##");
                    Intent intent = new Intent(getApplicationContext(), SellerActivity.class);
                    intent.putExtra(vars.TAG_ID, data[0]);
                    intent.putExtra(vars.TAG_NAME, data[1]);
                    intent.putExtra(vars.TAG_EMAIL, data[2]);
                    intent.putExtra(vars.TAG_PHONE, data[3]);
                    intent.putExtra(vars.TAG_ADDRESS, data[4]);
                    intent.putExtra(vars.TAG_USER, data[5]);
                    intent.putExtra(vars.TAG_PASS, data[6]);
                    startActivityForResult(intent, 100);
                    return  "Success Seller Login";
                }else if (success == 3) {
                    //// read the user data
                    String[] data = parser.getString(TAG_MESSAGE).split("##");
                    String status = data[9];
                    if(status.equals("Accepted")){
                        Intent intent = new Intent(getApplicationContext(), ExpertActivity.class);
                        intent.putExtra(vars.TAG_ID, data[0]);
                        intent.putExtra(vars.TAG_NAME, data[1]);
                        intent.putExtra(vars.TAG_EMAIL, data[2]);
                        intent.putExtra(vars.TAG_PHONE, data[3]);
                        intent.putExtra(vars.TAG_USER, data[4]);
                        intent.putExtra(vars.TAG_PASS, data[5]);
                        intent.putExtra(vars.TAG_EXPER1, data[6]);
                        intent.putExtra(vars.TAG_EXPER2, data[7]);
                        intent.putExtra(vars.TAG_EXPER3, data[8]);
                        startActivityForResult(intent, 100);
                        return  "Success Expert Login";
                    }else{
                        return  "Please wait until your account accepted";
                    }
                }else if (success == 4) {
                    //// read the user data
                    String[] data = parser.getString(TAG_MESSAGE).split("##");
                    String status = data[10];
                    if(status.equals("Accepted")){
                        Intent intent = new Intent(getApplicationContext(), CaptainActivity.class);
                        intent.putExtra(vars.TAG_ID, data[0]);
                        intent.putExtra(vars.TAG_NAME, data[1]);
                        intent.putExtra(vars.TAG_EMAIL, data[2]);
                        intent.putExtra(vars.TAG_PHONE, data[3]);
                        intent.putExtra(vars.TAG_USER, data[4]);
                        intent.putExtra(vars.TAG_PASS, data[5]);
                        intent.putExtra(vars.TAG_MODEL, data[6]);
                        intent.putExtra(vars.TAG_YEAR, data[7]);
                        intent.putExtra(vars.TAG_CITY, data[8]);
                        intent.putExtra(vars.TAG_WORKING, data[9]);
                        startActivityForResult(intent, 100);
                        return  "Success Delivery Captain Login";
                    }else{
                        return  "Please wait until your account accepted";
                    }
                }else if (success == 5) {
                    //// read the user data
                    String[] data = parser.getString(TAG_MESSAGE).split("##");
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    intent.putExtra(vars.TAG_ID, data[0]);
                    intent.putExtra(vars.TAG_NAME, data[1]);
                    intent.putExtra(vars.TAG_EMAIL, data[2]);
                    intent.putExtra(vars.TAG_USER, data[3]);
                    intent.putExtra(vars.TAG_PASS, data[4]);
                    startActivityForResult(intent, 100);
                    return  "Success Admin Login";
                }
                else {
                    return "No user found, try again";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            dialogBox.dismiss();
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
        }
    }


}