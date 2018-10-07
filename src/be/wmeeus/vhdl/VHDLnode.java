package be.wmeeus.vhdl;

import java.util.*;

public class VHDLnode {
	String name = null;
	String comment = null;

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public VHDLnode setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		return null;
	}

}
