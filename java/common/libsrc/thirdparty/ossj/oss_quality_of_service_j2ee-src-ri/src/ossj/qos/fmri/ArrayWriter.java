package ossj.qos.fmri;

/**
 * ArrayWriter
 *
 * Utility class which prints out arrays of Objects in a readable form. 
 * An Object with 3 elements would result in the output:
 *
 * <CODE>3: [ "object1"; "object2"; "object3"]</CODE>
 * This class was taken from Nokia's SA Reference Implementation.
 *
 * @author aebbert
 * @version 1.0
 */
public class ArrayWriter {

    /**
     * Prints array string to a given stream.
     * @param array array which is to be printed
     * @param out Stream to which the array will be written as string
     */
    public static void println(Object[] array, java.io.PrintStream out) {
        out.println(toString(array));
    }

    /**
     * Prints array string to <CODE>System.out</CODE>
     * @param array array which shall be printed
     */
    public static void println(Object[] array) {
        println(array, System.out);
    }

    /**
     * Returns what wouls otherwise be printed out to System.out or to a Stream.
     * @param array array which is to be printed
     * @return String, containing the string array
     */
    public static String toString(Object[] array) {
        if (array==null) return "null";
        if (array.length == 0) return "empty array";
        StringBuffer sb = new StringBuffer(16*array.length); // estimate length;
        sb.append(array.length);
        sb.append(": [");
        for (int i=0; i<array.length; i++) {
            if (array[i] == null) {
                sb.append("null;");
                continue;
            }
            sb.append(" \"");
            if (array[i].getClass().isArray()) {
                sb.append(toString((Object[])array[i]));
            } else {
                sb.append(array[i].toString());
            }
            sb.append("\";");
        }
        sb.append(" ]");
        return sb.toString();
    }
}
