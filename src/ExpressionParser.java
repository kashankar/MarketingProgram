import java.util.Stack;
import java.util.ArrayList;

public class ExpressionParser {
    private String rawExpression;
    private ArrayList<String> expression;
    private final String[] operators = {"<", "<=", ">", ">=", "==", "!=", "contains", "&&", "||"};

    public void setRawExpression(String expression) {
        rawExpression = expression;
        parseExpression();
    }

    public String getRawExpression() {
        return rawExpression;
    }

    public void parseExpression() {
        expression = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < rawExpression.length(); i++) {
            char c = rawExpression.charAt(i);
            if (c == '(') {
                stack.push("(");
            } else if (c == ')') {
                String word = currentWord.toString().strip();
                if (!word.isEmpty()) {
                    expression.add(word);
                }
                currentWord.setLength(0);
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    expression.add(stack.pop());
                }
                stack.pop();
            } else if (c != ' '){
                currentWord.append(c);
            } else {
                String word = currentWord.toString().strip();
                if (word.isEmpty()) {
                    continue;
                }
                if (!isOperator(word)) {
                    expression.add(word);
                    currentWord.setLength(0);
                } else {
                    while (!stack.isEmpty() && getPrecedence(word) <= getPrecedence(stack.peek())) {
                        expression.add(stack.pop());
                    }
                    stack.push(word);
                    currentWord.setLength(0);
                }
            }
        }
        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Invalid expression");
            }
            expression.add(stack.pop());
        }
    }

    public int getPrecedence(String operator) {
        return switch (operator) {
            case "<", "<=", ">", ">=" -> 3;
            case "==", "!=", "contains" -> 2;
            case "&&", "||" -> 1;
            default -> -1;
        };
    }

    public boolean isOperator(String word) {
        for (String operator: operators) {
            if (word.equals(operator)) {
                return true;
            }
        }
        return false;
    }

    public boolean evaluateExpression(Customer customer) {
        Stack<String> stack = new Stack<>();
        for (String word: expression) {
            if (isOperator(word)) {
                String operand2 = stack.pop(), operand1 = stack.pop();
                switch (operand1) {
                    case "customerID":
                        operand1 = Integer.toString(customer.getCustomerID());
                        break;
                    case "customerName":
                        operand1 = customer.getCustomerName();
                        break;
                    case "customerEmail":
                        operand1 = customer.getCustomerEmail();
                        break;
                    case "customerDescription":
                        operand1 = customer.getCustomerDescription();
                        break;
                }
                boolean res = false;
                switch (word) {
                    case "contains":
                        res = operand1.toLowerCase().contains(operand2.toLowerCase());
                        break;
                    case "&&":
                    case "||":
                        res = evaluateBoolean(word, operand1, operand2);
                        break;
                    case "<":
                        res = Integer.parseInt(operand1) < Integer.parseInt(operand2);
                        break;
                    case "<=":
                        res = Integer.parseInt(operand1) <= Integer.parseInt(operand2);
                        break;
                    case ">":
                        res = Integer.parseInt(operand1) > Integer.parseInt(operand2);
                        break;
                    case ">=":
                        res = Integer.parseInt(operand1) >= Integer.parseInt(operand2);
                        break;
                    case "==":
                        res = Integer.parseInt(operand1) == Integer.parseInt(operand2);
                        break;
                    case "!=":
                        res = Integer.parseInt(operand1) != Integer.parseInt(operand2);
                        break;
                }
                String result = "false";
                if (res) {
                    result = "true";
                }
                stack.push(result);
            } else {
                stack.push(word);
            }
        }
        return stack.peek().equals("true");
    }

    private static boolean evaluateBoolean(String word, String operand1, String operand2) {
        boolean op1 = false, op2 = false;
        if (operand1.equals("true")) {
            op1 = true;
        }
        if (operand2.equals("true")) {
            op2 = true;
        }
        boolean res = false;
        if (word.equals("||")) {
            res = op1 || op2;
        } else if (word.equals("&&")) {
            res = op1 && op2;
        }
        return res;
    }
}
