import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final File startDirectory = new File("test");
        final List<File> files = findAllFilesInDirectory(startDirectory);
        final List<File> sortedFiles = sortFilesByName(files);

        copyContentFromFilesToOutputFile(sortedFiles);

        System.out.println("DONE!");
    }

    public static List<File> findAllFilesInDirectory(final File dir) {
        final File[] dirList = dir.listFiles();
        final List<File> result = new ArrayList<>();

        for (int i = 0; i < dirList.length; i++) {
            if (dirList[i].isDirectory()) {
                result.addAll(findAllFilesInDirectory(dirList[i]));
            } else if (dirList[i].isFile()) {
                result.add(dirList[i]);
            }
        }

        return result;
    }

    public static List<File> sortFilesByName(final List<File> filesToSort) {
        return filesToSort.stream()
            .sorted((file1, file2) -> file1.getName().compareTo(file2.getName()))
            .collect(Collectors.toList());
    }

    public static void copyContentFromFilesToOutputFile(final List<File> filesToRead) {
        try (final BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"))) {
            for (File fileToRead : filesToRead) {
                final BufferedReader input = new BufferedReader(new FileReader(fileToRead));

                String line;
                while ((line = input.readLine()) != null) {
                    output.append(line);
                    output.newLine();
                }

                input.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}