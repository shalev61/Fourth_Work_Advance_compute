package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.MotionEventCompat;

public class Joystick extends View {
    private MyClient client;

    private float width, height;
    // knob properties
    private float knobXPos, knobYPos, radius;
    private boolean isKnobMoving;

    public void initJoystick(MyClient client, float width, float height) {
        this.client = client;
        this.width = width;
        this.height = height;

        radius = 250;
        knobXPos = width / 2;
        knobYPos = height / 2;
        isKnobMoving = false;
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Joystick(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    public Joystick(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        knobXPos = width / 2;
        knobYPos = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        // paint surface
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        RectF oval1 = new RectF(0, 0, (int) width, (int) height);
        canvas.drawOval(oval1, paint);

        // paint knob
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        RectF oval2 = new RectF(knobXPos - radius / 2, knobYPos - radius / 2,
                knobXPos + radius / 2, knobYPos + radius / 2);
        canvas.drawOval(oval2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();

                float dx = knobXPos - x;
                float dy = knobYPos - y;

                if (dx * dx + dy * dy <= radius * radius) {
                    isKnobMoving = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isKnobMoving)
                    return true;

                float x = event.getX();
                float y = event.getY();

                if (x <= width && y <= height && x >= 0 && y >= 0) {
                    knobXPos = x;
                    knobYPos = y;
                }

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                isKnobMoving = false;
                knobXPos = width / 2;
                knobYPos = height / 2;
            }
            break;
        }

        invalidate();

        client.send("set aileron " + (2 * knobXPos / width - 1) + "\r\n");
        client.send("set elevator " + (-2 * knobYPos / height + 1) + "\r\n");

        return true;
    }
}