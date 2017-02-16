/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import javacourse.silviodrawer.Shapes;

/**
 *
 * @author silvo
 */


public class LineSegment extends Shapes{

    	private Polygon line = new Polygon();
	private Point first = new Point();
	private Point second = new Point();
        
        private Anchor start, end;
        
public LineSegment(Point x, Point y, Color color, Group root, LinkedList<Shapes> AllShapes) {
	getFirst().setLocation(x);
	getSecond().setLocation(y);
	List<Double> values = new ArrayList<Double>();
	values.add((double) x.x);
	values.add((double) x.y);
	values.add((double) y.x);
	values.add((double) y.y);
        // init first point for a short initial line (x=y)
        line.getPoints().addAll(values); 
        System.out.println("LineSegment() line: "+line);

        this.setType("line");
        setShape(line);

	setEverything(color, color, root, AllShapes);
}

    public Point getFirst() {
        return first;
    }
        
    public void setFirst(Point first) {
        this.first = first;
    }

    public Point getSecond() {
	return second;
    }

    public void setSecond(Point second) {
        this.second = second;
    }

    public Polygon getLine() {
        return line;
    }

    public void setLine(Polygon line) {
        this.line = line;
    }

    @Override
    public void dragIt(Point x) {
        getLine().getPoints().set(2, (double) x.x);
        getLine().getPoints().set(3, (double) x.y);
        setCorners();
    }
        
//        @Override
    public void moveWithAnchors(Anchor anchor, double endX, double endY, Group root) {
	if (start.equals(anchor)) {
            getLine().getPoints().set(0, endX);
            getLine().getPoints().set(1, endY);
	} else if (end.equals(anchor)) {
		getLine().getPoints().set(2, endX);
		getLine().getPoints().set(3, endY);
		}
        
	setCorners();
	updateAnchors(root);
    }

//	@Override
    public void createControlAnchorsFor(Group root) { //draw selectors
	setShapeAnchors(new Anchor[2]);
        
	getShapeAnchors()[0] = start = new Anchor(getPoints()[0], getPoints()[1], this, root);
	getShapeAnchors()[1] = end = new Anchor(getPoints()[2], getPoints()[3], this, root);

	root.getChildren().addAll(start.circle, end.circle);
//        System.out.println("LineSegment() createControlAnchorFor() shows selectors");
//        System.out.println("Anchors: "+getShapeAnchors().toString());
    }

//	@Override
    public void setCorners() {
	setPoints(new double[4]);
	// start
	getPoints()[0] = getLine().getPoints().get(0);
	getPoints()[1] = getLine().getPoints().get(1);
	// end
	getPoints()[2] = getLine().getPoints().get(2);
	getPoints()[3] = getLine().getPoints().get(3);
	}

	@Override
    public void updateAnchors(Group root) {
        start.move(getPoints()[0], getPoints()[1]);
	end.move(getPoints()[2], getPoints()[3]);
    }
    
    @Override
    public void MoveParameters() {
	for (int i = 0; i < 4; i++) {
            line.getPoints().set(i, getPoints()[i]);
	}
    }
}
