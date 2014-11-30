package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.game.GameState;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener{
	public LobbyController(Model model) {
		super(model);
	}
	
	//TODO
	public void onGameStateChange(GameState gameState){
		
	}
	
	public void clickedLeaveButton() {
		try{
			Response response = model.getConnectionProxy().leaveRoom(model.login);
			if(response.isSuccess()){
				GameListController newController = new GameListController(model);
				GameListView newView = new GameListView(model, newController);
				newController.setView(newView);
				model.diceApplication.setView(newView);
			}
			else{
				//TODO Could it happen?
			}
		}
		catch(Exception e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
		}

	}
	
	public void clickedSitDownStandUpButton() {
		if(model.sitting==true){
			try{
				Response response = model.getConnectionProxy().standUp(model.login);
				if(response.isSuccess()){
					model.sitting=false;
					view.refresh();
				}
				else{
					//TODO Could it happen?
					view.showErrorDialog("Nie uda�o si� wsta�","B��d wstawania", false);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
		else{
			try{
				Response response = model.getConnectionProxy().sitDown(model.login);
				if(response.isSuccess()){
					model.sitting=true;
					view.refresh();
				}
				else{
					view.showErrorDialog("Nie uda�o si� usi���","B��d siadania", false);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
	}
}
