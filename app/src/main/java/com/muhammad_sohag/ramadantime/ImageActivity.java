package com.muhammad_sohag.ramadantime;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageActivity extends AppCompatActivity {
    private TextView ramadanDayTV, dayTV, iftarTV, sehriTV, titleTV, subTitleTV, distTV;
    private RelativeLayout layout;
    private LinearLayout linearLayoutButtom;
    private OutputStream outputStream;
    private Button saveBtn;
    private CircleImageView imageView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image);

        //ads initialize
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        //myInterstitialAdId: ca-app-pub-8851464520979715/1650297592
        //testInterstitialAdId: ca-app-pub-3940256099942544/1033173712
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8851464520979715/1650297592");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        layout = findViewById(R.id.layout);
        linearLayoutButtom = findViewById(R.id.linear_layout_bottom);
        saveBtn = findViewById(R.id.save_btn);
        ramadanDayTV = findViewById(R.id.ramadanDay);
        dayTV = findViewById(R.id.day);
        iftarTV = findViewById(R.id.iftar_time);
        sehriTV = findViewById(R.id.sehri_time);
        titleTV = findViewById(R.id.title);
        subTitleTV = findViewById(R.id.subTitle);
        distTV = findViewById(R.id.dist);
        imageView = findViewById(R.id.image_view);

        //ager activity theke asa string gola get kora hoiche
        String ramadanDay = getIntent().getStringExtra("ramadanDay");
        String day = getIntent().getStringExtra("day");
        String iftar = getIntent().getStringExtra("iftar");
        String sehri = getIntent().getStringExtra("sehri");
        String title = getIntent().getStringExtra("title");
        String subTitle = getIntent().getStringExtra("subTitle");
        String dist = getIntent().getStringExtra("dist");
        String uri = getIntent().getStringExtra("uri");

        //layout e sob gola user data set kora hoiche
        ramadanDayTV.setText(ramadanDay);
        dayTV.setText(day);
        iftarTV.setText(iftar);
        sehriTV.setText(sehri);
        titleTV.setText(title);
        subTitleTV.setText(subTitle);
        distTV.setText(dist);
        assert uri != null;
        if (!uri.equals("null")) {
            imageView.setImageURI(Uri.parse(uri));
        } else {
            imageView.setVisibility(View.GONE);
            linearLayoutButtom.setWeightSum(4);
        }

    }


    //save button listener
    public void save(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(ImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                saveProcess();

            } else {
                saveProcess();
            }

        } else {
            saveProcess();
        }


    }


    //layout takee image e convert kore sd card e save kora hoiche
    private void saveProcess() {
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);

        File sd = Environment.getExternalStorageDirectory();
        File dir = new File(sd.getAbsolutePath() + "/Ramadan Time/");
        dir.mkdir();
        File file = new File(dir, "RamadanTime" + System.currentTimeMillis() + ".jpg");

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);

            //saveBtn.setClickable(false);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        saveBtn.setClickable(false);
                        customToast();
                        Toast.makeText(ImageActivity.this, "ইমেজ সেভ হয়েছে !!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClicked() {
                        customToast();
                        Toast.makeText(ImageActivity.this, "ইমেজ সেভ হয়েছে !!", Toast.LENGTH_SHORT).show();
                        saveBtn.setClickable(false);
                        super.onAdClicked();
                    }
                });
            } else {
                customToast();
                Toast.makeText(this, "ইমেজ সেভ হয়েছে !!", Toast.LENGTH_SHORT).show();
                saveBtn.setClickable(false);


            }


        } catch (IOException e) {
            Log.e("TAG", "saveProcess: ", e);
            e.printStackTrace();
            Toast.makeText(this, "সমস্যা হয়েছে !!: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void customToast() {
        View view = LayoutInflater.from(this).inflate(R.layout.confirm_toast_massage, null, false);
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();

    }
}
