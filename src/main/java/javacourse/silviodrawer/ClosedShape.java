/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.util.LinkedList;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *
 * @author silvo
 */
public abstract class ClosedShape extends Shapes {
	
	protected Color color ; 

	public void setStyle(Color color) {
		getShape().setFill(color);
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public void setEverything(Color color,Color border, Group Root, LinkedList<Shapes> AllShapes, Stack<Action> undoStack) {
		getShape().setStrokeWidth(3);
		AllShapes.add(this);
		Root.getChildren().add(getShape());
		SetActions(Root, AllShapes, undoStack);
		for (int i = 0; i < AllShapes.size() - 1; i++) {
			Shapes crnt = AllShapes.get(i);
			crnt.notSelected(Root);
		}
		addColor(border);
		setStyle(color);
	}
}
