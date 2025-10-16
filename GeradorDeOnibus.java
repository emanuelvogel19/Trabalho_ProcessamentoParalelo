import java.util.Random;

public class GeradorDeOnibus extends Thread {
    private PontoDeOnibus pontoDeOnibus;
    private int idOnibus = 1;

    public GeradorDeOnibus(PontoDeOnibus pontoDeOnibus) {
        super("GeradorDeOnibus");
        this.pontoDeOnibus = pontoDeOnibus;
    }

    @Override
    public void run() {
        Random random = new Random();
        
        try {
            while (true) {
                int intervalo = random.nextInt(60001) + 120000;
                Thread.sleep(intervalo);

                Onibus onibus = new Onibus(idOnibus, pontoDeOnibus);
                onibus.start();
                idOnibus++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}