package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.User;
import Utils.Formats;
import Utils.ErrorRegistry;

public class UserDAO extends AbstractDAO<User> {

    private static final String TABLE_NAME = "USERS";

    private boolean cloud;

    public UserDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(User user) {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        return (insert(users));
    }

    @Override
    public boolean insert(ArrayList<User> users) {
        String query = "INSERT INTO " + TABLE_NAME + " (NICKNAME, PASSWORD, ROLE, STATE, DATE_START) " +
                   "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, users);
    }

    @Override
    public boolean save(User user) {
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        return save(users);
    }

    @Override
    public boolean save(ArrayList<User> users) {
        String query = "INSERT INTO " + TABLE_NAME + " (NICKNAME, PASSWORD, ROLE, STATE, DATE_START) " +
                   "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, users);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, User user) {
        try {
            preparedStatement.setString(1, new Utils.Security.Encryption().encrypt(user.getNickname()));
            preparedStatement.setString(2, new Utils.Security.Encryption().encrypt(user.getPassword()));
            preparedStatement.setString(3, new Utils.Security.Encryption().encrypt(user.getRole()));
            preparedStatement.setString(4, new Utils.Security.Encryption().encrypt(user.getState()));
            preparedStatement.setTimestamp(5, new Timestamp(user.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<User> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<User> resultSetToObject(ResultSet rs) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setNickname(new Utils.Security.Encryption().decrypt(rs.getString("NICKNAME")));
                user.setPassword(new Utils.Security.Encryption().decrypt(rs.getString("PASSWORD")));
                user.setRole(new Utils.Security.Encryption().decrypt(rs.getString("ROLE")));
                user.setState(new Utils.Security.Encryption().decrypt(rs.getString("STATE")));
                user.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE " + TABLE_NAME + " SET NICKNAME = ?, PASSWORD = ?, ROLE = ?, STATE = ?, DATE_START = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, user);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, User user) {
        try {
            preparedStatement.setString(1, new Utils.Security.Encryption().encrypt(user.getNickname()));
            preparedStatement.setString(2, new Utils.Security.Encryption().encrypt(user.getPassword()));
            preparedStatement.setString(3, new Utils.Security.Encryption().encrypt(user.getRole()));
            preparedStatement.setString(4, new Utils.Security.Encryption().encrypt(user.getState()));
            preparedStatement.setTimestamp(5, new Timestamp(user.getDateStart().getTimeInMillis()));
            preparedStatement.setInt(6, user.getId());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        deleteById(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME, id);
    }

    @Override
    public void deleteAll() {
        deleteAll(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME);
    }

    @Override
    public void resetIdCounter() {
        resetIdCounter(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME, cloud);
    }

    @Override
    public int countAll() {
        return countAll(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME);
    }

}