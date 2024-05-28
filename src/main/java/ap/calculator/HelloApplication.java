package ap.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
public class HelloApplication extends Application {
    static ArrayList<String> infix=new ArrayList<>();
    static StringBuffer buffer=new StringBuffer();
    static Label displaylbl=new Label("");
    static Label displayBuffer=new Label("");
    static VBox historyBox;
    EventHandler<ActionEvent> clear=e->{
        displaylbl.setText("");
        infix.clear();
        displayBuffer.setText("");
        if(!buffer.isEmpty())buffer.delete(0,buffer.length());
    };
    static EventHandler<ActionEvent>  refreshLbl=e->displaylbl.setText(infix.stream().reduce("", String::concat));
    public static Button numpad(int i){
        Button num=new Button(String.valueOf(i));
        num.setOnAction(e->{
            buffer.append(String.valueOf(i));
            displayBuffer.setText(buffer.toString());
        });
        num.addEventHandler(ActionEvent.ACTION,refreshLbl);
        return num;
    }
    public static Button operator(String s){
        Button op=new Button(s);
        op.setOnAction(e->{
            infix.add(buffer.toString());
            buffer.delete(0,buffer.length());
            infix.add(s);
            displayBuffer.setText("");
            displaylbl.setText(infix.stream().reduce("", String::concat));
        });
        return op;
    }
    @Override
    public void start(Stage stage) throws IOException {
        GridPane numberGrid=new GridPane();
        displaylbl.setMinHeight(30);
        Button equalsbtn=new Button("=");
        Button clearbtn=new Button("C");
        Label historylbl=new Label("History");
        historylbl.setWrapText(true);
        clearbtn.setOnAction(clear);

        equalsbtn.setOnAction(e->{
            if(!buffer.isEmpty()){
                infix.add(buffer.toString());
                buffer.delete(0,buffer.length());
            }
            displayBuffer.setText("");
            String rt=String.valueOf(Postfix.calc(infix));
            String hs="\n"+infix.stream().reduce("", String::concat) +" = " +rt;
            historylbl.setText(historylbl.getText()+"\n"+hs);
            infix.clear();
            buffer.append(rt);

            displayBuffer.setText(buffer.toString());
            displaylbl.setText("");
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
        Button squarebtn=new Button("x²");
        squarebtn.setOnAction(e->{
        infix.add(buffer.toString());
        infix.add("^");
        infix.add("2");
        buffer.delete(0,buffer.length());
        displayBuffer.setText("");
        displaylbl.setText(infix.stream().reduce("", String::concat));
        });
        Button powerbtn=new Button("xʸ");
        powerbtn.setOnAction(e->{
            infix.add(buffer.toString());
            infix.add("^");
            buffer.delete(0,buffer.length());
            displayBuffer.setText("");
            displaylbl.setText(infix.stream().reduce("", String::concat));
        });
        Button sqrtbtn=new Button("²√");
        sqrtbtn.setOnAction(e->{
            infix.add(buffer.toString());
            infix.add("2");
            infix.add("√");
            buffer.delete(0,buffer.length());
            displayBuffer.setText("");
            displaylbl.setText(infix.stream().reduce("", String::concat));
        });
        Button yrootbtn=new Button("ʸ√");
        yrootbtn.setOnAction(e->{
            if(!buffer.isEmpty()){
                infix.add(buffer.toString());
                infix.add("√");
                buffer.delete(0,buffer.length());
                displayBuffer.setText("");
                displaylbl.setText(infix.stream().reduce("", String::concat));
            }

        });
        VBox viewB=new VBox(10);
        historyBox=new VBox(10);
        historyBox.getChildren().add(historylbl);
        viewB.setPadding(new Insets(5));
        numberGrid.setHgap(6);
        numberGrid.setVgap(4);
        numberGrid.addRow(0,squarebtn,clearbtn,operator("("),operator(")"),backbtn);
        numberGrid.addRow(1,powerbtn,numpad(9),numpad(8),numpad(7),operator("/"));
        numberGrid.addRow(2,operator("%"),numpad(6),numpad(5),numpad(4),operator("*"));
        numberGrid.addRow(3,sqrtbtn,numpad(3),numpad(2),numpad(1),operator("-"));
        numberGrid.addRow(4,yrootbtn,numpad(0),point,equalsbtn,operator("+"));
        viewB.getChildren().addAll(displaylbl,displayBuffer,numberGrid);
        HBox displayBox=new HBox(10);
        displayBox.getChildren().addAll(viewB,historyBox);
        stage.setMinWidth(300);
        stage.setMaxHeight(260);
        stage.setScene(new Scene(displayBox));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}