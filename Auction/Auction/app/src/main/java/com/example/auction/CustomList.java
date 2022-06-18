package com.example.auction;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private String[] urls, names, fName, prices, ids, prodDesc, fIDs, prodStatus, prodModel, prodQty, prodImg2, prodImg3, prodImg4;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] urls, String[] names, String[] prices, String[] ids, String[] prodState, String[] prodModel, Bitmap[] bitmaps, String[] qtys, String[] imgs2, String[] imgs3, String[] imgs4)   {
        super(context, R.layout.image_list_view, urls);
        this.context = context;
        this.urls = urls;
        this.names = names;
        this.prices = prices;
        this.ids = ids;
        this.prodStatus = prodState;
        this.bitmaps= bitmaps;
        this.prodModel = prodModel;
        this.prodQty = qtys;
        this.prodImg2 = imgs2;
        this.prodImg3 = imgs3;
        this.prodImg4 = imgs4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        ImageView prodImg = (ImageView) listViewItem.findViewById(R.id.prodImage);
        TextView prodName = (TextView) listViewItem.findViewById(R.id.prodName);
        TextView prodPrice = (TextView) listViewItem.findViewById(R.id.prodPrice);
        TextView prodID = (TextView) listViewItem.findViewById(R.id.prodID);
        TextView prodURL = (TextView) listViewItem.findViewById(R.id.prodURL);
        TextView prodState = (TextView) listViewItem.findViewById(R.id.prodStatus);
        TextView prodModl = (TextView) listViewItem.findViewById(R.id.prodModel);
        TextView prodQ = (TextView) listViewItem.findViewById(R.id.prodQty);
        TextView prodImage2 = (TextView) listViewItem.findViewById(R.id.image2);
        TextView prodImage3 = (TextView) listViewItem.findViewById(R.id.image3);
        TextView prodImage4 = (TextView) listViewItem.findViewById(R.id.image4);


        prodName.setText(names[position]);
        prodURL.setText(urls[position]);
        prodPrice.setText(prices[position] + "SR.");
        prodID.setText(ids[position]);
        prodState.setText(prodStatus[position]);
        prodModl.setText(prodModel[position]);
        prodQ.setText(prodQty[position]);
        prodImage2.setText(prodImg2[position]);
        prodImage3.setText(prodImg3[position]);
        prodImage4.setText(prodImg4[position]);
        prodImg.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],50,50,false));
        return  listViewItem;
    }
}
