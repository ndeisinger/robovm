package org.robovm.compiler.llvm.debug;

public class FileDebugStatement extends DebugStatement {
	
	private int contextRef;

	public FileDebugStatement(int reference, int line, int contextRef) {
		super(reference, line);
		this.contextRef = contextRef;
		text = "metadata !{i32 786473, metadata !" + contextRef + "}";
		// TODO Add proper DWARF tag here -requires bringing in filename+directory.
	}

}
