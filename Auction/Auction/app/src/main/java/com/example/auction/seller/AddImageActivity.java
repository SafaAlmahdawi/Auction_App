package com.example.auction.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class AddImageActivity extends AppCompatActivity {

    AppCompatButton btnSave, btnChoose1, btnChoose2, btnChoose3, btnChoose4;
    AppCompatButton btnUpload1, btnUpload2, btnUpload3, btnUpload4;
    ImageView img1, img2, img3, img4;

    String uid, name, email, phone, address, usern, pass, strPID, strIMG1, strIMG2, strIMG3, strIMG4;

    CommonVars vars;
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    Uri filePath;
    boolean choose_image1 = false, choose_image2 = false, choose_image3 = false, choose_image4 = false;
    int pic = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_image);

        btnSave = findViewById(R.id.butSave);
        btnChoose1 = findViewById(R.id.butChoose1);
        btnChoose2 = findViewById(R.id.butChoose2);
        btnChoose3 = findViewById(R.id.butChoose3);
        btnChoose4 = findViewById(R.id.butChoose4);
        btnUpload1 = findViewById(R.id.butUpload1);
        btnUpload2 = findViewById(R.id.butUpload2);
        btnUpload3 = findViewById(R.id.butUpload3);
        btnUpload4 = findViewById(R.id.butUpload4);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        strPID = intent.getStringExtra(vars.TAG_PROD_ID);
        strIMG1 = intent.getStringExtra(vars.TAG_PROD_IMG1);
        strIMG2 = intent.getStringExtra(vars.TAG_PROD_IMG2);
        strIMG3 = intent.getStringExtra(vars.TAG_PROD_IMG3);
        strIMG4 = intent.getStringExtra(vars.TAG_PROD_IMG4);

        if(!strIMG1.equals("-")){
            String url = "https://tuttut1443.000webhostapp.com/" + strIMG1;
            new DownloadImageTask(img1).execute(url);
        }
        if(!strIMG2.equals("-")) {
            String url2 = "https://tuttut1443.000webhostapp.com/" + strIMG2;
            new DownloadImageTask(img2).execute(url2);
        }
        if(!strIMG3.equals("-")) {
            String url3 = "https://tuttut1443.000webhostapp.com/" + strIMG3;
            new DownloadImageTask(img3).execute(url3);
        }
        if(!strIMG4.equals("-")) {
            String url4 = "https://tuttut1443.000webhostapp.com/" + strIMG4;
            new DownloadImageTask(img4).execute(url4);
        }

        btnChoose1.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              pic = 1;
                                              showFileChooser();
                                              btnUpload1.setEnabled(true);
                                          }
                                      }
        );
        btnUpload1.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(choose_image1){
                                                  uploadImage1();
                                                  btnChoose2.setEnabled(true);
                                              }else {
                                                  Toast.makeText(getApplicationContext(),"You must choose an image", Toast.LENGTH_LONG).show();
                                              }

                                          }
                                      }
        );
        btnChoose2.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              pic = 2;
                                              showFileChooser();
                                              btnUpload2.setEnabled(true);
                                          }
                                      }
        );
        btnUpload2.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(choose_image2){
                                                  uploadImage2();
                                                  btnChoose3.setEnabled(true);
                                              }else {
                                                  Toast.makeText(getApplicationContext(),"You must choose an image", Toast.LENGTH_LONG).show();
                                              }

                                          }
                                      }
        );
        btnChoose3.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              pic = 3;
                                              showFileChooser();
                                              btnUpload3.setEnabled(true);
                                          }
                                      }
        );
        btnUpload3.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(choose_image3){
                                                  uploadImage3();
                                                  btnChoose4.setEnabled(true);
                                              }else {
                                                  Toast.makeText(getApplicationContext(),"You must choose an image", Toast.LENGTH_LONG).show();
                                              }

                                          }
                                      }
        );
        btnChoose4.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              pic = 4;
                                              showFileChooser();
                                              btnUpload4.setEnabled(true);
                                          }
                                      }
        );
        btnUpload4.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if(choose_image4){
                                                  uploadImage4();
                                                  btnChoose4.setEnabled(true);
                                              }else {
                                                  Toast.makeText(getApplicationContext(),"You must choose an image", Toast.LENGTH_LONG).show();
                                              }

                                          }
                                      }
        );
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(getApplicationContext(), SellerActivity.class);
                                           intent.putExtra(vars.TAG_ID, uid);
                                           intent.putExtra(vars.TAG_NAME, name);
                                           intent.putExtra(vars.TAG_EMAIL, email);
                                           intent.putExtra(vars.TAG_PHONE, phone);
                                           intent.putExtra(vars.TAG_ADDRESS, address);
                                           intent.putExtra(vars.TAG_USER, usern);
                                           intent.putExtra(vars.TAG_PASS, pass);
                                           startActivityForResult(intent, 100);
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Product Image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                if(pic == 1){
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img1.setImageBitmap(bitmap);
                    choose_image1 = true;
                } else if(pic == 2){
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img2.setImageBitmap(bitmap);
                    choose_image2 = true;
                } else if(pic == 3){
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img3.setImageBitmap(bitmap);
                    choose_image3 = true;
                } else if(pic == 4){
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img4.setImageBitmap(bitmap);
                    choose_image4 = true;
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"You have to choose an image", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage1(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddImageActivity.this, "Save Image ..", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("prodID", strPID);

                String result = rh.sendPostRequest(vars.url_upload,data);
                if (result.contains("success")) {
                    return "Image Uploaded successfully";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
    private void uploadImage2(){
        class UploadImage2 extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddImageActivity.this, "Save Image ..", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("prodID", strPID);

                String result = rh.sendPostRequest(vars.url_upload2,data);
                if (result.contains("success")) {
                    return "Image Uploaded successfully";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadImage2 ui = new UploadImage2();
        ui.execute(bitmap);
    }
    private void uploadImage3(){
        class UploadImage3 extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddImageActivity.this, "Save Image ..", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("prodID", strPID);

                String result = rh.sendPostRequest(vars.url_upload3,data);
                if (result.contains("success")) {
                    return "Image Uploaded successfully";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadImage3 ui = new UploadImage3();
        ui.execute(bitmap);
    }
    private void uploadImage4(){
        class UploadImage4 extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddImageActivity.this, "Save Image ..", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                int success;
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put("prodID", strPID);

                String result = rh.sendPostRequest(vars.url_upload4,data);
                if (result.contains("success")) {
                    return "Image Uploaded successfully";
                }
                else
                {
                    return "Failed Upload";
                }
            }
        }
        UploadImage4 ui = new UploadImage4();
        ui.execute(bitmap);
    }
}