package datenBanken.dao;

import businessObjects.Document;
import businessObjects.User;
import datenBanken.interfaace.DaoInterace;
import datenBanken.service.ConnectedStatement;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BenutzerDao implements DaoInterace<User,Integer> {

    @Override
    public void create(User user) {
        ConnectedStatement cstm = new ConnectedStatement("insert into Benutzer (Username,Password,lastLogin,Tags,Docs) values (?,?,?,?,?);");
        cstm.setString(new String[]{user.getName(),user.getPassword()});
        cstm.setDate(3, Date.valueOf(user.getLastLogin()));
        cstm.setString(4,new String[]{getTags(user),getDocs(user)});
        cstm.executeQuery();
        cstm.close();
    }

    private static String getTags(User user) {
        StringBuilder tags= new StringBuilder();
        for (String s: user.getTags()) tags.append(s).append(";");
        return tags.toString();
    }

    private static String getDocs(User user) {
        StringBuilder docs = new StringBuilder();
        for(Document d: user.getDocs()) docs.append(d).append(";");
        return docs.toString();
    }

    @Override
    public void update(User user) {
        ConnectedStatement cstm = new ConnectedStatement("update User where id = ? set Username =?,Password=?,lastLogin=?,Tags=?,Docs=?");
        cstm.setInt(1,user.getId());
        cstm.setString(2,new String[]{user.getName(),user.getPassword()});
        cstm.setDate(4, Date.valueOf(user.getLastLogin()));
        cstm.setString(5,new String[]{getTags(user),getDocs(user)});
        cstm.executeQuery();
        cstm.close();
    }

    @Override
    public User read(Integer integer) {
        ConnectedStatement cstm = new ConnectedStatement("select * from Benutzer where id=?");
        cstm.setInt(1,integer);
        ResultSet resultSet= null;
        try {
            resultSet = cstm.executeQuery();
            int id = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String password = resultSet.getString(3);
            Date lastLogin = resultSet.getDate(4);
            ArrayList<String> taglist = getTagList(resultSet,5);
            ArrayList<String> docslist =getDocsList(resultSet,6);
            return new User(id,userName,password,docslist,taglist,lastLogin);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<String> getTagList(ResultSet resultSet,int index) throws SQLException {
        return (ArrayList<String>) Arrays.stream(resultSet.getString(index).split(";"))
                .filter(o->!o.isEmpty())
                .toList();
    }
    private static ArrayList<Document> getDocList(ResultSet resultSet,int index) throws SQLException {

    }

    public User read(String benutzerName) {
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
