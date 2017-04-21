package com.example.zxy.doodleboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
public class MainActivity extends Activity implements OnClickListener {
    private ImageView image;
    private Button save;
    private Button reset;
    private Bitmap panel;
    private Canvas canvas;
    private Paint paint;
    private float downX;
    private float downY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }
    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        save = (Button) findViewById(R.id.save);
        reset = (Button) findViewById(R.id.reset);
        image.setOnTouchListener(new onTouchListener());
        save.setOnClickListener(this);
        reset.setOnClickListener(this);
    }
    class onTouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            switch (action) {
                //按下触发
                case MotionEvent.ACTION_DOWN:
                    System.out.println("按下");
                    initPanel();
                    downX=motionEvent.getX();
                    downY=motionEvent.getY();
                    break;
                //移动触发
                case MotionEvent.ACTION_MOVE:
                    System.out.println("移动");
                    float moveX = motionEvent.getX();
                    float moveY = motionEvent.getY();
                    canvas.drawLine(downX, downY, moveX, moveY, paint);
                    image.setImageBitmap(panel);
                    downX = moveX;
                    downY = moveY;
                    break;
                //松开触发
                case MotionEvent.ACTION_UP:
                    System.out.println("松开");
                    break;
                default:
                    break;
            }
            //false只触发按下  true全触发
            return true;
        }
        private void initPanel() {
            if(panel == null){
                //画纸
                panel = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Config.ARGB_8888);
                //初始化一个画板
                canvas = new Canvas(panel);
                //初始化画笔
                paint = new Paint();
                //指定画笔颜色
                paint.setColor(Color.RED);
                //指定宽度
                paint.setStrokeWidth(5);
                //指定画板颜色
                canvas.drawColor(Color.YELLOW);
                image.setImageBitmap(panel);
            }
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        System.out.println("id"+id);
        if(id == R.id.save){
            //保存
            System.out.println("保存...");
        }else if(id == R.id.reset){
            //清空
            System.out.println("清空...");
            canvas.drawColor(Color.YELLOW);
            image.setImageBitmap(panel);
        }
    }
}
