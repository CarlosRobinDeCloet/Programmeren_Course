import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        ImmutableLucasSequence fib = new ImmutableLucasSequence(1, 0, 3, 5);
        BigInteger bi = fib.getValue();
        System.out.println(bi.toString());
        fib = fib.getNext();
        bi = fib.getValue();
        System.out.println(bi.toString());
        fib = fib.getPrevious().getPrevious();
        bi = fib.getValue();
        System.out.println(bi.toString());
        fib = fib.getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext().getNext();
        bi = fib.getValue();
        System.out.println(bi.toString());

    }

}
