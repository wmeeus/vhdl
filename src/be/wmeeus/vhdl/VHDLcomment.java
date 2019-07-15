package be.wmeeus.vhdl;
import be.wmeeus.util.*;

/**
 * Class VHDLcomment represents a comment in VHDL. It allows to add text in a list of statements.
 * @author Wim Meeus
 *
 */
public class VHDLcomment extends VHDLnode {
	/**
	 * The comment text
	 */
	String comment = null;
	
	/**
	 * Construct a VHDL comment
	 * @param s the VHDL comment
	 */
	public VHDLcomment(String s) {
		comment = s;
	}
	
	/**
	 * Returns a String representation of this comment
	 */
	public String toString() {
		return pp(comment);
	}
	
	/**
	 * Formats a comment
	 * @param c the comment to format
	 * @return the formatted comment
	 */
	public static String pp(String c) {
		return PP.C + c.replaceAll("\n", "\n"+PP.C);
	}
}
