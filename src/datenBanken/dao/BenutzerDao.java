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
        for(Document d: user.getDocs()) docs.append(d.getId())
                .append(d.getName()).append(",")
                .append(d.getPath()).append(",")
                .append(d.getType()).append(",")
                .append(getTagsString(d)).append(";");
        return docs.substring(0,docs.length()-1);
    }
    public static String getTagsString(Document d){
        StringBuilder builder = new StringBuilder();
        for(String s: d.getTags() ) builder.append(s).append(",");
        return builder.substring(0,builder.length()-1)+";";
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
            resultSet.next();
            resultSet.close();
            cstm.close();
            return getUser(cstm,resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private static User getUser(ConnectedStatement cstm,ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt(1);
        String userName = resultSet.getString(2);
        String password = resultSet.getString(3);
        Date lastLogin = resultSet.getDate(4);
        ArrayList<String> taglist = getTagList(resultSet,5);
        ArrayList<Document> docslist =getDocList(resultSet,6);
        return new User(id, userName, password, docslist, taglist, lastLogin.toLocalDate());
    }

    private static ArrayList<String> getTagList(ResultSet resultSet,int index) throws SQLException {
        return (ArrayList<String>) Arrays.stream(resultSet.getString(index).split(";"))
                .filter(o->!o.isEmpty())
                .toList();
    }

    //die Docs noch irgendwie auslesen  Keine Ahnung ob die Funktioniert
    private static ArrayList<Document> getDocList(ResultSet resultSet,int index) throws SQLException {
        ArrayList<Document> documents = new ArrayList<>();
        String docsString=resultSet.getString(index);
        String[] docStrings = docsString.split(";");

        for (String docString : docStrings) {
            String[] attributes = docString.split(",");
            Document doc = new Document();
            doc.setId(Integer.parseInt(attributes[0]));
            doc.setName(attributes[1]);
            doc.setPath(attributes[2]);
            doc.setType(attributes[3]);

            ArrayList<String> tags = new ArrayList<>();
            for (int i = 4; i < attributes.length; i++) {
                tags.add(attributes[i]);
            }
            doc.setTags(tags);

            documents.add(doc);
        }

        return documents;
    }

    public User read(String benutzerName) {
        ConnectedStatement cstm = new ConnectedStatement("select * from Benutzer where UserName=?");
        cstm.setString(1,benutzerName);
        try {
            ResultSet  resultSet = cstm.executeQuery();
            resultSet.next();
            resultSet.close();
            cstm.close();
            return getUser(cstm,resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  ArrayList<?> read() {
        ArrayList<User> list = new ArrayList<>();
        ConnectedStatement cstm= new ConnectedStatement("select * from Benutzer");
        try {
            ResultSet resultSet=cstm.executeQuery();
            while (resultSet.next()){
                list.add(getUser(cstm,resultSet));
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cstm.close();
        return list;
    }

    @Override
    public void delete(Integer integer) {
        ConnectedStatement cstm = new ConnectedStatement("delete from benutzer where id=?;");
        cstm.setInt(integer);
        cstm.executeQuery();
        cstm.close();
    }
}
