package com.example.auction.admin;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssignOrdersActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    String uid, name, email, usern, pass;
    String selected_type, url_link;
    String strType;
    String orderID ="", prodName ="", prodPrice ="", payment ="", buyerID ="", prodID ="";
    Spinner txtSpin;
    AppCompatButton butView, butNext;
    CommonVars vars;

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
        setContentView(R.layout.activity_assign_orders);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtSpin = findViewById(R.id.txtSpin);
        butView = findViewById(R.id.butView);
        butNext = findViewById(R.id.butNext);

        orders_list = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();
        butView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           selected_type = txtSpin.getSelectedItem().toString().trim();
                                           if(selected_type.equals("Purchase Order")){
                                               strType = "Purchase";
                                               url_link = vars.url_view_purchase_orders;
                                               orders_list.clear();
                                           }else {
                                               strType = "Bidding";
                                               url_link = vars.url_view_bidding_orders;
                                               orders_list.clear();
                                           }
                                           new LoadAllOrders().execute();
                                       }
                                   }
        );

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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                orderID = ((TextView) view.findViewById(R.id.orderid)).getText().toString();
                payment = ((TextView) view.findViewById(R.id.payment)).getText().toString();
                buyerID = ((TextView) view.findViewById(R.id.buyerid)).getText().toString();
            }
        });

        butNext.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!orderID.equals("")){
                                                 Intent intent = new Intent(getApplicationContext(), AssignOrders2Activity.class);
                                                 intent.putExtra(vars.TAG_ID, uid);
                                                 intent.putExtra(vars.TAG_NAME, name);
                                                 intent.putExtra(vars.TAG_EMAIL, email);
                                                 intent.putExtra(vars.TAG_USER, usern);
                                                 intent.putExtra(vars.TAG_PASS, pass);
                                                 intent.putExtra(vars.TAG_ORDER_TYPE, strType);
                                                 intent.putExtra(vars.TAG_ORDER_ID, orderID);
                                                 intent.putExtra(vars.TAG_DELIVER_BUYER, buyerID);
                                                 startActivityForResult(intent, 100);
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose Order to assign", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
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
            dialogBox = new ProgressDialog(AssignOrdersActivity.this);
            dialogBox.setMessage("Read Orders data .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            //Posting user data to script
            JSONObject json = jParser.makeHttpRequest(url_link, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_DELIVERS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String o_id = r.getString(vars.TAG_DELIVER_ORDER);
                        String buy_id = r.getString(vars.TAG_DELIVER_BUYER);
                        String payment = r.getString(vars.TAG_DELIVER_PAYMENT);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(vars.TAG_DELIVER_ORDER, o_id);
                        map.put(vars.TAG_DELIVER_BUYER, buy_id);
                        if(payment.equals("Cash")){
                            map.put(vars.TAG_DELIVER_PAYMENT, "Cash");
                        }else{
                            map.put(vars.TAG_DELIVER_PAYMENT, "Credit Card");
                        }
                        orders_list.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            dialogBox.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(AssignOrdersActivity.this, orders_list,
                            R.layout.deliverslist, new String[]{vars.TAG_DELIVER_ORDER, vars.TAG_DELIVER_BUYER, vars.TAG_DELIVER_PAYMENT},
                            new int[]{R.id.orderid, R.id.buyerid, R.id.payment});
                    setListAdapter(adapter);
                }
            });
        }
    }
}