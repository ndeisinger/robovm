package org.robovm.compiler.llvm.debug;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Stores the debug information for a given class.
 * @author Nathan Deisinger
 *
 */
public class DebugClass {

	private NullDebugStatement nullStmt;
	private ContextDebugStatement context;
	private CompileUnitDebugStatement unit;
	private TypeDebugStatement nullType;
	private SubprogramListDebugStatement subprograms;
	private FileDebugStatement file;
	
	private HashMap<Integer, LineDebugStatement> lineStatements;
	private HashMap<String, FunctionDebugStatement> functionStatements;
	private String className;
	private String fileName;
	private String directory;
	
	public DebugClass(String className, String fileName, String directory)
	{
		this.className = className;
		this.fileName = fileName;
		this.directory = directory;
		lineStatements = new HashMap<Integer, LineDebugStatement>();
		functionStatements = new HashMap<String, FunctionDebugStatement>();	
		subprograms = new SubprogramListDebugStatement(DebugManager.getRefInteger(), -1);
		context = new ContextDebugStatement(DebugManager.getRefInteger(), -1, fileName, directory);
		nullStmt = new NullDebugStatement(DebugManager.getRefInteger(), -1);
		unit = new CompileUnitDebugStatement(DebugManager.getRefInteger(), -1, context.getRef(), subprograms.getRef(), nullStmt.getRef());
		nullType = new TypeDebugStatement(DebugManager.getRefInteger(), -1, nullStmt.getRef());
		file = new FileDebugStatement(DebugManager.getRefInteger(), -1, context.getRef());
	}
	
	public HashMap<Integer, LineDebugStatement> getLines()
	{
		return lineStatements;
	}
	
	public HashMap<String, FunctionDebugStatement> getFunctions()
	{
		return functionStatements;
	}
	
	public void addFunction(FunctionDebugStatement f)
	{
		f.setContext(context.getRef());
		f.setType(nullType.getRef());
		f.setFile(file.getRef());
		f.setNull(nullStmt.getRef());
		functionStatements.put(f.getName(), f);
		subprograms.addFunction(f);
	}
	
	public int getCompileRef()
	{
		return unit.getRef();
	}
	
	public String noCompileUnit()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(unit.toString());
		sb.append("\n");
		sb.append(nullStmt.toString());
		sb.append("\n");
		sb.append(context.toString());
		sb.append("\n");
		sb.append(file.toString());
		sb.append("\n");
		sb.append(subprograms.toString());
		sb.append("\n");
		sb.append(nullType.toString());
		sb.append("\n");
		
		//Note that it doesn't matter what order we print out function/line debug info as long as it's all there
		for (FunctionDebugStatement function : functionStatements.values())
		{
			sb.append(function.toString());
			sb.append("\n");
		}
		for (LineDebugStatement line : lineStatements.values())
		{
			sb.append(line.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * For use on individual class compilations.  Returns debug information with a compile
	 * unit list defined containing just this class.
	 */
	public String toString()
	{
		//Print out llvm.cu setup
		//Then compile unit block
		//
		StringBuilder sb = new StringBuilder();
		sb.append("!llvm.dbg.cu = !{!");
		sb.append(unit.getRef());
		sb.append("}");
		sb.append(noCompileUnit());
		return sb.toString();
	}

}
