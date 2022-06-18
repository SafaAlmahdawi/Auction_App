package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class CompleteWinActivity extends AppCompatActivity {
    AppCompatButton butSave;
    Spinner txtSpin;
    TextView txtDetails;
    String name, email, phone, address, usern, pass, payment, uid;
    String bidID = "0", prodName, prodPrice, prodID;
    String payment_type;

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
        setContentView(R.layout.activity_complete_win);

        butSave = findViewById(R.id.butSave);
        txtSpin = findViewById(R.id.txtSpin);
        txtDetails = findViewById(R.id.txtDetails);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);
        bidID = intent.getStringExtra(vars.TAG_BID);
        prodID = intent.getStringExtra(vars.TAG_WIN_PID);
        prodName = intent.getStringExtra(vars.TAG_WIN_PROD_NAME);
        prodPrice = intent.getStringExtra(vars.TAG_WIN_PROD_PRC);

        txtDetails.setText("Product Name: " + prodName);
        txtDetails.append(System.getProperty("line.separator"));
        txtDetails.append("Product Price: " + prodPrice + " SR.");

        butSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           payment_type = txtSpin.getSelectedItem().toString();
                                           if(payment_type.equals("Cash")){
                                               Toast.makeText(getApplicationContext(), "You should pay on Delivery", Toast.LENGTH_LONG).show();
                                               new ChangeStatus().execute();
                                           }else{
                                               Intent intent = new Intent(getApplicationContext(), CardInformation2Activity.class);
                                               intent.putExtra(vars.TAG_BID, bidID);
                                               intent.putExtra(vars.TAG_ID, uid);
                                               intent.putExtra(vars.TAG_NAME, name);
                                               intent.putExtra(vars.TAG_EMAIL, email);
                                               intent.putExtra(vars.TAG_PHONE, phone);
                                               intent.putExtra(vars.TAG_ADDRESS, address);
                                               intent.putExtra(vars.TAG_USER, usern);
                                               intent.putExtra(vars.TAG_PASS, pass);
                                               intent.putExtra(vars.TAG_PAYMENT, payment);
                                               startActivityForResult(intent, 100);
                                           }
                                       }
                                   }
        );

    }
    class ChangeStatus extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(CompleteWinActivity.this);
            dialogBox.setMessage("Complete Purchase in Progress ...");
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
                params.add(new BasicNameValuePair("bidID", bidID));
                params.add(new BasicNameValuePair("pay", payment_type));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_bid_change_status, "POST", params);
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