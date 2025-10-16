public class Aluno extends Thread {
    private PontoDeOnibus pontoDeOnibus;
    private int duracaoAulaMs;

    public Aluno(int id, PontoDeOnibus pontoDeOnibus, int duracaoAulaMs) {
        super("Aluno " + id);
        this.pontoDeOnibus = pontoDeOnibus;
        this.duracaoAulaMs = duracaoAulaMs;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duracaoAulaMs);
            pontoDeOnibus.alunoChega(this);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}