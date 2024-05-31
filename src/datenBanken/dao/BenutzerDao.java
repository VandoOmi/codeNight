package datenBanken.dao;

import businessObjects.Document;
import businessObjects.User;
import datenBanken.interfaace.DaoInterace;
import datenBanken.service.ConnectedStatement;

import java.sql.Date;
import java.util.ArrayList;

public class BenutzerDao implements DaoInterace<User,Integer> {

    @Override
    public void create(User user) {
        ConnectedStatement cstm = new ConnectedStatement("insert into Benutzer (Username,Password,lastLogin,Tags,Docs) values (?,?,?,?,?);");
        cstm.setString(new String[]{user.getName(),user.getPassword()});
        cstm.setDate(3, Date.valueOf(user.getLastLogin()));
        StringBuilder tags= new StringBuilder();
        for (String s: user.getTags()) tags.append(s).append(";");
        StringBuilder docs = new StringBuilder();
        for(Document d:user.getDocs()) docs.append(d).append(";");
        cstm.setString(4,new String[]{tags.toString(),docs.toString()});
        cstm.executeQuery();
        return null;
    }

    @Override
    public void update(User user, Integer integer) {

    }

    @Override
    public User read(Integer integer) {
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
