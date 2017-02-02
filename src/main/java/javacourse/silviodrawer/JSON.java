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
            if (current.getType() == "circle") {
                shape.put("type", "circle");
/*
                shape.put("x", String.valueOf(((Oval) current).getCenterX()));
                shape.put("y", String.valueOf(((Oval) current).getCenterY()));
                shape.put("rad", String.valueOf(((Oval) current).getRadiusY()));
                shape.put("Bcolor", String.valueOf(current.getBoarderColor().toString()));
                shape.put("fcolor", String.valueOf(((ClosedShape)current).getColor().toString()));
*/
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
        } catch (Exception e) {
            throw e;
        }
        return Root;
    }
}