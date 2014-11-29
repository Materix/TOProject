package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.View;

public abstract class Controller {
	protected Model model;
	protected View view;
	
	public Controller(Model model,View view){
		this.model=model;
		this.view=view;
	}
}
