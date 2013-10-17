package org.robovm.compiler.llvm.debug;

/**
 * Extends the abstract DebugStatement class to contain information for a line number debug statement.
 * 
 * The statement is of the form "metadata !{i32 [line number], i32 0, i32 ![scope reference], null}".
 * @author ndeisinger
 *
 */
public class LineDebugStatement extends DebugStatement {

	private int scopeRef;
	
	/**
	 * Constructor for a line debug statement.
	 * @param reference The global debug reference number for this statement.
	 * @param line The line number being referred to.
	 * @param scopeRef The reference number of the containing scope.  At the moment this is equivalent to
	 * the containing function; in the future this should point to an enclosing lexical block.
	 */
	public LineDebugStatement(int reference, int line, int scopeRef) {
		super(reference, line);
		this.scopeRef = scopeRef;
		this.text = "metadata !{i32 " + line + ", i32 0, metadata !" + scopeRef + ", null}";
	}

}
