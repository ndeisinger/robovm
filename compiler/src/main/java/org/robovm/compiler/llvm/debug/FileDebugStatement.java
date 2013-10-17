package org.robovm.compiler.llvm.debug;

/**
 * A reference debug block to the context debug block.
 * @author ndeisinger
 *
 */
public class FileDebugStatement extends DebugStatement {
	
	private int contextRef;

	public FileDebugStatement(int reference, int line, int contextRef) {
		super(reference, line);
		this.contextRef = contextRef;
		text = "metadata !{i32 786473, metadata !" + contextRef + "}";
	}

}
