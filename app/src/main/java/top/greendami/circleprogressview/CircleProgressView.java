package top.greendami.circleprogressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.hau.circleprogressview.R;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.hau.circleprogressview.R;


/**
 * Created by Hau on 2017/3/25.
 */

public class CircleProgressView extends View {
    private  String title = "进度显示";//显示的文字
    private float progress = 0;//显示的进度
    private float now = 0;//当前进度
    private int mLayoutSize = 100;//控件大小（圆外方形）
    public int mColor;
    public  int mColorBackground;
    public int mColorShadow;

    public CircleProgressView(Context context){
        super(context);
    }
    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mColor = ContextCompat.getColor(context, R.color.colorPrimary);
        //getSource.getColor()方法过时，用ContextCompat.getColor（）；
        mColorBackground = ContextCompat.getColor(context,R.color.white);
        mColorShadow = ContextCompat.getColor(context,R.color.wheat);
    }
    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    protected void onMeasure(int withMeasureSpec,int heightMeasureSpec){
        super.onMeasure(withMeasureSpec,heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(withMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        mLayoutSize = Math.min(widthSpecSize,heightMeasureSpec);
        setMeasuredDimension(mLayoutSize,mLayoutSize);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //Shader:着色器  RadialGradient:环形渲染
        /*
          float x:  圆心X坐标
           float y:  圆心Y坐标
            float radius: 半径
             int[] colors:  渲染颜色数组
                floate[] positions: 相对位置数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布
                 Shader.TileMode tile:渲染器平铺模式
         */
        Shader shader = new RadialGradient(mLayoutSize/2,mLayoutSize/2,mLayoutSize/2,new int[]{Color.BLACK ,Color.DKGRAY , Color.GRAY ,Color.WHITE},null, Shader.TileMode.REPEAT);

        //画阴影
        Paint paint3 = new Paint();
        paint3.setShader(shader);
        canvas.drawCircle(mLayoutSize/2, mLayoutSize/2, mLayoutSize/2 * 0.99f, paint3);

        int center = getWidth()/2;//获取圆的x坐标
        float radius = mLayoutSize/2 * 0.95f; //圆环的半径



        //画圆环
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        radius = mLayoutSize/2*0.925f;//圆环半径
        canvas.drawCircle(center,center,radius,paint);

        //画第一个扇形
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        float boder = mLayoutSize/2-radius;
        RectF rectF = new RectF(boder,boder,mLayoutSize-boder,mLayoutSize-boder);
        canvas.drawArc(rectF,-90,360*(now/100),true,paint);

        //画第二个内心圆，如果想让线粗一点，把0.98f改小一点
        boder = boder * 1.2f;
        rectF = new RectF(boder, boder ,
                mLayoutSize - boder,  mLayoutSize - boder);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(center, center, radius * 0.98f, paint); //画出圆

        //画小球
        paint.setColor(mColor);
        float r = radius*0.98f;
        canvas.drawCircle(r + (float)(r * Math.sin(Math.toRadians(360 * (now / 100)))) + boder,
                r + boder - (float)(r  * Math.cos(Math.toRadians(360 * (now / 100)))) ,
                radius * 0.05f , paint);

        String per = now + "%";

        //写文字，1.1f控制百分比字的Y轴位置
        paint.measureText(per);
        paint.setColor(mColor);
        paint.setTextSize(mLayoutSize/5);//控制文字大小
        canvas.drawText(per,center-paint.measureText(per)/2,center - (paint.ascent()+ paint.descent()) * 1.1f ,paint);

        //写标题
        paint.setColor(Color.GRAY);
        paint.setTextSize(mLayoutSize/10);
        canvas.drawText(title,center- paint.measureText(title)/2,center + (paint.ascent()+ paint.descent()) ,paint);

        //外部回调
        if(now < progress - 1){
            now = now + 1 ;
            postInvalidate();
        }else if(now < progress){
            now = progress;
            postInvalidate();
        }

    }
    public void setProgress(float progress){
        this.progress = progress;
    }
}