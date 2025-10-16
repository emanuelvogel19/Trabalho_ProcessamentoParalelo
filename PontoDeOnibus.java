public class PontoDeOnibus {
    private Aluno[] fila = new Aluno[500];
    private int totalNaFila = 0;
    private boolean embarcando = false;
    private Onibus onibusAtual = null;
    private int alunosNoEmbarque = 0;

    public synchronized void alunoChega(Aluno aluno) {
        boolean chegouDuranteEmbarque = embarcando;
        
        fila[totalNaFila] = aluno;
        totalNaFila++;
        
        if (chegouDuranteEmbarque) {
            System.out.println(aluno.getName() + " chegou DURANTE embarque. Aguardará próximo ônibus. (Fila: " + totalNaFila + ")");
            
            while (embarcando) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } else {
            System.out.println(aluno.getName() + " chegou ao ponto. (Fila: " + totalNaFila + ")");
        }
        
        while (aluno != fila[0] || !embarcando || alunosNoEmbarque <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        onibusAtual.registrarEmbarque();
        System.out.println("  " + aluno.getName() + " embarcou no ônibus " + onibusAtual.getName());
        removerPrimeiroDaFila();
        alunosNoEmbarque--;
        notifyAll();
    }

    private void removerPrimeiroDaFila() {
        for (int i = 0; i < totalNaFila - 1; i++) {
            fila[i] = fila[i + 1];
        }
        totalNaFila--;
    }

    public synchronized void onibusChega(Onibus onibus) {
        System.out.println("\n[ÔNIBUS " + onibus.getName() + "] chegou. Alunos na fila: " + totalNaFila);

        if (totalNaFila == 0) {
            System.out.println("[ÔNIBUS " + onibus.getName() + "] partindo vazio.\n");
            return;
        }

        alunosNoEmbarque = Math.min(totalNaFila, 50);
        onibusAtual = onibus;
        onibus.configurarEmbarque(alunosNoEmbarque);
        embarcando = true;
        notifyAll();
    }

    public synchronized void onibusParte(Onibus onibus) {
        System.out.println("[ÔNIBUS " + onibus.getName() + "] partiu com " + 
            onibus.getTotalEmbarcados() + " alunos. Restam " + totalNaFila + " na fila.\n");
        
        embarcando = false;
        onibusAtual = null;
        alunosNoEmbarque = 0;
        notifyAll(); 
    }
}