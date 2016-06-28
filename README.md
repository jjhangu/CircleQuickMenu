# CircleQuickMenu
this is a sample CircleMenu ~! 
you can rotate with touchevent~!

## version
this is beta version 0.0.1

## ScreenShot
<img src="/image/screenshot_small.png" width="350">

## Detail of Components 
- FrameLayout and Imageviews

## How to use it
```java
CircleMenuFrameLayout layout = new CircleMenuFrameLayout(this);

// layout.setInit(circleCount,  centerPointX, centerPointY, imageWidth, imageHeight, radius);
layout.setInit(6,  centerPointX, centerPointY, outSize.x/8, outSize.x/8, outSize.x/4);
// number of images should match circleCount;
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
```
