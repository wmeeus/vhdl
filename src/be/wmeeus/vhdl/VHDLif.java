package be.wmeeus.vhdl;
import be.wmeeus.util.PP;

public class VHDLif extends VHDLnode {
	VHDLnode cond;
	VHDLnode bthen = null;
	VHDLnode belse = null;
	
	public VHDLif(VHDLnode c, VHDLnode t) {
		cond = c;
		bthen = t;
	}
	
	public VHDLif(VHDLnode c, VHDLnode t, VHDLnode e) {
		cond = c;
		bthen = t;
		belse = e;
	}
	
	public String toString() {
		String r = PP.I + "if " + cond + " ";
		if (bthen != null) {
			r += "then\n";
			PP.down();
			r += bthen;
			PP.up();
		}
		if (belse != null) {
			r += "else\n";
			PP.down();
			r += belse;
			PP.up();
		}
		return r + PP.I + "end if;\n";
	}
}
