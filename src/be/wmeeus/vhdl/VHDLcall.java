package be.wmeeus.vhdl;
import java.util.*;

public class VHDLcall extends VHDLnode {
	public ArrayList<VHDLnode> arguments = null;
	
	public VHDLcall(String n) {
		name = n;
	}
	
	public VHDLcall(String n, VHDLnode a) {
		name = n;
		arguments = new ArrayList<VHDLnode>();
		arguments.add(a);
	}
	
	public void add(VHDLnode a) {
		if (arguments==null)
			arguments = new ArrayList<VHDLnode>();
		arguments.add(a);
	}
	
	public String toString() {
		String r = name + "(";
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
