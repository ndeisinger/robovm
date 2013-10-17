package org.robovm.compiler.llvm.debug;

public abstract class DebugStatement implements Comparable<DebugStatement>
{
	//Can be a first block (is that constant?), function statement, scoping statement, or line number statement
	
	protected int line; //Appropriate line number 
	protected int reference; //Global debug reference number
	protected String text; //Final format of text to be output
	
	public DebugStatement(int reference, int line)
	{
		if (line == 0)
		{
			RuntimeException e = new RuntimeException("Bad line value!");
			//e.printStackTrace();
			throw(e);
		}
		this.line = line;
		this.reference = reference;
	}
	
	public int getRef()
	{
		return reference;
	}
	
	public String toString()
	{
		return "!" + reference + " = " + text;
	}
	
	public int compareTo(DebugStatement o)
	{
		return ((Integer)reference).compareTo(o.getRef());
	}
}