package javacourse.silviodrawer;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApp extends Application {
    Group root = new Group();
    public LinkedList<Shapes> AllShapes = new LinkedList<>();       //The collection of active figures
    Class<?> c =  null, s = null;
    MControl MControl = new MControl();
    static String state = "blank";                                  // what type of figures is selected
    static int counter = 0;
    Shapes current;                                                 // Selected figure now
    private boolean fillColor = false;                              // status of fill button
    private ColorPicker colorPicker = new ColorPicker(Color.BLACK);
    LinkedList<Point> clickpoint = new LinkedList<Point>();         //collection dots for drawing
    public int layoutMaxX = 900;                                    //Size of a layout
    public int layoutMaxY = 650;
    Stage window;
    
    
    @Override
    public void start(Stage arg0) throws Exception {
        final Stage window;
        window = arg0;
        
        window.setTitle("JavaFX");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, layoutMaxX, layoutMaxY);
        layout.setStyle("-fx-background-color: ffffe0");
        window.setScene(scene);
        
        // --- Add Menu bar
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem New = new MenuItem("New");
        
        New.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu New has choosen");
                CheckBeforeOpen();
            }

            private void CheckBeforeOpen() {
                final Stage dialog = new Stage();
                dialog.setTitle("To save or not to save");
                dialog.setMinWidth(200);
                final Label x = new Label("Do you want to save your current picture?");
                
                final Button Yes = new Button("Yes");
                Yes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    save();
                    dialog.close();
                    for (Shapes s:AllShapes){
                        s.notSelected(root);
                    }                        
                    root.getChildren().remove(1,AllShapes.size()+1);
                    AllShapes.clear();
                    counter = 0;
                }
                });
        
                final Button No = new Button("No");
                No.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        dialog.close();                   
                        for (Shapes s:AllShapes){
                            s.notSelected(root);
                        }                        
                        root.getChildren().remove(1,AllShapes.size()+1);
                        AllShapes.clear();
                        counter = 0;
                    }
                });
                
                VBox sP = new VBox(8);
                sP.setAlignment(Pos.CENTER);
                sP.getChildren().addAll(x, Yes, No);
                Scene s = new Scene(sP, 400, 100);
                dialog.setScene(s);
                dialog.show();
            }
        });
                
        MenuItem Open = new MenuItem("Open ...");
        
        Open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu Open has choosen");
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
                File file = fileChooser.showOpenDialog(window);
                try {
                    root = MControl.fileOpen(file, root, AllShapes, c, s);
                } catch (Exception e) {
                    
                }
            }
        });        
        
        MenuItem Save = new MenuItem("Save");
        
        Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu Save has choosen");
                save();
            }

        });
                
        MenuItem Exit = new MenuItem("Exit");
        
        Exit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Menu Exit has choosen");
                System.exit(0);
            }
        });
        
        menuFile.getItems().addAll(New, Open, Save, new SeparatorMenuItem(), Exit);
        
        menuBar.getMenus().addAll(menuFile);
        layout.setTop(menuBar);
        
        
// Adding tools bar on the left node of layout (BordePaint)       
        
        VBox toolsArea = new VBox(8); //space between elements
        toolsArea.setPrefWidth(78);
        toolsArea.setId("toolsArea");
        
        // Set-up buttons: line, square, triangle, circle, ellipse, rectangle, fill
        
        MButton buttonLine = new MButton("line", "resources/fun_line.jpg", state, toolsArea, root, AllShapes);
        toolsArea.getChildren().add(buttonLine);
        
        MButton buttonSquare = new MButton("square", "resources/fun_square.jpg", state, toolsArea, root, AllShapes);
        toolsArea.getChildren().add(buttonSquare);
        
        MButton buttonRectangle = new MButton("rectangle", "resources/fun_rec.jpg", state, toolsArea, root, AllShapes);
        toolsArea.getChildren().add(buttonRectangle);
        
        MButton buttonTriangle = new MButton("triangle", "resources/fun_tri.jpg", state, toolsArea, root, AllShapes);
        toolsArea.getChildren().add(buttonTriangle);
        
        MButton buttonCircle = new MButton("circle", "resources/fun_circle.jpg", state, toolsArea, root, AllShapes); 
        toolsArea.getChildren().add(buttonCircle);
        
        MButton buttonEllipse = new MButton("ellipse", "resources/fun_ellipse.jpg", state, toolsArea, root, AllShapes);        
        toolsArea.getChildren().add(buttonEllipse);
        
        File file = new File("resources/fill.jpg");
        Image image = new Image(file.toURI().toString());
        final ImageView fill = new ImageView(image);
        fill.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                fillColor = !fillColor;
                for (Shapes crnt : AllShapes) {
                    crnt.notSelected(root);
                }
                System.out.println("The button fill is pressed.");
            }
        });
        
        toolsArea.getChildren().add(fill);
        
