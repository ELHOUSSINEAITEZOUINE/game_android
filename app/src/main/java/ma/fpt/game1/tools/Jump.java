package ma.fpt.game1.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ma.fpt.game1.R;

public class Jump {
    public boolean isGoingUp = false;
    int x,y, width, height, wingCounter = 0;
    Bitmap j1, j2;

    Jump(int screenY, Resources res) {
        j1 = BitmapFactory.decodeResource(res, R.drawable.jump1);
        j2 = BitmapFactory.decodeResource(res, R.drawable.jump2);

        width = j1.getWidth();
        height = j1.getHeight();

        j1 = Bitmap.createScaledBitmap(j1, width, height, false);
        j2 = Bitmap.createScaledBitmap(j2, width, height, false);

        y = screenY;
        x = 30;
    }

    Bitmap getJump(){
        if(wingCounter == 0){
            wingCounter++;
            return j1;
        }else {
            wingCounter--;
            return j2;
        }
    }
}
