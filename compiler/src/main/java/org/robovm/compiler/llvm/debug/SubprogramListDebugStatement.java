package org.robovm.compiler.llvm.debug;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A debug block that lists subprograms (methods/functions) of a program.
 * 
 * @author ndeisinger
 * 
 */
public class SubprogramListDebugStatement extends DebugStatement {

    LinkedList<FunctionDebugStatement> myFuncs;

    public SubprogramListDebugStatement(int reference, int line) {
        super(reference, line);
        myFuncs = new LinkedList<FunctionDebugStatement>();
    }

    public void addFunction(FunctionDebugStatement function) {
        myFuncs.add(function);
    }

    public void addFunctions(Collection<FunctionDebugStatement> functions) {
        for (FunctionDebugStatement f : functions) {
            myFuncs.add(f);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("metadata !{");
        Iterator<FunctionDebugStatement> funcIt = myFuncs.iterator();
        while (funcIt.hasNext()) {
            FunctionDebugStatement f = funcIt.next();
            sb.append("metadata !" + f.getRef());
            if (funcIt.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("}");
        text = sb.toString();
        return super.toString();
    }

}
