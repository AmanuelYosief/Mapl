package compiler;

/**
 * Fresh label generator.
 */
public class FreshLabelGenerator {
    
    private static int count = 0;
    
    private FreshLabelGenerator(){}

    /**
     * Generate a new label. Each call to this method is guaranteed to return a
     * label distinct from all those returned by any previous calls.
     * @param prefix the label will start with this string
     * @return a new label
     */
    public static String makeLabel(String prefix) {
        String label = prefix + "@" + count;
        ++count;
        return label;
    }

}
