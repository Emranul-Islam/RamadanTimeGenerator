package com.muhammad_sohag.ramadantime;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private Spinner ramadanDaySp, daySp, dateSP, monthSP;
    private String[] valueRd, valueDay, valueMonth;
    private EditText iftatTimeET, sehriTimeET, titleET, subET, distET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ramadanDaySp = findViewById(R.id.spinner_ramadan_day);
        daySp = findViewById(R.id.spinner_day);
        dateSP = findViewById(R.id.spinner_date);
        monthSP = findViewById(R.id.spinner_month);
        iftatTimeET = findViewById(R.id.u_iftar_et);
        sehriTimeET = findViewById(R.id.u_sehri_et);
        titleET = findViewById(R.id.u_title_et);
        subET = findViewById(R.id.u_sub_et);
        distET = findViewById(R.id.u_dist_et);

        valueRd = getResources().getStringArray(R.array.ramadan_day);
        valueDay = getResources().getStringArray(R.array.day);
        valueMonth = getResources().getStringArray(R.array.month);

        ArrayAdapter<String> rdAa = new ArrayAdapter<>(this, R.layout.spinner_show, R.id.sp_show, valueRd);
        ramadanDaySp.setAdapter(rdAa);

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, R.layout.spinner_show, R.id.sp_show, valueRd);
        dateSP.setAdapter(dateAdapter);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, R.layout.spinner_show, R.id.sp_show, valueDay);
        daySp.setAdapter(dayAdapter);


        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, R.layout.spinner_show, R.id.sp_show, valueMonth);
        monthSP.setAdapter(monthAdapter);


    }

    public void click(View view) {

        String ramadanDay = ramadanDaySp.getSelectedItem().toString();
        String day = daySp.getSelectedItem().toString();
        String date = dateSP.getSelectedItem().toString();
        String month = monthSP.getSelectedItem().toString();
        String iftar = iftatTimeET.getText().toString();
        String sehri = sehriTimeET.getText().toString();
        String title = titleET.getText().toString();
        String subTitle = subET.getText().toString();
        String dist = distET.getText().toString();

        if (!TextUtils.isEmpty(iftar) && !TextUtils.isEmpty(sehri) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(subTitle) && !TextUtils.isEmpty(dist)) {

            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra("ramadanDay", ramadanDay);
            intent.putExtra("day", day + ", " + date + " " + month + " ২০২০ ইং");
            intent.putExtra("iftar", iftar);
            intent.putExtra("sehri", sehri);
            intent.putExtra("title", title);
            intent.putExtra("subTitle", subTitle);
            intent.putExtra("dist", dist);
            startActivity(intent);

        } else {
            Toast.makeText(this, "সব গুলা বক্স পূরন করুন !!", Toast.LENGTH_LONG).show();
        }


    }

    public void about(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Muhammad Sohag")
                .setMessage("একজন এপ ডেভেলপার। আপনাদের সবার কাছে দোয়া পার্থি। প্রয়োজনে: emranul.islam.sohag@gmail.com")
                .setPositiveButton("ওকে", null)
                .show();

    }
}
