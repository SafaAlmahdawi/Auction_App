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

public class UpdateProductActivity extends AppCompatActivity {

    AppCompatButton butSave, butEdit;
    ImageView imgLogo, imgArrow;
    EditText txtTitle, txtQuantity, txtPrice, txtModel;
    Spinner txtCategory, txtStatus;
    String uid, name, email, phone, address, usern, pass;
    String strTitle, strQuantity, strPrice, strModel, strCategory, strStatus, strPID, strIMG1, strIMG2, strIMG3,strIMG4 ;

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
        setContentView(R.layout.activity_update_product);


        butSave = findViewById(R.id.butSave);
        butEdit = findViewById(R.id.butEdit);
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
        strPID = intent.getStringExtra(vars.TAG_PROD_ID);
        strTitle = intent.getStringExtra(vars.TAG_PROD_TITLE);
        strCategory = intent.getStringExtra(vars.TAG_PROD_CAT);
        strModel = intent.getStringExtra(vars.TAG_PROD_MODEL);
        strPrice = intent.getStringExtra(vars.TAG_PROD_PRICE);
        strQuantity = intent.getStringExtra(vars.TAG_PROD_Qty);
        strStatus = intent.getStringExtra(vars.TAG_PROD_STATUS);
        strIMG1 = intent.getStringExtra(vars.TAG_PROD_IMG1);
        strIMG2 = intent.getStringExtra(vars.TAG_PROD_IMG2);
        strIMG3 = intent.getStringExtra(vars.TAG_PROD_IMG3);
        strIMG4 = intent.getStringExtra(vars.TAG_PROD_IMG4);

        txtTitle.setText(strTitle);
        txtPrice.setText(strPrice);
        txtModel.setText(strModel);
        txtQuantity.setText(strQuantity);
        for (int i = 0; i < txtCategory.getCount(); i++) {
            if (txtCategory.getItemAtPosition(i).equals(strCategory)) {
                txtCategory.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < txtStatus.getCount(); i++) {
            if (txtStatus.getItemAtPosition(i).equals(strStatus)) {
                txtStatus.setSelection(i);
                break;
            }
        }

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
                                               new UpdateProduct().execute();
                                           }
                                       }
                                   }
        );
        butEdit.setOnClickListener(new View.OnClickListener() {
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
                                               new UpdateProduct2().execute();
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
    class UpdateProduct extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(UpdateProductActivity.this);
            dialogBox.setMessage("Update Product Information ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("id", strPID));
                params.add(new BasicNameValuePair("title", strTitle));
                params.add(new BasicNameValuePair("quantity", strQuantity));
                params.add(new BasicNameValuePair("category", strCategory));
                params.add(new BasicNameValuePair("price", strPrice));
                params.add(new BasicNameValuePair("model", strModel));
                params.add(new BasicNameValuePair("status", strStatus));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_update_product, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String prodID = json.getString(TAG_MESSAGE);
                    Intent intent = new Intent(getApplicationContext(), ManageProductActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    startActivityForResult(intent, 100);
                    return "Product updated Successfully!";
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

    class UpdateProduct2 extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(UpdateProductActivity.this);
            dialogBox.setMessage("Update Product Information ...");
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
                params.add(new BasicNameValuePair("id", strPID));
                params.add(new BasicNameValuePair("title", strTitle));
                params.add(new BasicNameValuePair("quantity", strQuantity));
                params.add(new BasicNameValuePair("category", strCategory));
                params.add(new BasicNameValuePair("price", strPrice));
                params.add(new BasicNameValuePair("model", strModel));
                params.add(new BasicNameValuePair("status", strStatus));

                JSONObject json = jsonParser.makeHttpRequest(vars.url_update_product, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String prodID = json.getString(TAG_MESSAGE);
                    Intent intent = new Intent(getApplicationContext(), AddImageActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_PROD_ID, strPID);
                    intent.putExtra(vars.TAG_PROD_IMG1, strIMG1);
                    intent.putExtra(vars.TAG_PROD_IMG2, strIMG2);
                    intent.putExtra(vars.TAG_PROD_IMG3, strIMG3);
                    intent.putExtra(vars.TAG_PROD_IMG4, strIMG4);
                    startActivityForResult(intent, 100);
                    return "Step2: Update Images!";
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