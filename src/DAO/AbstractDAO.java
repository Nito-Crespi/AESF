package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Resources.DBConstants;
import Utils.ErrorRegistry;

public abstract class AbstractDAO<T> implements GenericDAO<T> {

    protected Connection getConnectionCloud() {
        try {
            return DriverManager.getConnection(DBConstants.URL_CLOUD, DBConstants.USERNAME_CLOUD, DBConstants.PASSWORD_CLOUD);
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    protected Connection getConnectionFile() {
        try {
            return DriverManager.getConnection(DBConstants.URL_LOCAL, DBConstants.USERNAME_LOCAL, DBConstants.PASSWORD_LOCAL);
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }
}
