package jjhangu.circleapp.menu;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016-06-27.
 */
public class CircleMenuFrameLayout extends FrameLayout implements   View.OnTouchListener{

    private SelectedListener listener = null;
    private CircleCore circleCore;
    private float lastx= 0;
    private float lastY= 0;

    private List<ImageView> imageViewList = new ArrayList<ImageView>();

    public CircleMenuFrameLayout(Context context) {
        super(context);
        this.setOnTouchListener(this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                  listener.onClick(0);
                }
            }
        });


        FrameLayout.LayoutParams parmas = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(parmas);
        this.setBackgroundColor(Color.YELLOW);
        this.setVisibility(View.GONE);
        this.setClickable(true);
        this.setFocusableInTouchMode(true);
        this.setFocusable(true);
    }

    public void setInit(int num, int startX, int starY, int width, int height, int radius){
        circleCore = new CircleCore();

        setCircleView(num, width, height);
        circleCore.setCoodinate(startX, starY);
        circleCore.setRadius(radius);
        circleCore.setWidthandHeight(width, height);

    }

    public void show(){
        this.setVisibility(View.VISIBLE);
    }

    public void hide(){
        this.setVisibility(View.GONE);
    }

    public void start(){
        circleCore.setInit();
        int positions[] = circleCore.getPositions();
        for (int i=0; i<positions.length; i++){

            int po = positions[i]%360;
            int x=0;
            int y=0;
            if(po>0){
                x = circleCore.plX[po];
                y = circleCore.plY[po];
            }else{
                x = circleCore.miX[po *-1];
                y = circleCore.miY[po *-1];
            }

            x+= circleCore.startX- circleCore.width/2;
            y+= circleCore.startY- circleCore.height/2;

            ImageView view= imageViewList.get(i);
            FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams)view.getLayoutParams();
            layoutParams.setMargins(x, y, 0, 0);
            view.setLayoutParams(layoutParams);
        }
    }

    /**
     * Max Circle count is 12
     * @param num
     */
    private void setCircleView(int num, int width, int height){
        circleCore.setCircleCount(num);

        for(int i=0; i<num; i++){
            ImageView view = new ImageView(getContext());
            view.setId(i);
//            view.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("", "onclick");
//                    if(listener != null){
//                        listener.onClick(v.getId());
//                    }
//                }
//            });
            imageViewList.add(view);

            FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(width, height);
            layoutParams.setMargins(0, 0, 0, 0);
            view.setLayoutParams(layoutParams);
            this.addView(view);
        }
    }

    public void setSelectedListener(SelectedListener listener){
        this.listener = listener;
    }

    public void setImageListResource(int[] arr){
        for (int i=0; i<arr.length; i++){
            imageViewList.get(i).setImageResource(arr[i]);
        }
    }

    boolean onclick = true;



    /**
     * Max allowed duration for a "click", in milliseconds.
     */
    private static final int MAX_CLICK_DURATION = 1000;

    /**
     * Max allowed distance to move during a "click", in DP.
     */
    private static final int MAX_CLICK_DISTANCE = 15;

    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private boolean stayedWithinClickDistance;

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        float distanceInPx = (float) Math.sqrt(dx * dx + dy * dy);
        return pxToDp(distanceInPx);
    }

    private float pxToDp(float px) {
        return px / this.getResources().getDisplayMetrics().density;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("TAG", event.getAction() +"");
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                lastx  = event.getX();
                lastY= event.getY();
                pressStartTime = System.currentTimeMillis();
                pressedX = event.getX();
                pressedY = event.getY();
                stayedWithinClickDistance = true;

                break;

            case MotionEvent.ACTION_UP:
                lastx  = event.getX();
                lastY= event.getY();
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                    // Click event has occurred
                    // try to find
                    for (int i=0; i<imageViewList.size(); i++){
                        ImageView view = imageViewList.get(i);
                        int x= (int)view.getX();
                        int y= (int)view.getY();

                        Log.e("coodinate", "x " + x +", y "+y +", lastx " + lastx + ", lastY "+ lastY +", width " + view.getWidth() + ", height " + view.getHeight());

                        if(x< lastx && lastx<x+view.getWidth() && y< lastY && lastY<y+view.getHeight()   ){
                            Log.e("", "onclick");
                            if(listener != null){
                                listener.onClick(view.getId());
                            }
                            break;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (stayedWithinClickDistance && distance(pressedX, pressedY, event.getX(), event.getY()) > MAX_CLICK_DISTANCE) {
                    stayedWithinClickDistance = false;
                }

                onclick = false;
                int isRight =1;

                float num = 0;
                if(Math.abs(lastx - event.getX()) > Math.abs(lastY - event.getY())){
                    if(lastx > event.getX()){
                        // right
                        isRight= 1;
                    }else{
                        // left
                        isRight = -1;
                    }
                    num = (float)Math.abs(lastx - event.getX());
                }else{
                    if(lastY > event.getY()){
                        // left
                        isRight = -1;
                    }else{
                        // right
                        isRight= 1;
                    }
                    num = (float)Math.abs(lastY - event.getY());
                }



                num *=0.5;

                int positions[] = circleCore.getPositions();
                lastx = event.getX();
                lastY = event.getY();
                for (int i=0; i<positions.length; i++){
                    positions[i]= (positions[i]+((int)num*isRight))%360;

                    int po = positions[i];
                    int x=0;
                    int y=0;
                    if(po>0){
                        x = circleCore.plX[po];
                        y = circleCore.plY[po];
                    }else{
                        x = circleCore.miX[po *-1];
                        y = circleCore.miY[po *-1];
                    }

                    x+= circleCore.startX- circleCore.width/2;
                    y+= circleCore.startY- circleCore.height/2;




                    ImageView view= imageViewList.get(i);
                    FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams)view.getLayoutParams();
                    layoutParams.setMargins(x, y, 0, 0);
                    view.setLayoutParams(layoutParams);
                }
                break;

        }
        return true;
    }
}
