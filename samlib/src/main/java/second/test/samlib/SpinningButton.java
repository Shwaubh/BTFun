package second.test.samlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Saurabh on 5/23/2018.
 */

public class SpinningButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener
{
    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    private int mdir = 0;

    public SpinningButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public SpinningButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context , attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpinningButton, 0, 0);
            try
            {
                mdir = a.getInt(R.styleable.SpinningButton_dir, RIGHT);
                Toast.makeText(context, "value of mdir " +mdir, Toast.LENGTH_SHORT).show();
            }
            finally
            {
                a.recycle();
            }
        }
        setOnClickListener(this);
    }

    public SpinningButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        RotateAnimation animation = null;
        if (mdir == RIGHT) {
            animation = new RotateAnimation(0, 360, v.getWidth() / 2, v.getHeight() / 2);
        } else if (mdir == LEFT) {
            animation = new RotateAnimation(360, 0, v.getWidth() / 2, v.getHeight() / 2);
        }
        animation.setDuration(500);
        v.startAnimation(animation);
    }
    public void setSpinDirection(int dir)
    {
        mdir = dir;
    }
}
