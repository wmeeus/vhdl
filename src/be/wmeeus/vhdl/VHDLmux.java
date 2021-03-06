package be.wmeeus.vhdl;

import java.util.*;

/**
 * Class VHDLmux represents a multiplexer in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLmux {
	/**
	 * Build and return a one-hot multiplexer
	 * @param a list of data inputs
	 * @param s list of selection inputs
	 * @param y multiplexer output
	 * @param arch the architecture in which this multiplexer is instantiated
	 * @return the multiplexer
	 * @throws VHDLexception
	 */
	public static VHDLnode onehotmux(ArrayList<VHDLnode> a, ArrayList<VHDLnode> s, VHDLsymbol y, VHDLarchitecture arch) throws VHDLexception {
		if (a.size()!=s.size()) {
			throw new VHDLexception("Onehot mux: number of inputs and numbber of selection bits differs");
		}
		int len = a.size();
		
		VHDLblock b = new VHDLblock();
		VHDLsignal sel = new VHDLsignal("s_lbus_mux", new VHDLstd_logic_vector(s.size()));
		arch.add(sel);
		b.add(new VHDLassign(sel, new VHDLexpression("&", s)));
		
		VHDLprocess p = new VHDLprocess("p_mux", sel);
		b.add(p);
		
		VHDLcase c = new VHDLcase(sel);
		p.add(c);
		c.setDefault(new VHDLassign(y, VHDLvalue.OTHERSDC));
		for (int i=0; i<len; i++) {
			// construct selection vector
			c.add(VHDLvalue.onehot(i, len), new VHDLassign(y, a.get(i)));
		}
		
		
		
		return b;
	}
}
