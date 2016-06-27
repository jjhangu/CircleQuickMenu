package jjhangu.circleapp.menu;

import android.util.Log;

/**
 * Created by david on 2016-06-27.
 */
public class CircleCore {
    private static String TAG = "CircleCore";

    /**
     * Circle X, Y coordinate
     */
    public int miX[] = new int [360];
    public int miY[] = new int [360];
    public int plX[] = new int [360];
    public int plY[] = new int [360];

    public int startX;
    public int startY;
    private int radius;
    private int num;

    public int width;
    public int height;

    private int positions[];

    /**
     *

     */
    CircleCore(){
    }
    public void setInit(){
        // 각도별 좌표 x,y 셋팅
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
    }

    public void setCoodinate(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }
    public void setCircleCount(int num){
        // 이미지뷰 최초 각도 셋팅
        positions = new int[num];
        int degree = 360/num;
        for(int i=0; i<positions.length; i++){
            positions[i] = degree * (i+1);
        }
    }

    public void setWidthandHeight(int width, int height){
        this.width = width;
        this.height = height;
    }

    private  int[] getXY(int degree){
        double degrees = degree;
        double radians = Math.toRadians(degrees);

        Log.e(TAG, "degrees: " + degrees + " radians : " + radians);

        double width = Math.sin(radians )*(double) radius;
        double height =  Math.sqrt(Math.pow(radius, 2) - Math.pow(width, 2));

        int[] xy= new int[2];



        if((degree > 90 && degree <= 180) || (degree <= -90 && degree > -180)){
            xy[0] = 0 + (int) width;
            xy[1] = 0+ (int) height;
        }else if((degree > 180 && degree <= 270) || (degree <= -180 && degree > -270)){
            xy[0] = 0 + (int) width;
            xy[1] = 0+ (int) height;
        }else if(degree > 270 || degree < -270){
            xy[0] = 0 + (int) width;
            xy[1] = 0- (int) height;
        }else{
            xy[0] = 0 + (int) width;
            xy[1] = 0 - (int) height;
        }
        return xy;
    }

    public int getSize(){
        return positions.length;
    }

    public int[] getPositions(){
        return positions;
    }

}
