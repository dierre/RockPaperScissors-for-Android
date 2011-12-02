package fdr.rockpaperscissor.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

public class Game implements Parcelable {
	public enum Move {ROCK, PAPER, SCISSORS};
	public enum Result {PLAYER_A_WINS, PLAYER_B_WINS, TIE}

	private Move playerAMove;
	private Move playerBMove;
	private int  playerAScore;
	private int  playerBScore;
	
	public synchronized Move getPlayerAMove() {
		return playerAMove;
	}
	public synchronized void setPlayerAMove(Move playerAMove) {
		this.playerAMove = playerAMove;
		setPlayerBMove(chooseRandomMove());
		updateScore();
	}
	public synchronized Move getPlayerBMove() {
		return playerBMove;
	}
	public synchronized void setPlayerBMove(Move playerBMove) {
		this.playerBMove = playerBMove;
	}
		
	private Move chooseRandomMove() {
		Random rnd = new Random();
		Move[] moves = Move.values();
		Move randomMove = moves[rnd.nextInt(moves.length)];
		return randomMove;
	}
	
	private void updateScore() {
		Result result = getResult();
		if (result == Result.PLAYER_A_WINS) playerAScore++;
		else if (result == Result.PLAYER_B_WINS) playerBScore++;
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
	
	public int getPlayerAScore() {
		return playerAScore;
	}
	public void setPlayerAScore(int playerAScore) {
		this.playerAScore = playerAScore;
	}
	public int getPlayerBScore() {
		return playerBScore;
	}
	public void setPlayerBScore(int playerBScore) {
		this.playerBScore = playerBScore;
	}
	public void resetGame() {
		playerAScore = playerBScore = 0;
		playerAMove = playerBMove = null;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(playerAScore);
		parcel.writeInt(playerBScore);

		parcel.writeString(playerAMove.toString());
		parcel.writeString(playerBMove.toString());
	}
	
	public static final Parcelable.Creator<Game> CREATOR = new Creator<Game>() {
		@Override
		public Game createFromParcel(Parcel source) {
			Game result = new Game();
			result.playerAScore = source.readInt();
			result.playerBScore = source.readInt();

			String move;
			
			move = source.readString();
			result.playerAMove = (move == null) ? null : Move.valueOf(move); 
			
			move = source.readString();
			result.playerBMove = (move == null) ? null : Move.valueOf(move); 
			return null;
		}

		@Override
		public Game[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}
	
}