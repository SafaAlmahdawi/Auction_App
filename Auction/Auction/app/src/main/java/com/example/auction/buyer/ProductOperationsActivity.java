package com.example.auction.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.CustomList;
import com.example.auction.DataHandler;
import com.example.auction.GetAlImages;
import com.example.auction.MainActivity;
import com.example.auction.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductOperationsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    AppCompatButton butView;
    EditText txtSearch;
    ImageView imgLogo, imgArrow;
    ImageView butFavorite, butShare, butBuy, butBid, butRate, butComment;
    String name, email, phone, address, usern, pass, payment, uid;
    AppCompatButton btn_submit, btn_review, btn_view_comments;
    TextView txtStartDate, txtEndDate, txtStartPrice;
    AppCompatButton btn_yes, btn_no;
    AppCompatButton btn_participate;
    RatingBar ratebar;
    EditText txtReview, txtQty;
    TextView txtRate;
    CommonVars vars;
    int selected_index = 0;
    String start_date, end_date, start_price;

    public static String GET_IMAGE_URL="https://tuttut1443.000webhostapp.com//AllProducts.php";
    public GetAlImages getAlImages;
    public static final String BITMAP_ID = "BITMAP_ID";
    Dialog card_rate, card_review, card_buy, card_bidding;
    String rateValue, prodID = "", txt_review, rate_avg, prodPrice, strQty, prodQty;

    ///// Deal with database parameters ///////////
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_product_operations);

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
        butFavorite = findViewById(R.id.butFavorite);
        butShare = findViewById(R.id.butShare);
        butBuy = findViewById(R.id.butBuy);
        butBid = findViewById(R.id.butBidding);
        butRate = findViewById(R.id.butRate);
        butComment = findViewById(R.id.butComment);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        //// rate dialog /////////////
        card_rate = new Dialog(this);
        card_rate.setContentView(R.layout.card_rate);
        btn_submit = card_rate.findViewById(R.id.btn_save);
        ratebar = card_rate.findViewById(R.id.ratingBar);
        txtRate = card_rate.findViewById(R.id.txtRate);
        //// review dialog /////////////
        card_review = new Dialog(this);
        card_review.setContentView(R.layout.card_review);
        btn_review = card_review.findViewById(R.id.btn_submit);
        btn_view_comments = card_review.findViewById(R.id.btn_view_comments);
        txtReview = card_review.findViewById(R.id.txtReview);
        //// buy dialog /////////////
        card_buy = new Dialog(this);
        card_buy.setContentView(R.layout.card_buy);
        btn_yes = card_buy.findViewById(R.id.btn_yes);
        btn_no = card_buy.findViewById(R.id.btn_no);
        txtQty = card_buy.findViewById(R.id.txtQty);
        //// bidding dialog /////////////
        card_bidding = new Dialog(this);
        card_bidding.setContentView(R.layout.card_bidding);
        btn_participate = card_bidding.findViewById(R.id.btn_participate);
        txtStartDate = card_bidding.findViewById(R.id.txtStartDate);
        txtEndDate = card_bidding.findViewById(R.id.txtEndDate);
        txtStartPrice = card_bidding.findViewById(R.id.txtStartPrice);

