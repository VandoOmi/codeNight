package businessObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
@AllArgsConstructor
public class Tags {
    private HashSet<String> tags = new HashSet<>();
    public boolean containsTag(String tag){
        return tags.contains(tag);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }



}
