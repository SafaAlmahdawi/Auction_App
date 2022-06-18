package com.example.auction.buyer;

import androidx.appcompat.widget.AppCompatButton;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WinBiddingActivity extends ListActivity {

    String name, email, phone, address, usern, pass, payment, uid;
    ImageView imgLogo, imgArrow;
    AppCompatButton butPurchase;
    CommonVars vars;
    String bidID = "0", prodName, prodPrice, prodID, buyerID;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> orders_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_win_bidding);

        Intent i = getIntent();
        uid = i.getStringExtra(vars.TAG_ID);
        name = i.getStringExtra(vars.TAG_NAME);
        email = i.getStringExtra(vars.TAG_EMAIL);
        phone = i.getStringExtra(vars.TAG_PHONE);
        address = i.getStringExtra(vars.TAG_ADDRESS);
        usern = i.getStringExtra(vars.TAG_USER);
        pass = i.getStringExtra(vars.TAG_PASS);
        payment = i.getStringExtra(vars.TAG_PAYMENT);

        //// read the orders list
        orders_list = new ArrayList<HashMap<String, String>>();
        new LoadAllOrders().execute();

        ListView lv = getListView();

        imgArrow = findViewById(R.id.imgArrow);
        imgLogo = findViewById(R.id.imgLogo);
        butPurchase = findViewById(R.id.butPurchase);

        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
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
        butPurchase.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if(!bidID.equals("0")){
                                                   Intent in = new Intent(getApplicationContext(), CompleteWinActivity.class);
                                                   in.putExtra(vars.TAG_WIN_PID, prodID);
                                                   in.putExtra(vars.TAG_BID, bidID);
                                                   in.putExtra(vars.TAG_WIN_PROD_PRC, prodPrice);
                                                   in.putExtra(vars.TAG_WIN_PROD_NAME, prodName);
                                                   in.putExtra(vars.TAG_ID, uid);
                                                   in.putExtra(vars.TAG_NAME, name);
                                                   in.putExtra(vars.TAG_EMAIL, email);
                                                   in.putExtra(vars.TAG_PHONE, phone);
                                                   in.putExtra(vars.TAG_ADDRESS, address);
                                                   in.putExtra(vars.TAG_USER, usern);
                                                   in.putExtra(vars.TAG_PASS, pass);
                                                   in.putExtra(vars.TAG_PAYMENT, payment);
                                                   startActivityForResult(in, 100);
                                               }else{
                                                   Toast.makeText(getApplicationContext(), "You must choose a bidding to purchase", Toast.LENGTH_SHORT).show();
                                               }

                                           }
                                       }
        );

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                bidID = ((TextView) view.findViewById(R.id.orderid)).getText().toString();
                prodName = ((TextView) view.findViewById(R.id.prodName)).getText().toString();
                prodPrice = ((TextView) view.findViewById(R.id.prodPrice)).getText().toString();
                prodID = ((TextView) view.findViewById(R.id.prodid)).getText().toString();
                buyerID = ((TextView) view.findViewById(R.id.buyerid)).getText().toString();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class LoadAllOrders extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(WinBiddingActivity.this);
            dialogBox.setMessage("Load Bidding you win ....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(false);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);

            Log.d("request!", "starting");
            //Posting user data to script
            JSONObject json = jParser.makeHttpRequest(vars.url_view_win_orders, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_WINS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String name = r.getString(vars.TAG_WIN_PROD_NAME);
                        String pid = r.getString(vars.TAG_WIN_PID);
                        String adate = r.getString(vars.TAG_WIN_DATE);
                        String ordid = r.getString(vars.TAG_BID);
                        String custid = r.getString(vars.TAG_BUYER);
                        String price = r.getString(vars.TAG_WIN_PROD_PRC);
                        if(custid.equals(uid)){
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_WIN_PROD_NAME, name);
                            map.put(vars.TAG_WIN_DATE, adate);
                            map.put(vars.TAG_BID, ordid);
                            map.put(vars.TAG_WIN_PID, pid);
                            map.put(vars.TAG_BUYER, custid);
                            map.put(vars.TAG_WIN_PROD_PRC, price);
                            orders_list.add(map);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         //         * After completing background task Dismiss the progress dialog
         //         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            dialogBox.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(WinBiddingActivity.this, orders_list,
                            R.layout.winslist, new String[]{vars.TAG_WIN_PROD_NAME, vars.TAG_WIN_DATE , vars.TAG_BID, vars.TAG_WIN_PID, vars.TAG_BUYER, vars.TAG_WIN_PROD_PRC},
                            new int[]{R.id.prodName, R.id.orderDate, R.id.orderid, R.id.prodid, R.id.buyerid, R.id.prodPrice});
                    setListAdapter(adapter);
                }
            });

        }
    }
}