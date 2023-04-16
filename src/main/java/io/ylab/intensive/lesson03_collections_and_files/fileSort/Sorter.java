package io.ylab.intensive.lesson03_collections_and_files.fileSort;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Sorter {
    List<File> tempFiles = new ArrayList<>();
    String path = "./src/main/java/io/ylab/intensive/lesson03_collections_and_files/fileSort/";

    public File sortFile(File dataFile) throws IOException {

        int chunkSize = 152;

        List<Long> chunk = new ArrayList<>(chunkSize);
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String currentLine = null;
            int tempFileCounter = 0;
            while ((currentLine = br.readLine()) != null) {
                chunk.add(Long.parseLong(currentLine));
                if (chunk.size() >= chunkSize) {
                    fillTempFileBySortedChunk(chunk, tempFileCounter);
                    tempFileCounter++;
                }
            }
            if (!chunk.isEmpty()) {
                fillTempFileBySortedChunk(chunk, tempFiles.size());
            }
        }

        int mergedFileCounter = 0;
        while (tempFiles.size() > 1) {
            List<File> mergedFiles = new ArrayList<>();
            for (int i = 0; i < tempFiles.size(); i += 2) {
                if (i != tempFiles.size() - 1) {
                    File mergedFile = new File(path + "tempMerged" + mergedFileCounter + ".txt");
                    mergedFileCounter++;
                    mergeFile(tempFiles.get(i), tempFiles.get(i + 1), mergedFile);
                    tempFiles.get(i).delete();
                    tempFiles.get(i + 1).delete();
                    mergedFiles.add(mergedFile);
                } else {
                    mergedFiles.add(tempFiles.get(i));
                }
            }
            tempFiles = mergedFiles;
        }

        File sortedFile = new File(path + "sorted");
        Files.copy(tempFiles.get(0).toPath(), sortedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        for (File tempFile : tempFiles) {
            tempFile.delete();
        }
        return sortedFile;
    }

    private void fillTempFileBySortedChunk(List<Long> chunck, int tempFileCounter) {
        Collections.sort(chunck);
        File tempFile = new File(path + "tempChunk" + +tempFileCounter + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            for (Long number : chunck) {
                bw.write(Long.toString(number));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        tempFiles.add(tempFile);
        chunck.clear();
    }

    private void mergeFile(File firstFile, File secondFile, File mergedFile) throws IOException {
        BufferedReader firstBr = new BufferedReader(new FileReader(firstFile));
        BufferedReader secondBr = new BufferedReader(new FileReader(secondFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(mergedFile));

        String numFromFirstFile = firstBr.readLine();
        String numFromSecondFile = secondBr.readLine();

        while (numFromFirstFile != null || numFromSecondFile != null) {
            if (numFromFirstFile != null && numFromSecondFile != null) {
                long firstNumber = Long.parseLong(numFromFirstFile);
                long secondNumber = Long.parseLong(numFromSecondFile);

                if (firstNumber < secondNumber) {
                    numFromFirstFile = writeAndReadNumber(firstBr, bw, numFromFirstFile);
                } else {
                    numFromSecondFile = writeAndReadNumber(secondBr, bw, numFromSecondFile);
                }
            } else if (numFromFirstFile == null) {
                numFromSecondFile = writeAndReadNumber(secondBr, bw, numFromSecondFile);
            } else {
                numFromFirstFile = writeAndReadNumber(firstBr, bw, numFromFirstFile);
            }
        }
        firstBr.close();
        secondBr.close();
        bw.close();
    }

    private String writeAndReadNumber(BufferedReader br, BufferedWriter bw, String number) throws IOException {
        bw.write(number);
        bw.newLine();
        return br.readLine();
    }
}