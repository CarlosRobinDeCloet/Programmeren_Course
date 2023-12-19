import java.math.BigInteger;

/**
 * @author 621810cc
 *
 * Calculates values of a Lucas sequence using 4 given int parameters, by calculating the next or previous values of the
 * sequence.
 *
 */

public class MutableLucasSequence {

    private BigInteger a;
    private BigInteger b;
    private BigInteger x0;
    private BigInteger x1;

    /**
     * Provides the class necessary to calculate values of a Lucas Sequence.
     * The class uses BigInteger instance variables to be able to calculate large numbers.
     *
     * @param a first constant value of the Lucas sequence
     * @param b second constant value of the Lucas sequence
     * @param x0 (n - 1)th value of the Lucas sequence
     * @param x1 nth value of the Lucas Sequence
     */

    public MutableLucasSequence(int a, int b, int x0, int x1){

        this.a = BigInteger.valueOf(a);
        this.b = BigInteger.valueOf(b);
        this.x0 = BigInteger.valueOf(x0);
        this.x1 = BigInteger.valueOf(x1);

    }

    /**
     * Calculates (n + 1)th value of the Lucas sequence and sets the nth variable of the Lucas sequence with the value
     * of the (n+1)th value of the Lucas sequence, and the (n-1)th variable of the Lucas sequence with the value of the
     * (n-1)th value of the Lucas sequence.
     *
     * @return returns BigInteger object with value of the (n+1)th variable of the Lucas sequence.
     */

    public BigInteger nextValue(){

        BigInteger x2 = new BigInteger("0");
        BigInteger x2_1 = new BigInteger("0");
        BigInteger x2_2 = new BigInteger("0");

        x2_1 = x2_1.add(this.a);
        x2_1 = x2_1.multiply(this.x0);

        x2_2 = x2_2.add(this.a);
        x2_2 = x2_2.add(this.b);
        x2_2 = x2_2.multiply(this.x1);

        x2 = x2.add(x2_1);
        x2 = x2.add(x2_2);

        this.x0 = this.x1;
        this.x1 = x2;

        return this.x1;

    }

    /**
     * Calculates (n + 2)th value of the Lucas sequence and sets the nth variable of the Lucas sequence with the value
     * of the (n-1)th value of the Lucas sequence, and the (n-1)th variable of the Lucas sequence with the value of the
     * (n-2)th value of the Lucas sequence.
     *
     * @return returns BigInteger object with value of the (n-1)th variable of the Lucas sequence.
     */

    public BigInteger previousValue(){

        BigInteger x2 = this.x1;
        BigInteger x1 = this.x0;

        BigInteger x0 = new BigInteger("0");
        BigInteger x0_1 = new BigInteger("0");
        BigInteger x0_2 = new BigInteger("0");

        x0_1 = x0_1.add(x2);
        x0_2 = x0_2.add(this.a);
        x0_2 = x0_2.add(this.b);
        x0_2 = x0_2.multiply(x1);

        x0 = x0_1.subtract(x0_2);
        x0 = x0.divide(this.a);

        this.x1 = x1;
        this.x0 = x0;

        return this.x1;
    }

}
