package rouge.games.worldwar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class StartScreen1 extends Activity {
	private static long prevTouchTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen1);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		long currentTime = System.currentTimeMillis();
		if(currentTime - prevTouchTime > 500) {
			Intent intent = new Intent(this, StartScreen2.class);
			startActivity(intent);
		}
		prevTouchTime = currentTime;
		return true;
	}
}
