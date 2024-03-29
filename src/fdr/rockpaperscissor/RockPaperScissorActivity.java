package fdr.rockpaperscissor;

import fdr.rockpaperscissor.model.Game;
import fdr.rockpaperscissor.model.Game.GameChangeListener;
import fdr.rockpaperscissor.model.Game.Move;
import fdr.rockpaperscissor.model.Game.Result;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RockPaperScissorActivity extends Activity {

	private static final String SERIALIZE_MODEL = "fdr.rockpaperscissor.model.Game";

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		model = (Game) savedInstanceState.get(SERIALIZE_MODEL);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(SERIALIZE_MODEL, model);
	}

	Game model;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		model = new Game();
		model.setGameChangeListener(new GameChangeListener() {
			@Override
			public void onGameChange(Game game) {
				refreshWidgetContent();
			}
		});

		setContentView(R.layout.main);
		
		OnClickListener buttonListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button self = (Button) v;
				model.setPlayerAMove(Move.valueOf((String) self.getTag()));
			}
		};
		findViewById(R.id.rockButton).setOnClickListener(buttonListener);
		findViewById(R.id.paperButton).setOnClickListener(buttonListener);
		findViewById(R.id.scissorsButton).setOnClickListener(buttonListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean superReturnValue = super.onCreateOptionsMenu(menu);

		MenuItem item = menu.add(R.string.startNewGame);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				model.resetGame();
				return false;
			}
		});

		return superReturnValue;
	}
	
	

	public void refreshWidgetContent() {
		((TextView) findViewById(R.id.playerScore)).setText(Integer
				.toString(model.getPlayerAScore()));
		((TextView) findViewById(R.id.opponentScore)).setText(Integer
				.toString(model.getPlayerBScore()));

		Animation animation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
		animation.setDuration(300);
		
		ImageView playerImageView = (ImageView) findViewById(R.id.playerMoveView);
		Move playerMove = model.getPlayerAMove();
		updateMoveImage(playerImageView, playerMove);
		playerImageView.setAnimation(animation);

		ImageView opponentImageView = (ImageView) findViewById(R.id.opponentMoveView);
		Move opponentMove = model.getPlayerBMove();
		updateMoveImage(opponentImageView, opponentMove);
		opponentImageView.setAnimation(animation);
		
		Result result = model.getResult();
		TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
		resultTextView.setAnimation(animation);
		if (result == null) {
			resultTextView.setText(null);
		} else {
			switch (result) {
			case PLAYER_A_WINS:
				resultTextView.setText(R.string.youWin);
				break;
			case PLAYER_B_WINS:
				resultTextView.setText(R.string.opponentWins);
				break;
			case TIE:
				resultTextView.setText(R.string.tieResult);
				break;
			default:
				break;
			}
		}
		
		animation.start();

	}

	private void updateMoveImage(ImageView playerImageView, Move playerMove) {
		if (playerMove == Move.ROCK)
			playerImageView.setImageResource(R.drawable.rock);
		else if (playerMove == Move.PAPER)
			playerImageView.setImageResource(R.drawable.paper);
		else if (playerMove == Move.SCISSORS)
			playerImageView.setImageResource(R.drawable.scissors);
		else if (playerMove == null)
			playerImageView.setImageDrawable(null);
	}

}