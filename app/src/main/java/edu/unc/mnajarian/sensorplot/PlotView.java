package edu.unc.mnajarian.sensorplot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static edu.unc.mnajarian.sensorplot.R.*;


public class PlotView extends View {

    public ArrayList<Float> l;
    public float maxYValue;

    public PlotView(Context context) {
        super(context);
        l = new ArrayList<Float>(15);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        l = new ArrayList<Float>(15);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        l = new ArrayList<Float>(15);
    }

    public PlotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        l = new ArrayList<Float>(15);
    }

    public void clearList(){
        // Clears the list
        l.clear();
    }

    public void addPoint(Float pointToAdd){
        // inserts pointToAdd to the end of the list
        // if size of list >= 15
        if (l.size() >= 15){
            l.remove(0); // remove the first element of the list
        }
        l.add(pointToAdd);
    }

    public void setMaxYValue(Float maxY){
        maxYValue = maxY;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the points from the array: X axis = array index, Y axis = value
        // scale points using getWidth() and getHeight()

        float width = this.getWidth();
        float height = this.getHeight();
        float unitWidth = width/(float)11.0;
        float unitHeight = height/(float)maxYValue;

        Paint pAxesExt = new Paint();
        pAxesExt.setColor(Color.GRAY);
        pAxesExt.setStrokeWidth((float) 10.0);
        canvas.drawLine((float) 0.0, (float)0.0, (float)0.0, height, pAxesExt);
        canvas.drawLine(width, (float)0.0, width, height, pAxesExt);
        canvas.drawLine((float) 0.0, (float) 0.0, width, (float) 0.0, pAxesExt);
        canvas.drawLine((float) 0.0, height, width, height, pAxesExt);

        Paint pAxesInt = new Paint();
        pAxesInt.setColor(Color.GRAY);
        pAxesInt.setStrokeWidth((float) 3.0);
        canvas.drawLine(unitWidth*2, (float) 0.0, unitWidth*2, height, pAxesInt);
        canvas.drawLine(unitWidth*4, (float) 0.0, unitWidth*4, height, pAxesInt);
        canvas.drawLine(unitWidth*6, (float) 0.0, unitWidth*6, height, pAxesInt);
        canvas.drawLine(unitWidth*8, (float) 0.0, unitWidth*8, height, pAxesInt);
        canvas.drawLine(unitWidth*10, (float) 0.0, unitWidth*10, height, pAxesInt);
        canvas.drawLine((float) 0.0, height/2, width, height/2, pAxesInt);

        // values
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStrokeWidth((float) 5.0);

        // mean values
        Paint p2 = new Paint();
        p2.setColor(Color.YELLOW);
        p2.setStrokeWidth((float) 5.0);

        // stdev values
        Paint p3 = new Paint();
        p3.setColor(Color.RED);
        p3.setStrokeWidth((float) 5.0);

        if (l.size() < 10) {
            for (int i = 0; i < l.size(); i++) {
                canvas.drawCircle(i * unitWidth + unitWidth / 2, height - l.get(i) * unitHeight,
                        10, p);
                if (i >= 1){
                    canvas.drawLine((i-1)*unitWidth+unitWidth/2, height-l.get(i-1)*unitHeight,
                            (i)*unitWidth+unitWidth/2, height-l.get(i)*unitHeight, p);
                }
            }
        } else{
            int toAdd = l.size()-10;
            float prevMean = 0;
            float prevStdev = 0;
            float mean = 0;
            float stdev = 0;

            for (int i = 0; i < 10; i++) {

                //Log.i("value", Float.toString(l.get(i+toAdd)));

                if (toAdd >= 5){
                    mean = (l.get(i+toAdd) + l.get(i+toAdd-1) + l.get(i+toAdd-2) +
                        l.get(i+toAdd-3) + l.get(i+toAdd-4))/5;
                    //Log.i("mean", Float.toString(mean));

                    stdev = (float) Math.sqrt((Math.pow(l.get(i+toAdd)-mean,2) +
                            Math.pow(l.get(i+toAdd-1)-mean,2) +
                            Math.pow(l.get(i+toAdd-2)-mean,2) +
                            Math.pow(l.get(i+toAdd-3)-mean,2) +
                            Math.pow(l.get(i+toAdd-4)-mean,2)
                    )/4);
                    //Log.i("stdev draw", Float.toString(stdev));

                }

                // circles
                canvas.drawCircle(i * unitWidth + unitWidth,
                        height - l.get(i+toAdd) * unitHeight, 10, p);
                canvas.drawCircle(i * unitWidth + unitWidth, height - mean * unitHeight,
                        10, p2);
                canvas.drawCircle(i * unitWidth + unitWidth, height - stdev * unitHeight,
                        10, p3);

                // lines between circles
                if (i >= 1){
                    canvas.drawLine((i-1)*unitWidth + unitWidth, height-l.get(i-1+toAdd)*unitHeight,
                            (i)*unitWidth + unitWidth, height-l.get(i+toAdd)*unitHeight, p);
                    canvas.drawLine((i-1)*unitWidth + unitWidth, height-prevMean*unitHeight,
                            (i)*unitWidth + unitWidth, height-mean*unitHeight, p2);
                    canvas.drawLine((i-1)*unitWidth + unitWidth, height-prevStdev*unitHeight,
                            (i)*unitWidth + unitWidth, height-stdev*unitHeight, p3);

                }
                prevMean = mean;
                prevStdev = stdev;
            }
        }

    }

}
