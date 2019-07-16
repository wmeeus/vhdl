package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.*;

/**
 * Class VHDLpackage represents a package in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLpackage extends VHDLnode {
	/**
	 * Package definition
	 */
	ArrayList<VHDLnode> pack = new ArrayList<VHDLnode>();
	
	/**
	 * Package body
	 */
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	/**
	 * Construct a new package
	 * @param n the package name
	 */
	public VHDLpackage(String n) {
		name = n;
	}
	
	/**
	 * Add a node to the package definition
	 * @param n the node to add
	 */
	public void add(VHDLnode n) {
		pack.add(n);
	}
	
	/**
	 * Add a node to the package body
	 * @param n the node to add
	 */
	public void addBody(VHDLnode n) {
		body.add(n);
	}
	
	/**
	 * Returns a String representation of this package
	 */
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
