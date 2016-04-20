package alan.kai.hw5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button takePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePic = (Button) findViewById(R.id.cameraButton);
        takePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent (this, PictureActivity.class);
        this.startActivity(intent);
    }
}