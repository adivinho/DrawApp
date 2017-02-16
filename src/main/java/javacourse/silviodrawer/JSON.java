/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacourse.silviodrawer;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.Stack;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author silvo
 */
class JSON {
    @SuppressWarnings("unchecked")
    public void saveJSON(LinkedList<Shapes> allShapes, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
        for (Shapes current : allShapes) {
            JSONObject shape = new JSONObject();
            System.out.println("getType() "+current.getType());
            switch (current.getType()){
                case ("circle"):
                    shape.put("type", "circle");
                    shape.put("x", String.valueOf(((Oval) current).getCenterX()));
                    shape.put("y", String.valueOf(((Oval) current).getCenterY()));
                    shape.put("rad", String.valueOf(((Oval) current).getRadiusY()));
                    shape.put("fcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                case ("oval"):
                    shape.put("type", "oval");
                    shape.put("x", String.valueOf(((Oval) current).getCenterX()));
                    shape.put("y", String.valueOf(((Oval) current).getCenterY()));
                    shape.put("rad1", String.valueOf(((Oval) current).getRadiusX()));
                    shape.put("rad2", String.valueOf(((Oval) current).getRadiusY()));
                    shape.put("fcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                case ("line"):
                    double[] p = current.getPoints();
                    shape.put("type", "line");
                    shape.put("x1", String.valueOf(p[0]));
                    shape.put("y1", String.valueOf(p[1]));
                    shape.put("x2", String.valueOf(p[2]));
                    shape.put("y2", String.valueOf(p[3]));
                    shape.put("Bcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                case ("square"):
                    shape.put("type", "square");
                    shape.put("x", String.valueOf(((RectangleShape) current).getX()));
                    shape.put("y", String.valueOf(((RectangleShape) current).getY()));
                    shape.put("l", String.valueOf(((RectangleShape) current).getwidth()));
                    shape.put("fcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                case ("rectangle"):
                    shape.put("type", "rectangle");
                    shape.put("x", String.valueOf(((RectangleShape) current).getX()));
                    shape.put("y", String.valueOf(((RectangleShape) current).getY()));
                    shape.put("h", String.valueOf(((RectangleShape) current).gethight()));
                    shape.put("w", String.valueOf(((RectangleShape) current).getwidth()));
                    shape.put("fcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                case ("triangle"):
                    double[] p2 = current.getPoints();
                    shape.put("type", "triangle");
                    shape.put("x1", String.valueOf(p2[0]));
                    shape.put("y1", String.valueOf(p2[1]));
                    shape.put("x2", String.valueOf(p2[2]));
                    shape.put("y2", String.valueOf(p2[3]));
                    shape.put("x3", String.valueOf(p2[4]));
                    shape.put("y3", String.valueOf(p2[5]));
                    shape.put("fcolor", String.valueOf(current.getBoarderColor().toString()));
                    break;
                default:
                    System.out.println("Some mystery on your stage! Be careful.");
                    break;
            }
        try {
                fileWriter.write(shape.toJSONString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileWriter.flush();
        fileWriter.close();
    }

    @SuppressWarnings("rawtypes")
    public Group loadJSON(File file, Group Root, LinkedList<Shapes> AllShapes, Class cfile,
            Class sfile) throws Exception {
        JSONObject obj;
        String crntLine = "";
        try {
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((crntLine = bufferedReader.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(crntLine);  
                if (obj.get("type").equals("line")) {
                    Point p1 = new Point();
                    Point p2 = new Point();
                    p1.setLocation(Double.parseDouble((String) obj.get("x1")),Double.parseDouble((String) obj.get("y1")));
                    p2.setLocation(Double.parseDouble((String) obj.get("x2")),Double.parseDouble((String) obj.get("y2")));
                    String c = (String) obj.get("Bcolor");
                    LineSegment l = new LineSegment(p1, p2, Color.web(c), Root, AllShapes);
                    l.setCorners();
                    l.createControlAnchorsFor(Root);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return Root;
    }
}