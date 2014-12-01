package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.GameView;
import to2.dice.GUI.views.LobbyView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameInfo;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class GameListController extends Controller {

	public GameListController(Model model) {
		super(model);
	}

	public void refreshGameList(){
		try{
			model.roomList = model.getConnectionProxy().getRoomList();
			view.refresh();
		}
		catch(Exception e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			
		}
	}
	
	//TODO Needs to be checked
	public void clickedCreateGameButton(){
		CreateGameController newController = new CreateGameController(model);
		CreateGameView newView = new CreateGameView(model, newController);
		newController.setView(newView);
		model.diceApplication.setView(newView);
	}
	
	//TODO
	public void clickedJoinGameButton(){
		GameListView glv = (GameListView) view;
		GameInfo selectedGame = glv.getSelectedGame();
		if(selectedGame!=null){
			model.gameSettings = selectedGame.getSettings();
			Controller newController;
			View newView;
			if (selectedGame.isGameStarted()) {
				GameAnimController gameAnimController = new GameAnimController(model);
				GameAnimation gameAnimation = new GameAnimation(model, gameAnimController);
				gameAnimController.setGameAnimation(gameAnimation);
				newController = new GameController(model, gameAnimController);
				newView = new GameView(model, (GameController) newController, gameAnimation);
				newController.setView(newView);
			} else {
				newController = new LobbyController(model);
				newView = new LobbyView(model, (LobbyController) newController);
				newController.setView(newView);
			}
			model.serverMessageContainer.setServerMessageListener((ServerMessageListener) newController);
			try{
				Response response = model.getConnectionProxy().joinRoom(model.gameSettings.getName(), model.login);
				if(response.isSuccess()){
					model.diceApplication.setView(newView);
				}
				else{
					model.serverMessageContainer.removeServerMessageListener();
					view.showErrorDialog("Nie uda�o si� do��czy� do gry","B��d do��czania",false);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
	}
}
