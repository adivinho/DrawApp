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
public class Square extends RectangleShape{
    
    public Square(Point corner, Double width, Double hight, Color color, Color border, Group root, LinkedList<Shapes> AllShapes) {
        super(corner, width, hight, color, border, root, AllShapes);
        this.setType("square");
    }
    
    @Override
    public void dragIt(Point x) {
	end = x;

	rectangle.setWidth(Math.abs((end.getX() - start.getX())));
	rectangle.setHeight(Math.abs((end.getX() - start.getX())));

	double centerX = (start.getX() + 0.5 * (end.getX() - start.getX()));
	double centerY = (start.getY() + 0.5 * (end.getY() - start.getY()));

	rectangle.setX(centerX - 0.5 * rectangle.getWidth());
	rectangle.setY(centerY - 0.5 * rectangle.getHeight());
		
	setCorners();

    }
    
    @Override
    public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {

	double startX = anchor.circle.getCenterX();
	double startY = anchor.circle.getCenterY();
		
	if (anchor.equals(upleft)) {
            rectangle.setX(endX);
            rectangle.setY(endY);
            rectangle.setWidth(Math.abs(rectangle.getWidth() + startX - endX));
            rectangle.setHeight(Math.abs(rectangle.getHeight() + startX - endX));

	} else if (anchor.equals(upright)) {
            rectangle.setY(rectangle.getY() + endY - startY);
            rectangle.setWidth(Math.max(rectangle.getWidth() + startY - endY, 0.0));
            rectangle.setHeight(Math.max(rectangle.getHeight() + startY - endY, 0.0));

	} else if (anchor.equals(downleft)) {
            rectangle.setX(rectangle.getX() + endX - startX);
            rectangle.setWidth(Math.max(rectangle.getWidth() + startX - endX, 0.0));
            rectangle.setHeight(Math.max(rectangle.getHeight() + startX - endX, 0.0));
	
        } else if (anchor.equals(downright)) {
            rectangle.setWidth(Math.abs(rectangle.getWidth() - startY + endY));
            rectangle.setHeight(Math.abs(rectangle.getHeight() - startY + endY));
	}
	setCorners();
	updateAnchors(root);
    }
    
}
