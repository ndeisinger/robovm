package org.robovm.compiler.llvm.debug;

import org.robovm.compiler.llvm.Function;
import org.robovm.compiler.llvm.Type;

/**
 * Provides a debug statement for a function. NOTICE: This will require
 * significant additional work in the future. At the moment it simply records
 * line/context information, _not_ return type/etc.
 * 
 * @author ndeisinger
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
    }

    // Gets LLVM-related info from the function
    public void getFunctionInfo(Function compFunc) {
        this.llvmName = compFunc.getName();
        this.funcPointer = compFunc.getFunctionPointer();
    }

    public void setContext(int contextRef) {
        this.contextRef = contextRef;
    }

    public void setFile(int fileRef) {
        this.fileRef = fileRef;
    }

    public void setType(int typeRef) {
        // This is a dummy function; in future we will have real type info
        // contained in the Type of our Function.
        this.typeRef = typeRef;
    }

    public void setNull(int nullRef) {
        this.nullRef = nullRef;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Returns the LLVM metadata block for this function. For further reference,
     * see http://llvm.org/docs/SourceLevelDebugging.html#subprogram-descriptors
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("metadata !{i32 786478, metadata !" + contextRef + ", "); // Tag,
                                                                            // context
        sb.append("metadata !" + fileRef + ", "); // Full file name
        sb.append("metadata !\"" + name + "\", "); // Name
        sb.append("metadata !\"" + llvmName + "\", "); // LLVM name
        sb.append("metadata !\"\", "); // MIPS Linkage name (unused)
        sb.append("i32 " + line + ", "); // Line number
        sb.append("metadata !" + typeRef + ", "); // Return type - set to dummy
                                                  // type for now

        // TODO: Set these flags appropriately.
        sb.append("i1 false, "); // True if global is local to compile unit
        sb.append("i1 true, "); // True if the global is defined in the compile
                                // unit (not extern)
        sb.append("i32 0, "); // Virtuality, e.g. dwarf::DW_VIRTUALITY__virtual
        sb.append("i32 0, "); // Index into a virtual function
        sb.append("null, "); // indicates which base type contains the vtable
                             // pointer for the derived class
        sb.append("i32 0, "); // Flags - Artifical, Private, Protected,
                              // Explicit, Prototyped.
        sb.append("i1 false, "); // Is optimized
        // End todo

        sb.append(funcPointer + ", "); // Function pointer for LLVM debug info
        sb.append("null, null, "); // TODO: Function template parameters,
                                   // function declaration descriptor.
        sb.append("metadata !" + nullRef + ", "); // List of function variables.
                                                  // Leave null for now.
        sb.append("i32 " + line); // TODO: Need to get opening { line number
        sb.append("}");
        text = sb.toString();
        return super.toString();
    }

}
