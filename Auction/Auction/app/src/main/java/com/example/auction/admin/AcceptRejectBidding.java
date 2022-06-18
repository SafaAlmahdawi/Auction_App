package com.example.auction.admin;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.seller.BiddingPeriodActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRejectBidding extends ListActivity {

    ImageView imgHome, imgArrow;
    String uid, name, email, usern, pass;
    String reqID ="", prodName ="", reqDate ="", prodPrice ="", prodStatus ="", reqStart ="", reqEnd ="";
    Spinner txtSpin;
    ImageView butAccept, butReject;
    CommonVars vars;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> reqs_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_accept_reject_bidding);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtSpin = findViewById(R.id.txtSpin);
        butAccept = findViewById(R.id.butAccept);
        butReject = findViewById(R.id.butReject);

        reqs_list = new ArrayList<HashMap<String, String>>();
        new LoadAllRequests().execute();
        ListView lv = getListView();
        butAccept.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!reqID.equals("")){
                                                 new AcceptRequest().execute();
                                             }else {
                                                 Toast.makeText(getApplicationContext(), "Please choose Bidding Request to Accept", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
        butReject.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!reqID.equals("")){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose Bidding Request to Reject", Toast.LENGTH_SHORT).show();
                                             }
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
                reqID = ((TextView) view.findViewById(R.id.req_id)).getText().toString();
                prodName = ((TextView) view.findViewById(R.id.prod_name)).getText().toString();
                reqDate = ((TextView) view.findViewById(R.id.req_date)).getText().toString();
                prodPrice = ((TextView) view.findViewById(R.id.prod_price)).getText().toString();
                prodStatus = ((TextView) view.findViewById(R.id.prod_status)).getText().toString();
                reqStart = ((TextView) view.findViewById(R.id.req_start)).getText().toString();
                reqEnd = ((TextView) view.findViewById(R.id.req_end)).getText().toString();
            }
        });
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Reject Bidding Request")
                .setMessage("Are you sure you want to reject selected Bidding Request?")
                .setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        new RejectRequest().execute();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
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

    class LoadAllRequests extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectBidding.this);
            dialogBox.setMessage("Read Waiting Bidding Requests data .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            JSONObject json = jParser.makeHttpRequest(vars.url_view_requests, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_REQUESTS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String req_id = r.getString(vars.TAG_REQ_ID);
                        String prod_name = r.getString(vars.TAG_REQ_PROD_NAME);
                        String prod_price = r.getString(vars.TAG_REQ_PROD_PRICE);
                        String prod_status = r.getString(vars.TAG_REQ_PROD_STATUS);
                        String req_date = r.getString(vars.TAG_REQ_DATE);
                        String reqSRT = r.getString(vars.TAG_REQ_START);
                        String reqE = r.getString(vars.TAG_REQ_END);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(vars.TAG_REQ_ID, req_id);
                        map.put(vars.TAG_REQ_PROD_NAME, prod_name);
                        map.put(vars.TAG_REQ_PROD_PRICE, prod_price);
                        map.put(vars.TAG_REQ_PROD_STATUS, prod_status);
                        map.put(vars.TAG_REQ_DATE, req_date);
                        map.put(vars.TAG_REQ_START, reqSRT);
                        map.put(vars.TAG_REQ_END, reqE);
                        reqs_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(AcceptRejectBidding.this, reqs_list,
                            R.layout.requestlist, new String[]{vars.TAG_REQ_ID, vars.TAG_REQ_PROD_NAME, vars.TAG_REQ_PROD_PRICE, vars.TAG_REQ_PROD_STATUS, vars.TAG_REQ_DATE, vars.TAG_REQ_START, vars.TAG_REQ_END},
                            new int[]{R.id.req_id, R.id.prod_name, R.id.prod_price, R.id.prod_status, R.id.req_date, R.id.req_start, R.id.req_end});
                    setListAdapter(adapter);
                }
            });
        }
    }

    class RejectRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectBidding.this);
            dialogBox.setMessage("Reject the selected Bidding Request ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }

        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("reqID", reqID));
                JSONObject json = jParser.makeHttpRequest(vars.url_reject_request, "POST", params);

                Log.d("Delete User", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), AcceptRejectBidding.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    startActivityForResult(intent, 100);
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
    class AcceptRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectBidding.this);
            dialogBox.setMessage("Accept the selected Bidding Request ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("reqID", reqID));
                params.add(new BasicNameValuePair("bidStart", reqStart));
                params.add(new BasicNameValuePair("bidEnd", reqEnd));
                JSONObject json = jParser.makeHttpRequest(vars.url_accept_request, "POST", params);
                Log.d("Delete User", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), AcceptRejectBidding.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    startActivityForResult(intent, 100);
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