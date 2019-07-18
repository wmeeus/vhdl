package be.wmeeus.vhdl;

import java.util.*;

/**
 * Class VHDLsymbol represents a symbol (variable, signal, port, generic...) in VHDL.
 * @author Wim Meeus
 *
 */
public class VHDLsymbol extends VHDLnode {
	/**
	 * Symbol type
	 */
	VHDLtype type = null;
	
	/**
	 * Return a String representation of this symbol
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Return a String definition of this symbol. This method should be called on subclasses only.
	 * @return a String definition of this symbol.
	 */
	public String toStringDef() {
		return "--fishy";
		// TODO throw exception
	}
	
	/**
	 * Return a String definition of this symbol. This method should be called on subclasses only. 
	 * Used for pretty-printing.
	 * @param l the with of the column with symbol names
	 * @return a String definition of this symbol.
	 */
	public String toStringDef(int l) {
		return "--fishy";
		// TODO throw exception
	}

	/**
	 * Sets the type of this symbol
	 * @param t the type
	 */
	public void setType(VHDLtype t) {
		type = t;
	}
	
	/**
	 * Returns the type of this symbol
	 * @return the type of this symbol
	 */
	public VHDLtype getType() {
		return type;
	}
	
	/**
	 * The current symbol table.
	 */
	public static VHDLsymbolTable table = null;
	
	/**
	 * Adds a symbol to the symbol table
	 * @param s the symbol to add
	 * @throws VHDLexception
	 */
	public static void add(VHDLsymbol s) throws VHDLexception {
		// TODO protect against redefinition
		if (table!=null) table.add(s); // enable symbol creation without addition to any symbol table by setting table to null
	}
	
	/**
	 * Get a symbol from the symbol table
	 * @param s the name of the requested symbol
	 * @return the requested symbol, or null if the table doesn't contain a symbol with the given name
	 */
	public static VHDLsymbol get(String s) {
		if (table!=null) return table.get(s);
		return null;
	}
	
	/**
	 * Construct and return a new symbol table
	 * @param h 
	 * @return the new symbol table
	 * @throws VHDLexception
	 */
	public static VHDLsymbolTable newTable(String h) throws VHDLexception {
		table = new VHDLsymbolTable(h);
		return table;
	}
	
	/**
	 * Construct a new symbol table as a child of another symbol table
	 * @param h
	 * @return the new symbol table
	 * @throws VHDLexception
	 */
	public static VHDLsymbolTable childTable(String h) throws VHDLexception {
		if (table==null) throw new VHDLexception("*VHDLsymbol* Table is null, cannot create child table");
		table = new VHDLsymbolTable(h, table.parent);
		return table;
	}
	
	/**
	 * Returns to the parent of the current symbol table
	 * @return the parent of the current symbol table
	 * @throws VHDLexception if either the current symbol table is null, or if its parent table is null 
	 */
	public static VHDLsymbolTable parentTable() throws VHDLexception {
		if (table==null) throw new VHDLexception("*VHDLsymbol* Table is null, cannot return to parent table");
		if (table.parent==null) throw new VHDLexception("*VHDLsymbol* Parent is null, cannot return to parent table");
		table = table.parent;
		return table;
	}

	/**
	 * A translation table, linking Strings (names) to symbols.
	 */
	public static Hashtable<String, VHDLsymbol> transtable = null;
	
	/**
	 * Look up a symbol either (...) or in the translation table
	 * @param s the name of the symbol
	 * @return the requested symbol, or null if a symbol with the given name isn't found
	 */
	public static VHDLsymbol lookup(String s) {
		VHDLsymbol r = get(s);
		if (r!=null) return r;
		if (transtable!=null) {
			return transtable.get(s);
		}
		return null;
	}
	
}
