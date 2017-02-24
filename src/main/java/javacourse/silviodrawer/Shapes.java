/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.util.LinkedList;
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
public abstract class Shapes{
    
    private Shape shape;
    private boolean selected = true;
    private String type = "";
    private Anchor shapeAnchors[];
    private Color boarderColor;
    private double points[];

    public Point beforeMove = new Point();
    public Point afterMove = new Point();
    public Point now = new Point();
    public Point then = new Point();
    public Point dragDelta = new Point();
    
    public abstract void dragIt(Point x);
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public Color getMainBoarderColor() {
        return boarderColor; 
    }
    
    public Color getBoarderColor() {
	return boarderColor;
    }
    void addColor(Color c) {
	getShape().setStroke(c);
        getShape().setFill(c);
	setBoarderColor(c);
    }
    
    private void MouseClick(final Group root, final LinkedList<Shapes> AllShapes) {
	getShape().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Shapes().MouseClicked() on "+this);
                if (Shapes.this.isSelected()) {
                    Shapes.this.notSelected(root);
                }
                else {
                    Shapes.this.Selected(root, AllShapes);
                }
            }
	});
    }
        
    private void MousePressed() {
	getShape().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                then.setLocation(mouseEvent.getX(), mouseEvent.getY());
                beforeMove.setLocation(mouseEvent.getX(), mouseEvent.getY());
                System.out.println("Shapes().MousePressed() then:"+then+" beforeMove: "+beforeMove);
            }
	});
    }
    
    private void MouseDrag(final Group root) {
	getShape().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
        //        System.out.println("Shapes() MouseDrag "+this.getClass());
		now.setLocation(mouseEvent.getX(), mouseEvent.getY());
		dragDelta.setLocation(now.x - then.x , now.y - then.y);

		for(int i = 0; i < points.length - 1; i+=2) {
                    points[i] += dragDelta.getX();
                    points[i + 1] += dragDelta.getY();
		}
                
		MoveParameters();
		updateAnchors(root);
                
		then.setLocation(now);
            }
	});
    }
    
    private void MouseReleased(final Group root) {
		getShape().setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
                            System.out.println("Shapes().MouseReleased() "+arg0);
                            root.setCursor(Cursor.DEFAULT);
                            afterMove.setLocation(arg0.getX(), arg0.getY());
                            Shapes.this.getShape().toFront(); //place figure on top level
			}
		});
    }
    
    private void MouseEntered(final Group root) {
	getShape().setOnMouseEntered(new EventHandler<MouseEvent>() {
	@Override
	public void handle(final MouseEvent arg0) {
            root.setCursor(Cursor.CLOSED_HAND);
	}
	});
    }
    
    private void MouseExited (final Group root) {
	getShape().setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent arg0) {
                root.setCursor(Cursor.DEFAULT);
            }
	});
    }
    
    public void setBoarderColor(Color boarderColor) {
		this.boarderColor = boarderColor;
    }
    
    public Shape getShape() {
	return shape;
    }
    
    public void setShape(Shape shape) {
	this.shape = shape;
    }
    
    public void setEverything(Color color, Color border, Group Root, LinkedList<Shapes> AllShapes) {
	shape.setStrokeWidth(3); //TODO change by a menu parameter
	AllShapes.add(this);
        addColor(color);
	Root.getChildren().add(this.getShape());
        SetActions(Root, AllShapes);
	for (int i = 0; i < AllShapes.size()-1; i++) { //it can be set to (size - 1) in order to left the last shape active
		Shapes crnt = AllShapes.get(i);
		crnt.notSelected(Root);
                System.out.println("Shapes.setEverything() AllShapes["+i+"]: "+crnt.shape);
	}
    }
    
    public void SetActions(Group root, LinkedList<Shapes> AllShapes) {
		MousePressed();
		MouseDrag(root);
                MouseEntered(root);
                MouseExited(root);
		MouseClick(root, AllShapes);
		MouseReleased(root);
    }
    
    public void Selected(Group root, LinkedList<Shapes> AllShapes) {
	selected = true;
        System.out.println("Shapes().Selected() "+this.shape);
        for (Anchor anchor : getShapeAnchors()) {
            if (root.getChildren().contains(anchor.circle)) {
		break;
            }
            root.getChildren().add(anchor.circle);
            System.out.println("anchor: "+anchor.shape.shape);
	}       
    }
        
    public void notSelected(Group root) {
	selected = false;
        System.out.println("Shapes().notSelected() "+this.shape);
	for (Anchor anchor : getShapeAnchors()) {
            root.getChildren().remove(anchor.circle);
        }
    }
    
    public boolean isSelected() {
	return selected;
    }
    
    public Anchor[] getShapeAnchors() {
	return shapeAnchors;
    }

    public void setShapeAnchors(Anchor shapeAnchors[]) {
	this.shapeAnchors = shapeAnchors;
    }
    
    public double[] getPoints() {
	return points;
    }

    public void setPoints(double points[]) {
	this.points = points;
    }
    
    public void delete(Group Root, LinkedList<Shapes> AllShapes) {
	Root.getChildren().remove(getShape());
	for (int i = 0; i < AllShapes.size() ; i++) {
		if (AllShapes.get(i).equals(this)) {
			AllShapes.remove(i);
		}
	}
	for (Anchor anchor : getShapeAnchors()) {
            Root.getChildren().remove(anchor.circle);
	}
    }
    
    public abstract void createControlAnchorsFor(Group root);
    public abstract void moveWithAnchors(Anchor anchor, double endX, double endY, Group root);
    public abstract void setCorners();
    public abstract void MoveParameters();
    public abstract void updateAnchors(Group root);
        
}
