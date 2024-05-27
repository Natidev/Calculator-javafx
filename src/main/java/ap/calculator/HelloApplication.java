package ap.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
public class HelloApplication extends Application {
    static ArrayList<String> infix=new ArrayList<>();
    static StringBuffer buffer=new StringBuffer();
    static Label displaylbl=new Label("");
    static Label displayBuffer=new Label("");
    EventHandler<ActionEvent> clear=e->{
        displaylbl.setText("");
        infix.clear();
        displayBuffer.setText("");
        if(!buffer.isEmpty())buffer.delete(0,buffer.length());
    };
    public static Button numpad(int i){
        Button num=new Button(String.valueOf(i));
        num.setOnAction(e->{
            buffer.append(String.valueOf(i));
            displayBuffer.setText(buffer.toString());
        });
        return num;
    }
    public static Button operator(String s){
        Button op=new Button(s);
        op.setOnAction(e->{
            infix.add(buffer.toString());
            buffer.delete(0,buffer.length());
            infix.add(s);
            displaylbl.setText(infix.stream().reduce("", String::concat));
            displayBuffer.setText("");
        });
        return op;
    }
    @Override
    public void start(Stage stage) throws IOException {
        GridPane numberGrid=new GridPane();
        displaylbl.setMinHeight(30);
        Button equalsbtn=new Button("=");
        Button clearbtn=new Button("C");
        clearbtn.setOnAction(clear);

        equalsbtn.setOnAction(e->{
            if(!buffer.isEmpty()){
                infix.add(buffer.toString());
                buffer.delete(0,buffer.length());
            }
            displayBuffer.setText("");
            System.out.println(infix.toString());
            String rt=String.valueOf(Postfix.calc(infix));
            infix.clear();
            infix.add(rt);
            displaylbl.setText(infix.getFirst());
        });

        Button backbtn=new Button("del");
        backbtn.setOnAction(e->{
            if(!buffer.isEmpty())buffer.deleteCharAt(buffer.length()-1);
            displayBuffer.setText(buffer.toString());
        });
        Button point=new Button(".");
        point.setOnAction(e->{
            if(buffer.isEmpty())buffer.append("0.");
            else if(!buffer.toString().contains("."))buffer.append(".");
            displayBuffer.setText(buffer.toString());
        });

        VBox viewB=new VBox(10);
        numberGrid.addRow(0,clearbtn,operator("("),operator(")"),backbtn);
        numberGrid.addRow(1,numpad(9),numpad(8),numpad(7),operator("/"));
        numberGrid.addRow(2,numpad(6),numpad(5),numpad(4),operator("*"));
        numberGrid.addRow(3,numpad(3),numpad(2),numpad(1),operator("-"));
        numberGrid.addRow(4,equalsbtn,numpad(0),point,operator("+"));
        viewB.getChildren().addAll(displaylbl,displayBuffer,numberGrid);
        stage.setScene(new Scene(viewB));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}