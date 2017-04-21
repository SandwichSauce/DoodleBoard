package com.example.zxy.doodleboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private ImageView image;
    private Button save;
    private Button reset;
    private Bitmap panel;
    private Canvas canvas;
    private Paint paint;
    private float downX;
    private float downY;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.etxt);
        // 为文本框注册上下文菜单
        registerForContextMenu(editText);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = new MenuInflater(this);
        //装填R.menu.my_menu对应的菜单，并添加到menu中
        inflator.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // 创建上下文菜单时触发该方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View source,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflator = new MenuInflater(this);
        //装填R.menu.context对应的菜单，并添加到menu中
        inflator.inflate(R.menu.context , menu);
        menu.setHeaderIcon(R.drawable.tools);
        menu.setHeaderTitle("请选择背景色");
    }
    // 上下文菜单中菜单项被单击时触发该方法
    @Override
    public boolean onContextItemSelected(MenuItem mi)
    {
        // 勾选该菜单项
        mi.setChecked(true);  // ①
        switch (mi.getItemId())
        {
            case R.id.red:
                mi.setChecked(true);
                editText.setTextColor(Color.RED);
//                canvas.drawColor(Color.RED);
//                paint.setColor(Color.RED);

                break;
            case R.id.green:
                mi.setChecked(true);
                editText.setTextColor(Color.GREEN);
//                paint.setColor(Color.GREEN);

                break;
            case R.id.blue:
                mi.setChecked(true);
                editText.setTextColor(Color.BLUE);
                break;
        }
        return true;
    }
    @Override
    // 菜单项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        if(mi.isCheckable())
        {
            // 勾选该菜单项
            mi.setChecked(true);  // ②
        }
        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (mi.getItemId())
        {
            case R.id.font_10:
                editText.setTextSize(10 * 2);
                break;
            case R.id.font_12:
                editText.setTextSize(12 * 2);
                break;
            case R.id.font_14:
                editText.setTextSize(14 * 2);
                break;
            case R.id.font_16:
                editText.setTextSize(16 * 2);
                break;
            case R.id.font_18:
                editText.setTextSize(18 * 2);
                break;
            case R.id.red_font:
                editText.setTextColor(Color.RED);
                mi.setChecked(true);
                break;
            case R.id.green_font:
                editText.setTextColor(Color.GREEN);
                mi.setChecked(true);
                break;
            case R.id.blue_font:
                editText.setTextColor(Color.BLUE);
                mi.setChecked(true);
                break;
//            case R.id.plain_item:
//                Toast toast = Toast.makeText(MainActivity.this
//                        , "您单击了普通菜单项" , Toast.LENGTH_SHORT);
//                toast.show();
//                break;
        }
        return true;
    }
}
