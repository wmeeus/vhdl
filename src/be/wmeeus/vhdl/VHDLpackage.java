package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.*;

public class VHDLpackage extends VHDLnode {
	ArrayList<VHDLnode> pack = new ArrayList<VHDLnode>();
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	public VHDLpackage(String n) {
		name = n;
	}
	
	public void add(VHDLnode n) {
		pack.add(n);
	}
	
	public void addBody(VHDLnode n) {
		body.add(n);
	}
	
	public String toString() {
		String r = "package " + name + " is\n";
		PP.down();
		for (VHDLnode n: pack) {
			r += PP.I + n;
		}
		PP.up();
		r += "end " + name + ";\n";
		
		if (!body.isEmpty()) {
			r += "package body " + name + " is\n";
			PP.down();
			for (VHDLnode n: body) {
				r += PP.I + n;
			}
			PP.up();
			r += "end " + name + ";\n";
		}
		
		return r;
	}
	
}
