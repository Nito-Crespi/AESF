package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Objects.Credential;
import Utils.ErrorRegistry;

public class CredentialDAO extends AbstractDAO<Credential> {

    private static final String TABLE_NAME = "CREDENTIALS";

    private boolean cloud;

    public CredentialDAO(boolean cloud) {
        this.cloud = cloud;
    }

    @Override
    public boolean insert(Credential credential) {
        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        return (insert(credentials));
    }

    @Override
    public boolean insert(ArrayList<Credential> credentials) {
        String query = "INSERT INTO " + TABLE_NAME + " (USERNAME, OSNAME, MACADDRESS, OSARCH, IDBRANCHES) " +
                       "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, credentials);
    }
    
    @Override
    public boolean save(Credential credential) {
        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        return save(credentials);
    }

    @Override
    public boolean save(ArrayList<Credential> credentials) {
        String query = "INSERT INTO " + TABLE_NAME + " (USERNAME, OSNAME, MACADDRESS, OSARCH, IDBRANCHES) " +
                       "VALUES (?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, credentials);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, Credential credential) {
        try {
            preparedStatement.setString(1, new Utils.Security.Encryption().encrypt(credential.getUsername()));
            preparedStatement.setString(2, new Utils.Security.Encryption().encrypt(credential.getOsName()));
            preparedStatement.setString(3, new Utils.Security.Encryption().encrypt(credential.getMacAddress()));
            preparedStatement.setString(4, new Utils.Security.Encryption().encrypt(credential.getOsArch()));
            preparedStatement.setInt(5, credential.getIdBranch());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<Credential> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<Credential> resultSetToObject(ResultSet rs) {
        ArrayList<Credential> credentials = new ArrayList<>();
        try {
            while (rs.next()) {
                Credential credential = new Credential();
                credential.setId(rs.getInt("ID"));
                credential.setUsername(new Utils.Security.Encryption().decrypt(rs.getString("USERNAME")));
                credential.setOsName(new Utils.Security.Encryption().decrypt(rs.getString("OSNAME")));
                credential.setMacAddress(new Utils.Security.Encryption().decrypt(rs.getString("MACADDRESS")));
                credential.setOsArch(new Utils.Security.Encryption().decrypt(rs.getString("OSARCH")));
                credential.setIdBranch(rs.getInt("IDBRANCHES"));
                credentials.add(credential);
            }
            return credentials;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(Credential credential) {
        String query = "UPDATE " + TABLE_NAME + " SET USERNAME = ?, OSNAME = ?, MACADDRESS = ?, " +
                       "OSARCH = ?, IDBRANCHES = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, credential);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, Credential credential) {
        try {
            preparedStatement.setString(1, new Utils.Security.Encryption().encrypt(credential.getUsername()));
            preparedStatement.setString(2, new Utils.Security.Encryption().encrypt(credential.getOsName()));
            preparedStatement.setString(3, new Utils.Security.Encryption().encrypt(credential.getMacAddress()));
            preparedStatement.setString(4, new Utils.Security.Encryption().encrypt(credential.getOsArch()));
            preparedStatement.setInt(5, credential.getIdBranch());
            preparedStatement.setInt(6, credential.getId());
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