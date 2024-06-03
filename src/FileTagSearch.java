import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTagSearch {

    public List<Path> getFilesByTag(String directoryPath, String tag) throws IOException {
        List<Path> matchingFiles = new ArrayList<>();
        Path directory = Paths.get(directoryPath);

        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                UserDefinedFileAttributeView view = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
                if (view != null) {
                    try {
                        String attributeName = "user.tags";
                        if (view.list().contains(attributeName)) { // Überprüfe, ob das Attribut vorhanden ist
                            ByteBuffer buffer = ByteBuffer.allocate(view.size(attributeName));
                            view.read(attributeName, buffer);
                            buffer.flip();
                            String tags = StandardCharsets.UTF_8.decode(buffer).toString();
                            if (Arrays.asList(tags.split(",")).contains(tag)) matchingFiles.add(file);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return matchingFiles;
    }
    public static void openExplorerAndNavigate(String filePath) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(filePath));
            } else System.out.println("Desktop-Unterstützung ist nicht verfügbar.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        FileTagSearch fileTagSearch = new FileTagSearch();
        List<Path> filesWithTag = fileTagSearch.getFilesByTag("C:/Users/hoffm/IdeaProjects/untitled/Formation", "schlecht");
        for (Path file : filesWithTag) fileTagSearch.openExplorerAndNavigate(file.toString());
    }
}