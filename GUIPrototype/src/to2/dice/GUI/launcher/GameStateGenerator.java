package to2.dice.GUI.launcher;

import java.util.Random;

import to2.dice.game.Dice;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.server.ServerMessageListener;

public class GameStateGenerator implements Runnable {
	public boolean generate;
	public boolean isStarted;
	public GameState currentGameState;
	public GameSettings gameSettings;
	public ServerMessageListener serverMessageListener;
	private Random r = new Random();
	
	public GameStateGenerator() {
		generate = false;
		isStarted = false;
		currentGameState = new GameState();
	}

	@Override
	public void run() {
		int i;
		while(true) {
			if (generate) {
				if (isStarted) {
					for (int a = 0; a < currentGameState.getPlayersNumber(); a++) {
						if (currentGameState.getCurrentPlayer().equals(currentGameState.getPlayers().get(a))) {
							if (a+1 == currentGameState.getPlayersNumber()) {
								currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(0));
							} else {
								currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(a+1));
							}
							break;
						}
					}
				} else if (currentGameState.getPlayers().size() == gameSettings.getMaxPlayers()) {
					isStarted = true;
					currentGameState.setGameStarted(true);
					for (Player p: currentGameState.getPlayers()) {
						p.setDice(new Dice(new int[]{r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1}));
					}
					currentGameState.setCurrentRound(1);
					currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(0));
				} else {
					i = r.nextInt(gameSettings.getMaxPlayers());
					if (r.nextBoolean()) {
						currentGameState.addPlayer(new Player("Kot" + i, (i%2)==0, 5));
					} else {
						currentGameState.removePlayerWithName("Kot" + i);
					}
				}
				serverMessageListener.onGameStateChange(currentGameState);
				System.out.println("Dzialam");
			}
			try {
				if (isStarted) {
					Thread.sleep(5000);
				} else {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
