package com.example.auction.captain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.expert.EditProfileExpertActivity;
import com.example.auction.expert.ExpertActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProfileCaptainActivity extends AppCompatActivity {

    AppCompatButton butSave;
    ImageView imgLogo, imgArrow;
    EditText txtName, txtEmail, txtPass, txtPhone, txtCar, txtCarYear, txtCity, txtWorking;
    String name, email, phone, pass, uid, car="", caryear="", city="", working="";

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
        setContentView(R.layout.activity_edit_profile_captain);

        butSave = findViewById(R.id.butSave);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtPhone = findViewById(R.id.txtPhone);
        txtCar = findViewById(R.id.txtCar);
        txtCarYear = findViewById(R.id.txtCarYear);
        txtCity = findViewById(R.id.txtCity);
        txtWorking = findViewById(R.id.txtWorking);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        pass = intent.getStringExtra(vars.TAG_PASS);
        car = intent.getStringExtra(vars.TAG_MODEL);
        caryear = intent.getStringExtra(vars.TAG_YEAR);
        city = intent.getStringExtra(vars.TAG_CITY);
        working = intent.getStringExtra(vars.TAG_WORKING);

        txtName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtPass.setText(pass);
        txtCar.setText(car);
        txtCarYear.setText(caryear);
        txtCity.setText(city);
        txtWorking.setText(working);

        butSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           /// check for valid and complete inputs
                                           if (txtName.getText().toString().equals("") || txtPass.getText().toString().equals("")
                                                   || txtEmail.getText().toString().equals("") || txtPhone.getText().toString().equals("")
                                                   || txtCar.getText().toString().equals("") || txtCarYear.getText().toString().equals("")
                                                   || txtCity.getText().toString().equals("") || txtWorking.getText().toString().equals("")) {
                                               Toast.makeText(getApplicationContext(), "Missing Required Data", Toast.LENGTH_SHORT).show();
                                           }
                                           else if (txtPass.getText().toString().length() < 6 || txtPass.getText().toString().length() > 20) {
                                               Toast.makeText(getApplicationContext(), "Password should between 6 to 20 characters", Toast.LENGTH_SHORT).show();
                                           }
                                           else {
                                               name = txtName.getText().toString().trim();
                                               email = txtEmail.getText().toString().trim();
                                               pass = txtPass.getText().toString().trim();
                                               phone = txtPhone.getText().toString().trim();
                                               car = txtCar.getText().toString().trim();
                                               caryear = txtCarYear.getText().toString().trim();
                                               city = txtCity.getText().toString().trim();
                                               working = txtWorking.getText().toString().trim();
                                               new EditProfile().execute();
                                           }

                                       }
                                   }
        );
        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), CaptainActivity.class);
                                            intent.putExtra(vars.TAG_ID, uid);
                                            intent.putExtra(vars.TAG_NAME, name);
                                            intent.putExtra(vars.TAG_EMAIL, email);
                                            intent.putExtra(vars.TAG_PHONE, phone);
                                            intent.putExtra(vars.TAG_PASS, pass);
                                            intent.putExtra(vars.TAG_MODEL, car);
                                            intent.putExtra(vars.TAG_YEAR, caryear);
                                            intent.putExtra(vars.TAG_CITY, city);
                                            intent.putExtra(vars.TAG_WORKING, working);
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

    class EditProfile extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(EditProfileCaptainActivity.this);
            dialogBox.setMessage("Save Changes ...");
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
                params.add(new BasicNameValuePair("id", uid));
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("userPass", pass));
                params.add(new BasicNameValuePair("phone", phone));
                params.add(new BasicNameValuePair("car", car));
                params.add(new BasicNameValuePair("caryear", caryear));
                params.add(new BasicNameValuePair("city", city));
                params.add(new BasicNameValuePair("working", working));

                JSONObject json = jsonParser.makeHttpRequest(vars.url_edit_captain, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), CaptainActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_MODEL, car);
                    intent.putExtra(vars.TAG_YEAR, caryear);
                    intent.putExtra(vars.TAG_CITY, city);
                    intent.putExtra(vars.TAG_WORKING, working);
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