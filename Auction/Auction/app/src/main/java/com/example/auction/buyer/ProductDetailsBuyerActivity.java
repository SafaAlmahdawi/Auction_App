package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.GetAlImages;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.expert.ExpertActivity;
import com.example.auction.expert.ViewProductExpertActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ProductDetailsBuyerActivity extends AppCompatActivity {

    ImageView imgLogo, imgArrow, imgView, img1, img2, img3, img4;
    TextView txtTitle, txtPrice, txtStatus, txtModel, txtQty, txtStart, txtEnd;
    AppCompatButton btn_submit;
    CommonVars vars;
    int index;

    String name, email, phone, address, usern, pass, payment, uid;
    String prodID, prodTitle, prodPrice, prodModel, prodStatus, prodQty, prodImg1, prodImg2, prodImg3, prodImg4;
    PhotoViewAttacher pAttacher;
    String strStart, strEnd;

    /// deal with database ///////////
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    public static final String BITMAP_ID = "BITMAP_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_product_details_buyer);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        payment = intent.getStringExtra(vars.TAG_PAYMENT);
        index = intent.getIntExtra(BITMAP_ID, 0);

        prodID = GetAlImages.prodIDs[index];
        prodTitle = GetAlImages.names[index];
        prodPrice = GetAlImages.Price[index];
        prodModel = GetAlImages.prodMod[index];
        prodStatus = GetAlImages.prodState[index];
        prodQty = GetAlImages.prodQty[index];
        prodImg1 = GetAlImages.urls[index];
        prodImg2 = GetAlImages.prodImg2[index];
        prodImg3 = GetAlImages.prodImg3[index];
        prodImg4 = GetAlImages.prodImg4[index];

        imgArrow = findViewById(R.id.imgArrow);
        imgLogo = findViewById(R.id.imgLogo);
        imgView = findViewById(R.id.imgView);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtModel = findViewById(R.id.txtModel);
        txtQty = findViewById(R.id.txtQty);
        txtStatus = findViewById(R.id.txtStatus);
        txtStart = findViewById(R.id.txtStart);
        txtEnd = findViewById(R.id.txtEnd);

        txtTitle.setText(prodTitle);
        txtPrice.setText(prodPrice);
        txtModel.setText(prodModel);
        txtStatus.setText(prodStatus);
        txtQty.setText(prodQty);

        if(!prodImg1.equals("-")){
            String url = prodImg1;
            new DownloadImageTask(img1).execute(url);
            imgView.setImageBitmap(GetAlImages.bitmaps[index]);
        }
        if(!prodImg2.equals("-")) {
            String url2 = "https://tuttut1443.000webhostapp.com/" + prodImg2;
            new DownloadImageTask(img2).execute(url2);
        }
        if(!prodImg3.equals("-")) {
            String url3 = "https://tuttut1443.000webhostapp.com/" + prodImg3;
            new DownloadImageTask(img3).execute(url3);
        }
        if(!prodImg4.equals("-")) {
            String url4 = "https://tuttut1443.000webhostapp.com/" + prodImg4;
            new DownloadImageTask(img4).execute(url4);
        }
        new ViewBidding().execute();

        pAttacher = new PhotoViewAttacher(imgView);
        pAttacher.update();

        imgArrow.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
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

        img1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = prodImg1;
                                        new DownloadImageTask(imgView).execute(url);
                                    }
                                }
        );
        img2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://tuttut1443.000webhostapp.com/" + prodImg2;
                                        new DownloadImageTask(imgView).execute(url);
                                    }
                                }
        );
        img3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://tuttut1443.000webhostapp.com/" + prodImg3;
                                        new DownloadImageTask(imgView).execute(url);
                                    }
                                }
        );
        img4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://tuttut1443.000webhostapp.com/" + prodImg4;
                                        new DownloadImageTask(imgView).execute(url);
                                    }
                                }
        );

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

    class ViewBidding extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductDetailsBuyerActivity.this);
            dialogBox.setMessage("Read Bidding Dates .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("prodID", prodID));

                JSONObject json = jsonParser.makeHttpRequest(vars.view_bidding_dates, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                String[] data = json.getString(TAG_MESSAGE).split("###");
                if (success == 1) {
                    strStart = data[0];
                    strEnd = data[1];
                }
                else {
                    strStart = "No Bid";
                    strEnd = "No Bid";
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
            txtStart.setText(strStart);
            txtEnd.setText(strEnd);
        }
    }
}