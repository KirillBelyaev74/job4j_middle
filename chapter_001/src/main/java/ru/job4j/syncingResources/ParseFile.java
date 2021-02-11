package ru.job4j.syncingResources;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ParseFile {

    private volatile File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            Predicate<Integer> predicate = this.getPredicate("getContent");
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

    public String getContentWithoutUnicode() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            Predicate<Integer> predicate = this.getPredicate("getContentWithoutUnicode");
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

    private Predicate<Integer> getPredicate(String nameMethod) {
        Map<String, Predicate<Integer>> hashMap = new HashMap<>();
        hashMap.put("getContent", (o) -> true);
        hashMap.put("getContentWithoutUnicode", (o) -> o < 0x80);
        return hashMap.get(nameMethod);
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))) {
            bufferedWriter.write(content);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
