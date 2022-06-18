package com.example.auction.expert;

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
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.seller.AddImageActivity;
import com.example.auction.seller.AddProductActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewProductExpertActivity extends AppCompatActivity {
    ImageView imgLogo, imgArrow, imgView, img1, img2, img3, img4;
    TextView txtTitle, txtPrice, txtStatus, txtModel;
    AppCompatButton btn_submit;
    EditText txtMinPrice, txtMaxPrice;
    CommonVars vars;

    String name, email, phone, pass, uid, exper1="", exper2="", exper3="";
    String prodID, prodTitle, prodPrice, prodModel, prodStatus, prodImg1, prodImg2, prodImg3, prodImg4;
    PhotoViewAttacher pAttacher;
    String strMax, strMin;

    /// deal with database ///////////
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_product_expert);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        pass = intent.getStringExtra(vars.TAG_PASS);
        exper1 = intent.getStringExtra(vars.TAG_EXPER1);
        exper2 = intent.getStringExtra(vars.TAG_EXPER2);
        exper3 = intent.getStringExtra(vars.TAG_EXPER3);
        prodID = intent.getStringExtra(vars.TAG_PROD_ID);
        prodTitle = intent.getStringExtra(vars.TAG_PROD_TITLE);
        prodPrice = intent.getStringExtra(vars.TAG_PROD_PRICE);
        prodModel = intent.getStringExtra(vars.TAG_PROD_MODEL);
        prodStatus = intent.getStringExtra(vars.TAG_PROD_STATUS);
        prodImg1 = intent.getStringExtra(vars.TAG_PROD_IMG1);
        prodImg2 = intent.getStringExtra(vars.TAG_PROD_IMG2);
        prodImg3 = intent.getStringExtra(vars.TAG_PROD_IMG3);
        prodImg4 = intent.getStringExtra(vars.TAG_PROD_IMG4);

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
        txtStatus = findViewById(R.id.txtStatus);
        txtMinPrice = findViewById(R.id.txtMinPrice);
        txtMaxPrice = findViewById(R.id.txtMaxPrice);
        btn_submit = findViewById(R.id.btn_submit);

        txtTitle.setText(prodTitle);
        txtPrice.setText(prodPrice);
        txtModel.setText(prodModel);
        txtStatus.setText(prodStatus);

        if(!prodImg1.equals("-")){
            String url = "https://tuttut1443.000webhostapp.com/" + prodImg1;
            new DownloadImageTask(img1).execute(url);
            new DownloadImageTask(imgView).execute(url);
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
        btn_submit.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           /// check for valid and complete inputs
                                           if (txtMaxPrice.getText().toString().equals("") || txtMinPrice.getText().toString().equals(""))
                                           {
                                               Toast.makeText(getApplicationContext(), "Missing Min or Max Product Price", Toast.LENGTH_SHORT).show();
                                           }
                                           else {
                                               strMax = txtMaxPrice.getText().toString().trim();
                                               strMin = txtMinPrice.getText().toString().trim();
                                               new Estimate().execute();
                                           }
                                       }
                                   }
        );
        img1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String url = "https://tuttut1443.000webhostapp.com/" + prodImg1;
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

    class Estimate extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ViewProductExpertActivity.this);
            dialogBox.setMessage("Save Estimate Prices .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("prodID", prodID));
                params.add(new BasicNameValuePair("max_price", strMax));
                params.add(new BasicNameValuePair("min_price", strMin));
                JSONObject json = jsonParser.makeHttpRequest(vars.url_edit_prices, "POST", params);
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ExpertActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_PASS, pass);
                    intent.putExtra(vars.TAG_EXPER1, exper1);
                    intent.putExtra(vars.TAG_EXPER2, exper2);
                    intent.putExtra(vars.TAG_EXPER3, exper3);
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
        }
    }
}