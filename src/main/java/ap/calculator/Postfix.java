package ap.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Postfix{
    public static boolean isDigit(String num){
        try{
            Double.parseDouble(num);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static Stack<String> infixToPostfix(ArrayList<String> infixExpression) {
        //StringBuilder postfix = new StringBuilder();
        Stack<String>  pf=new Stack<>();
        Stack<String> stack = new Stack<>();

        for (String c : infixExpression) {
            if(c.isEmpty())continue;
            if (isDigit(c)) pf.push(c);
            else if (c.equals("(")) stack.push(c);
            else if (c.equals(")")) {

                while (!stack.isEmpty() && !stack.peek().equals("(")) pf.push(stack.pop());

                if (!stack.isEmpty()) stack.pop();
                else throw new IllegalArgumentException("Mismatched parentheses in the expression.");

            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) pf.push(stack.pop());
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses in the expression.");
            }
            pf.push(stack.pop());
        }
    pf.forEach(v-> System.out.print(v+','));
        return pf;
    }
    public static double eval(Stack<String> vals){

        Calculate add=(x,y)->x+y;
        Calculate sub=(x,y)->x-y;
        Calculate mul=(x,y)->x*y;
        Calculate div=(x,y)->x/y;
        Calculate pow= Math::pow;
        Calculate mod=(x,y)->x%y;
        Calculate root=(y,x)->Math.pow(x,1/y);
        Stack<String> eq=new Stack<>();
        HashMap<String,Calculate> operations=new HashMap<>();
        operations.put("+",add);
        operations.put("-",sub);
        operations.put("*",mul);
        operations.put("/",div);
        operations.put("^",pow);
        operations.put("%",mod);
        operations.put("√",root);
        Stack<Double> result=new Stack<>();
        for (String str:vals){
            if(isDigit(str))result.push(Double.parseDouble(str));
            else{
                double a=result.pop();
                double b=result.pop();
                result.push(operations.get(str).Solution(b,a));
            }
        }
        return result.pop();
    }
    private static int precedence(String operator) {
        return switch (operator.charAt(0)) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^','√' -> 3;
            default -> 0;
        };
    }
    public static double calc(ArrayList<String> a){
        return eval(infixToPostfix(a));
    }

    public static void main(String[] args) {
        String infixExpression = "a + b * (c - d) / e";
        try {
           // String postfixExpression = infixToPostfix(infixExpression);
            System.out.println("Infix: " + infixExpression);
            //System.out.println("Postfix: " + postfixExpression);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
