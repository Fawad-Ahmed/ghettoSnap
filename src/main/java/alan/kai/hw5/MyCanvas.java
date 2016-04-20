package alan.kai.hw5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by weiak_000 on 4/13/2016.
 */
public class MyCanvas extends View {
    HashMap<Integer, PathColor> activePaths;
    Paint redPaint;
    Paint bluePaint;
    Paint greenPaint;

    String colorID = "RED";
    int count = 0;
    int pathid;
    PathColor pathColor;
    ArrayList<Integer> elements = new ArrayList<>();
    ArrayList<Integer> elements2 = new ArrayList<>();

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        activePaths = new HashMap<>();

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(50);

        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.STROKE);
        bluePaint.setStrokeWidth(50);

        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(50);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.morty2);
        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.gir);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathColor path : activePaths.values()) {
            if (path.color.contains("RED"))
                canvas.drawPath(path.path, redPaint);
            if(path.color.contains("BLUE"))
                canvas.drawPath(path.path, bluePaint);
            if(path.color.contains("GREEN"))
                canvas.drawPath(path.path, greenPaint);
        }
        for(int i = 0; i < elements.size(); i+=2) {
            canvas.drawBitmap(image, elements.get(i), elements.get(i+1), null);
        }
        for(int i = 0; i < elements2.size(); i+=2) {
            canvas.drawBitmap(image2, elements2.get(i), elements2.get(i+1), null);
        }

    }
    public void setColor(int colorid) {
        if(colorid == 1) {
            colorID = "RED";
        }
        if(colorid == 2) {
            colorID = "BLUE";
        }
        if(colorid == 3) {
            colorID = "GREEN";
        }
    }

    public void addPath(int id, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        PathColor pathColor = new PathColor();
        pathColor.path = path;
        pathColor.color = colorID;
        activePaths.put(id + count, pathColor);
        this.pathid = id + count;
        path.close();
        invalidate();
    }
    public void updatePath(int id, int x, int y) {

        PathColor path = activePaths.get(id + count);
        if(path != null) {
            if (path.path != null) {
                path.path.lineTo(x, y);
            }
        }

        invalidate();
    }

    public void removePath(int id) {
        if (activePaths.containsKey(id)) {
            activePaths.remove(id);
        }
        invalidate();
    }
    Bitmap image;
    Bitmap image2;
    public void updateBGcolorOnDoubleTap(float x, float y) {
        elements.add((int)x);
        elements.add((int)y);
        invalidate();
    }
    public void updateBGcolorOnLongPress(float x, float y) {
        elements2.add((int) x - 90);
        elements2.add((int) y - 90);
        invalidate();
    }
    public void clear() {
        activePaths.clear();
        elements.clear();
        elements2.clear();
        invalidate();
    }
    public void undo() {
        //activePaths.remove(activePaths.get(pathid + count - 1));
        activePaths.remove(pathid);
        pathid--;
        invalidate();
    }
    public void save() {
        count++;
    }
}


