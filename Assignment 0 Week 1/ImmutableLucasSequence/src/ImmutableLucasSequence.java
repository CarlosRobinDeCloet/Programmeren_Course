/**
 * @author 621810cc
 *
 * Calculates values of a Lucas sequence using 4 given int parameters, by calculating the next or previous values of the
 * sequence.
 *
 */


import java.math.BigInteger;

public class ImmutableLucasSequence {

    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger x0;
    private final BigInteger x1;

    /**
     * Provides the class necessary to calculate values of a Lucas Sequence.
     * The class is immutable and uses BigInteger instance variables to be able to calculate large numbers.
     *
     * @param a first constant value of the Lucas sequence
     * @param b second constant value of the Lucas sequence
     * @param x0 (n - 1)th value of the Lucas sequence
     * @param x1 nth value of the Lucas Sequence
     */

    public ImmutableLucasSequence(int a, int b, int x0, int x1){

        this.a = BigInteger.valueOf(a);
        this.b = BigInteger.valueOf(b);
        this.x0 = BigInteger.valueOf(x0);
        this.x1 = BigInteger.valueOf(x1);
    }

    /**
     * Overloading constructor to be able to create new objects of the class with BigInteger input variables
     * instead of int variables.
     *
     */

    public ImmutableLucasSequence(BigInteger a, BigInteger b, BigInteger x0, BigInteger x1){

        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.x1 = x1;

    }


    /**
     * Calculates (n + 1)th value of the Lucas sequence.
     *
     * @return new object of Lucas sequence with the (n + 1)th value of the original Lucas sequence as the nth value
     *         and nth value of the original Lucas sequence as the (n-1)th value. Copies the constant variables.
     */

    public ImmutableLucasSequence getNext(){

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

        return new ImmutableLucasSequence(this.a, this.b, this.x1, x2);
    }

    /**
     * Calculates previous value of the Lucas sequence
     *
     * @return new object of Lucas sequence with the (n-1)th value of the original Lucas sequence as the nth value
     *         and (n-2)th value of the original Lucas sequence as the (n-1)th value. Copies the constant variables.
     */

    public ImmutableLucasSequence getPrevious(){

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

        return new ImmutableLucasSequence(this.a, this.b, x0, x1);
    }

    /**
     * returns the nth value of the Lucas sequence
     *
     * @return returns BigInteger object with nth value of the Lucas sequence.
     */

    public BigInteger getValue(){
        return this.x1;
    }



}
