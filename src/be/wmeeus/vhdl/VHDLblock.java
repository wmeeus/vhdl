package be.wmeeus.vhdl;
import java.util.*;

public class VHDLblock extends VHDLnode {
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	public VHDLblock() {}
	
	public void add(VHDLnode n) {
		body.add(n);
	}
	
	public String toString() {
		String r = "";
		for (VHDLnode n: body) {
			r += n;
		}
		return r;
	}
}
