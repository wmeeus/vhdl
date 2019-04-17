package be.wmeeus.vhdl;
import java.util.*;

import be.wmeeus.util.PP;

public class VHDLinstance extends VHDLnode {
	VHDLarchitecture arch = null;
	VHDLentity entity = null;
	Hashtable<String, VHDLnode> genportmap = new Hashtable<String, VHDLnode>();
	
	public VHDLinstance(String n, VHDLentity e) {
		name = n;
		entity = e;
	}
	
	public void map(String s, VHDLnode n) {
		genportmap.put(s, n);
		if (!entity.has(s)) {
			System.err.println("*Warning* map: port " + s + " not found on instance " + name + " of " + entity.name);
			Thread.dumpStack();
		}
	}
	
	public String toString() {
		String r = "\n" + PP.I + name + ": entity work." + entity.name;
		if (comment!=null) r = "\n" + VHDLcomment.pp(comment) + r;
		if (arch!=null) {
			r += "(" + arch.name + ")";
		}
		r += "\n";
		PP.down();
		String gpmap = "";
		boolean gpempty = true;
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
					gpempty = false;
				}				
			}
			if (prev) gpmap += "\n";
			gpmap += PP.I + ")\n";
			PP.up();
		}
		if (!gpempty) r += gpmap;
		gpmap="";
		gpempty=true;
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
					gpempty = false;
				}				
			}
			if (prev) gpmap += "\n";
			gpmap += PP.I + ");\n\n";
			PP.up();
		}
		if (!gpempty) r += gpmap;
		PP.up();
		return r;
	}
	
	public VHDLsymbol get(String s) {
		return entity.get(s);
	}
	
	public boolean has(String s) {
		return (entity.get(s) != null);
	}

	public VHDLentity getEntity() {
		return entity;
	}
	
	public VHDLnode getmap(String s) {
		return genportmap.get(s);
	}
}
