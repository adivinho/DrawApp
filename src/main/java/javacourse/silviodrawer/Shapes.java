/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.util.LinkedList;
import java.util.Stack;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

/**
 *
 * @author silvo
 */
class Shapes {
    
    private Shape shape;
    private boolean selected = true;
    private String type = "";

    String getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Object getBoarderColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    void addColor(Color c) {
	getShape().setStroke(c);
	setBoarderColor(c);
    }

    private void setBoarderColor(Color c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Shape getShape() {
	return shape;
    }
    
    public void setShape(Shape shape) {
	this.shape = shape;
    }
    
    public void setEverything(Color color, Color border, Group Root, LinkedList<Shapes> AllShapes, Stack<Action> undoStack) {
	shape.setStrokeWidth(3);
	AllShapes.add(this);
	Root.getChildren().add(this.getShape());
	SetActions(Root, AllShapes, undoStack);
	for (int i = 0; i < AllShapes.size() - 1; i++) {
		Shapes crnt = AllShapes.get(i);
		crnt.notSelected(Root);
	}
	addColor(color);
    }
    
    public void SetActions(Group root, LinkedList<Shapes> AllShapes, Stack<Action> undoStack) {
/*
		MousePressed();
		MouseDrag(root);
		MouseClick(root, AllShapes);
		MouseEntered(root);
		MouseExited(root);
		MouseReleased(root, undoStack);
*/
    }
    public void notSelected(Group root) {
	selected = false;
/*
	for (Anchor anchor : getShapeAnchors()) {
            root.getChildren().remove(anchor.circle);
*/
    }
    
    public boolean isSelected() {
	return selected;
    }
}
