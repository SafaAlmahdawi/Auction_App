package com.example.auction.expert;

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
import com.example.auction.seller.EditProfileSellerActivity;
import com.example.auction.seller.SellerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProfileExpertActivity extends AppCompatActivity {

    AppCompatButton butSave;
    ImageView imgLogo, imgArrow;
    EditText txtName, txtEmail, txtPass, txtPhone, txtExper1, txtExper2, txtExper3;
    String name, email, phone, pass, uid, exper1="", exper2="", exper3="";

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
        setContentView(R.layout.activity_edit_profile_expert);

        butSave = findViewById(R.id.butSave);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtPhone = findViewById(R.id.txtPhone);
        txtExper1 = findViewById(R.id.txtExper1);
        txtExper2 = findViewById(R.id.txtExper2);
        txtExper3 = findViewById(R.id.txtExper3);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        pass = intent.getStringExtra(vars.TAG_PASS);
        exper1 = intent.getStringExtra(vars.TAG_EXPER1);
        exper2 = intent.getStringExtra(vars.TAG_EXPER2);
        exper3 = intent.getStringExtra(vars.TAG_EXPER3);

        txtName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtPass.setText(pass);
        txtExper1.setText(exper1);
        txtExper2.setText(exper2);
        txtExper3.setText(exper3);

        butSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           /// check for valid and complete inputs
                                           if (txtName.getText().toString().equals("") || txtPass.getText().toString().equals("")
                                                   || txtEmail.getText().toString().equals("") || txtPhone.getText().toString().equals("") || txtExper1.getText().toString().equals("")) {
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
                                               exper1 = txtExper1.getText().toString().trim();
                                               exper2 = txtExper2.getText().toString().trim();
                                               exper3 = txtExper3.getText().toString().trim();
                                               new EditProfile().execute();
                                           }

                                       }
                                   }
        );
        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
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
            dialogBox = new ProgressDialog(EditProfileExpertActivity.this);
            dialogBox.setMessage("Save Changes ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("id", uid));
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("userPass", pass));
                params.add(new BasicNameValuePair("phone", phone));
                params.add(new BasicNameValuePair("exper1", exper1));
                params.add(new BasicNameValuePair("exper2", exper2));
                params.add(new BasicNameValuePair("exper3", exper3));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_edit_expert, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ExpertActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_EXPER1, exper1);
                    intent.putExtra(vars.TAG_EXPER2, exper2);
                    intent.putExtra(vars.TAG_EXPER3, exper3);
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