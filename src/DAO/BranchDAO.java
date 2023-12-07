package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.Branch;
import Utils.Formats;
import Utils.ErrorRegistry;

public class BranchDAO extends AbstractDAO<Branch> {

    private static final String TABLE_NAME = "BRANCHES";

    private boolean cloud;

    public BranchDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(Branch branch) {
        ArrayList<Branch> branches = new ArrayList<>();
        branches.add(branch);
        return (insert(branches));
    }

    @Override
    public boolean insert(ArrayList<Branch> branches) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, COUNTRY, PROVINCE, CITY, ADDRESS, CELLPHONE, PHONE, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, branches);
    }

    @Override
    public boolean save(Branch branch) {
        ArrayList<Branch> branches = new ArrayList<>();
        branches.add(branch);
        return save(branches);
    }

    @Override
    public boolean save(ArrayList<Branch> branches) {
        String query = "INSERT INTO " + TABLE_NAME + " (NAME, COUNTRY, PROVINCE, CITY, ADDRESS, CELLPHONE, PHONE, STATE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, branches);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, Branch branch) {
        try {
            preparedStatement.setString(1, branch.getName());
            preparedStatement.setString(2, branch.getCountry());
            preparedStatement.setString(3, branch.getProvince());
            preparedStatement.setString(4, branch.getCity());
            preparedStatement.setString(5, branch.getAddress());
            preparedStatement.setString(6, branch.getCellphone());
            preparedStatement.setString(7, branch.getPhone());
            preparedStatement.setString(8, branch.getState());
            preparedStatement.setTimestamp(9, new Timestamp(branch.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<Branch> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<Branch> resultSetToObject(ResultSet rs) {
        ArrayList<Branch> branches = new ArrayList<>();
        try {
            while (rs.next()) {
                Branch branch = new Branch();
                branch.setId(rs.getInt("ID"));
                branch.setName(rs.getString("NAME"));
                branch.setCountry(rs.getString("COUNTRY"));
                branch.setProvince(rs.getString("PROVINCE"));
                branch.setCity(rs.getString("CITY"));
                branch.setAddress(rs.getString("ADDRESS"));
                branch.setCellphone(rs.getString("CELLPHONE"));
                branch.setPhone(rs.getString("PHONE"));
                branch.setState(rs.getString("STATE"));
                branch.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                branches.add(branch);
            }
            return branches;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(Branch branch) {
        String query = "UPDATE " + TABLE_NAME + " SET NAME = ?, COUNTRY = ?, PROVINCE = ?, CITY = ?, " +
                       "ADDRESS = ?, CELLPHONE = ?, PHONE = ?, STATE = ?, DATE_START = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, branch);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, Branch branch) {
        try {
            preparedStatement.setString(1, branch.getName());
            preparedStatement.setString(2, branch.getCountry());
            preparedStatement.setString(3, branch.getProvince());
            preparedStatement.setString(4, branch.getCity());
            preparedStatement.setString(5, branch.getAddress());
            preparedStatement.setString(6, branch.getCellphone());
            preparedStatement.setString(7, branch.getPhone());
            preparedStatement.setString(8, branch.getState());
            preparedStatement.setTimestamp(9, new Timestamp(branch.getDateStart().getTimeInMillis()));
            preparedStatement.setInt(10, branch.getId());
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