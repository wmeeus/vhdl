package be.wmeeus.vhdl;

/**
 * Class VHDLfield contains a field of a record type. Unclear whether class is used anywhere. 
 * @author Wim Meeus
 *
 */
public class VHDLfield extends VHDLnode {
	// name is the field name
	/**
	 * The node to which the field belongs
	 */
	VHDLnode node;
	
	/**
	 * Constructs a field
	 * @param s field name
	 * @param n node which contains this field
	 */
	public VHDLfield (String s, VHDLnode n) {
		name = s;
		node = n;
	}
	
	/**
	 * Returns a String representation of this field
	 */
	public String toString() {
		return node.name + "." + name;
	}

	/**
	 * Indicates whether this field belongs is a variable 
	 * @return true if this field belongs is a variable
	 */
	public boolean isVariable() {
		return (node instanceof VHDLvariable);
	}
	
}