///// read all products /////////////
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
        butShare.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if(selected_index != 0) {
                           Intent sendIntent = new Intent();
                           sendIntent.setAction(Intent.ACTION_SEND);
                           String product = GetAlImages.names[selected_index] + "\n Price: " + GetAlImages.Price[selected_index] + " SR." + "\n View Product: " + GetAlImages.urls[selected_index];
                           sendIntent.putExtra(Intent.EXTRA_TEXT, product);
                           sendIntent.setType("text/plain");

                           Intent shareIntent = Intent.createChooser(sendIntent, null);
                           startActivity(shareIntent);
                       }else{
                           Toast.makeText(getApplicationContext(), "Please choose a product to share", Toast.LENGTH_LONG).show();
                       }
                   }
               }
        );
        butFavorite.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(selected_index != 0) {
                                               prodID = GetAlImages.prodIDs[selected_index];
                                               new AddFavorite().execute();
                                           }else{
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
                                           }
                                       }
                                   }
        );
        butRate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(selected_index != 0) {
                                               card_rate.show();
                                               prodID = GetAlImages.prodIDs[selected_index];
                                               new ViewRate().execute();
                                            }else{
                                                Toast.makeText(getApplicationContext(), "Please choose a product to rate", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
        );
        butBuy.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(selected_index != 0) {
                                               card_buy.show();
                                               prodID = GetAlImages.prodIDs[selected_index];
                                               prodPrice = GetAlImages.Price[selected_index];
                                               prodQty = GetAlImages.prodQty[selected_index];
                                           }else{
                                               Toast.makeText(getApplicationContext(), "Please choose a product to buy", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   }
        );
        butBid.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if(selected_index != 0) {
                                              card_bidding.show();
                                              prodID = GetAlImages.prodIDs[selected_index];
                                              new ViewBidding().execute();
                                          }else{
                                              Toast.makeText(getApplicationContext(), "Please choose a product to buy", Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  }
        );
        btn_submit.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           rateValue = String.valueOf(ratebar.getRating());
                                           prodID = GetAlImages.prodIDs[selected_index];
                                           card_rate.dismiss();
                                           new AddRate().execute();
                                       }
                                   }
        );
        butComment.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if(selected_index != 0) {
                                               card_review.show();
                                           }else{
                                               Toast.makeText(getApplicationContext(), "Please choose a product to review", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   }
        );
        btn_review.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              txt_review = txtReview.getText().toString().trim();
                                              prodID = GetAlImages.prodIDs[selected_index];
                                              card_review.dismiss();
                                              new AddReview().execute();
                                          }
                                      }
        );
        btn_view_comments.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              prodID = GetAlImages.prodIDs[selected_index];
                                              card_review.dismiss();
                                              Intent intent = new Intent(getApplicationContext(), ViewCommentsActivity.class);
                                              intent.putExtra(vars.TAG_PROD_ID, prodID);
                                              startActivityForResult(intent, 100);
                                          }
                                      }
        );
        btn_yes.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              int prod_qty = Integer.parseInt(prodQty);
                                              int user_qty = Integer.parseInt(txtQty.getText().toString());
                                              if(user_qty > prod_qty){
                                                  Toast.makeText(getApplicationContext(), "Quantity should not exceed the product quantity", Toast.LENGTH_LONG).show();
                                              }else{
                                                  card_buy.dismiss();
                                                  strQty = txtQty.getText().toString();
                                                  new AddOrder().execute();
                                              }
                                          }
                                      }
        );
        btn_no.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           card_buy.dismiss();
                                       }
                                   }
        );
        btn_participate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getApplicationContext(), LiveBiddingActivity.class);
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

    }
    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProductOperationsActivity.this,"Downloading Products...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(ProductOperationsActivity.this,GetAlImages.urls,GetAlImages.names, GetAlImages.Price, GetAlImages.prodIDs, GetAlImages.prodState, GetAlImages.prodMod ,GetAlImages.bitmaps, GetAlImages.prodQty, GetAlImages.prodImg2, GetAlImages.prodImg3, GetAlImages.prodImg4);
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
                loading = ProgressDialog.show(ProductOperationsActivity.this,"Loading...","Please Wait...",true,true);
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
    class AddRate extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Submit Product Rate .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                parameters.add(new BasicNameValuePair("rateValue", rateValue));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_add_rate, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return parser.getString(TAG_MESSAGE);
                } else {
                    return "Something goes wrong, try again";
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
    class ViewRate extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Read Product Rate .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_view_rate, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    rate_avg = parser.getString(TAG_MESSAGE);
                    return "Rate Product";
                } else {
                    return "Something goes wrong, try again";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            dialogBox.dismiss();
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
            txtRate.setText("Product Rate ( " + rate_avg + " )");
        }
    }
    class AddReview extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Submit Product Review .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                parameters.add(new BasicNameValuePair("buyerID", uid));
                parameters.add(new BasicNameValuePair("review", txt_review));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_add_review, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return parser.getString(TAG_MESSAGE);
                } else {
                    return "Something goes wrong, try again";
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

    class AddOrder extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Submit Product Buy Order .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                // Building Parameters
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                parameters.add(new BasicNameValuePair("prodPrice", prodPrice));
                parameters.add(new BasicNameValuePair("prodQty", strQty));
                parameters.add(new BasicNameValuePair("buyerID", uid));
                parameters.add(new BasicNameValuePair("payment", payment));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_add_order, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    return parser.getString(TAG_MESSAGE);
                } else {
                    return parser.getString(TAG_MESSAGE);
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
    class ViewBidding extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Read Product Bidding info .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_view_bid_info, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String[] data = parser.getString(TAG_MESSAGE).split("###");
                    start_date = data[0];
                    end_date = data[1];
                    start_price = data[2];
                    return "Success";
                } else {
                    return "Something goes wrong, try again";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            dialogBox.dismiss();
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
            txtStartDate.setText(start_date );
            txtEndDate.setText(end_date );
            txtStartPrice.setText(start_price + " SR." );
        }
    }

    class AddFavorite extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(ProductOperationsActivity.this);
            dialogBox.setMessage("Add Product to Favorite .....");
            dialogBox.setIndeterminate(false);
            dialogBox.setCancelable(true);
            dialogBox.show();
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("prodID", prodID));
                parameters.add(new BasicNameValuePair("buyerID", uid));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_add_favorite, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
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
                    return parser.getString(TAG_MESSAGE);
                } else {
                    return parser.getString(TAG_MESSAGE);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selected_index = position;
    }
}