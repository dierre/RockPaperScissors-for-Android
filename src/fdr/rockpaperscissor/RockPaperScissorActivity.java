package fdr.rockpaperscissor;

import fdr.rockpaperscissor.model.Game;
import fdr.rockpaperscissor.model.Game.Move;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
			}
		};
        findViewById(R.id.rockButton).setOnClickListener(buttonListener);
        findViewById(R.id.paperButton).setOnClickListener(buttonListener);
        findViewById(R.id.scissorsButton).setOnClickListener(buttonListener);
    }
    
    public void refreshScores() {
    	findViewById(R.id.playerScore);
    	findViewById(R.id.opponentScore);
    }
    
}