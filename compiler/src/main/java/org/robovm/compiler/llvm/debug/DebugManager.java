/* This class written by Nathan Deisinger (ndeisinger@wisc.edu) for personal use in
 * research at the University of Wisconsin-Madison.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl-2.0.html>.
 */

package org.robovm.compiler.llvm.debug;

import java.util.HashMap;
import org.robovm.compiler.llvm.Function;

/**
 * Manages line-, function-, and file-level debug information for research purposes.
 * Does not provide support for variables/etc.
 * @author Nathan Deisinger
 *
 */
public class DebugManager {

	private static boolean activeDebug = true; //Will be true when debug info is being collected.  Disabled during linking/etc.
	private static int currLine = 1;
	private static String currFunc = null;
	private static String currFile = null;
	private static String currDir = null;
	private static int debugCount = 1;
	private static DebugClass currClass = null;
	private static HashMap<String, DebugClass> classMap = new HashMap<String, DebugClass>();
		
	public static void initDebug()
	{
		//Obsolete function; kept here in case we need to do special init work later.
	}
	
	public static void updateLine(int line)// throws Exception
	{
		if (line == 0 || line == -1)
		{
			DebugException e = new DebugException("Debug info: Bad line value! " + line);
			throw(e);
		}
		currLine = line;
	}
	
	public static void updateFunc(String func)
	{
		currFunc = func;
	}
	
	public static DebugClass updateContext(String className, String file, String directory)
	{
		currFile = file;
		currDir = directory;
		currClass = new DebugClass(className, file, directory);
		classMap.put(className, currClass);
		return currClass;
	}
	
	/**
	 * Allows DebugClasses to set up their own debug statements.
	 * @return
	 */
	public static int getRefInteger()
	{
		if (!activeDebug) return -1;
		int retVal = debugCount;
		debugCount++;
		return retVal;
	}
	
	public static int getFuncRef(Function func)
	{
		if (!activeDebug) return -1;
		
		FunctionDebugStatement myDebug = currClass.getFunctions().get(currFunc);
		if (myDebug == null)
		{
			//TODO: Get proper line info.  Soot does not appropriately mark function lines (or I'm not finding them).
			myDebug = new FunctionDebugStatement(debugCount, 1, currFunc);
			//myDebug = new FunctionDebugStatement(debugCount, currLine, currFunc);
			myDebug.getFunctionInfo(func);
			currClass.addFunction(myDebug);
			debugCount++;
		}		
		return myDebug.getRef();
	}
	
	public static int getLineRef()
	{
		if (!activeDebug) return -1;
		/*
		DebugClass myClass = classMap.get(currFile);
		if (myClass == null)
		{
			System.out.println("Creating class " + currFile);
			myClass = new DebugClass(null, currFile, currDir);
			classMap.put(currFile, myClass);
		}*/
		FunctionDebugStatement myFunc = currClass.getFunctions().get(currFunc);
		LineDebugStatement myDebug = currClass.getLines().get(currLine);
		if (myDebug == null)
		{
			if (myFunc == null)
			{
				System.out.println("Warning: No containing function found");
				System.out.println(getEnv());
				myDebug = new LineDebugStatement(debugCount, currLine, 0);
			}
			else
			{
				myDebug = new LineDebugStatement(debugCount, currLine, myFunc.getRef());
			}
			currClass.getLines().put(currLine, myDebug);
			debugCount++;
		}		
		return myDebug.getRef();
	}
	
	public static void toggleDebug(boolean doDebug)
	{
		activeDebug = doDebug;
	}
	
	public static DebugClass getClass(String filename)
	{
		return classMap.get(filename);
	}
	
	/**
	 * Call this just before writing out debug information.  It will set up the appropriate debug
	 * blocks that depend on others.  eg. function blocks.
	 */
	public static void finalizeDebug()
	{
		
	}
	
	public static String getEnv()
	{
		return ("currDir: " + currDir +"\n currFile: " + currFile + "\n currFunc: " + currFunc + "\n currLine: " + currLine);
	}
}
