package br.com.validadorcpf;

import java.io.*;
import java.util.List;

public class CPFProcessor extends Thread {
    private List<File> arquivos;
    private File output;

    public CPFProcessor(List<File> arquivos, File output) {
        this.arquivos = arquivos;
        this.output = output;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            for (File file : arquivos) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        boolean valido = ValidadorCPF.validaCPF(linha.trim());
                        writer.write(file.getName() + " - " + linha + " - " + (valido ? "Válido" : "Inválido"));
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
