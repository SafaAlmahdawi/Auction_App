package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    AppCompatButton butSignup, butLogin;
    Spinner txtSpin;
    EditText txtName, txtEmail, txtUser, txtPass;
    String strName, strUser, strEmail, strPass, strType;

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
        setContentView(R.layout.activity_create_account);

        butSignup = findViewById(R.id.butSignup);
        butLogin = findViewById(R.id.butLogin);
        txtSpin = findViewById(R.id.txtSpin);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);

        ArrayList spinnerTypeList = new ArrayList<String>();

        spinnerTypeList.add("Please select Type");
        spinnerTypeList.add("Buyer");
        spinnerTypeList.add("Seller");
        spinnerTypeList.add("Expert");
        spinnerTypeList.add("Delivery Captain");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerTypeList);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txtSpin.setAdapter(spinnerAdapter);

        butSignup.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             /// check for valid and complete inputs
                                             if (txtName.getText().toString().equals("") || txtPass.getText().toString().equals("")
                                                     || txtUser.getText().toString().equals("") || txtEmail.getText().toString().equals("")) {
                                                 Toast.makeText(getApplicationContext(), "Lost Required Data", Toast.LENGTH_SHORT).show();
                                             }
                                             else if (txtPass.getText().toString().length() < 6 || txtPass.getText().toString().length() > 20) {
                                                 Toast.makeText(getApplicationContext(), "Password should between 6 to 20 characters", Toast.LENGTH_SHORT).show();
                                             }
                                             else {
                                                 strName = txtName.getText().toString().trim();
                                                 strEmail = txtEmail.getText().toString().trim();
                                                 strUser = txtUser.getText().toString().trim();
                                                 strPass = txtPass.getText().toString().trim();
                                                 strType = txtSpin.getSelectedItem().toString();

                                                 new AddUser().execute();
                                             }

                                         }
                                     }
        );
        butLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivityForResult(intent, 100);
                                        }
                                    }
        );
    }

    class AddUser extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(CreateAccountActivity.this);
            dialogBox.setMessage("Add New User ...");
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
                params.add(new BasicNameValuePair("name", strName));
                params.add(new BasicNameValuePair("email", strEmail));
                params.add(new BasicNameValuePair("userName", strUser));
                params.add(new BasicNameValuePair("userPass", strPass));
                params.add(new BasicNameValuePair("userType", strType));
                Log.d("request!", "starting");
                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(vars.url_signup, "POST", params);
                // full json response
                Log.d("Signup attempt", json.toString());
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Signup Success!", json.toString());
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(in, 100);
                    return json.getString(TAG_MESSAGE);
                }
                else {
                    Log.d("Process Failure!", json.getString(TAG_MESSAGE));
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