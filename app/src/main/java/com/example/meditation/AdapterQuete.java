package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AdapterQuete extends BaseAdapter {

    protected Context mContext;
    List<MaskQuete> queteList;

    public AdapterQuete(Context mContext, List<MaskQuete> queteList) {
        this.mContext = mContext;
        this.queteList = queteList;
    }

    @Override
    public int getCount() {
        return queteList.size();
    }

    @Override
    public Object getItem(int i) {
        return queteList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return queteList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.item_quete,null);

        TextView title = v.findViewById(R.id.tvTitle);
        ImageView Image = v.findViewById(R.id.image);
        TextView description = v.findViewById(R.id.tvDescription);

        MaskQuete maskQuote = queteList.get(position);
        title.setText(maskQuote.getTitle());
        new DownloadImageTask((ImageView) Image).execute(maskQuote.getImage());
        description.setText(maskQuote.getDescription());
        return v;
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
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

}