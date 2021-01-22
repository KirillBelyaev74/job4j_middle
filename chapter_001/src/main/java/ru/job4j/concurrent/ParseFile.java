package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private volatile File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContentWithoutUnicode(Predicate<Integer> predicate) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (predicate.test(data)) {
                    stringBuilder.append((char) data);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))) {
            bufferedWriter.write(content);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
