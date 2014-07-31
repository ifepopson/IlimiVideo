

package com.hacklab.ilimivideo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




class Gridadapter extends BaseAdapter {
    private Context mContext;

    public Gridadapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return MainActivity.names.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	
        	view = new View(mContext);
        	
        	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	view = inflater.inflate(R.layout.gridsingle, null);
            
        	TextView tv = (TextView) view.findViewById(R.id.textView1);
        	ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
        	
        	tv.setText(MainActivity.names.elementAt(position).toString());
        	iv.setImageBitmap((Bitmap) MainActivity.icons.elementAt(position));
        	
        	view.setPadding(8, 8, 8, 8);
        	view.setTag(position);
        } else {
        	view = convertView;
        }

       return view;
    }

   
     
}