package be.wmeeus.vhdl;

public class VHDLfield extends VHDLnode {
	// name is the field name
	// node is the node to which the field belongs
	VHDLnode node;
	
	public VHDLfield (String s, VHDLnode n) {
		name = s;
		node = n;
	}
	
	public String toString() {
		return node.name + "." + name;
	}
	
	public boolean isVariable() {
		return (node instanceof VHDLvariable);
	}
	
}
