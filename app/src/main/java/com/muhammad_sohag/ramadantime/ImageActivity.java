package com.muhammad_sohag.ramadantime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageActivity extends AppCompatActivity {
    private TextView ramadanDayTV, dayTV, iftarTV, sehriTV, titleTV, subTitleTV, distTV;
    private RelativeLayout layout;
    private OutputStream outputStream;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        layout = findViewById(R.id.layout);
        saveBtn = findViewById(R.id.save_btn);

        ramadanDayTV = findViewById(R.id.ramadanDay);
        dayTV = findViewById(R.id.day);
        iftarTV = findViewById(R.id.iftar_time);
        sehriTV = findViewById(R.id.sehri_time);
        titleTV = findViewById(R.id.title);
        subTitleTV = findViewById(R.id.subTitle);
        distTV = findViewById(R.id.dist);

        String ramadanDay = getIntent().getStringExtra("ramadanDay");
        String day = getIntent().getStringExtra("day");
        String iftar = getIntent().getStringExtra("iftar");
        String sehri = getIntent().getStringExtra("sehri");
        String title = getIntent().getStringExtra("title");
        String subTitle = getIntent().getStringExtra("subTitle");
        String dist = getIntent().getStringExtra("dist");

        ramadanDayTV.setText(ramadanDay);
        dayTV.setText(day);
        iftarTV.setText(iftar);
        sehriTV.setText(sehri);
        titleTV.setText(title);
        subTitleTV.setText(subTitle);
        distTV.setText(dist);

    }

    public void save(View view) {
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);

        File sd = Environment.getExternalStorageDirectory();
        File dir = new File(sd.getAbsolutePath() + "/Ramadan Time/");
        File file = new File(dir, "RamadanTime" + System.currentTimeMillis() + ".jpg");

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);

            saveBtn.setClickable(false);

            Toast.makeText(this, "ইমেজ সেভ হয়েছে !!", Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
