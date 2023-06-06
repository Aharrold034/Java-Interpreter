/**
 * Derived class that represents a do statement in the SILLY language.
 * 
 *   @author Adam Harrold, based on code from Dave Reed
 *   @version 2/25/23
 */
public class Do extends Statement {
    private Compound body;
    private Expression expr;
    
    /**
     * Reads in a do statement from the specified stream.
     *   @param input the stream to be read from
     */
    public Do(TokenStream input) throws Exception {
        if (!input.next().toString().equals("do")) {
            throw new Exception("SYNTAX ERROR: Malformed do statement");
        }
        this.body = new Compound(input);
        
        if (!input.next().toString().equals("until")) {
            throw new Exception("SYNTAX ERROR: Malformed do statement");
        }
        this.expr = new Expression(input);
    }
     
    /**
     * Executes the current do statement.
     */
    public void execute() throws Exception {
        do {
        	Interpreter.MEMORY.pushScope();
        	System.out.println("do statement");
            this.body.execute();
            Interpreter.MEMORY.popScope();
        } while (this.expr.evaluate().getType() == DataValue.Type.BOOLEAN_VALUE && !((Boolean) this.expr.evaluate().getValue()));
        if (this.expr.evaluate().getType() != DataValue.Type.BOOLEAN_VALUE) {
            throw new Exception("RUNTIME ERROR: until statement requires Boolean test.");
        }
    }
    
    /**
     * Converts the current do statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "do\n" + this.body + "until " + this.expr;
    }
}