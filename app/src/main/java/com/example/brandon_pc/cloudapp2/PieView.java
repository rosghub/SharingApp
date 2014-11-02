package com.example.brandon_pc.cloudapp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PieView extends View {
    private static final float YBOUND = 0.7142f,
        YBOUND_MID = 0.5555f;

    private final int TEXT_SIZE = 80;

    private int width, height, midx, midy, pokeTxtOffsetPx;
    private Point p1, p2, p3;
    private Paint paint;
    private PieClickListener listener;
    private Typeface font;

    public interface PieClickListener {
        void onLeftClick();
        void onBottomClick();
        void onRightClick();
    }

    public PieView(Context context, AttributeSet attributesSet) {
        super(context, attributesSet);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        font = MainActivity.getTypeFace(context);
    }

    public void setOnPieClickListener(PieClickListener l) {
        listener = l;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);

        // pie center
        midx = width / 2;
        midy = (int)(height * YBOUND_MID);

        int yb = (int)(height * YBOUND);

        p1 = new Point(0, yb);  // left
        p2 = new Point(midx, midy); // center
        p3 = new Point(width, yb); // right

        pokeTxtOffsetPx = getResources().getDimensionPixelOffset(R.dimen.pokeTxt_offsetY);

        setMeasuredDimension(width, height);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // poke background
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(p1.x, p1.y);
        path.close();

        // REMOVE
        if (paint == null)
            paint = new Paint();

        paint.setColor(getResources().getColor(R.color.purple));
        canvas.drawPath(path, paint);

        // messages background
        Path path2 = new Path();
        path2.setFillType(Path.FillType.EVEN_ODD);
        path2.moveTo(p1.x, p1.y);
        path2.lineTo(p2.x, p2.y);
        path2.lineTo(p2.x, 0);
        path2.lineTo(0, 0);
        path2.lineTo(p1.x, p1.y);
        path.close();

        paint.setColor(getResources().getColor(R.color.blue));
        canvas.drawPath(path2, paint);

        // photos background
        Path path3 = new Path();
        path3.setFillType(Path.FillType.EVEN_ODD);
        path3.moveTo(p3.x, p3.y);
        path3.lineTo(width, 0);
        path3.lineTo(p2.x, 0);
        path3.lineTo(p2.x, p2.y);
        path3.lineTo(p3.x, p3.y);
        path3.close();

        paint.setColor(getResources().getColor(R.color.green));
        canvas.drawPath(path3, paint);

        // draw labels
        String txtPoke = "Poke";

        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_SIZE);
        //paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTypeface(font);


        int h, l, x, y;
        h = (int)(paint.descent() - paint.ascent());
        l = (int)paint.measureText(txtPoke, 0, txtPoke.length());
        x = midx - l / 2;
        y = p1.y + h + pokeTxtOffsetPx;

        canvas.drawText(txtPoke, x, y, paint);

        String txtMsg = "Messages";

        h = (int)(paint.descent() - paint.ascent());
        l = (int)paint.measureText(txtMsg, 0, txtMsg.length());
        x = midx / 2 - l / 2;
        //y = 40 + h;
        y = p1.y / 2 - h / 2;

        canvas.drawText(txtMsg, x, y, paint);

        String txtPhoto = "Photos";

        l = (int)paint.measureText(txtPhoto, 0, txtPhoto.length());
        x = (width + midx) / 2 - (l / 2);
        // y = same as messages label

        canvas.drawText(txtPhoto, x, y, paint);
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            float x = e.getX(), y = e.getY();
            float yborder; // point where poke area starts

            if (x < midx) {
                // y = -.45x + 1112
                yborder = -0.45f * x + 1112;
            } else {
                // y = .45x + 622
                yborder = 0.45f * x + 622;
            }

            if (y > yborder)
                listener.onBottomClick();
            else if (x < midx)
                listener.onLeftClick();
            else
                listener.onRightClick();
        }
        return true;
    }
}
