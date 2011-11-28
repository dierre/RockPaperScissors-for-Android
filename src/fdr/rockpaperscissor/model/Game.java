package fdr.rockpaperscissor.model;

import java.util.HashSet;
import java.util.Set;

import android.util.Pair;

public class Game {
	public enum Move {ROCK, PAPER, SCISSORS};
	public enum Result {PLAYER_A_WINS, PLAYER_B_WINS, TIE}

	private Move playerAMove;
	private Move playerBMove;
	
	public synchronized Move getPlayerAMove() {
		return playerAMove;
	}
	public synchronized void setPlayerAMove(Move playerAMove) {
		this.playerAMove = playerAMove;
	}
	public synchronized Move getPlayerBMove() {
		return playerBMove;
	}
	public synchronized void setPlayerBMove(Move playerBMove) {
		this.playerBMove = playerBMove;
	}
	
	public Result getResult() {
		Set<Pair<Move,Move>> firstPlayerWinsPairs = new HashSet<Pair<Move,Move>>();
		firstPlayerWinsPairs.add( new Pair<Move, Move>(Move.ROCK, Move.SCISSORS));
		firstPlayerWinsPairs.add( new Pair<Move, Move>(Move.PAPER, Move.ROCK));
		firstPlayerWinsPairs.add( new Pair<Move, Move>(Move.SCISSORS, Move.PAPER));

		if (playerAMove == null || playerBMove == null)
			return null;
		if (playerAMove == playerBMove)
			return Result.TIE;
		
		Pair<Move,Move> actualMove = new Pair<Move, Move>(playerAMove, playerBMove);
		
		if (firstPlayerWinsPairs.contains(actualMove))
			return Result.PLAYER_A_WINS;
		else
			return Result.PLAYER_B_WINS;
	}
	
}