//        for (toolsArea.getChildren().get(2);
//                buttonEllipse.Release();
        
        // --- Element for setting a color
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color c = colorPicker.getValue();
                LinkedList<Shapes> selectedShapes = new LinkedList<Shapes>();
                LinkedList<Color> selectedcolors = new LinkedList<Color>();

                for (Shapes current : AllShapes) {
                    if (current.isSelected()) {
                        selectedShapes.add(current);
                            current.addColor(c);
                    }
                }
                
                System.out.println("New color has been choosen "+c);
            }
        });

        toolsArea.getChildren().add(colorPicker);
        
        layout.setLeft(toolsArea);
        
// Adding drawing area   

        final Canvas drawingArea = new Canvas(scene.getWidth(), scene.getHeight());
          
        drawingArea.setOnMouseClicked(click);
        scene.setOnKeyPressed(pressKey);
        drawingArea.setOnMouseDragged(drag);
        drawingArea.setOnMouseReleased(leave);

        final GraphicsContext gc = drawingArea.getGraphicsContext2D();
        gc.setLineWidth(5);
        drawingArea.setId("drawingArea");
        root.getChildren().add(drawingArea);
        
        layout.setCenter(root);          
        scene.getStylesheets().add("/styles/styles.css");
        window.setScene(scene);
        window.show();
    };
    
    // Event for mouse -> clicked button
    EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("Mouse has clicked ("+event.getX()+"x"+event.getY()+") with the shape: "+state+" going to call notSelected()");
            
            for (Shapes crnt : AllShapes) {
                    crnt.notSelected(root);
            }
        }
    };
    
    // Event for mouse -> move with pressed button
    EventHandler<MouseEvent> drag = (new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Point now = new Point();
            now.setLocation(event.getSceneX(), event.getSceneY());
            if (current == null) {
                switch (state) {
                    case "line":
                        LineSegment line = new LineSegment(now, now, colorPicker.getValue(), root, AllShapes);
                        current = line;
                        break;
                    case "square":
                        Square square = new Square(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes);
                        current = square;
                        break;
                    case "rectangle":
                        RectangleShape rectangle = new RectangleShape(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes);
                        current = rectangle;
                        break;
                    case "triangle": 
                        TriangleShape triangle = new TriangleShape(now, now, now, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes);
                        current = triangle;
                        break;
                    case "circle":
                        Circle circle = new Circle(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes);
                        current = circle;
                        break;
                    case "ellipse":
                        Oval oval = new Oval(now, 0.0, 0.0, colorPicker.getValue(), colorPicker.getValue(), root, AllShapes);
                        current = oval;
                        break;
                    default: 
                        System.out.println("Draging mouse pointer without any selection");
                }
            } else {
                current.dragIt(now);
            }
        }
    });
    
    // Event for mouse release button - add anchors to a shape
    EventHandler<MouseEvent> leave = (new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("Mouse button has released -> calling createControlAnchorsFor()");
            if (current != null) {
                current.createControlAnchorsFor(root);
                current = null;
            }
            
        }
    });
    
    EventHandler<KeyEvent> pressKey = new EventHandler<KeyEvent>() {
        @Override
        public void handle(final KeyEvent keyEvent) {
            System.out.println("Key is pressed "+keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.DELETE) {
                LinkedList<Shapes> selectedShapes = new LinkedList<Shapes>();
                for (Shapes current : AllShapes) {
                    if (current.isSelected()) {
                        selectedShapes.add(current);
                    }
                }
                for (Shapes sel : selectedShapes) {
                    sel.delete(root, AllShapes);
                }
            }
        }
    
};
        
public void save() {        
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialFileName("Simple.json");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

    // Show save file dialog
    File file = fileChooser.showSaveDialog(window);
    try {
        MControl.fileSave(AllShapes, file);
    } catch (IOException e) {
        System.out.println("Exception for during writing a file "+e);
    }
}
        
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
public static void main(String[] args) {
        launch(args);
    }

}
