package cubi.rafi.worldwar.game;

import java.util.Random;

public class Hangman {
	public static final int TOTAL_TRIES = 7; 
	
	private String word;
	private StringBuffer currentWord;
	
	private int triesLeft;
	private boolean gameWon;
	private boolean gameLost;
	private int dashes;
	private Random random = new Random();

	public Hangman(String[] wordList) {
		word = wordList[random.nextInt(wordList.length)];
		triesLeft = TOTAL_TRIES;
		gameWon = gameLost = false;
		
		currentWord = new StringBuffer(word);
		for (int idx = 0; idx < word.length(); idx++)
			currentWord.setCharAt(idx, '-');
		dashes = word.length();
	}
	
	public GameState updateCurrentWord(char ch) {
		boolean changed = false;
		for(int idx = 0; idx < currentWord.length(); idx++) {
			if(ch == word.charAt(idx) && currentWord.charAt(idx) == '-') {
				currentWord.setCharAt(idx, ch);
				dashes--;
				changed = true;
			}
		}
		if(!changed)
			triesLeft--;
		
		if(dashes == 0)
			gameWon = true;
		else if(triesLeft == 0)
			gameLost = true;
		
		return new GameState(gameWon, gameLost, currentWord.toString(), triesLeft);
	}
	
	public String getCurrentWord() {
		return currentWord.toString();
	}

	public class GameState {
		public boolean isGameWon;
		public boolean isGameLost;
		public String currentWord;
		public int triesLeft;
		
		public GameState(boolean isGameWon, boolean isGameLost,
				String currentWord, int triesLeft) {
			this.isGameWon = isGameWon;
			this.isGameLost = isGameLost;
			this.currentWord = currentWord;
			this.triesLeft = triesLeft;
		}
	}
}
