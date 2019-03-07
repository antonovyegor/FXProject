package Generator.Objects;

public class FFT extends Thread {
    private Gen gen;
    public FFT(Gen gen) {
        this.gen=gen;

    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " начал работу");
        gen.runFFT();
        System.out.println(Thread.currentThread().getName() + " закончил работу");
    }
}
