package be.wmeeus.vhdl;

import java.util.ArrayList;
import java.util.Hashtable;

public class VHDLexpression extends VHDLnode {
	ArrayList<VHDLnode> args = new ArrayList<VHDLnode>();
	String op = null;
	
	public VHDLexpression(String o, VHDLnode r) {
		// unary expression
		op = o;
		args.add(r);
	}
	
	public VHDLexpression(VHDLnode l, String o, VHDLnode r) {
		args.add(l);
		op = o;
		args.add(r);
	}
	
	public VHDLexpression(String o, ArrayList<VHDLnode> a) {
		args = a;
		op = o;
	}
	
	public void add(VHDLnode n) {
		args.add(n);
	}
	
	public String toString() {
		if (args.isEmpty()) return "";
		if (args.size()==1)
			return ("(" + op + args.get(0) + ")");
		if (args.size()==2)
			return "(" + args.get(0) + " " + op + " " + args.get(1) + ")";
		String s = "(" + args.get(0);
		for (int i=1; i<args.size(); i++) 
			s += " " + op + " " + args.get(i);
		return s + ")";
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		ArrayList<VHDLnode> rn = new ArrayList<VHDLnode>();
		boolean hasreplace=false;
		for (VHDLnode n: args) {
			VHDLnode ra = n.replace(replacetable);
			if (ra!=null) {
				rn.add(ra);
				hasreplace = true;
			} else {
				rn.add(n);
			}
		}
		
		if (hasreplace) {
			return new VHDLexpression(op, rn);
		}
		return null;
	}

}
