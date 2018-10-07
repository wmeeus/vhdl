package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

public class VHDLarchitecture extends VHDLnode {

	VHDLentity entity = null;
	ArrayList<VHDLsymbol> symbols = new ArrayList<VHDLsymbol>();
	VHDLsymbolTable symboltable   = null;
	ArrayList<VHDLnode> body      = new ArrayList<VHDLnode>();

	public VHDLarchitecture(String n, VHDLentity e) throws VHDLexception {
		name = n;
		entity = e;
		if (e!=null) {
			e.add(this);
			symboltable = new VHDLsymbolTable("arch", e.symboltable);
		}
	}

	public void add(VHDLnode n) throws VHDLexception {
		if (n instanceof VHDLsymbol) {
			symbols.add((VHDLsymbol)n);
			symboltable.add((VHDLsymbol)n);
		} else {
			body.add(n);
		}
	}

	public String toString() {
		String r = "";
		if (!VHDLentity.includearchitecture) 
			r += entity.toStringLibsPkgs();
		r += "architecture " + name + " of " + entity.name + " is\n";
		// print symbol table
		PP.down();
		if (!symbols.isEmpty()) {
			int l = maxSymbolLength();
			for (VHDLsymbol s: symbols)
				r += PP.I + s.toStringDef(l) + ";\n";
		}
		r += "\nbegin\n";
		// print body
		for (VHDLnode n: body) 
			r += n;
		PP.up();
		return r + "\nend " + name + ";\n";
	}

	public VHDLsymbol get(String s) {
		return symboltable.get(s);
	}

	public VHDLsymbol up(VHDLinstance i, String p) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::up* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");
		entity.add(s);
		i.map(p, s);
		return s;
	}

	public VHDLsymbol up(VHDLinstance i, String p, VHDLtype t) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::up* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");
		if (s instanceof VHDLport) {
			s = new VHDLport(s.name, ((VHDLport) s).direction, t);
		} else {
			// TODO trouble
		}
		entity.add(s);
		i.map(p, s);
		return s;
	}

	public VHDLsymbol up(VHDLinstance i, String p, String infix) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::up* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");
		String np=null;
		if (p.substring(1, 2).equals("_")) {
			// insert infix
			np = p.substring(0, 2) + infix + "_" + p.substring(2);
		} else {
			// prepend infix
			np = infix + p;
		}
		VHDLsymbol ns = null;
		if (s instanceof VHDLgeneric) {
			VHDLgeneric g = (VHDLgeneric) s;
			ns = new VHDLgeneric(np, g.type, g.defaultvalue);
		}
		if (s instanceof VHDLport) {
			VHDLport pp = (VHDLport) s;
			ns = new VHDLport(np, pp.direction, pp.type);
		}

		entity.add(ns);
		i.map(p, ns);
		return ns;
	}

	public VHDLsymbol upname(VHDLinstance i, String p, String n) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::upname* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");

		VHDLsymbol ns = entity.get(n);
		if (ns==null) {
			if (s instanceof VHDLgeneric) {
				VHDLgeneric g = (VHDLgeneric) s;
				ns = new VHDLgeneric(n, g.type, g.defaultvalue);
			}
			if (s instanceof VHDLport) {
				VHDLport pp = (VHDLport) s;
				ns = new VHDLport(n, pp.direction, pp.type);
			}
			entity.add(ns);
		}

		i.map(p, ns);
		return ns;
	}

	public VHDLsymbol up(VHDLinstance i, String p, String infix, VHDLtype t) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::up* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");
		String np=null;
		if (p.substring(1, 2).equals("_")) {
			// insert infix
			np = p.substring(0, 2) + infix + "_" + p.substring(2);
		} else {
			// prepend infix
			np = infix + p;
		}
		VHDLsymbol ns = null;
		if (s instanceof VHDLgeneric) {
			VHDLgeneric g = (VHDLgeneric) s;
			ns = new VHDLgeneric(np, t, g.defaultvalue);
		}
		if (s instanceof VHDLport) {
			VHDLport pp = (VHDLport) s;
			ns = new VHDLport(np, pp.direction, t);
		}

		entity.add(ns);
		i.map(p, ns);
		return ns;
	}

	public VHDLsymbol mapLocal(VHDLinstance i, String p) throws VHDLexception {
		if (i==null) throw new VHDLexception("Instance is null");
		if (p==null) throw new VHDLexception("Signal is null");
		String sname = null;
		if (p.substring(1, 2).equals("_")) {
			sname = "s_" + p;
		} else {
			sname = p;
		}
		VHDLsymbol s = get(sname);
		if (s==null) {
			// signal 
			VHDLsymbol ip = i.entity.get(p);
			if (ip==null) throw new VHDLexception("Could not find port " + p + " of entity " + i.entity);
			s = new VHDLsignal(sname, ip.type);
			symbols.add(s);
		}
		i.map(p, s);
		return s;
	}

	public VHDLsymbol mapLocal(VHDLinstance i, String p, String sname) throws VHDLexception {
		VHDLsymbol s = get(sname);
		if (s==null) {
			// signal 
			VHDLsymbol ip = i.entity.get(p);
			if (ip == null) {
				throw new VHDLexception("Port " + p + " of entity " + i.entity.getName() + " does not exist");
			}
			s = new VHDLsignal(sname, ip.type);
			add(s);
		}
		i.map(p, s);
		return s;
	}

	public VHDLsymbol mapLocal(VHDLinstance i, String p, String sname, VHDLtype t) throws VHDLexception {
		VHDLsymbol s = get(sname);
		if (s==null) {
			// signal 
			VHDLsymbol ip = i.entity.get(p);
			if (ip == null) {
				throw new VHDLexception("Port " + p + " of entity " + i.entity.getName() + " does not exist");
			}
			s = new VHDLsignal(sname, t);
			add(s);
		}
		i.map(p, s);
		return s;
	}

	public int maxSymbolLength() {
		int l = 0;
		for (VHDLsymbol s: symbols)
			l = Math.max(l,  s.name.length());
		return l;
	}
}
