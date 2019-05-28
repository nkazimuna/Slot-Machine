package com.example.slot.ImageViewScroll;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.slot.R;

public class ImageViewScroll extends FrameLayout {

    private static int ANIMATION_DUR = 150;
    ImageView currect_image, next_image;

    int last_result = 0, old_value = 0;

    IEventEnd iEventEnd;

    public void setiEventEnd(IEventEnd iEventEnd) {
        this.iEventEnd = iEventEnd;
    }

    public ImageViewScroll(Context context) {
        super(context);
        init(context);
    }

    public ImageViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this);
        currect_image = (ImageView) getRootView().findViewById(R.id.current_image);
        next_image = (ImageView) getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());

    }

    public void setValueRandom(final int image, final int rotate_count) {

        currect_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(2).setDuration(ANIMATION_DUR)//TODO: 101
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setImage(currect_image, old_value % 6);//have 6 images
                        currect_image.setTranslationY(0);

                        if (old_value != rotate_count) {
                            //if old_value still not equal rotate count, will still roll
                            setValueRandom(image, rotate_count);
                            old_value++;

                        } else {
                            //if rotate is reached

                            last_result = 0;
                            old_value = 0;
                            setImage(next_image, image);
                            iEventEnd.eventEnd(image % 6, rotate_count);

                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setImage(ImageView image_view, int value) {
        if (value == Util.BAR)
            image_view.setImageResource(R.drawable.bar_done);
        else if (value == Util.SEVEN)
            image_view.setImageResource(R.drawable.sevent_done);
        else if (value == Util.ORANGE)
            image_view.setImageResource(R.drawable.orange_done);
        else if (value == Util.LEMON)
            image_view.setImageResource(R.drawable.lemon_done);
        else if (value == Util.TRIPLE)
            image_view.setImageResource(R.drawable.triple_done);
        else if (value == Util.WATERMELON)
            image_view.setImageResource(R.drawable.waternelon_done);

        //set image tag for use to compare results
        image_view.setTag(value);
        last_result = value;

    }
    public int getValue() {
        return Integer.parseInt(next_image.getTag().toString());
    }
}
