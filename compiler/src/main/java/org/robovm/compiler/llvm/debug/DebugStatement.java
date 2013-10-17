package org.robovm.compiler.llvm.debug;

/**
 * Superclass for various types of LLVM debug statements. Keeps a line number,
 * global debug reference, and the actual formatted metadata string.
 * 
 * In the future we may want to move line number out to relevant subclasses
 * only.
 * 
 * @author ndeisinger
 * 
 */
public abstract class DebugStatement implements Comparable<DebugStatement> {

    protected int line; // Appropriate line number
    protected int reference; // Global debug reference number
    protected String text; // Final format of text to be output

    public DebugStatement(int reference, int line) {
        if (line == 0) {
            DebugException e = new DebugException("Bad line value!");
            // e.printStackTrace();
            throw (e);
        }
        this.line = line;
        this.reference = reference;
    }

    public int getRef() {
        return reference;
    }

    /**
     * Returns the LLVM metadata block for this debug info (eg. the text at the
     * end of the LLVM file).
     */
    public String toString() {
        return "!" + reference + " = " + text;
    }

    public int compareTo(DebugStatement o) {
        return ((Integer) reference).compareTo(o.getRef());
    }
}