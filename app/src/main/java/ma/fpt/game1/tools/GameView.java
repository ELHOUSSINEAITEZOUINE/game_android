package ma.fpt.game1.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import ma.fpt.game1.R;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private Boolean isPlaying;
    private int screenX, screenY;
    private Background background1, background2;
    Paint paint;
    Jump jump;
    Obstacle obs1;
    Obstacle obs2;
    int cnt;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        jump = new Jump(screenY, getResources());

        obs1 = new Obstacle(screenX/2, screenY, getResources(), R.drawable.obstacle1);
        obs2 = new Obstacle(screenX/2+380, screenY, getResources(), R.drawable.obstacle2);

        obs1.y -= obs1.height;
        obs2.y -= obs2.height;

        background2.x = screenX;
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
        background1.x -= 20;
        background2.x -= 20;

        obs1.x -= 20;
        obs2.x -= 20;


        if(background1.x + background1.background.getWidth() <= 0){
            background1.x = screenX;
            obs1.x = screenX-100;
            obs2.x = screenX-540;
        }

        if(background2.x + background2.background.getWidth() <= 0){
            background2.x = screenX;
            obs1.x = screenX-100;
            obs2.x = screenX-540;
        }



        if(jump.isGoingUp){
            if(jump.y>=(screenY)/2-50){
                jump.y -=50;
            }
            cnt++;
            if(cnt==8)jump.isGoingUp=false;
        }
        else{
            jump.y +=30;
            cnt =0;
        }

        if(jump.y<0)
            jump.y=0;

        if(jump.y>=screenY - jump.height)
            jump.y=screenY - jump.height;
    }

    public void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(jump.getJump() , jump.x, jump.y, paint);

            canvas.drawBitmap(obs1.obstacle , obs1.x, obs1.y, paint);

            canvas.drawBitmap(obs2.obstacle , obs2.x, obs2.y, paint);


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
