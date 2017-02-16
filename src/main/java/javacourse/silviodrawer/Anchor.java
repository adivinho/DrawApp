/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author silvo
 */

import javacourse.silviodrawer.Shapes;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Anchor {
    //
    public Shapes shape;
    public Circle circle;
    
      public Anchor(double x, double y, Shapes shape, Group root) {

        circle = new Circle(x, y, 5);
        circle.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(2);
        circle.setStrokeType(StrokeType.OUTSIDE);

        this.shape = shape;

        enableDrag(root);

    }

    public void move(double x, double y) {
        circle.setCenterX(x);
        circle.setCenterY(y);
    }
    

    public void enableDrag(final Group root) {

        final Point dragDelta = new Point();
        final Point start = new Point();
        final Point end = new Point();

        circle.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                start.setLocation(mouseEvent.getX(), mouseEvent.getY());
                dragDelta.setLocation(circle.getCenterX() - mouseEvent.getX(), circle.getCenterY() - mouseEvent.getY());
                root.setCursor(javafx.scene.Cursor.MOVE);

            }

        });

        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                root.setCursor(Cursor.HAND);
                end.setLocation(mouseEvent.getX(), mouseEvent.getY());
//                Action action = new ResizeAction(shape, Anchor.this, start, end);
            }

        });

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {
                double newX = mouseEvent.getX() + dragDelta.x;
                double newY = mouseEvent.getY() + dragDelta.y;

                if (newX > 0 && newX < circle.getScene().getWidth() && newY > 0
                        && newY < circle.getScene().getHeight()) {
                    try {
                        shape.moveWithAnchors(Anchor.this, mouseEvent.getX(), mouseEvent.getY(), root);

                    } catch (Exception e) {
                        System.out.println("Exception is cought on mouse drag "+e);
                    }
                }
    //        System.out.println("Anchor() anchor is dragged");
            }
        });

        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {

                if (!mouseEvent.isPrimaryButtonDown()) {

                    root.setCursor(javafx.scene.Cursor.HAND);

                }

            }

        });

        circle.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override

            public void handle(MouseEvent mouseEvent) {

                if (!mouseEvent.isPrimaryButtonDown()) {

                    root.setCursor(javafx.scene.Cursor.DEFAULT);

                }

            }

        });

    }
}
