package com.william.reservationsystem.ResetPassword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class IdentifyingCode {
    //    private static final char[] CHARS = {
//            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
//    };
//
//    private static IdentifyingCode identifyingCode;
//    private int mPaddingLeft, mPaddingTop;
//    private StringBuilder mBuilder = new StringBuilder();
//    private Random mRandom = new Random();
//
//    //Default Settings
//    private static final int DEFAULT_CODE_LENGTH = 4;//验证码的长度  这里是6位
//    private static final int DEFAULT_FONT_SIZE = 15;//字体大小
//    private static final int DEFAULT_LINE_NUMBER = 3;//多少条干扰线
//    private static final int BASE_PADDING_LEFT = 10; //左边距
//    private static final int RANGE_PADDING_LEFT = 30;//左边距范围值
//    private static final int BASE_PADDING_TOP = 10;//上边距
//    private static final int RANGE_PADDING_TOP = 15;//上边距范围值
//    private static final int DEFAULT_WIDTH = 150;//默认宽度.图片的总宽
//    private static final int DEFAULT_HEIGHT = 50;//默认高度.图片的总高
//    private static final int DEFAULT_COLOR = 0xDF;//默认背景颜色值
//
//    private String code;
//
//    public static IdentifyingCode getInstance() {
//        if(identifyingCode == null) {
//            identifyingCode = new IdentifyingCode();
//        }
//        return identifyingCode;
//    }
//
//    //生成验证码图片
//    public Bitmap createBitmap() {
//        mPaddingLeft = 0; //每次生成验证码图片时初始化
//        mPaddingTop = 0;
//
//        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//
//        code = createCode();
//
//        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
//        Paint paint = new Paint();
//        paint.setTextSize(DEFAULT_FONT_SIZE);
//
//        for (int i = 0; i < code.length(); i++) {
//            randomTextStyle(paint);
//            randomPadding();
//            canvas.drawText(code.charAt(i) + "" , mPaddingLeft, mPaddingTop, paint);
//        }
//
//        //干扰线
//        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
//            drawLine(canvas, paint);
//        }
//
//        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
//        canvas.restore();
//        return bitmap;
//    }
//    /**
//     * 得到图片中的验证码字符串
//     * @return
//     */
//    public String getCode() {
//        return code;
//    }
//
//    //生成验证码
//    public String createCode() {
//        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容
//
//        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
//            mBuilder.append(CHARS[mRandom.nextInt(CHARS.length)]);
//        }
//
//        return mBuilder.toString();
//    }
//
//    //生成干扰线
//    private void drawLine(Canvas canvas, Paint paint) {
//        int color = randomColor();
//        int startX = mRandom.nextInt(DEFAULT_WIDTH);
//        int startY = mRandom.nextInt(DEFAULT_HEIGHT);
//        int stopX = mRandom.nextInt(DEFAULT_WIDTH);
//        int stopY = mRandom.nextInt(DEFAULT_HEIGHT);
//        paint.setStrokeWidth(1);
//        paint.setColor(color);
//        canvas.drawLine(startX, startY, stopX, stopY, paint);
//    }
//
//    //随机颜色
//    private int randomColor() {
//        mBuilder.delete(0, mBuilder.length()); //使用之前首先清空内容
//
//        String haxString;
//        for (int i = 0; i < 3; i++) {
//            haxString = Integer.toHexString(mRandom.nextInt(0xFF));
//            if (haxString.length() == 1) {
//                haxString = "0" + haxString;
//            }
//
//            mBuilder.append(haxString);
//        }
//
//        return Color.parseColor("#" + mBuilder.toString());
//    }
//
//    //随机文本样式
//    private void randomTextStyle(Paint paint) {
//        int color = randomColor();
//        paint.setColor(color);
//        paint.setFakeBoldText(mRandom.nextBoolean());  //true为粗体，false为非粗体
//        float skewX = mRandom.nextInt(11) / 10;
//        skewX = mRandom.nextBoolean() ? skewX : -skewX;
//        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
////        paint.setUnderlineText(true); //true为下划线，false为非下划线
////        paint.setStrikeThruText(true); //true为删除线，false为非删除线
//    }
//
//    //随机间距
//    private void randomPadding() {
//        mPaddingLeft += BASE_PADDING_LEFT + mRandom.nextInt(RANGE_PADDING_LEFT);
//        mPaddingTop = BASE_PADDING_TOP + mRandom.nextInt(RANGE_PADDING_TOP);
//    }
//这是一个单例模式
    private static IdentifyingCode IdentifyingCode;


    //随机数数组，验证码上的数字和字母
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public static IdentifyingCode getInstance() {
        if (IdentifyingCode == null) {
            IdentifyingCode = new IdentifyingCode();
        }
        return IdentifyingCode;
    }

    //验证码个数
    private static final int CODE_LENGTH = 4;
    //字体大小
    private static final int FONT_SIZE = 50;
    //线条数
    private static final int LINE_NUMBER = 5;
    //padding，其中base的意思是初始值，而range是变化范围。数值根据自己想要的大小来设置
    private static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 100, BASE_PADDING_TOP = 75, RANGE_PADDING_TOP = 50;
    //验证码默认宽高
    private static final int DEFAULT_WIDTH = 400, DEFAULT_HEIGHT = 150;

    //画布的长宽
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

    //字体的随机位置
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;
    //验证码个数，线条数，字体大小
    private int codeLength = CODE_LENGTH, line_number = LINE_NUMBER, font_size = FONT_SIZE;

    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    //验证码图片(生成一个用位图)
    public Bitmap createBitmap() {
        padding_left = 0;
        padding_top = 0;
        //创建指定格式，大小的位图//Config.ARGB_8888是一种色彩的存储方法
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

        code = createCode();
        //将画布填充为白色
        c.drawColor(Color.WHITE);
        //新建一个画笔
        Paint paint = new Paint();
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);
        //在画布上画上验证码
//        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            //这里的padding_left,padding_top是文字的基线
            c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }
        //画干扰线
        for (int i = 0; i < line_number; i++) {
            drawLine(c, paint);
        }
        //保存一下画布
        c.save();
        c.restore();
        return bp;
    }

    //生成验证码
    private String createCode() {
        StringBuilder sb = new StringBuilder();
        //利用random生成随机下标
        for (int i = 0; i < codeLength; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    //画线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    //随机文字样式，颜色，文字粗细与倾斜度
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());//true为粗体，false为非粗体
        double skew = random.nextInt(11) / 10;
        //随机ture或者false来生成正数或者负数，来表示文字的倾斜度，负数右倾，正数左倾
        skew = random.nextBoolean() ? skew : -skew;
        //   paint.setUnderlineText(true);//下划线
        // paint.setStrikeThruText(true);//删除线
    }

    //生成随机颜色，利用RGB
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    //验证码位置随机
    private void randomPadding() {

        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }

    public String getCode() {
        return code;
    }
}
