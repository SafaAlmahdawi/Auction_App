package com.example.auction.seller;

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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageProductActivity extends ListActivity {

    ImageView imgHome, imgUpdate, imgRequest, imgDelete, imgArrow;
    String uid, name, email, phone, address, usern, pass;
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
        setContentView(R.layout.activity_manage_product);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);

        imgHome = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        imgUpdate = findViewById(R.id.butUpdate);
        imgRequest = findViewById(R.id.butBidding);
        imgDelete = findViewById(R.id.butDelete);

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
        imgUpdate.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent in = new Intent(getApplicationContext(), UpdateProductActivity.class);
                                           in.putExtra(vars.TAG_PROD_ID, prodID);
                                           in.putExtra(vars.TAG_PROD_TITLE, prodTitle);
                                           in.putExtra(vars.TAG_PROD_CAT, prodCat);
                                           in.putExtra(vars.TAG_PROD_MODEL, prodModel);
                                           in.putExtra(vars.TAG_PROD_PRICE, prodPrice);
                                           in.putExtra(vars.TAG_PROD_STATUS, prodStatus);
                                           in.putExtra(vars.TAG_PROD_Qty, prodQty);
                                           in.putExtra(vars.TAG_PROD_IMG1, prodIMG1);
                                           in.putExtra(vars.TAG_PROD_IMG2, prodIMG2);
                                           in.putExtra(vars.TAG_PROD_IMG3, prodIMG3);
                                           in.putExtra(vars.TAG_PROD_IMG4, prodIMG4);
                                           in.putExtra(vars.TAG_ID, uid);
                                           in.putExtra(vars.TAG_NAME, name);
                                           in.putExtra(vars.TAG_EMAIL, email);
                                           in.putExtra(vars.TAG_PHONE, phone);
                                           in.putExtra(vars.TAG_ADDRESS, address);
                                           in.putExtra(vars.TAG_USER, usern);
                                           in.putExtra(vars.TAG_PASS, pass);
                                           startActivityForResult(in, 100);
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
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!prodID.equals("")){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose Product to delete", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
        imgRequest.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(!prodID.equals("")){
                                                 AlertDialog diaBox = AskOption2();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose Product to Request Bidding", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete selected Product?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        new DeleteProduct().execute();
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

    private AlertDialog AskOption2() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Request Bidding")
                .setMessage("Are you sure you want to request bidding for the selected Product?")
                .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        Intent intent = new Intent(getApplicationContext(), BiddingPeriodActivity.class);
                        intent.putExtra(vars.TAG_ID, uid);
                        intent.putExtra(vars.TAG_NAME, name);
                        intent.putExtra(vars.TAG_EMAIL, email);
                        intent.putExtra(vars.TAG_PHONE, phone);
                        intent.putExtra(vars.TAG_ADDRESS, address);
                        intent.putExtra(vars.TAG_USER, usern);
                        intent.putExtra(vars.TAG_PASS, pass);
                        intent.putExtra(vars.TAG_PROD_ID, prodID);
                        startActivityForResult(intent, 100);
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

    class LoadAllProducts extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ManageProductActivity.this);
            dialogBox.setMessage("Read Products data .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            Log.d("request!", "starting");
            JSONObject json = jParser.makeHttpRequest(vars.url_view_products, "GET", params);
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
                        if(s_id.equals(uid)){
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
                    ListAdapter adapter = new SimpleAdapter(ManageProductActivity.this, products_list,
                            R.layout.prodslist, new String[]{vars.TAG_PROD_ID, vars.TAG_PROD_TITLE, vars.TAG_PROD_PRICE, vars.TAG_PROD_STATUS, vars.TAG_PROD_Qty, vars.TAG_PROD_MODEL, vars.TAG_PROD_CAT, vars.TAG_PROD_SELLER, vars.TAG_PROD_IMG1, vars.TAG_PROD_IMG2, vars.TAG_PROD_IMG3, vars.TAG_PROD_IMG4},
                            new int[]{R.id.prod_id, R.id.prod_title, R.id.prod_price, R.id.prod_status, R.id.prod_qty, R.id.prod_model, R.id.prod_cat, R.id.seller_id, R.id.prod_img1, R.id.prod_img2, R.id.prod_img3, R.id.prod_img4 });
                    setListAdapter(adapter);
                }
            });
        }
    }

    class DeleteProduct extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ManageProductActivity.this);
            dialogBox.setMessage("Delete the selected Product ...");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        protected String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("prodID", prodID));
                JSONObject json = jParser.makeHttpRequest(vars.url_delete_product, "POST", params);
                Log.d("Delete Product", json.toString());
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageProductActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
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