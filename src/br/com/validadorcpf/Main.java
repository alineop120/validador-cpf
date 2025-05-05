package br.com.validadorcpf;

import br.com.validadorcpf.ValidadorCPF;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pergunta ao usuário qual versão deseja executar
        System.out.print("Escolha a versão (1 a 8): ");
        int versao = scanner.nextInt();

        if (versao < 1 || versao > 8) {
            System.out.println("Versão inválida. Por favor, escolha de 1 a 8.");
            return;
        }

        System.out.println("Você escolheu a versão " + versao);

        int numThreads = getNumThreads(versao);

        List<File> arquivos = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            arquivos.add(new File("cpf/f" + i + "-4-25.txt"));
        }

        List<List<File>> arquivosDivididos = dividirArquivos(arquivos, numThreads);

        AtomicInteger cpfsValidos = new AtomicInteger();
        AtomicInteger cpfsInvalidos = new AtomicInteger();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        long startTime = System.currentTimeMillis();

        for (List<File> arquivosThread : arquivosDivididos) {
            executor.submit(() -> {
                for (File arquivo : arquivosThread) {
                    try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                        String linha;
                        while ((linha = br.readLine()) != null) {
                            String cpf = linha.trim();
                            boolean valido = ValidadorCPF.validaCPF(cpf);

                            String status = valido ? "Válido" : "Inválido";
                            System.out.println(cpf + " - " + status); // Terminal

                            if (valido) {
                                cpfsValidos.incrementAndGet();
                            } else {
                                cpfsInvalidos.incrementAndGet();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Mostrar total de CPFs válidos e inválidos no terminal
        System.out.println("\nResumo:");
        System.out.println("CPFs válidos: " + cpfsValidos.get());
        System.out.println("CPFs inválidos: " + cpfsInvalidos.get());

        // Gravar tempo no arquivo
        String resultadoPath = "resultados/versao_" + versao + "_threads.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultadoPath))) {
            writer.write("Tempo de execução: " + duration + " milissegundos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getNumThreads(int versao) {
        return switch (versao) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 5;
            case 5 -> 6;
            case 6 -> 10;
            case 7 -> 15;
            case 8 -> 30;
            default -> throw new IllegalArgumentException("Versão inválida");
        };
    }

    private static List<List<File>> dividirArquivos(List<File> arquivos, int numThreads) {
        List<List<File>> resultado = new ArrayList<>();
        int arquivosPorThread = arquivos.size() / numThreads;
        int sobra = arquivos.size() % numThreads;
        int inicio = 0;

        for (int i = 0; i < numThreads; i++) {
            int fim = inicio + arquivosPorThread + (sobra > 0 ? 1 : 0);
            resultado.add(new ArrayList<>(arquivos.subList(inicio, fim)));
            inicio = fim;
            if (sobra > 0) sobra--;
        }

        return resultado;
    }
}