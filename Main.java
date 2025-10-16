import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int totalAlunos = 150;
        PontoDeOnibus pontoDeOnibus = new PontoDeOnibus();
        Random random = new Random();

        // Duração da aula (2 a 10 minutos)
        int duracaoAulaMs = (random.nextInt(9) + 2) * 60000;

        System.out.println("=== SIMULAÇÃO DO PONTO DE ÔNIBUS ===");
        System.out.println("Total de alunos: " + totalAlunos);
        System.out.println("Duração da aula: " + (duracaoAulaMs / 1000) + " segundos\n");

        GeradorDeOnibus gerador = new GeradorDeOnibus(pontoDeOnibus);
        gerador.start();

        for (int i = 1; i <= totalAlunos; i++) {
            Aluno aluno = new Aluno(i, pontoDeOnibus, duracaoAulaMs);
            aluno.start();
        }
    }
}