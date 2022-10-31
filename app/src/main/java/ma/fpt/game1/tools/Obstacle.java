package ma.fpt.game1.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ma.fpt.game1.R;

public class Obstacle {
   int x = 0, y = 0, width, height;
   Bitmap obstacle;

   public Obstacle(int screenX, int screenY, Resources res, int idRes) {
      obstacle = BitmapFactory.decodeResource(res, idRes);
      width = obstacle.getWidth();
      height = obstacle.getHeight();
      x = screenX;
      y = screenY;
      obstacle = Bitmap.createScaledBitmap(obstacle, width, height, false);
   }
}
