package utils;

import businessObjects.User;
import datenBanken.dao.BenutzerDao;

public class UserUtil {
    public static boolean userAvailable(String name, String password){
        BenutzerDao dao = new BenutzerDao();
        if(dao.read(name).getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
