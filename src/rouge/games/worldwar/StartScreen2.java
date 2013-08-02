package rouge.games.worldwar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartScreen2 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen2);
		
		findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), GameScreen.class);
				startActivity(intent);
			}
		});
	}
}
