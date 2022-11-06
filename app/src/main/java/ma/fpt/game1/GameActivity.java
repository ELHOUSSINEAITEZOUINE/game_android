package ma.fpt.game1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

import ma.fpt.game1.tools.GameView;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();

    }

    private void init() {

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(getApplicationContext(), size.x, size.y);

        getWindow().setContentView(gameView);
        //Toast.makeText(getApplicationContext(), "x = "+size.x+" y="+size.y, Toast.LENGTH_SHORT).show();
        //setContentView(gameView);

    }


    public void gameOver() {
        Log.e("CALLBACK", "IN GAME Activity");
        getWindow().setContentView(R.layout.activity_game);
        //setContentView(R.layout.activity_game);
        findViewById(R.id.replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });
        findViewById(R.id.mainmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}