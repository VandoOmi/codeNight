package datenBanken.dao;

import businessObjects.Document;
import datenBanken.interfaace.DaoInterace;

import java.util.ArrayList;

public class FilesDao implements DaoInterace<Document,Integer> {
    @Override
    public void create(Document document) {

    }

    @Override
    public void update(Document document) {

    }

    @Override
    public Document read(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<?> read() {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
