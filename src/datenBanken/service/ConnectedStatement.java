package datenBanken.service;

import lombok.Getter;

import java.sql.*;

@Getter
public class ConnectedStatement {
    private Connection con;
    private PreparedStatement pstm;

    public ConnectedStatement(String query) {
        con = ConnectionManager.creatConnection();
        try {
            pstm = con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet executeQuery() {
        try {
            return pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate() {
        try {
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setString(String[] value) {
        try {
            for (int i = 0; i < value.length; i++) {
                pstm.setString(i + 1, value[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setString(int index,String[] value) {
        try {
            for (int i = index; i < value.length; i++) {
                pstm.setString(i, value[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setInt(int index, int value) {
        try {
            pstm.setInt(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setString(int index, String value) {
        try {
            pstm.setString(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setInt(int value) {
        try {
            pstm.setInt(1, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setDate(int index, Date value) {
        try {
            pstm.setDate(index, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

