package alan.kai.hw5;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class PictureActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    private static final int PICTURE_REQUEST = 100;
    private Uri imageUri;
    MyCanvas myCanvas;
    TouchHandler touchHandler;


    Button redButton;
    Button blueButton;
    Button greenButton;
    Button doneButton;
    Button clearButton;
    Button undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        image = (ImageView) findViewById(R.id.picture);

        //Take picture
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        this.startActivityForResult(camera, PICTURE_REQUEST);

        redButton = (Button) findViewById(R.id.redButton);
        redButton.setOnClickListener(this);
        blueButton = (Button) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(this);
        greenButton = (Button) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(this);

        doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(this);
        clearButton = (Button) findViewById(R.id.clear);
        clearButton.setOnClickListener(this);
        undoButton = (Button) findViewById(R.id.undo);
        undoButton.setOnClickListener(this);

        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchHandler = new TouchHandler(this);
        myCanvas.setOnTouchListener(touchHandler);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == PICTURE_REQUEST) {
            if (result == Activity.RESULT_OK) {
                Uri selectedImage = imageUri;
                this.getContentResolver().notifyChange(selectedImage, null);
                ContentResolver cr = this.getContentResolver();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media
                            .getBitmap(cr, selectedImage);

                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    image.setImageBitmap(bitmap);

                } catch (Exception e) {
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                            .show();

                }
            } else if (result == Activity.RESULT_CANCELED) {
                //FFF
            }
        }
    }
    public void addNewPath(int id, int x, int y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, int x, int y) {
        myCanvas.updatePath(id, x, y);
    }

    public void removePath(int id) {
        myCanvas.removePath(id);
    }


    public void onDoubleTap(int x, int y) {
        myCanvas.updateBGcolorOnDoubleTap(x, y);
    }

    public void onLongPress(int x, int y) {
        myCanvas.updateBGcolorOnLongPress(x, y);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == redButton.getId()) {
            myCanvas.setColor(1);
        }
        if (v.getId() == blueButton.getId()) {
            myCanvas.setColor(2);
        }
        if (v.getId() == greenButton.getId()) {
            myCanvas.setColor(3);
        }
        if(v.getId() == doneButton.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(v.getId() == clearButton.getId()) {
            myCanvas.clear();
        }
        if(v.getId() == undoButton.getId()) {
            myCanvas.undo();
        }
    }
    public void saveCount() {
        myCanvas.save();
    }
}
