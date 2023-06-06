import java.util.ArrayList;
import java.util.List;

/**
 * Derived class that represents an output statement in the SILLY language.
 * 
 * 
 * @author Dave Reed, modified by Adam Harrold
 * @version 1/15/23
 */
public class Output extends Statement {
    private List<Expression> expressions;

    /**
     * Reads in an output statement from the specified TokenStream.
     * @param input the stream to be read from
     */
    public Output(TokenStream input) throws Exception {
        if (!input.next().toString().equals("output")) {
            throw new Exception("SYNTAX ERROR: Malformed output statement");
        }

        if (input.lookAhead().toString().equals("{")) {
            // read in multiple expressions enclosed in curly braces
            input.next(); // consume the "{"
            this.expressions = new ArrayList<>();
            while (!input.lookAhead().toString().equals("}")) {
                this.expressions.add(new Expression(input));
                if (input.lookAhead().toString().equals(",")) {
                    input.next(); // consume the ","
                } else if (!input.lookAhead().toString().equals("}")) {
                    throw new Exception("SYNTAX ERROR: Malformed output statement");
                }
            }
            input.next(); // consume the "}"
        } else {
            // read in a single expression
            this.expressions = new ArrayList<>();
            this.expressions.add(new Expression(input));
        }
    }

    /**
     * Executes the current output statement.
     */
    public void execute() throws Exception {
        String output = "";
        for (int i = 0; i < this.expressions.size(); i++) {
            String str = this.expressions.get(i).evaluate().toString();
            if (str.charAt(0) == '"') {
                str = str.substring(1, str.length() - 1);
            }
            output += str;
            if (i < this.expressions.size() - 1) {
                output += " ";
            }
        }
        System.out.println(output);
    }

    /**
     * Converts the current output statement into a String.
     * @return the String representation of this statement
     */
    public String toString() {
        String str = "output ";
        if (this.expressions.size() == 1) {
            str += this.expressions.get(0);
        } else {
            str += "{";
            for (int i = 0; i < this.expressions.size(); i++) {
                str += this.expressions.get(i);
                if (i < this.expressions.size() - 1) {
                    str += ", ";
                }
            }
            str += "}";
        }
        return str;
    }
}
