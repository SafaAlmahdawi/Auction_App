package com.example.auction.buyer;

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

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LiveBiddingActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    CommonVars vars;
    String bidID, prodID, minPrice, lastDate, prod_id, prodCat ="", prodModel ="", prodTitle ="";
    String prodIMG1 ="";
    String bid_date;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> bidding_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;
    String name, email, phone, address, usern, pass, payment, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_live_bidding);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);

        bidding_list = new ArrayList<HashMap<String, String>>();
        new LoadLiveBidding().execute();
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
                bidID = ((TextView) view.findViewById(R.id.bid_id)).getText().toString();
                prod_id = ((TextView) view.findViewById(R.id.prod_id)).getText().toString();
                prodTitle = ((TextView) view.findViewById(R.id.prod_title)).getText().toString();
                minPrice = ((TextView) view.findViewById(R.id.prod_price)).getText().toString();
                lastDate = ((TextView) view.findViewById(R.id.remain_time)).getText().toString();
                prodCat = ((TextView) view.findViewById(R.id.prod_cat)).getText().toString();
                prodModel = ((TextView) view.findViewById(R.id.prod_model)).getText().toString();
                prodIMG1 = ((TextView) view.findViewById(R.id.prod_img1)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), ParticipateBiddingActivity.class);
                intent.putExtra(vars.TAG_ID, uid);
                intent.putExtra(vars.TAG_NAME, name);
                intent.putExtra(vars.TAG_EMAIL, email);
                intent.putExtra(vars.TAG_PHONE, phone);
                intent.putExtra(vars.TAG_ADDRESS, address);
                intent.putExtra(vars.TAG_USER, usern);
                intent.putExtra(vars.TAG_PASS, pass);
                intent.putExtra(vars.TAG_PAYMENT, payment);
                intent.putExtra(vars.TAG_BID_TITLE, prodTitle);
                intent.putExtra(vars.TAG_BID_ID, bidID);
                intent.putExtra(vars.TAG_BID_PROD, prod_id);
                intent.putExtra(vars.TAG_BID_PRICE, minPrice);
                intent.putExtra(vars.TAG_BID_MODEL, prodModel);
                intent.putExtra(vars.TAG_BID_IMG, prodIMG1);
                intent.putExtra(vars.TAG_BID_DATE, bid_date);
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

    class LoadLiveBidding extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(LiveBiddingActivity.this);
            dialogBox.setMessage("Read Live Auctions .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Long newVal;
            long today_time;
            long diff;
            Log.d("request!", "starting");
            //Posting user data to script
            JSONObject json = jParser.makeHttpRequest(vars.url_live_bidding, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_BIDDINGS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String bid_id = r.getString(vars.TAG_BID_ID);
                        String bidProd = r.getString(vars.TAG_BID_PROD);
                        String bid_title = r.getString(vars.TAG_BID_TITLE);
                        bid_date = r.getString(vars.TAG_BID_DATE);
                        String bid_price = r.getString(vars.TAG_BID_PRICE);
                        String bid_cat = r.getString(vars.TAG_BID_CAT);
                        String bid_model = r.getString(vars.TAG_BID_MODEL);
                        String bid_image = r.getString(vars.TAG_BID_IMG);
                        ////// change biddate to days, hours, and minutes //////
                        Date today = new Date();
                        today_time = today.getTime();
                        diff = Long.parseLong(bid_date) - today_time;
                        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                        newVal = diff - (1000 * 60 * 60 * 24 * numOfDays);
                        int hours = (int) (newVal / (1000 * 60 * 60));
                        newVal = newVal - (1000 * 60 * 60 * hours);
                        int minutes = (int) (newVal / (1000 * 60));
                        newVal = newVal - (1000 * 60 * minutes);
                        long seconds =  (int) (newVal/1000);
                        String bidDate = numOfDays + "days " + hours + "H. " + minutes + "Min. " + seconds + "Sec.";
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_BID_ID, bid_id);
                            map.put(vars.TAG_BID_PROD, bidProd);
                            map.put(vars.TAG_BID_TITLE, bid_title);
                            map.put(vars.TAG_BID_DATE, bidDate);
                            map.put(vars.TAG_BID_PRICE, bid_price);
                            map.put(vars.TAG_BID_CAT, bid_cat);
                            map.put(vars.TAG_BID_MODEL, bid_model);
                            map.put(vars.TAG_BID_IMG, bid_image);
                            bidding_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(LiveBiddingActivity.this, bidding_list,
                            R.layout.livebiddinglist, new String[]{vars.TAG_BID_ID, vars.TAG_BID_PROD, vars.TAG_BID_TITLE, vars.TAG_BID_DATE, vars.TAG_BID_PRICE, vars.TAG_BID_CAT, vars.TAG_BID_MODEL, vars.TAG_BID_IMG},
                            new int[]{R.id.bid_id, R.id.prod_id, R.id.prod_title, R.id.remain_time, R.id.prod_price, R.id.prod_cat, R.id.prod_model, R.id.prod_img1});
                    setListAdapter(adapter);
                }
            });
        }
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}