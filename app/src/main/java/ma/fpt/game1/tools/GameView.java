package ma.fpt.game1.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.lang.reflect.Method;
import java.util.function.Function;

import ma.fpt.game1.GameActivity;
import ma.fpt.game1.R;

public class GameView extends SurfaceView implements Runnable {

    Context context;
    Canvas canvas;
    private Thread thread;
    private Boolean isPlaying;
    private int screenX, screenY;
    private Background background1, background2;
    Paint paint;
    Jump jump;
    Obstacle obs1;
    Obstacle obs2;
    int cnt;
    Rect rectPlayer, rectObs1, rectObs2;

    int speed = 30;

    boolean gameOver = false;

    public GameView(Context ctx, int screenX, int screenY) {
        super(ctx);
        context = ctx;
        this.screenX = screenX;
        this.screenY = screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        jump = new Jump(screenY, getResources());

        obs1 = new Obstacle(screenX/2, screenY, getResources(), R.drawable.obs1);
        obs2 = new Obstacle(screenX/2+780, screenY, getResources(), R.drawable.obs2);

        obs1.y -= obs1.height;
        obs2.y -= obs2.height;

        background2.x = screenX;

        rectObs1 = new Rect(
                obs1.x,
                obs1.y,
                obs1.x + obs1.width,
                obs1.y + obs1.height
        );

        rectObs2 = new Rect(
                obs2.x,
                obs2.y,
                obs2.x + obs2.width,
                obs2.y + obs2.height
        );

        rectPlayer = new Rect(
                jump.x,
                jump.y,
                jump.x+jump.width,
                jump.y+jump.height
        );

        paint = new Paint();
    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }
    public void update(){
        background1.x -= speed;
        background2.x -= speed;

        obs1.x -= speed;
        obs2.x -= speed;


        if(background1.x + background1.background.getWidth() <= 0){
            background1.x = screenX;
            obs1.x = screenX-150;
            obs2.x = screenX-700;
        }

        if(background2.x + background2.background.getWidth() <= 0){
            background2.x = screenX;
            obs1.x = screenX-150;
            obs2.x = screenX-700;
        }



        if(jump.isGoingUp){
            if(jump.y>=(screenY)/2-80){
                jump.y -=60;
            }
            cnt++;
            if(cnt==12)jump.isGoingUp=false;
        }
        else{
            jump.y +=50;
            cnt =0;
        }

        if(jump.y<0)
            jump.y=0;

        if(jump.y>=screenY - jump.height)
            jump.y=screenY - jump.height;


    }

    public void draw(){

        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);



            canvas.drawBitmap(obs1.obstacle , obs1.x, obs1.y, paint);
            rectObs1 = new Rect(
                    obs1.x,
                    obs1.y,
                        obs1.x + obs1.width,
                    obs1.y + obs1.height
            );

            canvas.drawBitmap(obs2.obstacle , obs2.x, obs2.y, paint);
            rectObs2 = new Rect(
                    obs2.x,
                    obs2.y,
                    obs2.x + obs2.width,
                    obs2.y + obs2.height
            );

            canvas.drawBitmap(jump.getJump() , jump.x, jump.y, paint);
            rectPlayer = new Rect(
                    jump.x,
                    jump.y,
                    jump.x+jump.width,
                    jump.y+jump.height
            );



            //paint.setColor(Color.RED);
            //canvas.drawRect(rectObs1, paint);

           // paint.setColor(Color.GREEN);
            //canvas.drawRect(rectObs2, paint);

            //paint.setColor(Color.BLUE);
            //canvas.drawRect(rectPlayer, paint);

            if(rectPlayer.intersect(rectObs1) || rectPlayer.intersect(rectObs2)){
                gameOver = true;
                if(rectPlayer.intersect(rectObs1)){
                    Log.e("COLLISION", "player touch obs 1");
                }
                if(rectPlayer.intersect(rectObs2)){
                    Log.e("COLLISION", "player touch obs 2");
                }

                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(150);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(10);
                canvas.drawText("Collision", screenX / 2 - 150, 200, paint);
            }



            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void sleep(){
        try{
            Thread.sleep(150);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try{
            isPlaying = false;
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void gameOver() {
        isPlaying = true;

        /*try {
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(150);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
            canvas.drawText("Collision", screenX / 2 - 150, 200, paint);
            //GameActivity gameActivity = (GameActivity)((Activity)  context);
            Looper.prepare();
            GameActivity gameActivity = new GameActivity();
            gameActivity.gameOver();
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }*/


    }
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if( jump.y== screenY - jump.height ){
                    jump.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                jump.isGoingUp = false;
                break;
        }
        return true;
    }
}
