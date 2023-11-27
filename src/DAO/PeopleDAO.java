package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.People;
import Utils.Formats;
import Utils.ErrorRegistry;

public class PeopleDAO extends AbstractDAO<People> {

    private static final String TABLE_NAME = "PEOPLES";

    private boolean cloud;

    public PeopleDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(People people) {
        ArrayList<People> peoples = new ArrayList<>();
        peoples.add(people);
        return (insert(peoples));
    }

    @Override
    public boolean insert(ArrayList<People> peoples) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, SURNAME, DNI, TYPE, STATE, COUNTRY, PROVINCE, CITY, ADDRESS, BIRTHDAY, EMAIL, CELLPHONE, PHONE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, peoples);
    }

    @Override
    public boolean save(People people) {
        ArrayList<People> peoples = new ArrayList<>();
        peoples.add(people);
        return save(peoples);
    }

    @Override
    public boolean save(ArrayList<People> peoples) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, SURNAME, DNI, TYPE, STATE, COUNTRY, PROVINCE, CITY, ADDRESS, BIRTHDAY, EMAIL, CELLPHONE, PHONE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, peoples);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, People people) {
        try {
            preparedStatement.setString(1, people.getName());
            preparedStatement.setString(2, people.getSurname());
            preparedStatement.setString(3, people.getDni());
            preparedStatement.setString(4, people.getType());
            preparedStatement.setString(5, people.getState());
            preparedStatement.setString(6, people.getCountry());
            preparedStatement.setString(7, people.getProvince());
            preparedStatement.setString(8, people.getCity());
            preparedStatement.setString(9, people.getAddress());
            preparedStatement.setTimestamp(10, new Timestamp(people.getBirthday().getTimeInMillis()));
            preparedStatement.setString(11, people.getEmail());
            preparedStatement.setString(12, people.getCellphone());
            preparedStatement.setString(13, people.getPhone());
            preparedStatement.setTimestamp(14, new Timestamp(people.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<People> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<People> resultSetToObject(ResultSet rs) {
        ArrayList<People> peoples = new ArrayList<>();
        try {
            while (rs.next()) {
                People people = new People();
                people.setId(rs.getInt("ID"));
                people.setName(rs.getString("NAME"));
                people.setSurname(rs.getString("SURNAME"));
                people.setDni(rs.getString("DNI"));
                people.setType(rs.getString("TYPE"));
                people.setState(rs.getString("STATE"));
                people.setCountry(rs.getString("COUNTRY"));
                people.setProvince(rs.getString("PROVINCE"));
                people.setCity(rs.getString("CITY"));
                people.setAddress(rs.getString("ADDRESS"));
                people.setBirthday(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("BIRTHDAY"))));
                people.setEmail(rs.getString("EMAIL"));
                people.setCellphone(rs.getString("CELLPHONE"));
                people.setPhone(rs.getString("PHONE"));
                people.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                peoples.add(people);
            }
            return peoples;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(People people) {
        String query = "UPDATE " + TABLE_NAME + " SET NAME = ?, SURNAME = ?, DNI = ?, TYPE = ?, STATE = ?, COUNTRY = ?, PROVINCE = ?, CITY = ?, ADDRESS = ?, BIRTHDAY = ?, EMAIL = ?, CELLPHONE = ?, PHONE = ?, DATE_START = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, people);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, People people) {
        try {
            preparedStatement.setString(1, people.getName());
            preparedStatement.setString(2, people.getSurname());
            preparedStatement.setString(3, people.getDni());
            preparedStatement.setString(4, people.getType());
            preparedStatement.setString(5, people.getState());
            preparedStatement.setString(6, people.getCountry());
            preparedStatement.setString(7, people.getProvince());
            preparedStatement.setString(8, people.getCity());
            preparedStatement.setString(9, people.getAddress());
            preparedStatement.setTimestamp(10, new Timestamp(people.getBirthday().getTimeInMillis()));
            preparedStatement.setString(11, people.getEmail());
            preparedStatement.setString(12, people.getCellphone());
            preparedStatement.setString(13, people.getPhone());
            preparedStatement.setTimestamp(14, new Timestamp(people.getDateStart().getTimeInMillis()));
            preparedStatement.setInt(15, people.getId());
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