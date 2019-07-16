package be.wmeeus.vhdl;

import java.util.*;

/**
 * Class VHDLnode contains a element of the VHDL language (node of the syntax tree).
 * @author Wim Meeus
 *
 */
public class VHDLnode {
	/**
	 * Name of this node
	 */
	String name = null;
	
	/**
	 * Comment
	 */
	String comment = null;

	/**
	 * Returns the name of this node
	 * @return the name of this node
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the comment
	 * @return the comment (null for no comment)
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment
	 * @param comment the comment
	 * @return
	 */
	public VHDLnode setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	/**
	 * Replaces nodes in a VHDL design according to a table
	 * @param replacetable the table with the nodes to replace
	 * @return the replaced node if this node is a key in the replacement table, otherwise null
	 * @throws VHDLexception
	 */
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		return null;
	}

}
