package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.*;

public class VHDLrecord extends VHDLtype {
	ArrayList<VHDLrecordField> fields = new ArrayList<VHDLrecordField>();
	
	public VHDLrecord(String n) {
		name = n;
	}
	
	public void add(String n, VHDLtype t) {
		fields.add(new VHDLrecordField(n, t));
	}
	
	public void add(VHDLrecordField f) {
		fields.add(f);
	}
	
	public String toStringDef() {
		String r = "type " + name + " is record\n";
		PP.down();
		for (VHDLrecordField f: fields) {
			r += PP.I + f;
		}
		PP.up();
		return r + "end record " + name + ";\n";
	}
	
}
