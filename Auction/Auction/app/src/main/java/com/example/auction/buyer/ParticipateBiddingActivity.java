package com.example.auction.buyer;

import androidx.appcompat.widget.AppCompatButton;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ParticipateBiddingActivity extends ListActivity {

    String name, email, phone, address, usern, pass, payment, uid;
    String prod_title, prod_id, bid_id, last_price, prod_model, prod_image, last_date;
    String strPrice, strTime;
    ImageView imgLogo, imgArrow, imgView, imgRefresh;
    TextView txtTitle, txtLastPrice, txtModel, txtTime;
    AppCompatButton btn_submit;
    EditText txtPrice;
    PhotoViewAttacher pAttacher;
    CommonVars vars;

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> bidders_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //ListView list;
    JSONArray jsonREQUEST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_participate_bidding);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);
        prod_title = intent.getStringExtra(vars.TAG_BID_TITLE);
        prod_id = intent.getStringExtra(vars.TAG_BID_PROD);
        bid_id = intent.getStringExtra(vars.TAG_BID_ID);
        last_price = intent.getStringExtra(vars.TAG_BID_PRICE);
        prod_model = intent.getStringExtra(vars.TAG_BID_MODEL);
        prod_image = intent.getStringExtra(vars.TAG_BID_IMG);
        last_date = intent.getStringExtra(vars.TAG_BID_DATE);

        imgArrow = findViewById(R.id.imgArrow);
        imgLogo = findViewById(R.id.imgLogo);
        imgView = findViewById(R.id.imgView);
        imgRefresh = findViewById(R.id.imgRefresh);
        txtTitle = findViewById(R.id.txtTitle);
        txtLastPrice = findViewById(R.id.txtLastPrice);
        txtModel = findViewById(R.id.txtModel);
        txtTime = findViewById(R.id.txtTime);
        txtPrice = findViewById(R.id.txtPrice);
        btn_submit = findViewById(R.id.btn_submit);

        txtTitle.setText(prod_title);
        txtLastPrice.setText("Last Price: " + last_price + " SR.");
        txtModel.setText("Product Model: " + prod_model);

        if(!prod_image.equals("-")){
            String url = "https://tuttut1443.000webhostapp.com/" + prod_image;
            new DownloadImageTask(imgView).execute(url);
        }
        pAttacher = new PhotoViewAttacher(imgView);
        pAttacher.update();

        ////// change biddate to days, hours, and minutes //////
        Date today = new Date();
        long today_time = today.getTime();
        long diff = Long.parseLong(last_date) - today_time;
        int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
        long newVal = diff - (1000 * 60 * 60 * 24 * numOfDays);
        int hours = (int) (newVal / (1000 * 60 * 60));
        newVal = newVal - (1000 * 60 * 60 * hours);
        int minutes = (int) (newVal / (1000 * 60));
        newVal = newVal - (1000 * 60 * minutes);
        long seconds =  (int) (newVal/1000);
        strTime = numOfDays + "days " + hours + "H. " + minutes + "Min. " + seconds + "Sec.";
        txtTime.setText(strTime);
        ////// display list of bidders /////////
        bidders_list = new ArrayList<HashMap<String, String>>();
        new LoadAllBidders().execute();
        ListView lv = getListView();

        btn_submit.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              /// check for valid and complete inputs
                                              if (txtPrice.getText().toString().equals(""))
                                              {
                                                  Toast.makeText(getApplicationContext(), "Missing Your Suggested Price", Toast.LENGTH_SHORT).show();
                                              }else if(Integer.parseInt(txtPrice.getText().toString()) < Integer.parseInt(last_price)){
                                                  Toast.makeText(getApplicationContext(), "Suggested Price should be greater than Last Price", Toast.LENGTH_SHORT).show();
                                              }
                                              else {
                                                  strPrice = txtPrice.getText().toString().trim();
                                                  new ParticipatePrice().execute();
                                              }
                                          }
                                      }
        );
        imgRefresh.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                             /// clear the list and refresh contents
                                              bidders_list.clear();
                                              new LoadAllBidders().execute();
                                          }
                                      }
        );
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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String bidID = ((TextView) view.findViewById(R.id.bid_id)).getText().toString();
                String userID = ((TextView) view.findViewById(R.id.user_id)).getText().toString();
                String userNM = ((TextView) view.findViewById(R.id.user_name)).getText().toString();
                String suggestPrc = ((TextView) view.findViewById(R.id.suggest_price)).getText().toString();
            }
        });

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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

    class ParticipatePrice extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ParticipateBiddingActivity.this);
            dialogBox.setMessage("Submit your Price .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("bidID", bid_id));
                params.add(new BasicNameValuePair("price", strPrice));
                params.add(new BasicNameValuePair("buyerID", uid));
                JSONObject json = jParser.makeHttpRequest(vars.url_participate, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ParticipateBiddingActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_PAYMENT, payment);
                    intent.putExtra(vars.TAG_BID_TITLE, prod_title);
                    intent.putExtra(vars.TAG_BID_ID, bid_id);
                    intent.putExtra(vars.TAG_BID_PROD, prod_id);
                    intent.putExtra(vars.TAG_BID_PRICE, last_price);
                    intent.putExtra(vars.TAG_BID_MODEL, prod_model);
                    intent.putExtra(vars.TAG_BID_IMG, prod_image);
                    intent.putExtra(vars.TAG_BID_DATE, last_date);
                    startActivityForResult(intent, 100);
                    return json.getString(TAG_MESSAGE);
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
            txtLastPrice.setText("Last Price: " + strPrice + " SR.");
        }
    }

    class LoadAllBidders extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ParticipateBiddingActivity.this);
            dialogBox.setMessage("Read all previous bidders  .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            JSONObject json = jParser.makeHttpRequest(vars.url_view_bidders, "GET", params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    jsonREQUEST = json.getJSONArray(vars.TAG_BIDDERS);
                    for (int i = 0; i < jsonREQUEST.length(); i++) {
                        JSONObject r = jsonREQUEST.getJSONObject(i);
                        String b_id = r.getString(vars.TAG_BIDID);
                        String u_id = r.getString(vars.TAG_BID_UID);
                        String u_name = r.getString(vars.TAG_BID_UNAME);
                        String bid_suggest = r.getString(vars.TAG_BID_SUGGEST);
                        if(b_id.equals(bid_id)) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(vars.TAG_BIDID, b_id);
                            map.put(vars.TAG_BID_UID, u_id);
                            map.put(vars.TAG_BID_UNAME, u_name);
                            map.put(vars.TAG_BID_SUGGEST, bid_suggest + " SR.");
                            bidders_list.add(map);
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
                    ListAdapter adapter = new SimpleAdapter(ParticipateBiddingActivity.this, bidders_list,
                            R.layout.bidderslist, new String[]{vars.TAG_BIDID, vars.TAG_BID_UID, vars.TAG_BID_UNAME, vars.TAG_BID_SUGGEST},
                            new int[]{R.id.bid_id, R.id.user_id, R.id.user_name, R.id.suggest_price});
                    setListAdapter(adapter);
                }
            });
        }
    }
}