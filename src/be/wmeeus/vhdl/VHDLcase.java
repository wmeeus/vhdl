package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

public class VHDLcase extends VHDLnode {
	VHDLnode casevar = null;
	Hashtable<VHDLvalue, VHDLnode> cases = new Hashtable<VHDLvalue, VHDLnode>();
	VHDLnode defaultcase = null;
	
	public VHDLcase(VHDLnode cv) {
		casevar = cv;
	}
	
	public void add(VHDLvalue v, VHDLnode n) {
		cases.put(v,  n);
	}
	
	public void add(int v, VHDLnode n) {
		cases.put(new VHDLvalue(v),  n);
	}
	
	public void setDefault(VHDLnode n) {
		defaultcase = n;
	}
	
	public String toString() {
		String r = PP.I + "case " + casevar + " is\n";
		ArrayList<VHDLvalue> keys = new ArrayList<VHDLvalue>(cases.keySet());
		Collections.sort(keys);
		for (VHDLvalue v: keys) {
			VHDLnode c = cases.get(v);
			r += PP.I + "when " + v + " => \n";
			PP.down();
			r += c;
			PP.up();
		}
		if (defaultcase != null) {
			r += PP.I + "when others => \n";
			PP.down();
			r += defaultcase;
			PP.up();
		}
		return r + PP.I + "end case;\n";
	}
}
