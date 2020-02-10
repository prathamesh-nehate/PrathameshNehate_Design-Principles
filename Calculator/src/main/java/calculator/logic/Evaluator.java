package calculator.logic;

import java.util.Stack;

public class Evaluator {

    static String generateExpression(String expression) {
        StringBuffer temp = new StringBuffer();
        boolean flag = false;
        for (int i = 0; i < expression.length(); i++) {
            if (!isAnOperator(expression.charAt(i)) && flag == false) {
                temp = temp.append(expression.charAt(i));
            } else {
                temp = temp.append(" " + expression.charAt(i));
                flag = true;
            }
        }

        return new String(temp);
    }

    public String evaluate(String expression) {
        if (!IsExpression(expression)) {
            return "Invalid Mathematical Expression.Press AC";
        }

        expression = generateExpression(expression);
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i] == ' ')
                continue;

            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuffer sbuf = new StringBuffer();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }

            else if (tokens[i] == '(')
                ops.push(tokens[i]);

            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            else{
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())){
                    try{
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                
                    }
                    catch(ArithmeticException e){
                        return "Divide By Zero Error.Press AC";
                    }
                }
                ops.push(tokens[i]);
            }
        }

        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return String.valueOf(values.pop());
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    public static int applyOp(char op, int b, int a) throws ArithmeticException {
        switch (op) {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            if (b == 0)
                throw new ArithmeticException();
            return a / b;
        }
        return 0;
    }

    public static Boolean IsExpression(String expression) {
        if (isAnOperator(expression.charAt(0)) || isAnOperator(expression.charAt(expression.length() - 1))) {
            return false;
        }

        int openParenthCount = 0;
        boolean lastWasOp = false;
        boolean lastWasOpen = false;

        for (char c : expression.toCharArray()) {
            if (c == ' ')
                continue;
            if (c == '(') {
                openParenthCount++;
                lastWasOpen = true;
                continue;
            } else if (c == ')') {
                if (openParenthCount <= 0 || lastWasOp) {
                    return false;
                }
                openParenthCount--;
            } else if (isAnOperator(c)) {
                if (lastWasOp || lastWasOpen)
                    return false;
                lastWasOp = true;
                continue;
            } else if (isAnOperator(c)) {
                return false;
            }
            lastWasOp = false;
            lastWasOpen = false;
        }
        if (openParenthCount != 0)
            return false;
        if (lastWasOp || lastWasOpen)
            return false;
        return true;
    }

    public static boolean isAnOperator(char c) {
        return (c == '*' || c == '/' || c == '+' || c == '-' || c == '%');
    }

    // public static void main(String[] args) {
    // Evaluator e = new Evaluator();
    // System.out.println(e.evaluate("33 + 3"));
    // }
}
