package com.example.auction.captain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.admin.AdminActivity;
import com.example.auction.admin.AssignOrders2Activity;
import com.example.auction.buyer.BuyerActivity;
import com.example.auction.expert.ExpertActivity;
import com.example.auction.seller.SellerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderDetailsActivity extends AppCompatActivity {

    TextView txtBuyer, txtSeller, txtOrder;
    AppCompatButton butSave;
    ImageView imgHome, imgArrow;
    String name, email, phone, pass, uid, car="", caryear="", city="", working="";
    String orderID ="", buyerID ="", prodID ="", orderType ="";
    CommonVars vars;
    String[] buyerData = new String[3];
    String[] sellerData = new String[3];
    String[] orderData = new String[3];

    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_order_details);

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
        orderID = intent.getStringExtra(vars.TAG_ORDER_ORDERID);
        buyerID = intent.getStringExtra(vars.TAG_ORDER_BUYER);
        prodID = intent.getStringExtra(vars.TAG_ORDER_PROD);
        orderType = intent.getStringExtra(vars.TAG_ORDER_TYPE);

        //Toast.makeText(getApplicationContext(), "Order: " + orderID + "BuyerID: " + buyerID + "ProdID: " + prodID, Toast.LENGTH_LONG).show();

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtBuyer = findViewById(R.id.txtBuyer);
        txtSeller = findViewById(R.id.txtSeller);
        txtOrder = findViewById(R.id.txtOrder);
        butSave = findViewById(R.id.butSave);

        new LoadData().execute();
        imgHome.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                           startActivityForResult(intent, 100);
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
        butSave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                           new DoneOrder().execute();
                                        }
                                    }
        );
    }

    class LoadData extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ViewOrderDetailsActivity.this);
            dialogBox.setMessage("Read Order Details .....");
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
                parameters.add(new BasicNameValuePair("orderID", orderID));
                parameters.add(new BasicNameValuePair("orderType", orderType));
                parameters.add(new BasicNameValuePair("buyerID", buyerID));
                parameters.add(new BasicNameValuePair("prodID", prodID));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_view_order_details, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                String[] data;
                if (success == 1) {
                    //// read the user data
                    data = parser.getString(TAG_MESSAGE).split("##");
                    buyerData[0] = data[0];
                    buyerData[1] = data[1];
                    buyerData[2] = data[2];
                    sellerData[0] = data[3];
                    sellerData[1] = data[4];
                    sellerData[2] = data[5];
                    orderData[0] = data[6];
                    orderData[1] = data[7];
                    orderData[2] = data[8];
                    return "Success";
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            dialogBox.dismiss();
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
            txtBuyer.setText("Buyer Name: " + buyerData[0] + "\n Buyer Phone: " + buyerData[1] + "\n Buyer Address: " + buyerData[2]);
            txtSeller.setText("Seller Name: " + sellerData[0] + "\n Seller Phone: " + sellerData[1] + "\n Seller Address: " + sellerData[2]);
            txtOrder.setText("Order Type: " + orderType + "\n Product Title: " + orderData[0] + "\n Price: " + orderData[1] + "\n Payment: " + orderData[2]);
        }
    }

    class DoneOrder extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ViewOrderDetailsActivity.this);
            dialogBox.setMessage("Save Order Status ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("deliverID", uid));
                params.add(new BasicNameValuePair("orderID", orderID));
                params.add(new BasicNameValuePair("orderType", orderType));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_done_order, "POST", params);
                Log.d("Assign Order", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    dialogBox.dismiss();
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}