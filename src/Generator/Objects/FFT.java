package Generator.Objects;

public class FFT extends Thread {
    private Gen gen;
    private int i;
    public FFT(Gen gen,int i) {
        this.gen=gen;
        this.i=i;

    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " начал работу");
        gen.runFFT(i);
        System.out.println(Thread.currentThread().getName() + " закончил работу");
    }
}
