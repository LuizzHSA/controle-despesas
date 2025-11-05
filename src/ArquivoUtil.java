import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Util para leitura/escrita simples de arquivos texto.
 */
public class ArquivoUtil {

    public static void garantirArquivo(String caminho) {
        try {
            Path p = Paths.get(caminho);
            if (!Files.exists(p)) {
                if (p.getParent() != null) Files.createDirectories(p.getParent());
                Files.createFile(p);
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo: " + caminho + " -> " + e.getMessage());
        }
    }

    public static List<String> lerLinhas(String caminho) {
        garantirArquivo(caminho);
        try {
            return Files.readAllLines(Paths.get(caminho));
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + caminho + " -> " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void appendLinha(String caminho, String linha) {
        garantirArquivo(caminho);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {
            bw.write(linha);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever: " + caminho + " -> " + e.getMessage());
        }
    }

    public static void sobrescrever(String caminho, List<String> linhas) {
        garantirArquivo(caminho);
        try {
            Files.write(Paths.get(caminho), linhas);
        } catch (IOException e) {
            System.out.println("Erro ao sobrescrever: " + caminho + " -> " + e.getMessage());
        }
    }
}
