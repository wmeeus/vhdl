package be.wmeeus.vhdl;
import java.util.*;

public class VHDLentity extends VHDLnode {
	ArrayList<VHDLarchitecture> architectures = new ArrayList<VHDLarchitecture>();
	ArrayList<VHDLgeneric>           generics = new ArrayList<VHDLgeneric>();
	ArrayList<VHDLport>                 ports = new ArrayList<VHDLport>();
	ArrayList<String>               libraries = new ArrayList<String>();
	ArrayList<String>                packages = new ArrayList<String>();
	VHDLsymbolTable               symboltable = null;
	
	public VHDLsymbolTable getSymboltable() {
		return symboltable;
	}
	
	public ArrayList<VHDLport> getPorts() {
		return ports;
	}

	public ArrayList<VHDLgeneric> getGenerics() {
		return generics;
	}

	static boolean includearchitecture = true;
	public static void includeArchitecture(boolean b) {
		includearchitecture = b;
	}
	
	public VHDLentity(String n) throws VHDLexception {
		name = n;
		libraries.add("ieee");
		packages.add("ieee.std_logic_1164");
		packages.add("ieee.numeric_std");
		symboltable = new VHDLsymbolTable("entity");
	}
	
	public void addPackage(String l, String p) {
		if (!libraries.contains(l)) libraries.add(l);
		if (!packages.contains(l+"."+p)) packages.add(l+"."+p);
	}

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
	
	public VHDLsymbol get(String s) {
		return symboltable.get(s);
	}
	
	public boolean has(String s) {
		return (get(s)!=null);
	}
	
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
	
	public int maxGenericLength() {
		int l = 0;
		for (VHDLgeneric g: generics) {
			l = Math.max(l, g.name.length());
		}
		return l;
	}
	
	public int maxPortLength() {
		int l = 0;
		for (VHDLport g: ports) {
			l = Math.max(l, g.name.length());
		}
		return l;
	}

	public ArrayList<VHDLarchitecture> getArchitectures() {
		return architectures;
	}
	
}
