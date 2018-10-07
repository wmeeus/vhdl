package be.wmeeus.vhdl;
import be.wmeeus.util.*;

public class VHDLcomment extends VHDLnode {
	String comment = null;
	public VHDLcomment(String s) {
		comment = s;
	}
	
	public String toString() {
		return PP.C + comment;
	}
}
