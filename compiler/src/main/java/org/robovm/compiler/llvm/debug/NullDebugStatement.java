package org.robovm.compiler.llvm.debug;

/**
 * A special debug statement that serves as a null value.
 * @author Nathan Deisinger
 *
 */
public class NullDebugStatement extends DebugStatement {

	public NullDebugStatement(int reference, int line) {
		super(reference, line);
		text = "metadata !{i32 0}";
		// TODO Auto-generated constructor stub
	}

}
