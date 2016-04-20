package alan.kai.hw5;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weiak_000 on 4/13/2016.
 */
public class TouchHandler implements View.OnTouchListener {
    PictureActivity pictureActivity;

    GestureDetectorCompat myGestureListener;

    public TouchHandler(PictureActivity pictureActivity) {

        this.pictureActivity = pictureActivity;
        myGestureListener = new GestureDetectorCompat(
                this.pictureActivity, new MyGestureListener());
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int maskedAction = motionEvent.getActionMasked();
        myGestureListener.onTouchEvent(motionEvent);
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int size = motionEvent.getPointerCount(), i = 0; i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    pictureActivity.addNewPath(id,
                            (int) motionEvent.getX(i),
                            (int) motionEvent.getY(i));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int size = motionEvent.getPointerCount(), i = 0; i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    pictureActivity.updatePath(id,
                            (int) motionEvent.getX(i),
                            (int) motionEvent.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                for (int size = motionEvent.getPointerCount(), i = 0; i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    //pictureActivity.removePath(id); changed
                }
                pictureActivity.saveCount();
                break;
        }
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            pictureActivity.onDoubleTap((int)e.getX(), (int)e.getY());
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            pictureActivity.onLongPress((int)e.getX(), (int)e.getY());
            super.onLongPress(e);
        }
    }

}
