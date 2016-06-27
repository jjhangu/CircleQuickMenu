package jjhangu.circleapp;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Arrays;

public class MainActivityBk extends AppCompatActivity {

    private static final String TAG = "TAG";

    private static int startX = 700;
    private static int startY = 1200;
    private int degree = 0;

    private int positions[] = new int[6];
    private ImageView imgs[] = new ImageView[6];

    private int miX[] = new int [360];
    private int miY[] = new int [360];
    private int plX[] = new int [360];
    private int plY[] = new int [360];


    float lastx=-1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+
        Log.i(TAG, "displayName  = " + displayName);

// display size in pixels
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i(TAG, "width        = " + width);
        Log.i(TAG, "height       = " + height);

        // 6개의 뷰를 더해준다.

        ImageView view =(ImageView) findViewById(R.id.no0);
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[0] = view;

        view =(ImageView) findViewById(R.id.no1);
        layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[1] = view;

        view =(ImageView) findViewById(R.id.no2);
        layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[2] = view;

        view =(ImageView) findViewById(R.id.no3);
        layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[3] = view;

        view =(ImageView) findViewById(R.id.no4);
        layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[4] = view;

        view =(ImageView) findViewById(R.id.no5);
        layoutParams=new FrameLayout.LayoutParams(280, 280);
        layoutParams.setMargins(startX, startY - 500, 0, 0);
        view.setLayoutParams(layoutParams);
        imgs[5] = view;


        findViewById(R.id.left).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastx  = event.getX();
                        break;

                        case MotionEvent.ACTION_MOVE:

                            int isRight =1;
                            if(lastx > event.getX()){
                                // right
                                isRight= 1;
                            }else{
                                // left
                                isRight = -1;
                            }



                            float num = (float)Math.abs(lastx - event.getX());
                            num *=0.5;

                            Log.e(TAG, "Arays" + Arrays.toString(positions) +" , " + (lastx - event.getX()));
                            lastx = event.getX();
                            for (int i=0; i<imgs.length; i++){
                                positions[i]= (positions[i]+((int)num*isRight))%360;

                                int po = positions[i];
                                int x=0;
                                int y=0;
                                if(po>0){
                                    x = plX[po];
                                    y = plY[po];
                                }else{
                                    x = miX[po *-1];
                                    y = miY[po *-1];
                                }

                                FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams)imgs[i].getLayoutParams();
                                layoutParams.setMargins(x, y, 0, 0);
                                imgs[i].setLayoutParams(layoutParams);
                            }

                            break;

                }


                return false;
            }
        });


        positions[0]= 0;
        positions[1]= 45;
        positions[2]= 90;
        positions[3]= 135;
        positions[4]= 180;
        positions[5]= 225;


        // 마이너스 플러스 셋팅

        for(int i=0; i<360; i++){
           int[]xy  = getXY(i);
            plX[i] = xy[0];
            plY[i] = xy[1];
        }

        for(int i=0; i<360; i++){
            int[]xy  = getXY(i*-1);
            miX[i] = xy[0];
            miY[i] = xy[1];
        }

        Log.e(TAG, "miX : " + Arrays.toString(miX));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  int[] getXY(int degree){
        double degrees = degree;
        double radians = Math.toRadians(degrees);

        Log.e(TAG, "degrees: " + degrees +" radians : " + radians);

        double height = Math.sin(radians )*(double) 500;
        double width =  Math.sqrt(Math.pow(500, 2) - Math.pow(height, 2));

        int[] xy= new int[2];

        if(degree > 90 && degree <= 180 || degree <= -90 && degree > -180){

            xy[0] = startX + (int) height;
            xy[1] = startY+ (int) width;
        }else if(degree > 180 && degree <= 270 || degree <= -180 && degree > -270){
            xy[0] = startX + (int) height;
            xy[1] = startY+ (int) width;
        }else if(degree > 270 || degree < -270){
            xy[0] = startX + (int) height;
            xy[1] = startY- (int) width;
        }else{
            xy[0] = startX + (int) height;
            xy[1] = startY - (int) width;
        }
        return xy;
    }

    public void draw(int de[], int  isRight){
        this.degree = this.degree + (10 * isRight);

        if(this.degree > 360){
            this.degree = this.degree%360;
        }

        if(this.degree < -360){
            this.degree = this.degree%360;
        }

        double degrees = this.degree;
        double radians = Math.toRadians(degrees);

        Log.e(TAG, "degrees: " + degrees +" radians : " + radians);

        double height = Math.sin(radians )*(double) 500;
        double width =  Math.sqrt(Math.pow(500, 2) - Math.pow(height, 2));
        FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams)((ImageView)findViewById(R.id.no0)).getLayoutParams();
        Log.e(TAG, "degrees: " + degrees +" radians : " + radians);
        if(degree > 90 && degree <= 180 || degree <= -90 && degree > -180){

            layoutParams.setMargins(startX + (int) height, startY+ (int) width, 0, 0);
        }else if(degree > 180 && degree <= 270 || degree <= -180 && degree > -270){
            layoutParams.setMargins(startX + (int) height, startY + (int) width, 0, 0);
        }else if(degree > 270 || degree < -270){
            layoutParams.setMargins(startX + (int) height, startY - (int) width, 0, 0);
        }else{
            layoutParams.setMargins(startX + (int) height, startY - (int) width, 0, 0);
        }




            // 1번
        ((ImageView)findViewById(R.id.no0)).setLayoutParams(layoutParams);
    }
}
