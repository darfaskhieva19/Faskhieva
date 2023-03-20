package com.example.meditation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterPhoto extends BaseAdapter
{
    private Context mContext;
    List<MaskPhoto> maskList;

    public AdapterPhoto(Context mContext, List<MaskPhoto> maskList)
    {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MaskPhoto maskImage = maskList.get(position);
        View v = null;
        if(maskImage.getImageProfile()==null){
            v = View.inflate(mContext, R.layout.item_photo,null);
        }
        else{
            v = View.inflate(mContext,R.layout.item_photo,null);
            ImageView Image = v.findViewById(R.id.image);
            TextView dateCreate = v.findViewById(R.id.Create);
            if(maskImage.getImageProfile().exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(maskImage.getImageProfile().getAbsolutePath());
                Image.setImageBitmap(myBitmap);
            }
            dateCreate.setText(maskImage.getData());
        }
        return v;
    }
}