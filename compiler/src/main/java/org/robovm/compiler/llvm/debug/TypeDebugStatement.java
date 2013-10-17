package org.robovm.compiler.llvm.debug;

/**
 * Represents a debug statement for a return type. NOTICE: At present this is a
 * 'null' class only. In the future proper handling of data types will be
 * necessary.
 * 
 * @author ndeisinger
 * 
 */
public class TypeDebugStatement extends DebugStatement {

    private int size;
    private int alignment;
    private int offset;
    private int nullRef;

    public TypeDebugStatement(int reference, int line, int nullRef) {
        super(reference, line);
        this.nullRef = nullRef;
        text = "metadata !{i32 786453, i32 0, i32 0, metadata !\"\", i32 1, i64 0, i64 0, i64 0, i32 0, null, metadata !"
                + nullRef + ", i32 0, i32 0}";
        // TODO Add actual type support here.
    }

}
