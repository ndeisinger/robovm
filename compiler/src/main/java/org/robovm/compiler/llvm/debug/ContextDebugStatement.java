package org.robovm.compiler.llvm.debug;

/**
 * Stores the context (file/directory) for a given class.
 * 
 * @author ndeisinger
 * 
 */
public class ContextDebugStatement extends DebugStatement {

    private String file;
    private String directory;

    public ContextDebugStatement(int reference, int line, String file,
            String directory) {
        super(reference, line);
        this.file = file;
        this.directory = directory;
        text = "metadata !{metadata !\"" + file + "\", metadata !\""
                + directory + "\"}";
    }

}
