package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

/**
 * Class VHDLarchitecture describes a VHDL architecture.
 * @author Wim Meeus
 *
 */
public class VHDLarchitecture extends VHDLnode {
	/**
	 * The entity in of which this is an architecture
	 */
	VHDLentity entity = null;
	
	/**
	 * The list of VHDL symbols in this architecture
	 */
	ArrayList<VHDLsymbol> symbols = new ArrayList<VHDLsymbol>();
	
	/**
	 * The symbol table of this architecture
	 */
	VHDLsymbolTable symboltable   = null;
	
	/**
	 * The architecture body
	 */
	ArrayList<VHDLnode> body      = new ArrayList<VHDLnode>();

	/**
	 * Construct a VHDL architecture
	 * @param n the architecture name
	 * @param e the entity of which this is an architecture
	 * @throws VHDLexception
	 */
	public VHDLarchitecture(String n, VHDLentity e) throws VHDLexception {
		name = n;
		entity = e;
		if (e!=null) {
			e.add(this);
			symboltable = new VHDLsymbolTable("arch", e.symboltable);
		}
	}

	/**
	 * Add a symbol to or set the body of this architecture
	 * @param n the symbol or body
	 * @throws VHDLexception
	 */
	public void add(VHDLnode n) throws VHDLexception {
		if (n instanceof VHDLsymbol) {
			symbols.add((VHDLsymbol)n);
			symboltable.add((VHDLsymbol)n);
		} else {
			body.add(n);
		}
	}

	/**
	 * Returns a String representation of this architecture = VHDL code
	 */
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

	/**
	 * Get a symbol from this architecture 
	 * @param s the symbol name
	 * @return the requested symbol, or null if no symbol with the requested name is present in this
	 *   architecture
	 */
	public VHDLsymbol get(String s) {
		return symboltable.get(s);
	}

	/**
	 * Connect a port of instance i in this architecture with the port of the same name on the
	 * entity of which this is the architecture 
	 * @param i the instance
	 * @param p the port name
	 * @return the port
	 * @throws VHDLexception
	 */
	public VHDLsymbol up(VHDLinstance i, String p) throws VHDLexception {
		VHDLsymbol s = i.entity.get(p);
		if (s==null) throw new VHDLexception("*VHDLsymbol::up* Cannot find port " + p + " on instance " + i.getName() + "(" + i.entity.name + ")");
		entity.add(s);
		i.map(p, s);
		return s;
	}

	/**
	 * Connect a port of instance i in this architecture with the port of the same name on the
	 * entity of which this is the architecture 
	 * @param i the instance
	 * @param p the port name
	 * @param t the VHDL type of the port
	 * @return the port
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of instance i in this architecture with a port on the entity of which this
	 * is the architecture. The port name on the entity gets a prefix or infix so ports with the 
	 * same name on multiple instances can connect with different ports on this entity.
	 * @param i the instance
	 * @param p the port name
	 * @param infix the prefix/infix to add. If the port name starts with a single character followed
	 *   by an underscore (so p=='p_restOfTheName'), the infix is placed after the underscore
	 *   (so the name becomes 'p_<infix>_restOfTheName').
	 * @return the (potentially new) port on the entity of which this is the architecture
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of instance i in this architecture with a port on the entity of which this
	 * is the architecture.
	 * @param i The instance in this architecture
	 * @param p The port name on the instance
	 * @param n The port name on the entity of which this is the architecture
	 * @return The port on the entity of which this is the architecture
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of instance i in this architecture with a port on the entity of which this
	 * is the architecture. The port name on the entity gets a prefix or infix so ports with the 
	 * same name on multiple instances can connect with different ports on this entity.
	 * @param i the instance
	 * @param p the port name
	 * @param infix the prefix/infix to add. If the port name starts with a single character followed
	 *   by an underscore (so p=='p_restOfTheName'), the infix is placed after the underscore
	 *   (so the name becomes 'p_<infix>_restOfTheName').
	 * @param t the VHDL type
	 * @return the (potentially new) port on the entity of which this is the architecture
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of an instance in this architecture to a local signal
	 * @param i the instance
	 * @param p the port name
	 * @return the local signal
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of an instance in this architecture to a local signal
	 * @param i the instance
	 * @param p the port name
	 * @param sname the signal name
	 * @return the local signal
	 * @throws VHDLexception
	 */
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

	/**
	 * Connect a port of an instance in this architecture to a local signal
	 * @param i the instance
	 * @param p the port name
	 * @param sname the signal name
	 * @param t the VHDL type
	 * @return the local signal
	 * @throws VHDLexception
	 */
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

	/**
	 * Determines and returns the maximum length of a symbol (signal) name in this architecture 
	 * @return the maximum length of a symbol (signal) name in this architecture
	 */
	public int maxSymbolLength() {
		int l = 0;
		for (VHDLsymbol s: symbols)
			l = Math.max(l,  s.name.length());
		return l;
	}
}
