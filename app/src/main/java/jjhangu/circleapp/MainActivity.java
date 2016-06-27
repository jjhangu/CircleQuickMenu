package jjhangu.circleapp;

import android.graphics.Point;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;

import jjhangu.circleapp.menu.CircleMenuFrameLayout;
import jjhangu.circleapp.menu.SelectedListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.show();
            }
        });



        // 6개의 뷰를 더해준다.
    }

    CircleMenuFrameLayout layout = null;
    @Override
    protected void onStart() {
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outSize);
        Log.e("deviceSize ", "x : " + outSize.x + "    Y:" + outSize.y);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        Log.e("deviceSize ", "x : " + w + "    Y:" + h);

        if(layout == null){
            // circle 중에서 셀렉트 될때 발생하는 이벤트
            layout = new CircleMenuFrameLayout(this);

            w/=2;

            layout.setInit(6,  w, 1000, outSize.x/8, outSize.x/8, outSize.x/4);
            layout.setImageListResource(new int[]{R.drawable.n0, R.drawable.n1, R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5});
            layout.setSelectedListener(new SelectedListener() {
                @Override
                public void onClick(int num) {
                    Toast.makeText(getApplicationContext(), "num : " + num, Toast.LENGTH_SHORT).show();
                    layout.hide();;
                }
            });
            layout.start();

            RelativeLayout contentView= (RelativeLayout)findViewById(R.id.contentMenu);
            contentView.addView(layout);
        }


        super.onStart();
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
}
