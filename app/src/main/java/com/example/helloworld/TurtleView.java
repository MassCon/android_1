package com.example.helloworld;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TurtleView extends View {

    public TurtleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        // head
        canvas.drawCircle(500, 500, 100, paint);
        // body
        canvas.drawCircle(500, 760, 200, paint);

        // arms
        canvas.drawCircle(720, 760, 40, paint);
        canvas.drawCircle(280, 760, 40, paint);

        // legs
        canvas.drawCircle(600, 1000, 40, paint);
        canvas.drawCircle(400, 1000, 40, paint);

        // stick
        paint.setColor(0xFF654321);
        canvas.drawRect(800, 1000, 750, 300, paint);
        paint.setColor(0xFF551A8B);
        canvas.drawRect(800, 500, 750, 400, paint);

        // headband
        canvas.drawRect(400, 400, 600, 500, paint);
    }
}
