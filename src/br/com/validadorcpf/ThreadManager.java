package br.com.validadorcpf;

import java.io.File;
import java.util.*;

public class ThreadManager {

    public static void runVersao(int versao, int numThreads, File[] arquivos) {
        int totalArquivos = arquivos.length;
        int arquivosPorThread = (int) Math.ceil((double) totalArquivos / numThreads);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * arquivosPorThread;
            int end = Math.min(start + arquivosPorThread, totalArquivos);
            if (start >= end) break;

            List<File> subList = Arrays.asList(Arrays.copyOfRange(arquivos, start, end));
            File output = new File("resultados/versao_" + versao + "_threads.txt");
            Thread t = new CPFProcessor(subList, output);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}