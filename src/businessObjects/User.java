package businessObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class User {
    private int id;
    @Setter
    private String name;
    private String password;
    private ArrayList<Document> docs;
    private ArrayList<String> tags;
    @Setter
    private LocalDate lastLogin;

    public void updateLastLogin(){
        lastLogin = LocalDate.now();
    }

    public void addDoc(Document doc){
        docs.add(doc);
    }

    public void addTag(String tag){
        tags.add(tag);
    }
}

