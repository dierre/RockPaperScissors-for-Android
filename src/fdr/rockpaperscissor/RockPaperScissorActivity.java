package fdr.rockpaperscissor;

import fdr.rockpaperscissor.model.Game;
import fdr.rockpaperscissor.model.Game.Move;
import fdr.rockpaperscissor.model.Game.Result;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RockPaperScissorActivity extends Activity {
	
	Game model;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        model = new Game();
        
        setContentView(R.layout.main);
        
        OnClickListener buttonListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Button self = (Button) v;
				model.setPlayerAMove(Move.valueOf((String) self.getTag()));
				
				refreshWidgetContent();
			}
		};
        findViewById(R.id.rockButton).setOnClickListener(buttonListener);
        findViewById(R.id.paperButton).setOnClickListener(buttonListener);
        findViewById(R.id.scissorsButton).setOnClickListener(buttonListener);
    }
    
    public void refreshWidgetContent() {
    	((TextView) findViewById(R.id.playerScore)).setText(Integer.toString(model.getPlayerAScore()));
    	((TextView) findViewById(R.id.opponentScore)).setText(Integer.toString(model.getPlayerBScore()));
    	
    	ImageView playerImageView = (ImageView) findViewById(R.id.playerMoveView);
    	Move playerMove = model.getPlayerAMove();
    	updateMoveImage(playerImageView, playerMove);

    	ImageView opponentImageView = (ImageView) findViewById(R.id.opponentMoveView);
    	Move opponentMove = model.getPlayerBMove();
    	updateMoveImage(opponentImageView, opponentMove);
    	
    	Result result = model.getResult();
    	TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
    	switch (result) {
		case PLAYER_A_WINS:
			resultTextView.setText("You win!");
			break;
		case PLAYER_B_WINS:
			resultTextView.setText("Your opponent wins!");
			break;
		case TIE:
			resultTextView.setText("It's a tie!");
			break;
		default:
			break;
		}
    	
    }

	private void updateMoveImage(ImageView playerImageView, Move playerMove) {
		if (playerMove == Move.ROCK)
			playerImageView.setImageResource(R.drawable.rock);
		else if (playerMove == Move.PAPER)
			playerImageView.setImageResource(R.drawable.paper);
		else if (playerMove == Move.SCISSORS)
			playerImageView.setImageResource(R.drawable.scissors);
	}
    
}