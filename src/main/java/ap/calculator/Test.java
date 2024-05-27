package ap.calculator;

import javafx.geometry.Pos;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        String []exp={"100","+","400","-","1","/","(","2","*","5",")"};
        ArrayList<String> xp = new ArrayList<>(Arrays.asList(exp));
        System.out.println(Postfix.eval(Postfix.infixToPostfix(xp)));
    }
}
