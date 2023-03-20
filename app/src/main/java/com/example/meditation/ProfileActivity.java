package com.example.meditation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ProfileActivity extends AppCompatActivity {

    ImageView img; //иконка
    TextView txtName; //имя пользователя
    OutputStream Stream;
    public static MaskPhoto maskImage;
    private AdapterPhoto pAdapter;
    private List<MaskPhoto> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        img = findViewById(R.id.Avatar);
        txtName = findViewById(R.id.NameProfile);
        txtName.setText(MainActivity.Name);
        new AdapterQuete.DownloadImageTask((ImageView) img).execute(MainActivity.image);
        GridView gvImage = findViewById(R.id.lvImg);
        pAdapter = new AdapterPhoto(ProfileActivity.this, list);
        gvImage.setAdapter(pAdapter);
        //ImgProfile();

        gvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                maskImage = list.get(i);
                if(maskImage.getImageProfile()==null){
                    addImage();
                }
                else{
                    maskImage = list.get(i);
                    startActivity(new Intent(ProfileActivity.this, PhotoActivity.class));
                }
            }
        });
    }

    private void ImgProfile() {
        list.clear();
        pAdapter.notifyDataSetInvalidated();
        String path = getApplicationInfo().dataDir + "/MyFiles";
        File directory = new File(path);
        File[] files = directory.listFiles();
        int j = 0;
        /*for (int i = 0; i < files.length; i++) {
            Long last = files[i].lastModified();
            MaskPhoto tempProduct = new MaskPhoto(
                    j,
                    files[i].getAbsolutePath(),
                    files[i],
                    getFullTime(last)
            );
            list.add(tempProduct);
            pAdapter.notifyDataSetInvalidated();
        }*/
    }

    public void NextMenu(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    public void NextLogin(View view) { //переход на страницу авторизации
        SharedPreferences prefs = getSharedPreferences("Date", Context.MODE_PRIVATE); // Сохранение данных
        prefs.edit().putString("Avatar", "").apply();
        prefs.edit().putString("NickName", "").apply();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void addImage()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap = null;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try
                        {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        File dir = new File(getApplicationInfo().dataDir + "/MyFiles/");
                        dir.mkdirs();
                        File file = new File(dir, System.currentTimeMillis() + ".jpg");
                        try {
                            Stream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, Stream);
                            Stream.flush();
                            Stream.close();
                            Toast.makeText(ProfileActivity.this, "Успешное сохранение изображения!", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "При сохранение возникла ошибка!", Toast.LENGTH_LONG).show();
                        }
                        ImgProfile();
                    }
                }
            });

    public void NextListen(View view) {
        startActivity(new Intent(this, ListenActivity.class));
    }

    public void NextFeeling(View view) {
        startActivity(new Intent(this, MainMenuActivity.class));
    }

    private static final String getFullTime(final long timeInMillis) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        c.setTimeZone(TimeZone.getDefault());
        return format.format(c.getTime());
    }
}