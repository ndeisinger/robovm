package org.robovm.compiler.llvm.debug;

public class DebugException extends RuntimeException {

	public DebugException() {
	}

	public DebugException(String detailMessage) {
		super(detailMessage);
	}

	public DebugException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public DebugException(Throwable throwable) {
		super(throwable);
	}

}
