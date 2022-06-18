package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.CustomList;
import com.example.auction.GetAlImages;
import com.example.auction.MainActivity;
import com.example.auction.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchProductActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    AppCompatButton butView;
    EditText txtSearch;
    ImageView imgLogo, imgArrow;
    String name, email, phone, address, usern, pass, payment, uid;
    CommonVars vars;

    public static String GET_IMAGE_URL="https://tuttut1443.000webhostapp.com//searchAllProducts.php";

    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_product);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);

        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtSearch = findViewById(R.id.txtSearch);
        butView = findViewById(R.id.butView);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        butView.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           if (!txtSearch.getText().toString().trim().equals("")) {
                                               String condition = txtSearch.getText().toString().trim();
                                               GET_IMAGE_URL = "https://tuttut1443.000webhostapp.com//searchAllProducts.php?s=" + condition;
                                               getURLs();
                                           } else {
                                               Toast.makeText(getApplicationContext(), "Please type search condition", Toast.LENGTH_LONG).show();
                                           }

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
    }
    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SearchProductActivity.this,"Downloading Products...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(SearchProductActivity.this,GetAlImages.urls,GetAlImages.names, GetAlImages.Price, GetAlImages.prodIDs, GetAlImages.prodState, GetAlImages.prodMod ,GetAlImages.bitmaps, GetAlImages.prodQty, GetAlImages.prodImg2, GetAlImages.prodImg3, GetAlImages.prodImg4);
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
                loading = ProgressDialog.show(SearchProductActivity.this,"Loading...","Please Wait...",true,true);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailsBuyerActivity.class);
        intent.putExtra(vars.TAG_ID, uid);
        intent.putExtra(vars.TAG_NAME, name);
        intent.putExtra(vars.TAG_EMAIL, email);
        intent.putExtra(vars.TAG_PHONE, phone);
        intent.putExtra(vars.TAG_ADDRESS, address);
        intent.putExtra(vars.TAG_USER, usern);
        intent.putExtra(vars.TAG_PASS, pass);
        intent.putExtra(vars.TAG_PAYMENT, payment);
        intent.putExtra(BITMAP_ID, position);
        startActivityForResult(intent, 100);
    }
}