package be.wmeeus.vhdl;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class VHDLexpression contains an expression in VHDL. The expression is stored as an ordered
 * list of operands and an operator.
 * @author Wim Meeus
 *
 */
public class VHDLexpression extends VHDLnode {
	/**
	 * The list of arguments
	 */
	ArrayList<VHDLnode> args = new ArrayList<VHDLnode>();
	
	/**
	 * The operator
	 */
	String op = null;
	
	/**
	 * Construct a unary expression
	 * @param o the operator
	 * @param r the RHS
	 */
	public VHDLexpression(String o, VHDLnode r) {
		// unary expression
		op = o;
		args.add(r);
	}
	
	/**
	 * Construct an expression
	 * @param l the LHS
	 * @param o the operator
	 * @param r the RHS
	 */
	public VHDLexpression(VHDLnode l, String o, VHDLnode r) {
		args.add(l);
		op = o;
		args.add(r);
	}
	
	/**
	 * Construct an expression from a list of arguments
	 * @param o the operator
	 * @param a the list of arguments
	 */
	public VHDLexpression(String o, ArrayList<VHDLnode> a) {
		args = a;
		op = o;
	}
	
	/**
	 * Adds an argument to this expression
	 * @param n the argument
	 */
	public void add(VHDLnode n) {
		args.add(n);
	}
	
	/**
	 * Returns a String representation of this expression
	 */
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

	/**
	 * Substitute parts of an expression
	 */
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
