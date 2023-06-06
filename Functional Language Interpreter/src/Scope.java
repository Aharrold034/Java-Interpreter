import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class to create scope objects which act as structure for the memory of the interpreter. 
 *   @author Adam Harrold 
 *   @version 2/25/23
 */

public class Scope {
    private Scope parent;
    private HashMap<Token, VariableBinding> symbolTable;

    public Scope(Scope parent) {
        this.parent = parent;
        this.symbolTable = new HashMap<>();
    }

    public boolean isDeclared(Token variable) {
        return symbolTable.containsKey(variable) || (parent != null && parent.isDeclared(variable));
    }

    public void declareVariable(Token variable, DataValue.Type type) {
        symbolTable.put(variable, new VariableBinding(type));
    }

    public VariableBinding getBinding(Token variable) {
        if (symbolTable.containsKey(variable)) {
            return symbolTable.get(variable);
        } else if (parent != null) {
            return parent.getBinding(variable);
        } else {
            return null;
        }
    }
}