package org.robovm.compiler.llvm.debug;

/**
 * Stores the compile-unit information for a given class.
 * @author ndeisinger
 *
 */
public class CompileUnitDebugStatement extends DebugStatement {

	private int contextRef;
	private int subprogramListRef;
	private int nullRef;
	
	public CompileUnitDebugStatement(int reference, int line, int contextRef, int subprogramListRef, int nullRef) {
		super(reference, line);
		this.contextRef = contextRef;
		this.subprogramListRef = subprogramListRef;
		this.nullRef = nullRef;
		
		StringBuilder sb = new StringBuilder();
		sb.append("metadata !{i32 786449, "); //Initial tag
		sb.append("metadata !" + contextRef + ", "); //Source context
		sb.append("i32 11, "); //Language identifier (DW_TAG_JAVA)
		sb.append("metadata ! \"RoboVM version 0.0.6\", "); //Compiler version; TODO: get appropriate version rather than hard-code
		sb.append("i1 0, "); //TODO: True if optimized
		sb.append("metadata !\"\", "); //TODO: Compilation flags
		sb.append("i32 0, "); //Runtime version
		sb.append("metadata !" + nullRef + ", "); //List of enum types
		sb.append("metadata !" + nullRef + ", "); //List of retained types
		sb.append("metadata !" + subprogramListRef + ", "); //List of subprograms
		sb.append("metadata !" + nullRef + ", "); //List of global variables
		sb.append("metadata !" + nullRef + ", "); //List of imported entities
		sb.append("metadata !\"\""); //Split debug filename
		sb.append("}");
		
		text = sb.toString();
	}
	

}
