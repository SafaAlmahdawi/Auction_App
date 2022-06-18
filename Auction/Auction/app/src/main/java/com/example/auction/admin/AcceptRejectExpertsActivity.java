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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRejectExpertsActivity extends ListActivity {

    ImageView imgHome, imgArrow;
    String uid, name, email, usern, pass;
    String userID ="", userName ="", userEmail ="", userPhone ="", userExperience ="";
    Spinner txtSpin;
    ImageView butAccept, butReject;
    TextView txtAccept, txtReject;
    CommonVars vars;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> users_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_accept_reject_experts);

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
        txtAccept = findViewById(R.id.txtAccept);
        txtReject = findViewById(R.id.txtReject);

        users_list = new ArrayList<HashMap<String, String>>();
        new LoadAllUsers().execute();
        ListView lv = getListView();
        butAccept.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(!userID.equals("")){
                                               new AcceptUser().execute();
                                           }else {
                                               Toast.makeText(getApplicationContext(), "Please choose an Expert to Accept", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
        );
        txtAccept.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!userID.equals("")){
                                                 new AcceptUser().execute();
                                             }else {
                                                 Toast.makeText(getApplicationContext(), "Please choose an Expert to Accept", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
        butReject.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!userID.equals("")){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose User to Reject", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
        txtReject.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!userID.equals("")){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose User to Reject", Toast.LENGTH_SHORT).show();
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
                userID = ((TextView) view.findViewById(R.id.user_id)).getText().toString();
                userName = ((TextView) view.findViewById(R.id.user_name)).getText().toString();
                userEmail = ((TextView) view.findViewById(R.id.user_email)).getText().toString();
                userPhone = ((TextView) view.findViewById(R.id.user_phone)).getText().toString();
                userExperience = ((TextView) view.findViewById(R.id.user_address)).getText().toString();
            }
        });
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Reject User")
                .setMessage("Are you sure you want to reject selected User?")
                .setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        new RejectUser().execute();
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

    class LoadAllUsers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectExpertsActivity.this);
            dialogBox.setMessage("Read Waiting Experts data .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            //Posting user data to script
            JSONObject json = jParser.makeHttpRequest(vars.url_view_experts2, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_USERS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String u_id = r.getString(vars.TAG_USER_ID);
                        String u_name = r.getString(vars.TAG_USER_NAME);
                        String u_email = r.getString(vars.TAG_USER_EMAIL);
                        String u_phone = r.getString(vars.TAG_USER_PHONE);
                        String u_address = r.getString(vars.TAG_USER_ADDRESS);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(vars.TAG_USER_ID, u_id);
                        map.put(vars.TAG_USER_NAME, u_name);
                        map.put(vars.TAG_USER_EMAIL, u_email);
                        map.put(vars.TAG_USER_PHONE, u_phone);
                        map.put(vars.TAG_USER_ADDRESS, u_address);
                        users_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(AcceptRejectExpertsActivity.this, users_list,
                            R.layout.userslist, new String[]{vars.TAG_USER_ID, vars.TAG_USER_NAME, vars.TAG_USER_EMAIL, vars.TAG_USER_PHONE, vars.TAG_USER_ADDRESS},
                            new int[]{R.id.user_id, R.id.user_name, R.id.user_email, R.id.user_phone, R.id.user_address});
                    setListAdapter(adapter);
                }
            });
        }
    }

    class RejectUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectExpertsActivity.this);
            dialogBox.setMessage("Reject the selected Expert ...");
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
                params.add(new BasicNameValuePair("userID", userID));
                JSONObject json = jParser.makeHttpRequest(vars.url_reject_expert, "POST", params);
                Log.d("Delete User", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), AcceptRejectExpertsActivity.class);
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
    class AcceptUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(AcceptRejectExpertsActivity.this);
            dialogBox.setMessage("Accept the selected Expert ...");
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
                params.add(new BasicNameValuePair("userID", userID));
                JSONObject json = jParser.makeHttpRequest(vars.url_accept_expert, "POST", params);

                Log.d("Delete User", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), AcceptRejectExpertsActivity.class);
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