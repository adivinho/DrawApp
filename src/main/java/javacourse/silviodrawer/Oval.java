/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author silvo
 */
public class Oval extends ClosedShape { 
    	protected Ellipse ellipse = new Ellipse();
	protected Point start, end;
	// start corner and end corner
	protected Point Center;
	protected Double rad1, rad2;
	protected Anchor top, bottom, left, right;
	
	String type = "";
        
        public Oval(Point center, Double radius1, Double radius2, Color color, Color border, Group Root, LinkedList<Shapes> AllShapes,
			Stack<Action> undoStack) {
		Center = center;
		rad1 = radius1;
		rad2 = radius2;
		ellipse.setCenterX(center.getX());
		ellipse.setCenterY(center.getY());
		ellipse.setRadiusX(radius1);
		ellipse.setRadiusY(radius2);
		start = end = center;

		setShape(ellipse);
                
                setEverything(color, border,  Root, AllShapes, undoStack);
        }
        
    	public  Double getCenterX() {
		return ellipse.getCenterX();
	}
	public  Double getCenterY() {
		return ellipse.getCenterY();
	}
	public  Double getRadiusX() {
		return ellipse.getRadiusX();
	}
	public  Double getRadiusY() {
		return ellipse.getRadiusY();
	}
}
