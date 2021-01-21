package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {

    private volatile File file;

    public synchronized void setFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String output;
            while ((output = bufferedReader.readLine()) == null) {
                stringBuilder.append(output);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public synchronized String getContentWithoutUnicode() {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (data < 0x80) {
                    stringBuilder.append((char)data);
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
