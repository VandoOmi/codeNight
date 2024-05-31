package businessObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.datatype.DatatypeConstants;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {
    private int id;
    private String name;
    private String path;
    private String type;
    private ArrayList<String> tags;

}
