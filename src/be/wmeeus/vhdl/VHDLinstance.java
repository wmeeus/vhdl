package be.wmeeus.vhdl;
import java.util.*;

import be.wmeeus.util.PP;

/**
 * Class VHDLinstance contains an instance in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLinstance extends VHDLnode {
	/**
	 * The architecture in which this instance is included
	 */
	VHDLarchitecture arch = null;

	/**
	 * The instantiated entity
	 */
	VHDLentity entity = null;

	/**
	 * The mapping table of generics and ports
	 */
	Hashtable<String, VHDLnode> genportmap = new Hashtable<String, VHDLnode>();

	/**
	 * Construct an instance
	 * @param n the instance name
	 * @param e the instantiated entity
	 */
	public VHDLinstance(String n, VHDLentity e) {
		name = n;
		entity = e;
	}

	/**
	 * Map a generic or a port
	 * @param s the name of the mapped generic or port
	 * @param n the signal, port, generic, ... to map to the generic or port
	 */
	public void map(String s, VHDLnode n) {
		VHDLsymbol p = entity.get(s);
		VHDLnode nn = n;

		try {
			if (p != null) {
				nn = p.getType().convertTo(n);
			}
			//System.out.println("*map*cast* " + n + " to " + nn);
		} catch (VHDLexception ex) {
			ex.printStackTrace();
			// Report and continue without a conversion, fingers crossed!
		}

		genportmap.put(s, nn);
		if (!entity.has(s)) {
			System.err.println("*Warning* map: port " + s + " not found on instance " + name + " of " + entity.name);
			Thread.dumpStack();
		}
	}

	/**
	 * Returns a String representation of this instance
	 */
	public String toString() {
		String r = "\n" + PP.I + name + ": entity work." + entity.name;
		if (comment!=null) r = "\n" + VHDLcomment.pp(comment) + r;
		if (arch!=null) {
			r += "(" + arch.name + ")";
		}
		PP.down();
		String gpmap = "";
		boolean genericmap_empty = true;
		if (entity.generics!=null && !entity.generics.isEmpty()) {
			int l = entity.maxGenericLength();
			gpmap += PP.I + "generic map(\n";
			PP.down();
			boolean prev = false;
			for (VHDLsymbol g: entity.generics) {
				VHDLnode gn = genportmap.get(g.name);
				if (gn!=null) {
					if (prev) gpmap += ",\n";
					prev=true;
					gpmap += PP.I + PP.W(g.name, l) + " => " + gn;
					genericmap_empty = false;
				}				
			}
			if (prev) gpmap += "\n";
			gpmap += PP.I + ")";
			PP.up();
		}
		if (!genericmap_empty) r += "\n" + gpmap;
		gpmap="";
		boolean portmap_empty=true;
		if (entity.ports!=null && !entity.ports.isEmpty()) {
			int l = entity.maxPortLength();
			gpmap += PP.I + "port map(\n";
			PP.down();
			boolean prev = false;
			for (VHDLsymbol p: entity.ports) {
				VHDLnode pn = genportmap.get(p.name);
				if (pn!=null) {
					if (prev) gpmap += ",\n";
					prev=true;
					gpmap += PP.I + PP.W(p.name, l) + " => " + pn;
					portmap_empty = false;
				}				
			}
			if (prev) gpmap += "\n";
			gpmap += PP.I + ")";
			PP.up();
		}
		if (!portmap_empty) r += "\n" + gpmap;
		PP.up();
		return r + ";\n";
	}

	/**
	 * Find a generic or port of the instantiated entity
	 * @param s the name of the generic or port 
	 * @return the requested generic or port, or null if the entity doesn't contain a generic or
	 *   port with the indicated name.
	 */
	public VHDLsymbol get(String s) {
		return entity.get(s);
	}

	/**
	 * Indicates whether the entity contains a generic or port with a particular name
	 * @param s the name of the generic or port
	 * @return true if the entity contains a generic or port with a particular name
	 */
	public boolean has(String s) {
		return (entity.get(s) != null);
	}

	/**
	 * Returns the instantiated entity
	 * @return the instantiated entity
	 */
	public VHDLentity getEntity() {
		return entity;
	}

	/**
	 * Gets an entry from the generic/port map table
	 * @param s the generic or port name
	 * @return the corresponding symbol 
	 */
	public VHDLnode getmap(String s) {
		return genportmap.get(s);
	}
}
