package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoActivity extends AppCompatActivity {

    ImageView img;

    //SubsamplingScaleImageView img;
    //View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        img = findViewById(R.id.image);
        if (ProfileActivity.maskImage.getImageProfile().exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(ProfileActivity.maskImage.getImageProfile().getAbsolutePath());
            img.setImageBitmap(myBitmap);
        }
    }

    public void onClose(View view) {
            startActivity(new Intent(this, ProfileActivity.class));
        }

    public void onDelete(View view) {
        try
        {
            ProfileActivity.maskImage.imageProfile.delete();
        }
        catch(Exception exception)
        {
            Toast.makeText(this, "При удаление возникла ошибка!", Toast.LENGTH_LONG).show();
        }
        startActivity(new Intent(this, ProfileActivity.class));
    }
}