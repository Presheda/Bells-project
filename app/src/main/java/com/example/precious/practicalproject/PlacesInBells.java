package com.example.precious.practicalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;


public class PlacesInBells extends AppCompatActivity {


    int dataCount;
    Button nextButton, previousButton;
    ViewPager mViewPager;
    LinearLayout sliderDotpanel, layout_pager, layout_text;
    int dotCount;
    private ImageView[] dots;
    int[] images = new int[]{R.drawable.collgeimageone, R.drawable.collegeimagetwo, R.drawable.collegeimagethree,};

    ArrayList<Bitmap> newBitmap;

    int[] images1 = new int[]{R.drawable.bellslogo, R.drawable.go};
    int[] MPH =  new int[]{};
    int[] ELT = new int[]{};
    int[] Library = new int[]{};
    int[] UpView_Daytime = new int[]{};
    int[] UpView_Night = new int[]{};
    int[] Uptown = new int[]{};
//    int[] COLNAS = new int[]{};
//    int[] COLENVS = new int[]{};
//    int[] COLMANS = new int[]{};
//    int[] COLENG = new int[]{};
    TextView  place_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_in_bells);
        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.message);
        place_info = (TextView) findViewById(R.id.text_info);
        place_info.setText(R.string.article_text);

        newBitmap  = new ArrayList<>();

        try{
            dataCount = Integer.parseInt(data.substring(data.length()-1));
            Log.e("PlacesInBells", " dataCount is " + dataCount);
        }
        catch (IndexOutOfBoundsException e){
            Log.e("PlacesInBells", "" + e);
        }

        layout_pager = (LinearLayout) findViewById(R.id.layout_pager);
        layout_text = (LinearLayout) findViewById(R.id.layout_text);

        Animation pager = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.uptodown);
        Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.downtoup);

        layout_pager.startAnimation(pager);
        layout_text.startAnimation(text);

        sliderDotpanel = (LinearLayout) findViewById(R.id.SliderDots);
        nextButton =  (Button) findViewById(R.id.button_next);
        previousButton = (Button) findViewById(R.id.button_previous);
        nextButton.setText(R.string.buttonNext);
        previousButton.setText(R.string.buttonPrevious);
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


//        checkDataCount();

        sendRequest();



    }

    void checkDataCount(){
        if(dataCount == 1){
            images = MPH;
            previousButton.setClickable(false);
            previousButton.setTextColor(Color.GRAY);
        }

        else if (dataCount == 2){
            images = ELT;
        }
        else if (dataCount == 3){
            images = Library;
        }

        else if(dataCount ==  4){
            images = UpView_Daytime;
        }

        else if(dataCount == 5){
            images = UpView_Night;
        }
        else if (dataCount == 6){
            images = Uptown;
            nextButton.setClickable(false);
            nextButton.setTextColor(Color.GRAY);
        }




    }

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


//        checkDataCount();



        final Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextButton.startAnimation(bounce);


                if(dataCount == 6) {

                    dataCount = 0;
                    Arrays.fill(dots, null);
                    adapterView.notifyDataSetChanged();
                    images = images1;
                    adapterView.notifyDataSetChanged();
                    sliderDotpanel.removeAllViewsInLayout();
                    adapterView.notifyDataSetChanged();
                    sendRequest();
                }
                else {
                    dataCount ++;
                    Arrays.fill(dots, null);
                    adapterView.notifyDataSetChanged();
                    images = images1;
                    adapterView.notifyDataSetChanged();
                    sliderDotpanel.removeAllViewsInLayout();
                    adapterView.notifyDataSetChanged();
                    sendRequest();
                }
            }
        });



//        checkDataCount();


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousButton.startAnimation(bounce);
                if(dataCount == 1) {

                    dataCount = 0;
                    Arrays.fill(dots, null);
                    adapterView.notifyDataSetChanged();
                    images = images1;
                    adapterView.notifyDataSetChanged();
                    sliderDotpanel.removeAllViewsInLayout();
                    adapterView.notifyDataSetChanged();
                    sendRequest();
                }
                else {
                    dataCount --;
                    Arrays.fill(dots, null);
                    adapterView.notifyDataSetChanged();
                    images = images1;
                    adapterView.notifyDataSetChanged();
                    sliderDotpanel.removeAllViewsInLayout();
                    adapterView.notifyDataSetChanged();
                    sendRequest();
                }
            }
        });




//        checkDataCount();


    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}