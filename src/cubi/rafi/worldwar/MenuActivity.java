package cubi.rafi.worldwar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button newGameButton;
	private Button continueGameButton;
	private Button quitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		newGameButton = (Button) findViewById(R.id.new_game);
		continueGameButton = (Button) findViewById(R.id.continue_game);
		quitButton = (Button) findViewById(R.id.quit);

		newGameButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						GameScreen.class);
				startActivity(intent);
				resetState();
			}
		});

		continueGameButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(), GameScreen.class);
				startActivity(intent);
			}
		});
		
		quitButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	protected void resetState() {
		SharedPreferences preferences
			= PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(GameScreen.CURRENT_US_STATE, 0);
		editor.commit();
	}
}
