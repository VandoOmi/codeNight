package businessObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
public class Tags {
    private ArrayList<String> tags = new ArrayList<>();
    public boolean containsTag(String tag){
        return tags.contains(tag);
    }
}
