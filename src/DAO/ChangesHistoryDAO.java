package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Objects.ChangesHistory;
import Utils.Formats;
import Utils.ErrorRegistry;

public class ChangesHistoryDAO extends AbstractDAO<ChangesHistory> {

    private static final String TABLE_NAME = "CHANGE_HISTORIES";

    private boolean cloud;

    public ChangesHistoryDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(ChangesHistory changesHistory) {
        ArrayList<ChangesHistory> changesHistories = new ArrayList<>();
        changesHistories.add(changesHistory);
        return (insert(changesHistories));
    }

    @Override
    public boolean insert(ArrayList<ChangesHistory> changesHistories) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_USERS, ID_PEOPLES, ID_INSTRUCTOR, ID_CAR, " +
                       "NAME_CLASS, MODIFIED, STATE, DATE_START, DATE_END, COMMENT) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, changesHistories);
    }

    @Override
    public boolean save(ChangesHistory changesHistory) {
        ArrayList<ChangesHistory> changesHistories = new ArrayList<>();
        changesHistories.add(changesHistory);
        return save(changesHistories);
    }

    @Override
    public boolean save(ArrayList<ChangesHistory> changesHistories) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_USERS, ID_BRANCHES, NAME_TABLE, NAME_COLUMN, IDOBJECT, CRUD_METHOD, PREVIOUS_VALUE, NEXT_VALUE, DATE_START) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, changesHistories);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, ChangesHistory changesHistory) {
        try {
            preparedStatement.setInt(1, changesHistory.getIdUser());
            preparedStatement.setInt(2, changesHistory.getIdBranch());
            preparedStatement.setString(3, changesHistory.getNameTable());
            preparedStatement.setString(4, changesHistory.getNameColumn());
            preparedStatement.setInt(5, changesHistory.getIdObject());
            preparedStatement.setString(6, changesHistory.getCrudMethod());
            preparedStatement.setString(7, changesHistory.getPreviousValue());
            preparedStatement.setString(8, changesHistory.getNextValue());
            preparedStatement.setTimestamp(9, new Timestamp(changesHistory.getDateStart().getTimeInMillis()));
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<ChangesHistory> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<ChangesHistory> resultSetToObject(ResultSet rs) {
        ArrayList<ChangesHistory> changesHistories = new ArrayList<>();
        try {
            while (rs.next()) {
                ChangesHistory changesHistory = new ChangesHistory();
                changesHistory.setId(rs.getInt("ID"));
                changesHistory.setIdUser(rs.getInt("ID_USERS"));
                changesHistory.setIdBranch(rs.getInt("ID_BRANCHES"));
                changesHistory.setNameTable(rs.getString("NAME_TABLE"));
                changesHistory.setNameColumn(rs.getString("NAME_COLUMN"));
                changesHistory.setIdObject(rs.getInt("IDOBJECT"));
                changesHistory.setCrudMethod(rs.getString("CRUD_METHOD"));
                changesHistory.setPreviousValue(rs.getString("PREVIOUS_VALUE"));
                changesHistory.setNextValue(rs.getString("NEXT_VALUE"));
                changesHistory.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                changesHistories.add(changesHistory);
            }
            return changesHistories;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(ChangesHistory changesHistory) {
        return false;
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, ChangesHistory changesHistory) {
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