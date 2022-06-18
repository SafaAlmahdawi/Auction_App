package com.example.auction.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auction.CommonVars;
import com.example.auction.DataHandler;
import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.admin.AdminActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BiddingPeriodActivity extends AppCompatActivity {

    AppCompatButton butSave;
    ImageView imgLogo, imgArrow;
    TextView txtStartDate, txtEndDate;
    String name, email, usern, pass, uid, phone, address, prodID;

    private Calendar calendar;
    String strStartDate, strEndDate;
    int selected_day = 0, selected_month=0, selected_year = 0;

    /////// codes for dealing with database
    CommonVars vars;
    ProgressDialog dialogBox;
    DataHandler jsonParser = new DataHandler();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bidding_period);

        butSave = findViewById(R.id.butSave);
        imgLogo = findViewById(R.id.imgLogo);
        imgArrow = findViewById(R.id.imgArrow);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);


        Intent intent = getIntent();
        uid = intent.getStringExtra(vars.TAG_ID);
        name = intent.getStringExtra(vars.TAG_NAME);
        email = intent.getStringExtra(vars.TAG_EMAIL);
        phone = intent.getStringExtra(vars.TAG_PHONE);
        address = intent.getStringExtra(vars.TAG_ADDRESS);
        usern = intent.getStringExtra(vars.TAG_USER);
        pass = intent.getStringExtra(vars.TAG_PASS);
        prodID = intent.getStringExtra(vars.TAG_PROD_ID);

        imgArrow.setOnClickListener(new View.OnClickListener() {
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
        imgLogo.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                           startActivityForResult(intent, 100);
                                       }
                                   }
        );
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(BiddingPeriodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        selected_day = i2;
                        selected_month = i1 + 1;
                        selected_year = i;
                        txtStartDate.setText(selected_day + "-" + selected_month + "-" + selected_year);
                    }
                }, year, month, day);
                datePicker.setTitle("Bidding Start Date");
                datePicker.show();
            }
        });
        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(BiddingPeriodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        selected_day = i2;
                        selected_month = i1 + 1;
                        selected_year = i;
                        txtEndDate.setText(selected_day + "-" + selected_month + "-" + selected_year);
                    }
                }, year, month, day);
                datePicker.setTitle("Bidding End Date");
                datePicker.show();
            }
        });

        butSave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (txtStartDate.getText().toString().equals("Choose Bidding Start Date")) {
                                                Toast.makeText(getApplicationContext(), "Please Choose Bidding Start Date", Toast.LENGTH_SHORT).show();
                                            } if (txtEndDate.getText().toString().equals("Choose Bidding End Date")) {
                                                Toast.makeText(getApplicationContext(), "Please Choose Bidding End Date", Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                                strStartDate = txtStartDate.getText().toString();
                                                strEndDate = txtEndDate.getText().toString();
                                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                                try {
                                                    Date startDate = formatter.parse(strStartDate);
                                                    Date endDate = formatter.parse(strEndDate);
                                                    Date date = new Date();
                                                    if(startDate.after(endDate))
                                                    {
                                                        Toast.makeText(getApplicationContext(), "End Date should be after Start Date", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else if(date.after(startDate))
                                                    {
                                                        Toast.makeText(getApplicationContext(), "Start Date should be in future", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        new AddBidding().execute();
                                                    }

                                                } catch (ParseException e) {
                                                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
        );
    }

    class AddBidding extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            dialogBox = new ProgressDialog(BiddingPeriodActivity.this);
            dialogBox.setMessage("Save Bidding Period ...");
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
                parameters.add(new BasicNameValuePair("sellerID", uid));
                parameters.add(new BasicNameValuePair("startDate", strStartDate));
                parameters.add(new BasicNameValuePair("endDate", strEndDate));
                JSONObject parser = jsonParser.makeHttpRequest(vars.url_bidding_period, "POST", parameters);
                success = parser.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), SellerActivity.class);
                    intent.putExtra(vars.TAG_ID, uid);
                    intent.putExtra(vars.TAG_NAME, name);
                    intent.putExtra(vars.TAG_EMAIL, email);
                    intent.putExtra(vars.TAG_PHONE, phone);
                    intent.putExtra(vars.TAG_ADDRESS, address);
                    intent.putExtra(vars.TAG_USER, usern);
                    intent.putExtra(vars.TAG_PASS, pass);
                    startActivityForResult(intent, 100);
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
}