public class Onibus extends Thread {
    private PontoDeOnibus pontoDeOnibus;
    private int vagasDisponiveis = 0;
    private int totalEmbarcados = 0;

    public Onibus(int id, PontoDeOnibus pontoDeOnibus) {
        super("" + id);
        this.pontoDeOnibus = pontoDeOnibus;
    }

    public synchronized void configurarEmbarque(int vagas) {
        this.vagasDisponiveis = vagas;
        this.totalEmbarcados = 0;
    }

    public synchronized void registrarEmbarque() {
        vagasDisponiveis--;
        totalEmbarcados++;
        notifyAll();
    }

    public synchronized int getTotalEmbarcados() {
        return totalEmbarcados;
    }

    private synchronized void esperarEmbarque() throws InterruptedException {
        while (vagasDisponiveis > 0) {
            wait();
        }
    }

    @Override
    public void run() {
        try {
            pontoDeOnibus.onibusChega(this);

            if (vagasDisponiveis > 0) {
                esperarEmbarque();
            }

            pontoDeOnibus.onibusParte(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}