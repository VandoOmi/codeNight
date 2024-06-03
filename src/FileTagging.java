import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileTagging {

    public void addTag(String filePath, List<String> tags) {
        Path path = Paths.get(filePath);
        UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        if (view != null) {
            try {
                String attributeName = "user.tags";
                StringBuilder tagBuilder = new StringBuilder();
                for (String tag : tags) {
                    tagBuilder.append(tag).append(",");
                }
                tagBuilder= new StringBuilder(tagBuilder.substring(0, tagBuilder.length() - 1));
                ByteBuffer buffer = ByteBuffer.wrap(tagBuilder.toString().getBytes(StandardCharsets.UTF_8));
                view.write(attributeName, buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Datei muss ein Tag haben
    public List<String> getTag(String filePath) {
        Path path = Paths.get(filePath);
        UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        if (view != null) {
            try {
                String attributeName = "user.tags";
                if (view.list().contains(attributeName)) {
                    ByteBuffer buffer = ByteBuffer.allocate(view.size(attributeName));
                    view.read(attributeName, buffer);
                    buffer.flip();
                    return Arrays.stream(StandardCharsets.UTF_8.decode(buffer).toString().split(",")).toList();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void removeTag(String filePath) {
        Path path = Paths.get(filePath);
        UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        if (view != null) {
            try {
                String attributeName = "user.tags";
                view.delete(attributeName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FileTagging tagging = new FileTagging();
        String filePath = "C:/Users/hoffm/IdeaProjects/untitled/Formation/src/example.txt";
        ArrayList<String> list = new ArrayList<>();
        list.add("toll");
        list.add("schlecht");


        //Tags hinzufügen
        // tagging.addTag(filePath,list);

        //Tags removen
//       tagging.removeTag(filePath);

        // Tag auslesen
        StringBuilder tag = new StringBuilder();
        List<String> list1=tagging.getTag(filePath);
        for(String s : list1) tag.append(s);
        System.out.println("Tag: " + tag);
    }
}