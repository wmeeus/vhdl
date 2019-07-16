package be.wmeeus.vhdl;
import java.util.*;

/**
 * Class VHDLentity represents en entity in VHDL. The toString() method generates VHDL code. 
 * @author wmeeus
 *
 */
public class VHDLentity extends VHDLnode {
	/**
	 * Architectures of this entity
	 */
	ArrayList<VHDLarchitecture> architectures = new ArrayList<VHDLarchitecture>();
	
	/**
	 * Generics of this entity
	 */
	ArrayList<VHDLgeneric>           generics = new ArrayList<VHDLgeneric>();
	
	/**
	 * Ports of this entity
	 */
	ArrayList<VHDLport>                 ports = new ArrayList<VHDLport>();
	
	/**
	 * Libraries required by this entity. Libraries ieee and work are available by default and
	 * should not be included.
	 */
	ArrayList<String>               libraries = new ArrayList<String>();
	
	/**
	 * Packages required by this entity. ieee.std_logic_1164 and ieee.numeric_std are available
	 * by default and should not be included.
	 */
	ArrayList<String>                packages = new ArrayList<String>();
	
	/**
	 * The sybmol table of this entity
	 */
	VHDLsymbolTable               symboltable = null;

	/**
	 * Returns the symbol table
	 * @return the symbol table
	 */
	public VHDLsymbolTable getSymboltable() {
		return symboltable;
	}
	
	/**
	 * Returns the list of ports in this entity
	 * @return the list of ports in this entity
	 */
	public ArrayList<VHDLport> getPorts() {
		return ports;
	}

	/**
	 * Returns the list of generics in this entity
	 * @return the list of generics in this entity
	 */
	public ArrayList<VHDLgeneric> getGenerics() {
		return generics;
	}

	/**
	 * Indicates whether the toString method will include architectures in its output.
	 */
	static boolean includearchitecture = true;
	
	/**
	 * Configures whether the toString method will include architectures in its output.
	 * @param b true if the toString method needs to include architectures in its output.
	 */
	public static void includeArchitecture(boolean b) {
		includearchitecture = b;
	}
	
	/**
	 * Construct an empty entity 
	 * @param n the entity name
	 * @throws VHDLexception
	 */
	public VHDLentity(String n) throws VHDLexception {
		name = n;
		libraries.add("ieee");
		packages.add("ieee.std_logic_1164");
		packages.add("ieee.numeric_std");
		symboltable = new VHDLsymbolTable("entity");
	}
	
	/**
	 * Add a package to this entity
	 * @param l library name
	 * @param p package name
	 */
	public void addPackage(String l, String p) {
		if (!libraries.contains(l)) libraries.add(l);
		if (!packages.contains(l+"."+p)) packages.add(l+"."+p);
	}

	/**
	 * Add a package to this entity
	 * @param s the package in <library>.<package> format. If only the package name is given, 
	 *   library work is assumed.
	 * @throws VHDLexception
	 */
	public void addPackage(String s) throws VHDLexception {
		if (s == null) return;
		s = s.trim();
		if (s.endsWith(";")) s = s.substring(0, s.length()-1);
		int dot = s.indexOf(".");
		if (dot<1) {
			addPackage("work", s);
		} else {
			addPackage(s.substring(0, dot).trim(), s.substring(dot+1).trim());
		}
	}

	/**
	 * Add an element to the entity. The element can be either a generic, a port, an architecture 
	 * or a package. The kind of element is derived from its object class.
	 * @param n the element to add.
	 * @throws VHDLexception
	 */
	public void add(VHDLnode n) throws VHDLexception {
		if (n instanceof VHDLpackage) {
			String pn = n.getName();
			if (pn.indexOf(".") < 1) {
				addPackage("work", pn);
			} else {
				addPackage(pn);
			}
			return;
		}
		if (n instanceof VHDLarchitecture) {
			architectures.add((VHDLarchitecture)n);
			return;
		}
		if (n instanceof VHDLgeneric) {
			generics.add((VHDLgeneric)n);
			symboltable.add((VHDLsymbol)n);
			return;
		}
		if (n instanceof VHDLport) {
			ports.add((VHDLport)n);
			symboltable.add((VHDLsymbol)n);
			return;
		}
		throw new VHDLexception("*VHDLentity::add* Don't know how to add " + n.getClass().getName());
	}

	/**
	 * Get a symbol from the symbol table
	 * @param s the symbol name
	 * @return the requested symbol, or null if this entity doesn't contain a symbol with that name 
	 */
	public VHDLsymbol get(String s) {
		return symboltable.get(s);
	}
	
	/**
	 * Indicates whether this entity contains a particular symbol
	 * @param s the symbol name
	 * @return true if this entity contains the requested symbol
	 */
	public boolean has(String s) {
		return (get(s)!=null);
	}

	/**
	 * Produce and return a String with the VHDL "library" and "use" statements
	 * @return the VHDL "library" and "use" statements
	 */
	public String toStringLibsPkgs() {
		String r = "library ";
		boolean first=true;
		for (String s: libraries) {
			if (s.equals("work")) continue;
			if (!first) r += ", ";
			r += s;
			first = false;
		}
		r += ";\n";
		for (String s: packages) {
			r += "use " + s + ".all;\n";
		}
		r += "\n";
		return r;
	}

	/**
	 * Returns the VHDL code for this entity. Whether the architecture(s) is/are included depends on
	 * the includearchitecture field
	 */
	public String toString() {
		String r = toStringLibsPkgs() + "entity " + name + " is\n";
		// generics?
		if (!generics.isEmpty()) {
			int l = maxGenericLength();
			r += "  generic(\n";
			boolean prev = false;
			for (VHDLgeneric g: generics) {
				if (prev) r += ";\n";
				prev = true;
				r += "    " + g.toStringDef(l);
			}
			r += "\n  );\n";
		}
		// port?
		if (!ports.isEmpty()) {
			int l = maxPortLength();
			r += "  port(\n";
			boolean prev = false;
			for (VHDLport p: ports) {
				if (prev) r += ";\n";
				prev = true;
				r += "    " + p.toStringDef(l);
			}
			r += "\n  );\n";
		}
		r += "end " + name + ";\n\n";
		
		if (includearchitecture) {
			for (VHDLarchitecture a: architectures) 
				r += a + "\n";
		} else {
			r += "-- architecture in separate file\n";
		}
		return r;
	}

	/**
	 * Returns the length of the longest generic name in this entity
	 * @return the length of the longest generic name in this entity
	 */
	public int maxGenericLength() {
		int l = 0;
		for (VHDLgeneric g: generics) {
			l = Math.max(l, g.name.length());
		}
		return l;
	}
	
	/**
	 * Returns the length of the longest port name in this entity
	 * @return the length of the longest port name in this entity
	 */
	public int maxPortLength() {
		int l = 0;
		for (VHDLport g: ports) {
			l = Math.max(l, g.name.length());
		}
		return l;
	}

	/**
	 * Returns the list of architectures of this entity
	 * @return the list of architectures of this entity
	 */
	public ArrayList<VHDLarchitecture> getArchitectures() {
		return architectures;
	}
	
}
