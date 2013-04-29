package cubi.rafi.worldwar;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import cubi.rafi.worldwar.adapter.ButtonAdapter;
import cubi.rafi.worldwar.game.Hangman;

public class GameScreen extends Activity {
	private ButtonAdapter adapter;
	private GridView gridView;
	private TextView hangmanText;
	private TextView triesField;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        adapter = new ButtonAdapter(this);
        gridView = (GridView) findViewById(R.id.buttons);
        gridView.setAdapter(adapter);
        
        hangmanText = (TextView) findViewById(R.id.hangman);
        triesField = (TextView) findViewById(R.id.tries);
        
        startGame();
    }
    
    private void startGame() {
    	String word = adapter.startGame();
    	hangmanText.setText(word);
    	triesField.setText(Hangman.TOTAL_TRIES + " of " + Hangman.TOTAL_TRIES);
    }
    
    public void updateView(Hangman.GameState gameState) {
    	hangmanText.setText(gameState.currentWord);
    	triesField.setText(gameState.triesLeft + " of " + Hangman.TOTAL_TRIES);
    	
    	if(gameState.isGameLost)
    		notifyGameLost();
    	else if(gameState.isGameWon)
    		notifyGameWon();
    }
    
    public void notifyGameWon() {
    	AlertDialog.Builder dialog =
    			new AlertDialog.Builder(this);
    	dialog.setTitle("Congratulations!")
    		.setMessage("You have won the game!")
    		.show();
    	startGame();
    }
    
    public void notifyGameLost() {
    	AlertDialog.Builder dialog =
    			new AlertDialog.Builder(this);
    	dialog.setTitle("Sorry!")
    		.setMessage("You have lost the game!")
    		.show();
    	startGame();
    }
}