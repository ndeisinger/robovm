package org.robovm.compiler.llvm.debug;

import java.util.HashMap;
import org.robovm.compiler.llvm.Function;

/**
 * Manages line-, function-, and file-level debug information. Does not provide
 * support for variables/etc.
 * 
 * @author ndeisinger
 * 
 */
public class DebugManager {

    private static boolean activeDebug = true; // Will be true when debug info
                                               // is being collected. Disabled
                                               // during linking/etc.
    private static int currLine = 1;
    private static String currFunc = null;
    private static String currFile = null;
    private static String currDir = null;
    private static int debugCount = 1;
    private static DebugClass currClass = null;
    private static HashMap<String, DebugClass> classMap = new HashMap<String, DebugClass>();

    public static void initDebug() {
        // Obsolete function; kept here in case we need to do special init work
        // later.
    }

    public static void updateLine(int line) {
        if (line == 0) {
            throw new DebugException(
                    "Debug: source defined as line number zero.");
        }
        currLine = line;
    }

    public static void updateFunc(String func) {
        currFunc = func;
    }

    public static DebugClass updateContext(String className, String file,
            String directory) {
        currFile = file;
        currDir = directory;
        currClass = new DebugClass(className, file, directory);
        classMap.put(className, currClass);
        return currClass;
    }

    /**
     * Allows DebugClasses to set up their own debug statements.
     * 
     * @return A new integer reference to a debug statement.
     */
    public static int getRefInteger() {
        if (!activeDebug)
            return -1;
        int retVal = debugCount;
        debugCount++;
        return retVal;
    }

    public static int getFuncRef(Function func) {
        if (!activeDebug)
            return -1;

        FunctionDebugStatement myDebug = currClass.getFunctions().get(currFunc);
        if (myDebug == null) {
            myDebug = new FunctionDebugStatement(debugCount, currLine, currFunc);
            myDebug.getFunctionInfo(func);
            currClass.addFunction(myDebug);
            debugCount++;
        }
        return myDebug.getRef();
    }

    public static int getLineRef() {
        if (currLine == -1)
            return -1; // Don't make debug statements if no source info
                       // available
        if (!activeDebug)
            return -1;

        FunctionDebugStatement myFunc = currClass.getFunctions().get(currFunc);
        LineDebugStatement myDebug = currClass.getLines().get(currLine);
        if (myDebug == null) {
            if (myFunc == null) {
                // No containing function found. This shouldn't happen.
                throw new DebugException(
                        "Debug: No containing function found for current line.");
            } else {
                myDebug = new LineDebugStatement(debugCount, currLine,
                        myFunc.getRef());
            }
            currClass.getLines().put(currLine, myDebug);
            debugCount++;
        }
        return myDebug.getRef();
    }

    public static void toggleDebug(boolean doDebug) {
        activeDebug = doDebug;
    }

    // TODO: Potential for clashes between classes with same name.
    // Since we compile classes one at a time and then store them
    // in the appropriate module, this shouldn't be a problem,
    // but we should be aware.
    public static DebugClass getClass(String className) {
        return classMap.get(className);
    }

    /**
     * For debugging purposes (as in, debugging the debug information, not what
     * we're putting to LLVM.)
     * 
     * @return A summary of the current DebugManager environment.
     */
    public static String getEnv() {
        return ("currDir: " + currDir + "\n currFile: " + currFile
                + "\n currFunc: " + currFunc + "\n currLine: " + currLine);
    }
}
