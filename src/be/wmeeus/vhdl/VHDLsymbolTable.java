package be.wmeeus.vhdl;

import java.util.*;

/**
 * Class VHDLsymbolTable represents a symbol table in VHDL. A symbol table links symbol names
 * to objects. 
 * 
 * @author Wim Meeus
 *
 */
public class VHDLsymbolTable {
	/**
	 * The "parent" symbol table
	 */
	VHDLsymbolTable parent = null;
	
	/**
	 * The table itself
	 */
	Hashtable<String, VHDLsymbol> table = null;

	
	boolean is_entity = false;
	boolean is_arch   = false;
	boolean is_proc   = false;
	boolean is_pkg    = false;

	public VHDLsymbolTable(String h) throws VHDLexception {
		setType(h);
		if (!is_entity && !is_pkg) 
			throw new VHDLexception("*VHDLsymbolTable:setType* parent symbol table required except with entity or package");
	}

	public VHDLsymbolTable(String h, VHDLsymbolTable p) throws VHDLexception {
		parent = p;
		setType(h);
		if (!is_entity && !is_pkg && parent==null) 
			throw new VHDLexception("*VHDLsymbolTable:setType* parent symbol table required except with entity or package");
	}

	private void setType(String h) throws VHDLexception {
		boolean isset=false;
		if (h.startsWith("e"))       { is_entity = true; isset = true; }
		else if (h.startsWith("a"))  { is_arch   = true; isset = true; }
		else if (h.startsWith("pr")) { is_proc   = true; isset = true; }
		else if (h.startsWith("pa")) { is_pkg    = true; isset = true; }
		if (!isset) throw new VHDLexception("*VHDLsymbolTable:setType* invalid type " + h);
	}

	/**
	 * Adds a symbol to the symbol table
	 * @param s the symbol to add
	 * @throws VHDLexception
	 */
	public void add(VHDLsymbol s) throws VHDLexception {
		// check hierarchy: ports and generics in entity, signals in architecture, variables in process... 
		if ((s instanceof VHDLport) || (s instanceof VHDLgeneric)) {
			if (is_entity) { __add(s); return; }
			if (!is_pkg) { 
				if (parent!=null) {
					parent.add(s); return;
				} else {
					throw new VHDLexception("*VHDLsymbolTabel::add* orphaned port/generic " + s);
				}
			}
			throw new VHDLexception("*VHDLsymbolTabel::add* can't add port/generic to package: " + s);
		}
		if (s instanceof VHDLsignal) {
			if (is_arch) { __add(s); return; }
			if (is_proc) {
				if (parent!=null) {
					parent.add(s); return;
				} else {
					throw new VHDLexception("*VHDLsymbolTabel::add* orphaned signal " + s);
				}
			}
			throw new VHDLexception("*VHDLsymbolTabel::add* can't add signal to package/entity: " + s);
		}
		if (s instanceof VHDLvariable) {
			if (is_proc) { __add(s); return; }
			throw new VHDLexception("*VHDLsymbolTabel::add* can't add variable to package/entity/architecture: " + s);
		}
		
	}

	/**
	 * Adds a symbol to the symbol table
	 * @param s the symbol to add
	 */
	private void __add(VHDLsymbol s) {
		if (table==null) table = new Hashtable<String, VHDLsymbol>();
		table.put(s.name, s);
	}

	/**
	 * Gets a symbol from the symbol table
	 * @param s the name of the symbol to get
	 * @return the requested symbol, or null if a symbol with the given name wasn't found 
	 */
	public VHDLsymbol get(String s) {
		VHDLsymbol r = null;
		if (table!=null) r = table.get(s);
		if (r!=null) return r;
		if (parent!=null) return parent.get(s);
		return null;
	}

	/**
	 * Returns a String representation of this symbol table
	 */
	public String toString() {
		if (table==null) return "empty symbol table";
		String r = "";
		for (Map.Entry<String, VHDLsymbol> s: table.entrySet()) {
			r += "## " + s.getKey() + " => " + s.getValue() + "\n";
		}
		
		return r;
	}
}
