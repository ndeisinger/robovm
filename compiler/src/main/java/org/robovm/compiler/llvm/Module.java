/*
 * Copyright (C) 2012 Trillian AB
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
package org.robovm.compiler.llvm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.robovm.compiler.llvm.debug.DebugClass;

import org.apache.commons.io.IOUtils;

/**
 *
 * @version $Id$
 */
public class Module {
    private final List<URL> includes;
    private final List<Global> globals;
    private final List<Alias> aliases;    
    private final List<Function> functions;
    private final List<FunctionDeclaration> functionDeclarations;
    private final List<UserType> types;
    private final List<String> asm;
    private final List<DebugClass> debugs;

    public Module(List<URL> includes, List<UserType> types,
            List<Global> globals, List<Alias> aliases,
            List<FunctionDeclaration> functionDeclarations, List<String> asm,
            List<Function> functions) {
        
        this.includes = includes;
        this.types = types;
        this.globals = globals;
        this.aliases = aliases;
        this.functionDeclarations = functionDeclarations;
        this.asm = asm;
        this.functions = functions;
        this.debugs = null;
    }
    
    public Module(List<URL> includes, List<UserType> types,
            List<Global> globals, List<Alias> aliases,
            List<FunctionDeclaration> functionDeclarations, List<String> asm,
            List<Function> functions, List<DebugClass> debugs) {
        
        this.includes = includes;
        this.types = types;
        this.globals = globals;
        this.aliases = aliases;
        this.functionDeclarations = functionDeclarations;
        this.asm = asm;
        this.functions = functions;
        this.debugs = debugs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (URL g : includes) {
            InputStream in = null;
            try {
                in = g.openStream();
                sb.append(IOUtils.toString(in, "UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(in);
            }
            sb.append("\n");
        }
        sb.append("\n");
        for (String s : asm) {
            sb.append("module asm \"");
            sb.append(s);
            sb.append("\"\n");
        }
        sb.append("\n");
        for (UserType type : types) {
            sb.append(type.getAlias());
            sb.append(" = type ");
            sb.append(type.getDefinition());
            sb.append("\n");
        }
        sb.append("\n");
        for (FunctionDeclaration fd : functionDeclarations) {
            sb.append(fd.toString());
            sb.append("\n");
        }
        sb.append("\n");
        for (Global g : globals) {
            sb.append(g.getDefinition());
            sb.append("\n");
        }
        sb.append("\n");
        for (Alias a : aliases) {
            sb.append(a.getDefinition());
            sb.append("\n");
        }
        sb.append("\n");
        for (Function f : functions) {
            sb.append(f.toString());
            sb.append("\n");
        }
        //The below supplies support for multiple compile units in one LLVM file.
        //I am uncertain if this functionality is ever used.
        if (debugs != null)
        {
        	int[] compileRefs = new int[debugs.size()];
        	int i = 0;
        	for (DebugClass d : debugs)
        	{
        		sb.append(d.debugBlocks());
                sb.append("\n");
                compileRefs[i] = d.getCompileRef();
        	}
        	sb.append("!llvm.dbg.cu = !{");
        	for (i = 0; i < compileRefs.length; i++)
        	{
        		if (i != (compileRefs.length - 1))
        		{
        			sb.append("!" + compileRefs[i] + ", ");
        		}
        		else
        		{
        			sb.append("!" + compileRefs[i]);
        		}
        	}
    		sb.append("}");
            sb.append("\n");
        }
        return sb.toString();
    }
}
