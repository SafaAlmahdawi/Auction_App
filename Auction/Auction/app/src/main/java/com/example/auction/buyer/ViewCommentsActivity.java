package com.example.auction.buyer;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.auction.admin.AcceptRejectExpertsActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewCommentsActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    CommonVars vars;
    String reviewID, prodID, reviewBody, reviewDate, buyerName, prod_id;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> reviews_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_comments);

        Intent intent = getIntent();
        prodID = intent.getStringExtra(vars.TAG_PROD_ID);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);

        reviews_list = new ArrayList<HashMap<String, String>>();
        new LoadAllReviews().execute();
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
                reviewID = ((TextView) view.findViewById(R.id.review_id)).getText().toString();
                prod_id = ((TextView) view.findViewById(R.id.prod_id)).getText().toString();
                reviewBody = ((TextView) view.findViewById(R.id.review_body)).getText().toString();
                reviewDate = ((TextView) view.findViewById(R.id.review_date)).getText().toString();
                buyerName = ((TextView) view.findViewById(R.id.buyer_name)).getText().toString();
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

    class LoadAllReviews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ViewCommentsActivity.this);
            dialogBox.setMessage("Read Product Reviews .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            JSONObject json = jParser.makeHttpRequest(vars.url_view_review, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_REVIEWS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String rev_id = r.getString(vars.TAG_REV_ID);
                        String rev_prod = r.getString(vars.TAG_REV_PROD);
                        String rev_body = r.getString(vars.TAG_REV_BODY);
                        String rev_date = r.getString(vars.TAG_REV_DATE);
                        String buyer_name = r.getString(vars.TAG_REV_BUYER);
                        if(rev_prod.equals(prodID)){
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_REV_ID, rev_id);
                            map.put(vars.TAG_REV_PROD, rev_prod);
                            map.put(vars.TAG_REV_BODY, rev_body);
                            map.put(vars.TAG_REV_DATE, rev_date);
                            map.put(vars.TAG_REV_BUYER, buyer_name);
                            reviews_list.add(map);
                        }
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
                    ListAdapter adapter = new SimpleAdapter(ViewCommentsActivity.this, reviews_list,
                            R.layout.reviewslist, new String[]{vars.TAG_REV_ID, vars.TAG_REV_PROD, vars.TAG_REV_BODY, vars.TAG_REV_DATE, vars.TAG_REV_BUYER},
                            new int[]{R.id.review_id, R.id.prod_id, R.id.review_body, R.id.review_date, R.id.buyer_name});
                    setListAdapter(adapter);
                }
            });
        }
    }
}