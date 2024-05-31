package datenBanken.interfaace;

import java.util.ArrayList;

public interface DaoInterace<T,K>{
 void create(T t);
 void update(T t);
 T read(K k);
 ArrayList<?> read();
 void delete(K k);
}

