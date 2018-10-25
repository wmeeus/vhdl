package be.wmeeus.vhdl;
import be.wmeeus.util.*;

public class VHDLcomment extends VHDLnode {
	String comment = null;
	public VHDLcomment(String s) {
		comment = s;
	}
	
	public String toString() {
		return pp(comment);
	}
	
	public static String pp(String c) {
		return PP.C + c.replaceAll("\n", "\n"+PP.C);
	}
}
