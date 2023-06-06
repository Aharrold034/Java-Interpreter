/**
         * Derived class that represents an if statement in the SILLY language.
         * It supports both the if case and the else case.
         * 
         * @author Adam Harrold
         * @version 2/25/23
         */
        public class If extends Statement {
            private Expression test;
            private Compound ifBody;
            private Compound elseBody;
 
            /**
             * Reads in an if statement from the specified stream.
             * @param input the stream to be read from
             */
            public If(TokenStream input) throws Exception {
                if (!input.next().toString().equals("if")) {
                    throw new Exception("SYNTAX ERROR: Malformed if statement");
                }
                this.test = new Expression(input);

                if (!input.next().toString().equals("then")) {
                    throw new Exception("SYNTAX ERROR: Malformed if statement");
                }
                this.ifBody = new Compound(input);

                if (input.lookAhead().toString().equals("else")) {
                    // read in the else body if it exists
                    input.next(); // consume the "else"
                    this.elseBody = new Compound(input);
                } else {
                    // set the else body to null if it doesn't exist
                    this.elseBody = null;
                    if (!input.next().toString().equals("noelse")) {
                        throw new Exception("SYNTAX ERROR: Malformed if statement");
                    }
                }
            }

            /**
             * Executes the current if statement.
             */
            public void execute() throws Exception {
                DataValue test = this.test.evaluate();
                if (test.getType() != DataValue.Type.BOOLEAN_VALUE) {
                    throw new Exception("RUNTIME ERROR: If statement requires Boolean test.");
                } 
                
                if (((Boolean) test.getValue())) {
                	//System.out.println("----------" + "\n" + Interpreter.MEMORY.scopeStack.size());
                	Interpreter.MEMORY.pushScope();
                    this.ifBody.execute();
                    //System.out.println(Interpreter.MEMORY.scopeStack.size());
                    Interpreter.MEMORY.popScope();
                    //System.out.println(Interpreter.MEMORY.scopeStack.size());
                } else if (this.elseBody != null) {
                	Interpreter.MEMORY.pushScope();
                	//System.out.println(Interpreter.MEMORY.scopeStack.size());
                    this.elseBody.execute();
                    Interpreter.MEMORY.popScope();
                    //System.out.println(Interpreter.MEMORY.scopeStack.size());
                }
            }

            /**
             * Converts the current if statement into a String.
             * @return the String representation of this statement
             */
            public String toString() {
                String str = "if " + this.test + " then\n" + this.ifBody;
                if (this.elseBody != null) {
                    str += "\nelse\n" + this.elseBody;
                } else {
                    str += "\nnoelse";
                }
                return str;
            }
        }
