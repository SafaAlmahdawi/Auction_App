package com.example.auction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAlImages {

    public static String[] urls;
    public static String[] names;
    public static String[] Price;
    public static String[] prodIDs;
    public static String[] prodState;
    public static String[] prodMod;
    public static String[] prodQty;
    public static String[] prodImg2;
    public static String[] prodImg3;
    public static String[] prodImg4;
    public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    public static final String PROD_NAME = "prodName";
    public static final String PROD_PRICE = "prodPrice";
    public static final String PROD_ID = "prodID";
    public static final String PROD_STATE = "prodStatus";
    public static final String PROD_MODEL = "prodModel";
    public static final String PROD_QTY = "prodQty";
    public static final String PROD_IMG2 = "prodImg2";
    public static final String PROD_IMG3 = "prodImg3";
    public static final String PROD_IMG4 = "prodImg4";
    private String json;
    private JSONArray jsonArray;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jsonObject){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jsonObject.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[jsonArray.length()];
        urls = new String[jsonArray.length()];
        names = new String[jsonArray.length()];
        Price = new String[jsonArray.length()];
        prodIDs = new String[jsonArray.length()];
        prodState = new String[jsonArray.length()];
        prodMod = new String[jsonArray.length()];
        prodQty = new String[jsonArray.length()];
        prodImg2 = new String[jsonArray.length()];
        prodImg3 = new String[jsonArray.length()];
        prodImg4 = new String[jsonArray.length()];

        for(int i = 0; i< jsonArray.length(); i++){
            urls[i] = jsonArray.getJSONObject(i).getString(IMAGE_URL);
            names[i] = jsonArray.getJSONObject(i).getString(PROD_NAME);
            Price[i] = jsonArray.getJSONObject(i).getString(PROD_PRICE);
            prodIDs[i] = jsonArray.getJSONObject(i).getString(PROD_ID);
            prodState[i] = jsonArray.getJSONObject(i).getString(PROD_STATE);
            prodMod[i] = jsonArray.getJSONObject(i).getString(PROD_MODEL);
            prodQty[i] = jsonArray.getJSONObject(i).getString(PROD_QTY);
            prodImg2[i] = jsonArray.getJSONObject(i).getString(PROD_IMG2);
            prodImg3[i] = jsonArray.getJSONObject(i).getString(PROD_IMG3);
            prodImg4[i] = jsonArray.getJSONObject(i).getString(PROD_IMG4);
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
        }
    }
}
