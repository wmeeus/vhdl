package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.*;

/**
 * Class VHDLrecord represents the VHDL record data type
 * @author Wim Meeus
 *
 */
public class VHDLrecord extends VHDLtype {
	/**
	 * List of fields of this record
	 */
	ArrayList<VHDLrecordField> fields = new ArrayList<VHDLrecordField>();
	
	/**
	 * Constructs an empty record
	 * @param n Name of this record type
	 */
	public VHDLrecord(String n) {
		name = n;
	}
	
	/**
	 * Adds a field to this record type
	 * @param n field name
	 * @param t field type
	 */
	public void add(String n, VHDLtype t) {
		fields.add(new VHDLrecordField(n, t));
	}
	
	/**
	 * Adds a field to this record type
	 * @param f the field
	 */
	public void add(VHDLrecordField f) {
		fields.add(f);
	}
	
	/**
	 * Returns a String with the definition of this record type
	 * @return a String with the definition of this record type
	 */
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
