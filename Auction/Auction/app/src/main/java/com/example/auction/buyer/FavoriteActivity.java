package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.CustomList;
import com.example.auction.DataHandler;
import com.example.auction.GetAlImages;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.admin.ManageUsersActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    ImageView imgLogo, imgArrow;
    String name, email, phone, address, usern, pass, payment, uid;
    CommonVars vars;
    int selected_index = -1;
    String prodID;

    public static String GET_IMAGE_URL="https://tuttut1443.000webhostapp.com//getFavoriteProducts.php";

    public GetAlImages getAlImages;
    AppCompatButton btn_view, btn_delete;

    public static final String BITMAP_ID = "BITMAP_ID";

    ProgressDialog dialogBox;
    DataHandler jParser = new DataHandler();
    ArrayList<HashMap<String, String>> users_list;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);

        btn_view = findViewById(R.id.btn_view);
        btn_delete = findViewById(R.id.btn_delete);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        GET_IMAGE_URL="https://tuttut1443.000webhostapp.com//getFavoriteProducts.php?u=" + uid;
        getURLs();

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
        btn_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(selected_index != -1){
                                                prodID = GetAlImages.prodIDs[selected_index];
                                                Intent intent = new Intent(getApplicationContext(), ProductDetailsBuyerActivity.class);
                                                intent.putExtra(vars.TAG_ID, uid);
                                                intent.putExtra(vars.TAG_NAME, name);
                                                intent.putExtra(vars.TAG_EMAIL, email);
                                                intent.putExtra(vars.TAG_PHONE, phone);
                                                intent.putExtra(vars.TAG_ADDRESS, address);
                                                intent.putExtra(vars.TAG_USER, usern);
                                                intent.putExtra(vars.TAG_PASS, pass);
                                                intent.putExtra(vars.TAG_PAYMENT, payment);
                                                intent.putExtra(BITMAP_ID,selected_index);
                                                startActivityForResult(intent, 100);
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Please choose a product to view", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
        );
        btn_delete.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(selected_index != -1){
                                                 AlertDialog diaBox = AskOption();
                                                 diaBox.show();
                                             }else{
                                                 Toast.makeText(getApplicationContext(), "Please choose a product to delete", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
    }
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Remove Favorite")
                .setMessage("Are you sure you want to remove the selected product from your Favorite?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //// code to cancel notification //////////
                        prodID = GetAlImages.prodIDs[selected_index];
                        new DeleteFavorite().execute();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FavoriteActivity.this,"Downloading Products...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(FavoriteActivity.this,GetAlImages.urls,GetAlImages.names, GetAlImages.Price, GetAlImages.prodIDs, GetAlImages.prodState, GetAlImages.prodMod ,GetAlImages.bitmaps, GetAlImages.prodQty, GetAlImages.prodImg2, GetAlImages.prodImg3, GetAlImages.prodImg4);
                listView.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(FavoriteActivity.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }
    class DeleteFavorite extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(FavoriteActivity.this);
            dialogBox.setMessage("Delete the selected Product ...");
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
                params.add(new BasicNameValuePair("prodID", prodID));
                params.add(new BasicNameValuePair("buyerID", uid));
                JSONObject json = jParser.makeHttpRequest(vars.url_delete_favorite, "POST", params);

                Log.d("Delete Favorite", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    dialogBox.dismiss();
                    Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_PAYMENT, payment);
                    startActivityForResult(intent, 100);
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selected_index = position;
    }

}