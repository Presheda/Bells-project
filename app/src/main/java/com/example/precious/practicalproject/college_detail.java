package com.example.precious.practicalproject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.precious.practicalproject.DataAdapter.college_detail_dataAdapter;
import com.example.precious.practicalproject.DataHolder.college_detail_data;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;

public class college_detail extends AppCompatActivity {





    int dataCount;
    ViewPager mViewPager;
    LinearLayout sliderDotpanel, layout_pager, layout_text;
    int dotCount;
    private ImageView[] dots;
    int[] images = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.cat,};
    ArrayList<Bitmap> newBitmap;
    int[] images1 = new int[]{R.drawable.bellslogo, R.drawable.go};
    int[] COLNAS = new int[]{R.drawable.collgeimageone, R.drawable.collegeimagetwo, R.drawable.collegeimagethree};
    int[] COLENVS = new int[] {R.drawable.collgeimageone, R.drawable.collegeimagetwo, R.drawable.collegeimagethree};
    int[] COLMANS = new int[] {R.drawable.collgeimageone, R.drawable.collegeimagetwo, R.drawable.collegeimagethree};
    int[] COLENG = new int[] {R.drawable.collgeimageone, R.drawable.collegeimagetwo, R.drawable.collegeimagethree};

    TextView college_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        layout_pager = (LinearLayout) findViewById(R.id.layout_pager);
        layout_text = (LinearLayout) findViewById(R.id.layout_text);


        Animation pager = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.uptodown);
        Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.downtoup);

        layout_pager.startAnimation(pager);
        layout_text.startAnimation(text);




        images = COLNAS;

       newBitmap  = new ArrayList<>();






        Intent intent =  getIntent();

        String data =  intent.getStringExtra(college.collegID);

        try{
            dataCount = Integer.parseInt(data.substring(data.length()-1));
            Log.e("PlacesInBells", " dataCount is " + dataCount);
        }
        catch (IndexOutOfBoundsException e){
            Log.e("PlacesInBells", "" + e);
        }



        for(int i =0; i < images.length; i++ ){



        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(images[i])).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 50, 50, mpaint);// Round Image Corner 100 100 100 100

            newBitmap.add(imageRounded);

        }


        college_info = (TextView) findViewById(R.id.college_info);
       if(dataCount == 1){
           college_info.setText(R.string.college_detail_colnas);
       }

       else if(dataCount == 2){
           college_info.setText(R.string.college_detail_coleng);
       }
       else if(dataCount == 3){
           college_info.setText(R.string.college_detail_colmans);
       }

       else if (dataCount ==  4){
           college_info.setText(R.string.college_detail_colenvs);
       }

        sliderDotpanel = (LinearLayout) findViewById(R.id.SliderDots);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< dotCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.black_circle));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.white_circle));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        checkDataCount();

        sendRequest();



    }
//
//    void checkDataCount(){
//         if (dataCount == 1){
//            images = COLNAS;
//        }
//        else if (dataCount == 2){
//            images = COLENVS;
//        }
//        else if(dataCount == 3){
//            images =  COLMANS;
//        }
//
//        else if(dataCount == 4){
//            images = COLENG;
//         }
//    }

    void sendRequest(){
        final AndroidImageAdapter adapterView = new AndroidImageAdapter(this, newBitmap);
        mViewPager.setAdapter(adapterView);


        dotCount =  adapterView.getCount();
        dots = new ImageView[dotCount];


        for (int i = 0; i<dotCount; i++){
            dots[i] =  new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.black_circle));

            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            sliderDotpanel.addView(dots[i], params);
        }



        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.white_circle));



    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

