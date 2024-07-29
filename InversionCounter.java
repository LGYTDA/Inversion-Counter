import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class with two different methods to count inversions in an array of integers.
 */
public class InversionCounter {

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses nested loops to run in Theta(n^2) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsSlow(int[] array) {
        long inversionCount = 0;
        for(int i = 0 ; i<array.length; i++){
            for(int j=i+1;j<array.length;j++){
                if(array[i]>array[j]){
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    private static long  mergesortHelper(int[] arrayCopy, int[] scratch, int low, int high) {
        long inversionCount = 0;
        if (low < high) {
            int mid = low + (high - low) / 2;
            inversionCount += mergesortHelper(arrayCopy, scratch, low, mid);
            inversionCount += mergesortHelper(arrayCopy, scratch, mid + 1, high);

            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || arrayCopy[i] <= arrayCopy[j])) {
                    scratch[k] = arrayCopy[i++];
                } else {
                    scratch[k] = arrayCopy[j++];
                    //inversion
                    inversionCount += mid - i + 1;
                }
            }
            for (int k = low; k <= high; k++) {
                arrayCopy[k] = scratch[k];
            }
        }
        return inversionCount;
    }


    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original
        // array.
        int[] arrayCopy = new int[array.length],
                scratch =  new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        // TODO - fix return statement
        return mergesortHelper(arrayCopy,scratch,0, arrayCopy.length-1);
    }

    /**
     * Reads an array of integers from stdin.
     * @return an array of integers
     * @throws IOException if data cannot be read from stdin
     * @throws NumberFormatException if there is an invalid character in the
     * input stream
     */
    private static int[] readArrayFromStdin() throws IOException,
            NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;
        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                                "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public static void main(String[] args) {
        if(args.length==0){

            try{
                int[] numbers = readArrayFromStdin();
                if(numbers.length == 0){
                    System.out.print("Enter sequence of integers, each followed by a space: ");
                    System.err.print("Error: Sequence of integers not received.");
                    System.exit(1);
                }
                long inversions = countInversionsFast(numbers);
                System.out.print("Enter sequence of integers, each followed by a space: Number of inversions: " + inversions);
            }
            catch(IOException ioe){
                System.exit(1);
            }
            catch(NumberFormatException nfe){
                System.out.print("Enter sequence of integers, each followed by a space: ");
                System.err.print("Error: " + nfe.getMessage());
                System.exit(1);
            }
        }

        else if(args.length==1){
            String speed = args[0];
            if(speed.equals("slow")){


                try{
                    int[] numbers = readArrayFromStdin();
                    System.out.print("Enter sequence of integers, each followed by a space: ");
                    if(numbers.length == 0){
                        System.err.print("Error: Sequence of integers not received.");
                        System.exit(1);
                    }
                    long inversions = countInversionsSlow(numbers);
                    System.out.print("Number of inversions: " + inversions);
                }
                catch(IOException ioe){
                    System.exit(1);
                }
                catch(NumberFormatException nfe){
                    System.err.println("Error: " + nfe.getMessage());
                    System.exit(1);
                }
            }
            else{
                System.err.print("Error: Unrecognized option '"+ speed + "'." );
                System.exit(1);
            }
        }

        else{
            System.err.print("Usage: java InversionCounter [slow]");
            System.exit(1);

        }
    }
}
