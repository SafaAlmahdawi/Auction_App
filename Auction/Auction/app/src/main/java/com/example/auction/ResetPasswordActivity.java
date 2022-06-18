package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class ResetPasswordActivity extends AppCompatActivity {

    AppCompatButton butReset;
    ImageView imgLogo, imgArrow;
    EditText txtResetPass, txtNewPass;
    String secretPassword, strPass, strType, strEmail;

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
        setContentView(R.layout.activity_reset_password);

        Intent i = getIntent();
        secretPassword = i.getStringExtra(TAG_KEY);
        strEmail = i.getStringExtra(TAG_EMAIL);
        strType = i.getStringExtra(TAG_TYPE);

        txtResetPass = (EditText) findViewById(R.id.txtResetPass);
        txtNewPass = (EditText) findViewById(R.id.txtNewPass);
        butReset = (AppCompatButton) findViewById(R.id.butReset);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);

        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
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
        butReset.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (txtResetPass.getText().toString().equals("")) {
                                                 Toast.makeText(getApplicationContext(), "Please type Reset Password", Toast.LENGTH_SHORT).show();
                                             }else if (txtNewPass.getText().toString().equals("")) {
                                                 Toast.makeText(getApplicationContext(), "Please type your New Password", Toast.LENGTH_SHORT).show();
                                             }else if(!txtResetPass.getText().toString().equals(secretPassword)){
                                                 Toast.makeText(getApplicationContext(), "Reset Password not matching with sent Password", Toast.LENGTH_SHORT).show();
                                             }else if (txtNewPass.getText().toString().length() < 6 || txtNewPass.getText().toString().length() > 20) {
                                                 Toast.makeText(getApplicationContext(), "Password should between 6 to 20 characters", Toast.LENGTH_SHORT).show();
                                             }
                                             else {
                                                 strPass = txtNewPass.getText().toString().trim();
                                                 new ResetPass().execute();
                                             }
                                         }
                                     }
        );
    }

    class ResetPass extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ResetPasswordActivity.this);
            dialogBox.setMessage("Edit your Password ...");
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
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_reset_pass, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, 100);
                    return "New Password is saved successfully";
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