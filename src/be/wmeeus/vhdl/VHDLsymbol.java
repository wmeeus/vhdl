package be.wmeeus.vhdl;

import java.util.*;

public class VHDLsymbol extends VHDLnode {
	VHDLtype type = null;
	
	public String toString() {
		return name;
	}
	
	public String toStringDef() {
		return "--fishy";
		// TODO throw exception
	}
	
	public String toStringDef(int l) {
		return "--fishy";
		// TODO throw exception
	}

	public void setType(VHDLtype t) {
		type = t;
	}
	
	public VHDLtype getType() {
		return type;
	}
	
	public static VHDLsymbolTable table = null;
	public static void add(VHDLsymbol s) throws VHDLexception {
		// TODO protect against redefinition
		if (table!=null) table.add(s); // enable symbol creation without addition to any symbol table by setting table to null
	}
	public static VHDLsymbol get(String s) {
		if (table!=null) return table.get(s);
		return null;
	}
	public static VHDLsymbolTable newTable(String h) throws VHDLexception {
		table = new VHDLsymbolTable(h);
		return table;
	}
	public static VHDLsymbolTable childTable(String h) throws VHDLexception {
		if (table==null) throw new VHDLexception("*VHDLsymbol* Table is null, cannot create child table");
		table = new VHDLsymbolTable(h, table.parent);
		return table;
	}
	public static VHDLsymbolTable parentTable() throws VHDLexception {
		if (table==null) throw new VHDLexception("*VHDLsymbol* Table is null, cannot return to parent table");
		if (table.parent==null) throw new VHDLexception("*VHDLsymbol* Parent is null, cannot return to parent table");
		table = table.parent;
		return table;
	}
	
	public static Hashtable<String, VHDLsymbol> transtable = null;
	public static VHDLsymbol lookup(String s) {
		VHDLsymbol r = get(s);
		if (r!=null) return r;
		if (transtable!=null) {
			return transtable.get(s);
		}
		return null;
	}
	
}
