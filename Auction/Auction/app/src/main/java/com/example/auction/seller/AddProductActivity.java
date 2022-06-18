package com.example.auction.seller;

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
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    AppCompatButton butSave;
    ImageView imgLogo, imgArrow;
    EditText txtTitle, txtQuantity, txtPrice, txtModel;
    Spinner txtCategory, txtStatus;
    String uid, name, email, phone, address, usern, pass;
    String strTitle, strQuantity, strPrice, strModel, strCategory, strStatus;

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
        setContentView(R.layout.activity_add_product);

        butSave = findViewById(R.id.butSave);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtTitle = findViewById(R.id.txtTitle);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtPrice = findViewById(R.id.txtPrice);
        txtModel = findViewById(R.id.txtModel);
        txtCategory = findViewById(R.id.txtCategory);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);

        butSave.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       /// check for valid and complete inputs
                       if (txtTitle.getText().toString().equals("") || txtCategory.getSelectedItem().toString().equals("Choose Product Category")
                               || txtQuantity.getText().toString().equals("") || txtPrice.getText().toString().equals("")
                               || txtModel.getText().toString().equals("") || txtStatus.getSelectedItem().toString().equals("Choose Product Status"))
                       {
                           Toast.makeText(getApplicationContext(), "Missing Required Data", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           strTitle = txtTitle.getText().toString().trim();
                           strQuantity = txtQuantity.getText().toString().trim();
                           strPrice = txtPrice.getText().toString().trim();
                           strModel = txtModel.getText().toString().trim();
                           strCategory = txtCategory.getSelectedItem().toString();
                           strStatus = txtStatus.getSelectedItem().toString();
                           new AddProduct().execute();
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
    class AddProduct extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AddProductActivity.this);
            dialogBox.setMessage("Add Product Information ...");
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
                params.add(new BasicNameValuePair("title", strTitle));
                params.add(new BasicNameValuePair("quantity", strQuantity));
                params.add(new BasicNameValuePair("category", strCategory));
                params.add(new BasicNameValuePair("price", strPrice));
                params.add(new BasicNameValuePair("model", strModel));
                params.add(new BasicNameValuePair("status", strStatus));

                JSONObject json = jsonParser.makeHttpRequest(vars.url_add_product, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String prodID = json.getString(TAG_MESSAGE);
                    Intent intent = new Intent(getApplicationContext(), AddProductImageActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_PRODUCT, prodID);
                    startActivityForResult(intent, 100);
                    return "Add Product Step2";
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