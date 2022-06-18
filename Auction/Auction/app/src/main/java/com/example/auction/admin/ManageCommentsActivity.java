package com.example.auction.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.buyer.ViewCommentsActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageCommentsActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    CommonVars vars;
    String reviewID = "", prodID, reviewBody, reviewDate, buyerName, prod_id;
    String uid, name, email, usern, pass;
    AppCompatButton butDelete;

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
        setContentView(R.layout.activity_manage_comments);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        butDelete = findViewById(R.id.butDelete);

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
        butDelete.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!reviewID.equals("")){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose Review to delete", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete Review")
                .setMessage("Are you sure you want to delete selected Review?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        new DeleteReview().execute();
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

    class LoadAllReviews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ManageCommentsActivity.this);
            dialogBox.setMessage("Read Reviews .....");
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
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_REV_ID, rev_id);
                            map.put(vars.TAG_REV_PROD, rev_prod);
                            map.put(vars.TAG_REV_BODY, rev_body);
                            map.put(vars.TAG_REV_DATE, rev_date);
                            map.put(vars.TAG_REV_BUYER, buyer_name);
                            reviews_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(ManageCommentsActivity.this, reviews_list,
                            R.layout.reviewslist, new String[]{vars.TAG_REV_ID, vars.TAG_REV_PROD, vars.TAG_REV_BODY, vars.TAG_REV_DATE, vars.TAG_REV_BUYER},
                            new int[]{R.id.review_id, R.id.prod_id, R.id.review_body, R.id.review_date, R.id.buyer_name});
                    setListAdapter(adapter);
                }
            });
        }
    }

    class DeleteReview extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ManageCommentsActivity.this);
            dialogBox.setMessage("Delete the selected Review ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("revID", reviewID));
                JSONObject json = jParser.makeHttpRequest(vars.url_delete_comment, "POST", params);
                Log.d("Delete User", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageCommentsActivity.class);
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