package Generator.Objects;

import java.util.Objects;

public class W  implements Comparable<W> {


    private int id;
    private double real;
    private double imag;


    public W(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        W w = (W) o;
        return getId() == w.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int  compareTo(W o){
        return getId() - o.getId();
    }


    public W(int index, double c1, double c2) {
        this.id = index;
        this.real = c1;
        this.imag = c2;
    }
    public int getId() {
        return id;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }


    @Override
    public String toString() {
        return "W{" +
                "id=" + id +
                ", real=" + real +
                ", imag=" + imag +
                '}';
    }
}
