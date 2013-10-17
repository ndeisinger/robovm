package org.robovm.compiler.llvm.debug;
import org.robovm.compiler.llvm.Function;
import org.robovm.compiler.llvm.Type;

/**
 * Provides a debug statement for a function.  
 * NOTICE: This will require significant additional work in the future.
 * At the moment it simply records line/context information, _not_ return type/etc.
 * @author Nathan Deisinger
 *
 */
public class FunctionDebugStatement extends DebugStatement {
	
	private String name;
	private String llvmName;
	private String funcPointer;
	private int contextRef;
	private int fileRef;
	private int nullRef;
	private int typeRef;

	public FunctionDebugStatement(int reference, int line, String name) {
		super(reference, line);
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	
	public void getFunctionInfo(Function compFunc)
	{
		this.llvmName = compFunc.getName();
		this.funcPointer = compFunc.getFunctionPointer();
	}
	
	public void setContext(int contextRef)
	{
		this.contextRef = contextRef;
	}
	
	public void setFile(int fileRef)
	{
		this.fileRef = fileRef;
	}
	
	public void setType(int typeRef)
	{
		//This is a dummy function; in future we will have real type info contained in the Type of our Function.
		this.typeRef = typeRef;
	}
	
	public void setNull(int nullRef)
	{
		this.nullRef = nullRef;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString()
	{
		//!4 = metadata !{i32 786478, metadata !1, metadata !5, metadata !"a", metadata !"a", metadata !"", 
		//i32 4, metadata !6, i1 false, i1 true, i32 0, i32 0, null, i32 0, i1 false, i32 ()* @a, null, null, metadata !2, i32 5} ; [ DW_TAG_subprogram ] [line 4] [def] [scope 5] [a]
		StringBuilder sb = new StringBuilder();
		sb.append("metadata !{i32 786478, metadata !" + contextRef + ", "); //Tag, context
		sb.append("metadata !" + fileRef + ", "); //Full file name
		sb.append("metadata !\"" + name + "\", "); //Name
		sb.append("metadata !\"" + llvmName + "\", "); //Display name
		sb.append("metadata !\"\", "); //MIPS Linkage name (unused)
		sb.append("i32 " + line + ", "); //Line number
		sb.append("metadata !" + typeRef + ", "); //Return type - set to null for now, will use dummy type soon
		sb.append("i1 false, i1 true, i32 0, i32 0, null, i32 0, i1 false, "); //Various flags
		sb.append(funcPointer + ", "); //Function pointer for LLVM debug info
		sb.append("null, null, "); //Function template parameters, function declaration descriptor
		sb.append("metadata !" + nullRef + ", "); //List of function variables
		sb.append("i32 " + line);//TODO: Need to get opening { line number
		//
		sb.append("}");
		text = sb.toString();
		return super.toString();
	}

}
