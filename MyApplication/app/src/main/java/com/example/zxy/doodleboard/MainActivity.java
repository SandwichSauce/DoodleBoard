package com.example.zxy.doodleboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
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
        image=(ImageView) findViewById(R.id.image);
        save=(Button) findViewById(R.id.save);
        reset=(Button) findViewById(R.id.reset);
        image.setOnTouchListener(new myOnTouchListener());
        save.setOnClickListener(this);
        reset.setOnClickListener(this);
    }
    class myOnTouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            int action=arg1.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN://按下触发
                    System.out.println("按下");
                    initPanel();
                    downX=arg1.getX();
                    downY=arg1.getY();
                    break;
                case MotionEvent.ACTION_MOVE://移动触发
                    System.out.println("移动");
                    float moveX= arg1.getX();
                    float moveY=arg1.getY();
                    canvas.drawLine(downX, downY, moveX, moveY, paint);
                    image.setImageBitmap(panel);
                    downX=moveX;
                    downY=moveY;
                    break;
                case MotionEvent.ACTION_UP://松开触发
                    System.out.println("松开");
                    break;
                default:
                    break;
            }
            return true;//false只触发按下  true全触发
        }
        private void initPanel() {
            if(panel==null){
                panel=Bitmap.createBitmap(image.getWidth(), image.getHeight(), Config.ARGB_8888);//画纸
//初始化一个画板
                canvas=new Canvas(panel);
//初始化画笔
                paint=new Paint();
                paint.setColor(Color.RED);//指定颜色
                paint.setStrokeWidth(5);//指定宽度
                canvas.drawColor(Color.YELLOW);
                image.setImageBitmap(panel);
            }
        }
    }
    @Override
    public void onClick(View arg0) {
        int id=arg0.getId();
        System.out.println("id"+id);
        if(id==R.id.save){
            System.out.println("保存...");
//保存
        }else if(id==R.id.reset){
//取消
            System.out.println("取消...");
            canvas.drawColor(Color.WHITE);
            image.setImageBitmap(panel);
        }
    }
}
