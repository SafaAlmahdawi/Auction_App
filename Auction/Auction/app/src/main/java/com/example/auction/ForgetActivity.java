package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ForgetActivity extends AppCompatActivity {

    AppCompatButton butSubmit;
    TextView txtLogin;
    Spinner txtSpin;
    ImageView imgLogo, imgArrow;
    EditText txtEmail;
    String secretPassword, strEmail, strType;

    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    private static final String TAG_KEY= "resetPassword";
    private static final String TAG_EMAIL= "email";
    private static final String TAG_TYPE= "type";
    ProgressDialog dialogBox;
    CommonVars vars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget);

        butSubmit = findViewById(R.id.butSubmit);
        txtLogin = findViewById(R.id.txtLogin);
        txtSpin = findViewById(R.id.txtSpin);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtEmail = findViewById(R.id.txtEmail);


        ArrayList spinnerTypeList = new ArrayList<String>();

        spinnerTypeList.add("Please select Type");
        spinnerTypeList.add("Manager");
        spinnerTypeList.add("Buyer");
        spinnerTypeList.add("Seller");
        spinnerTypeList.add("Expert");
        spinnerTypeList.add("Delivery Captain");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerTypeList);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSpin.setAdapter(spinnerAdapter);

        butSubmit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (txtEmail.getText().toString().equals("")) {
                                                Toast.makeText(getApplicationContext(), "Please type your Email", Toast.LENGTH_SHORT).show();
                                            } else if(txtSpin.getSelectedItem().toString().equals("Please select Type")){
                                                Toast.makeText(getApplicationContext(), "Please select your Type", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                strEmail = txtEmail.getText().toString().trim();
                                                strType = txtSpin.getSelectedItem().toString().trim();
                                                secretPassword = UUID.randomUUID().toString();
                                                secretPassword= secretPassword.substring(0,8);
                                                new ResetPass().execute();
                                            }
                                        }
                                    }
        );
        txtLogin.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                             startActivityForResult(intent, 100);
                                         }
                                     }
        );
        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivityForResult(intent, 100);
                                        }
                                    }
        );
        imgLogo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivityForResult(intent, 100);
                                        }
                                    }
        );
    }

    class ResetPass extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ForgetActivity.this);
            dialogBox.setMessage("Verify Email ...");
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
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("type", strType));
                parameters.add(new BasicNameValuePair("code", secretPassword));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_forget, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                    intent.putExtra(TAG_KEY, secretPassword);
                    intent.putExtra(TAG_EMAIL, strEmail);
                    intent.putExtra(TAG_TYPE, strType);
                    startActivityForResult(intent, 100);
                    return "Check your email reset password is sent";
                } else {
                    return "No email found, try again";
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