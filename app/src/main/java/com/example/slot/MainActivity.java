package com.example.slot;

import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slot.ImageViewScroll.IEventEnd;
import com.example.slot.ImageViewScroll.ImageViewScroll;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    ImageView btn_up, btn_down;
    ImageViewScroll image, image2, image3;
    TextView txt_score;
    RelativeLayout relativeLayout;

    int count_done = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btn_up = (ImageView) findViewById(R.id.btn_up);
        btn_down = (ImageView) findViewById(R.id.btn_down);
        image = (ImageViewScroll) findViewById(R.id.image);
        image2 = (ImageViewScroll) findViewById(R.id.image2);
        image3 = (ImageViewScroll) findViewById(R.id.image3);
        txt_score = (TextView) findViewById(R.id.txt_score);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

        //set Events
        image.setiEventEnd(MainActivity.this);
        image2.setiEventEnd(MainActivity.this);
        image3.setiEventEnd(MainActivity.this);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.SCORE >= 50) {
                    //50 is minimum price to roll
                    btn_up.setVisibility(View.GONE);
                    btn_down.setVisibility(View.VISIBLE);

                    image.setValueRandom(new Random().nextInt(6),/*we have six Images, check Class UTIL*/
                            new Random().nextInt((15 - 5) + 1) + 5);/*Random from range 5 - 15 for rotate count*/
                    image2.setValueRandom(new Random().nextInt(6),/*we have six Images, check Class UTIL*/
                            new Random().nextInt((15 - 5) + 1) + 5);/*Random from range 5 - 15 for rotate count*/
                    image3.setValueRandom(new Random().nextInt(6),/*we have six Images, check Class UTIL*/
                            new Random().nextInt((15 - 5) + 1) + 5);/*Random from range 5 - 15 for rotate count*/

                    Common.SCORE -= 50;
                    txt_score.setText(String.valueOf(Common.SCORE));

                } else {
                    //Toast.makeText(MainActivity.this, "Not enough currency", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "Not enough money!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });
    }

    @Override
    public void eventEnd(int result, int count) {
        if (count_done < 2)//slot has been running
            count_done++;
        else {
            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);

            count_done = 0; //reset

            //Calculate Result
            if (image.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {

                //Toast.makeText(this, "You Win!!!!! \n Big Prize", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "You win BIG prize", Snackbar.LENGTH_LONG);
                snackbar.show();

                Common.SCORE += 200;
                txt_score.setText(String.valueOf(Common.SCORE));

            } else if (image.getValue() == image2.getValue() || image2.getValue() == image3.getValue()
                    || image.getValue() == image3.getValue()) {

                //Toast.makeText(this, "You Win!!!!!  \n Average Prize", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "You WIN average prize", Snackbar.LENGTH_LONG);
                snackbar.show();
                Common.SCORE += 400;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else {
                //Toast.makeText(this, "Sorry you Lose, \n TRY AGAIN YOU MIGHT WIN NEXT TIME", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Sorry you LOSE, Try again next time!!! 0_0", Snackbar.LENGTH_LONG);
                snackbar.show();

                if (Common.SCORE > 50) {
                    txt_score.setText(String.valueOf(Common.SCORE));
                } else if (Common.SCORE <= 2000) {
                    Common.SCORE -= (Common.SCORE * 0.50);
                    txt_score.setText(String.valueOf(Common.SCORE));
                }
            }
        }
    }
}
