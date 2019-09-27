package be.wmeeus.vhdl;

import java.util.*;

public class VHDLfunction extends VHDLnode {

	// TODO arguments
	
	private static Hashtable<String, VHDLfunction> allfunctions = new Hashtable<String, VHDLfunction>(); 
	
	public VHDLfunction(String n, VHDLtype t) {
		name = n;
		type = t;
		allfunctions.put(n, this);
	}
	
	public VHDLfunction getFunction(String n) {
		return allfunctions.get(n);		
	}
	
	public static VHDLfunction mkFunction(String n, VHDLtype t) throws VHDLexception {
		VHDLfunction f = allfunctions.get(n);
		if (f == null) return new VHDLfunction(n, t);
		if (f.type != t) 
			throw new VHDLexception("function " + n + " found with different type: " + t + " vs. " + f.type);
		return f;
	}
	
}
