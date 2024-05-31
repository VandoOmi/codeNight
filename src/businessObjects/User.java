package businessObjects;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;

@Getter
public class User {
    int id;
    @Setter
    String name;
    String password;
    ArrayList<Document> docs;
    ArrayList<String> tags;
    @Setter
    Date lastLogin;
}
