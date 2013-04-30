package cubi.rafi.worldwar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import cubi.rafi.worldwar.GameScreen;
import cubi.rafi.worldwar.R;
import cubi.rafi.worldwar.game.Hangman;

public class ButtonAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private GameScreen gameScreen;
	private Hangman hangman;
	private static final String[] buttonValues = 
		{
			"",  "A", "B", "C", "D", "E",  "", "F", "G",
			"H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z",
		};
	private Button[] holder = new Button[getCount()];
	
	public ButtonAdapter(GameScreen gameScreen) {
		inflater = (LayoutInflater) gameScreen
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.gameScreen = gameScreen;
	}
	
	public String startGame() {
		String words[] = gameScreen.getResources().getStringArray(R.array.words);
		hangman = new Hangman(words);
		for(int idx = 0; idx < holder.length; idx++) {
			if(holder[idx] != null) {
				holder[idx].setEnabled(true);
				// Log.d("Button", idx + ":" + holder[idx].getText());
			}
		}
		return hangman.getCurrentWord();
	}

	public View getView(final int position, View convertView, ViewGroup arg2) {
		View view = convertView;
		if(view == null) {
			final Button button = new Button(gameScreen);
			button.setText(buttonValues[position]);
			
			view = button;
			holder[position] = button;
			view.setTag(holder[position]);
		}
		else
			holder[position] = (Button) view.getTag();
		
		holder[position].setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(buttonValues[position].length() != 0) {
					Hangman.GameState gameState = hangman.updateCurrentWord(
							buttonValues[position].charAt(0));
					holder[position].setEnabled(false);
					
					gameScreen.updateView(gameState);
					//Log.d("Button", position + ":" + holder[position].getText());
				}
			}
		});
		
		return view;
	}

	public int getCount() {
		return buttonValues.length;
	}

	public Object getItem(int position) {
		return buttonValues[position];
	}

	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return holder[position].isEnabled();
	}
}
