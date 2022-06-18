package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardInformationActivity extends AppCompatActivity {
    AppCompatButton butPay;
    Spinner txtSpin;
    EditText txtMonth, txtYear, txtVerfy;
    String name, email, phone, address, usern, pass, payment, uid;
    String orderID;
    String payment_type, card_type, expire_month, expire_year, verify_code;

    /////// codes for dealing with database
    CommonVars vars;
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_card_information);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);
        orderID = intent.getStringExtra(vars.TAG_OID);

        butPay = findViewById(R.id.btn_pay);
        txtSpin = findViewById(R.id.txtSpin);
        txtMonth = findViewById(R.id.txtMonth);
        txtYear = findViewById(R.id.txtYear);
        txtVerfy = findViewById(R.id.txtVerfy);

        payment_type = "Credit Card";

        butPay.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(txtMonth.getText().toString().trim().isEmpty()){
                           txtMonth.setError("Expiration Month is required");
                           txtMonth.requestFocus();
                           return;
                       }
                       else if(txtYear.getText().toString().trim().isEmpty()){
                           txtYear.setError("Expiration Year is required");
                           txtYear.requestFocus();
                           return;
                       }
                       else if(txtVerfy.getText().toString().trim().isEmpty()){
                           txtVerfy.setError("Verification Code is required");
                           txtVerfy.requestFocus();
                           return;
                       }
                       else if(!(Integer.parseInt(txtMonth.getText().toString()) >=1 && Integer.parseInt(txtMonth.getText().toString()) <= 12)){
                           txtMonth.setError("Expiration Month is not valid");
                           txtMonth.requestFocus();
                           return;
                       }
                       else if(!(Integer.parseInt(txtYear.getText().toString()) >= 2022)){
                           txtYear.setError("Expiration Year is not valid");
                           txtYear.requestFocus();
                           return;
                       }else{
                           card_type = txtSpin.getSelectedItem().toString();
                           expire_month = txtMonth.getText().toString();
                           expire_year = txtYear.getText().toString();
                           verify_code = txtVerfy.getText().toString();
                           new ChangeStatus().execute();
                       }
                   }
               }
        );
    }
    class ChangeStatus extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(CardInformationActivity.this);
            dialogBox.setMessage("Complete Order in Progress ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("orderID", orderID));
                params.add(new BasicNameValuePair(" pay", payment_type));
                params.add(new BasicNameValuePair("card", card_type));
                params.add(new BasicNameValuePair("expire_month", expire_month));
                params.add(new BasicNameValuePair("expire_year", expire_year));
                params.add(new BasicNameValuePair("verify_code", verify_code));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_change_status2, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), BuyerActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_PAYMENT, payment);
                    startActivityForResult(intent, 100);
                    return json.getString(TAG_MESSAGE);
                }
                else {
                    return json.getString(TAG_MESSAGE);
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