package rouge.games.worldwar;

import java.util.Timer;
import java.util.TimerTask;

import rouge.games.worldwar.adapter.ButtonAdapter;
import rouge.games.worldwar.game.Hangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GameScreen extends Activity {
	
	static final String CURRENT_US_STATE = "us_state";
	private static final int ABORT_CODE_TOTAL = 5;
	private static final int MAX_TIME = 100;
	
	private ButtonAdapter adapter;
	private Timer timer;
	
	private int taskNo = 0;
	
	private GridView gridView;
	private TextView hangmanText;
	private TextView triesField;
	private TextView stateNameField;
	private TextView abortCodeField;
	private TextView timeField;
	
	private String[] states;
	private int currentUsState;
	private int abortCodeSolved = 0;
	private int timeLeft; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        adapter = new ButtonAdapter(this);
        gridView = (GridView) findViewById(R.id.buttons);
        gridView.setAdapter(adapter);
        
        hangmanText = (TextView) findViewById(R.id.hangman);
        triesField = (TextView) findViewById(R.id.tries);
        stateNameField = (TextView) findViewById(R.id.state);
        abortCodeField = (TextView) findViewById(R.id.abort_code);
        timeField = (TextView) findViewById(R.id.time);
        
        states = getResources().getStringArray(R.array.states);
        Log.d("Activity", "game screen");
        
        loadData();
        startGame();
    }
    
    private void startGame() {
    	String word = adapter.startGame();
    	hangmanText.setText(word);
    	triesField.setText(Hangman.TOTAL_TRIES + " of " + Hangman.TOTAL_TRIES);
    	abortCodeField.setText((abortCodeSolved + 1) + " of " + ABORT_CODE_TOTAL);
    	stateNameField.setText(states[currentUsState]);

        timer = new Timer();
        taskNo++;
    	timer.scheduleAtFixedRate(new Task(), 0, 1000);
    	timeLeft = MAX_TIME;
    }
    
    public void updateView(Hangman.GameState gameState) {
    	hangmanText.setText(gameState.currentWord);
    	triesField.setText(gameState.triesLeft + " of " + Hangman.TOTAL_TRIES);
    	if(gameState.isGameLost)
    		notifyGameLost();
    	else if(gameState.isGameWon)
    		notifyGameWon();
    }
    
    private void notifyLevelCompleted() {
    	String message = "We have " + (states.length - currentUsState - 1)
    			+ " more states to save from terrorists";
    	AlertDialog.Builder dialog =
    			new AlertDialog.Builder(this);
    	dialog.setTitle("Congratulations!")
    		.setMessage(message)
    		.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					currentUsState++;
					if(currentUsState == states.length) 
						currentUsState = 0;
					writeData();
			    	startGame();
				}
			})
			.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
			    	startGame();				}
			})
    		.show();
    	
    	abortCodeSolved = 0;
    }
    
    public void notifyGameWon() {
    	timer.cancel();
    	abortCodeSolved++;
    	if(abortCodeSolved == ABORT_CODE_TOTAL)
    		notifyLevelCompleted();
    	else {
    		Toast.makeText(this, "Well Done!", Toast.LENGTH_SHORT).show();
    		startGame();
    	}
    }
    
    public void notifyGameLost() {
    	timer.cancel();
    	String message = "We have lost " + states[currentUsState] + 
    			". Lets try again!";
    	AlertDialog.Builder dialog =
    			new AlertDialog.Builder(this);
    	dialog.setTitle("Sorry!")
    		.setMessage(message)
    		.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
			    	startGame();
				}
			})
			.setNegativeButton("Restart Mission", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					currentUsState = 0;
					writeData();
			    	startGame();
				}
			})
    		.show();
    	
    	abortCodeSolved = 0;
    }
    
    private void writeData() {
    	SharedPreferences preferences
    		= PreferenceManager.getDefaultSharedPreferences(this);
    	SharedPreferences.Editor editor = preferences.edit();
    	editor.putInt(CURRENT_US_STATE, currentUsState);
    	editor.commit();
    }
    
    private void loadData() {
    	SharedPreferences preferences
			= PreferenceManager.getDefaultSharedPreferences(this);
    	currentUsState = preferences.getInt(CURRENT_US_STATE, 0);
    }
    
    private class Task extends TimerTask {
    	@Override
    	public void run() {
    		runOnUiThread(new Runnable() {
				
				public void run() {
		    		if(timeLeft < 10)
		    			timeField.setTextColor(Color.RED);
		    		else
		    			timeField.setTextColor(Color.WHITE);
		    		
		    		if(timeLeft == 0)
		    			notifyGameLost();
		    		else
		    			timeField.setText(timeLeft + " s");
				}
			});
    		timeLeft--;
    		//Log.d("Timer", taskNo + "");
    	}
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	timer.cancel();
    }
}