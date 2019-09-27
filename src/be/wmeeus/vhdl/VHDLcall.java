package be.wmeeus.vhdl;
import java.util.*;

/**
 * Class VHDLcall represents a function call in VHDL. Function support is limited for now. 
 * The name of the function call is the called function. Functions themselves are not represented,
 * and no type checking happens.  
 * @author Wim Meeus
 *
 */
public class VHDLcall extends VHDLnode {
	/**
	 * Function arguments list
	 */
	public ArrayList<VHDLnode> arguments = null;
	
	VHDLfunction function = null;
	
	/**
	 * Constructs a VHDL function call
	 * @param n name of the called function
	 */
	public VHDLcall(String n, VHDLtype t) throws VHDLexception {
		function = VHDLfunction.mkFunction(n, t);
	}
	
	/**
	 * Constructs a VHDL function call
	 * @param n name of the called function
	 */
	public VHDLcall(VHDLfunction f) throws VHDLexception {
		function = f;
	}
	
	/**
	 * Constructs a VHDL function call with one argument
	 * @param n name of the called function
	 * @param a the argument
	 */
	public VHDLcall(String n, VHDLtype t, VHDLnode a) throws VHDLexception {
		function = VHDLfunction.mkFunction(n, t);
		arguments = new ArrayList<VHDLnode>();
		arguments.add(a);
	}
	
	/**
	 * Add an argument to this function call
	 * @param a the argument
	 */
	public void add(VHDLnode a) {
		if (arguments==null)
			arguments = new ArrayList<VHDLnode>();
		arguments.add(a);
	}
	
	/**
	 * Returns a String representation of this function call
	 */
	public String toString() {
		String r = function.name + "(";
		boolean prev = false; 
		if (arguments!=null && !arguments.isEmpty()) {
			for (VHDLnode a: arguments) {
				if (prev) r += ", ";
				prev = true;
				r += a;
			}
		}
		return r + ")";
	}
}
