package com.example.auction.expert;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.seller.ManageProductActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EstimateProductsActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    String uid, name, email, phone, pass, exper1, exper2, exper3;
    String prodID ="", prodTitle ="", prodCat ="", prodModel ="", prodPrice ="", prodQty ="", prodStatus ="";
    String prodIMG1 ="", prodIMG2 ="", prodIMG3 ="", prodIMG4 ="", sellerID="";
    CommonVars vars;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> products_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_estimate_products);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        pass = intent.getStringExtra(vars.TAG_PASS);
        exper1 = intent.getStringExtra(vars.TAG_EXPER1);
        exper2 = intent.getStringExtra(vars.TAG_EXPER2);
        exper3 = intent.getStringExtra(vars.TAG_EXPER3);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);

        products_list = new ArrayList<HashMap<String, String>>();
        new LoadAllProducts().execute();
        ListView lv = getListView();

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
                prodID = ((TextView) view.findViewById(R.id.prod_id)).getText().toString();
                sellerID = ((TextView) view.findViewById(R.id.seller_id)).getText().toString();
                prodTitle = ((TextView) view.findViewById(R.id.prod_title)).getText().toString();
                prodPrice = ((TextView) view.findViewById(R.id.prod_price)).getText().toString();
                prodStatus = ((TextView) view.findViewById(R.id.prod_status)).getText().toString();
                prodCat = ((TextView) view.findViewById(R.id.prod_cat)).getText().toString();
                prodModel = ((TextView) view.findViewById(R.id.prod_model)).getText().toString();
                prodQty = ((TextView) view.findViewById(R.id.prod_qty)).getText().toString();
                prodIMG1 = ((TextView) view.findViewById(R.id.prod_img1)).getText().toString();
                prodIMG2 = ((TextView) view.findViewById(R.id.prod_img2)).getText().toString();
                prodIMG3 = ((TextView) view.findViewById(R.id.prod_img3)).getText().toString();
                prodIMG4 = ((TextView) view.findViewById(R.id.prod_img4)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), ViewProductExpertActivity.class);
                intent.putExtra(vars.TAG_ID, uid);
                intent.putExtra(vars.TAG_NAME, name);
                intent.putExtra(vars.TAG_EMAIL, email);
                intent.putExtra(vars.TAG_PHONE, phone);
                intent.putExtra(vars.TAG_PASS, pass);
                intent.putExtra(vars.TAG_EXPER1, exper1);
                intent.putExtra(vars.TAG_EXPER2, exper2);
                intent.putExtra(vars.TAG_EXPER3, exper3);
                intent.putExtra(vars.TAG_PROD_ID, prodID);
                intent.putExtra(vars.TAG_PROD_TITLE, prodTitle);
                intent.putExtra(vars.TAG_PROD_PRICE, prodPrice);
                intent.putExtra(vars.TAG_PROD_MODEL, prodModel);
                intent.putExtra(vars.TAG_PROD_STATUS, prodStatus);
                intent.putExtra(vars.TAG_PROD_IMG1, prodIMG1);
                intent.putExtra(vars.TAG_PROD_IMG2, prodIMG2);
                intent.putExtra(vars.TAG_PROD_IMG3, prodIMG3);
                intent.putExtra(vars.TAG_PROD_IMG4, prodIMG4);
                startActivityForResult(intent, 100);
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

    class LoadAllProducts extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(EstimateProductsActivity.this);
            dialogBox.setMessage("Read Products data .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            //Posting user data to script
            JSONObject json = jParser.makeHttpRequest(vars.url_view_bidding_products, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_PRODUCTS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String p_id = r.getString(vars.TAG_PROD_ID);
                        String title = r.getString(vars.TAG_PROD_TITLE);
                        String price = r.getString(vars.TAG_PROD_PRICE);
                        String state = r.getString(vars.TAG_PROD_STATUS);
                        String s_id = r.getString(vars.TAG_PROD_SELLER);
                        String qty = r.getString(vars.TAG_PROD_Qty);
                        String model = r.getString(vars.TAG_PROD_MODEL);
                        String cat = r.getString(vars.TAG_PROD_CAT);
                        String pic1 = r.getString(vars.TAG_PROD_IMG1);
                        String pic2 = r.getString(vars.TAG_PROD_IMG2);
                        String pic3 = r.getString(vars.TAG_PROD_IMG3);
                        String pic4 = r.getString(vars.TAG_PROD_IMG4);
///////////////// read only products for the current seller /////////////////

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_PROD_ID, p_id);
                            map.put(vars.TAG_PROD_TITLE, title);
                            map.put(vars.TAG_PROD_PRICE, price);
                            map.put(vars.TAG_PROD_STATUS, state);
                            map.put(vars.TAG_PROD_Qty, qty);
                            map.put(vars.TAG_PROD_MODEL, model);
                            map.put(vars.TAG_PROD_CAT, cat);
                            map.put(vars.TAG_PROD_SELLER, s_id);
                            map.put(vars.TAG_PROD_IMG1, pic1);
                            map.put(vars.TAG_PROD_IMG2, pic2);
                            map.put(vars.TAG_PROD_IMG3, pic3);
                            map.put(vars.TAG_PROD_IMG4, pic4);
                            products_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(EstimateProductsActivity.this, products_list,
                            R.layout.prodslist, new String[]{vars.TAG_PROD_ID, vars.TAG_PROD_TITLE, vars.TAG_PROD_PRICE, vars.TAG_PROD_STATUS, vars.TAG_PROD_Qty, vars.TAG_PROD_MODEL, vars.TAG_PROD_CAT, vars.TAG_PROD_SELLER, vars.TAG_PROD_IMG1, vars.TAG_PROD_IMG2, vars.TAG_PROD_IMG3, vars.TAG_PROD_IMG4},
                            new int[]{R.id.prod_id, R.id.prod_title, R.id.prod_price, R.id.prod_status, R.id.prod_qty, R.id.prod_model, R.id.prod_cat, R.id.seller_id, R.id.prod_img1, R.id.prod_img2, R.id.prod_img3, R.id.prod_img4 });
                    setListAdapter(adapter);
                }
            });
        }
    }
}