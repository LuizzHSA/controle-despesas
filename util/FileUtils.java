package util;

import java.io.*;
import java.util.*;

public class FileUtils {
    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (!l.trim().isEmpty()) lines.add(l);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + path);
        }
        return lines;
    }

    public static void appendLine(String path, String line) {
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo: " + path);
        }
    }

    public static void writeLines(String path, List<String> lines) {
        try (FileWriter fw = new FileWriter(path, false)) {
            for (String l : lines) {
                fw.write(l + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Erro ao sobrescrever arquivo: " + path);
        }
    }
}
