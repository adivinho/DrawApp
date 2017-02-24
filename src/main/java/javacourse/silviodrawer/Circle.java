/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.util.LinkedList;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *
 * @author silvo
 */
public class Circle extends Oval{
    
    public Circle(Point center, Double radius1, Double radius2, Color color, Color border, Group Root, LinkedList<Shapes> AllShapes) {
        super(center, radius1, radius2, color, border, Root, AllShapes);
        this.setType("circle");
    }
    
    @Override
    public void dragIt(Point x) {

	end = x;
             
	ellipse.setRadiusX(Math.abs(end.getX() - start.getX())/2);
	ellipse.setRadiusY(Math.abs(end.getX() - start.getX())/2);

	ellipse.setCenterX(start.getX() + (end.getX() - start.getX())/2);
	ellipse.setCenterY(start.getY() + (end.getY() - start.getY())/2);

	setCorners();
    }
    
        @Override
	public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {
            double startX = anchor.circle.getCenterX();
            double startY = anchor.circle.getCenterY();
            /// upper left
            if (anchor.equals(top)) {
        	ellipse.setRadiusY(Math.max(ellipse.getRadiusY() + (startY - endY)/2, 0.0));
                ellipse.setRadiusX(Math.max(ellipse.getRadiusX() + (startY - endY)/2, 0.0));
            } else if (anchor.equals(bottom)) {
		ellipse.setRadiusY(Math.max(ellipse.getRadiusY() - (startY - endY)/2, 0.0));
                ellipse.setRadiusX(Math.max(ellipse.getRadiusX() - (startY - endY)/2, 0.0));
            } else if (anchor.equals(left)) {
		ellipse.setRadiusX(Math.max(ellipse.getRadiusX() + (startX - endX)/2, 0.0));
    		ellipse.setRadiusY(Math.max(ellipse.getRadiusY() + (startX - endX)/2, 0.0));
            } else if (anchor.equals(right)) {
		ellipse.setRadiusX(Math.max(ellipse.getRadiusX() - (startX - endX)/2, 0.0));
                ellipse.setRadiusY(Math.max(ellipse.getRadiusY() - (startX - endX)/2, 0.0));
            }
            setCorners();
            updateAnchors(root);
	}
}
