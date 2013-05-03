package rouge.games.worldwar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class StartScreen2 extends Activity {
	private static long prevTouchTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen2);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("Touch", "touch event");
		long currentTime = System.currentTimeMillis();
		if(currentTime - prevTouchTime > 500) {
			Intent intent = new Intent(this, GameScreen.class);
			startActivity(intent);
		}
		prevTouchTime = currentTime;
		return true;
	}
}
