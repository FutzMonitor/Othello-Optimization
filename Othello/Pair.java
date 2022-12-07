public class Pair {
    private double a;
    private double b;

    public Pair(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public void set_a(double new_a) {
        a = new_a;
    }

    public void set_b(double new_b) {
        b = new_b;
    }

    public String print() {
        String s = String.valueOf(a) + ", " + String.valueOf(b); 
        System.out.println(s);
        return s;
    }
}
