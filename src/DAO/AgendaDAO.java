package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;

import Objects.Agenda;
import Objects.Car;
import Objects.People;
import Objects.User;
import Utils.Formats;
import Utils.ErrorRegistry;

public class AgendaDAO extends AbstractDAO<Agenda> {

    private static final String TABLE_NAME = "AGENDAS";

    private boolean cloud;

    public AgendaDAO(boolean cloud) {
        this.cloud = cloud;
    }

    public ArrayList<Agenda> readAllOrderByDateStart() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY DATE_START ASC";
        return resultSetToObject(read(getConnectionFile(), query, TABLE_NAME));
    }

    public ArrayList<Agenda> getAgendasByState(String state) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE STATE = ?";
        try (Connection connection = getConnectionFile();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, state);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return resultSetToObject(rs);
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    public ArrayList<Agenda> getAgendasDay(Calendar calendar) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE YEAR(DATE_START) = ? AND MONTH(DATE_START) = ? AND DAY(DATE_START) = ? AND HOUR(DATE_START) = ?;";
        try (Connection connection = getConnectionFile();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, calendar.get(Calendar.YEAR));
            preparedStatement.setInt(2, calendar.get(Calendar.MONTH) + 1);
            preparedStatement.setInt(3, calendar.get(Calendar.DAY_OF_MONTH));
            preparedStatement.setInt(4, calendar.get(Calendar.HOUR_OF_DAY));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return resultSetToObject(rs);
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    public Agenda getAgendaDay(Calendar calendar) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE YEAR(DATE_START) = ? AND MONTH(DATE_START) = ? AND DAY(DATE_START) = ? AND HOUR(DATE_START) = ?;";
        try (Connection connection = getConnectionFile();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, calendar.get(Calendar.YEAR));
            preparedStatement.setInt(2, calendar.get(Calendar.MONTH) + 1);
            preparedStatement.setInt(3, calendar.get(Calendar.DAY_OF_MONTH));
            preparedStatement.setInt(4, calendar.get(Calendar.HOUR_OF_DAY));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return resultSetToObject(rs).get(0);
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }
    
    public ArrayList<Agenda> getAgendasMonth(int month, int year) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE YEAR(DATE_START) = ? AND MONTH(DATE_START) = ?;";
        try (Connection connection = getConnectionFile();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return resultSetToObject(rs);
            } catch (SQLException e) {
                new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
                }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
            }
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean insert(Agenda agenda) {
        ArrayList<Agenda> agendas = new ArrayList<>();
        agendas.add(agenda);
        return (insert(agendas));
    }

    @Override
    public boolean insert(ArrayList<Agenda> agendas) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_USERS, ID_PEOPLES, ID_INSTRUCTOR, ID_CAR, " +
                       "NAME_CLASS, MODIFIED, STATE, DATE_START, DATE_END, COMMENT) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(cloud ? getConnectionCloud() : getConnectionFile(), query, agendas);
    }

    @Override
    public boolean save(Agenda agenda) {
        ArrayList<Agenda> agendas = new ArrayList<>();
        agendas.add(agenda);
        return (save(agendas));
    }

    @Override
    public boolean save(ArrayList<Agenda> agendas) {
        String query = "INSERT INTO " + TABLE_NAME + " (ID_USERS, ID_PEOPLES, ID_INSTRUCTOR, ID_CAR, " +
                       "NAME_CLASS, MODIFIED, STATE, DATE_START, DATE_END, COMMENT) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return insertBatch(getConnectionFile(), getConnectionCloud(), query, agendas);
    }

    @Override
    public PreparedStatement prepareInsertStatement(PreparedStatement preparedStatement, Agenda agenda) {
        try {
            preparedStatement.setInt(1, agenda.getIdUser());
            preparedStatement.setInt(2, agenda.getIdPeople());
            preparedStatement.setInt(3, agenda.getIdInstructor());
            preparedStatement.setInt(4, agenda.getIdCar());
            preparedStatement.setString(5, agenda.getNameClass());
            preparedStatement.setInt(6, agenda.getModified());
            preparedStatement.setString(7, agenda.getState());
            preparedStatement.setTimestamp(8, new Timestamp(agenda.getDateStart().getTimeInMillis()));
            preparedStatement.setTimestamp(9, new Timestamp(agenda.getDateEnd().getTimeInMillis()));
            preparedStatement.setString(10, agenda.getComment());
            return preparedStatement;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public ArrayList<Agenda> read() {
        return resultSetToObject(read(cloud ? getConnectionCloud() : getConnectionFile(), TABLE_NAME));
    }

    @Override
    public ArrayList<Agenda> resultSetToObject(ResultSet rs) {
        ArrayList<Agenda> agendas = new ArrayList<>();

        ArrayList<User> users = new DAO.UserDAO(false).read();
        ArrayList<People> peoples = new DAO.PeopleDAO(false).read();
        ArrayList<Car> cars = new DAO.CarDAO(false).read();

        try {
            while (rs.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(rs.getInt("ID"));
                agenda.setIdUser(rs.getInt("ID_USERS"));
                agenda.setIdPeople(rs.getInt("ID_PEOPLES"));
                agenda.setIdInstructor(rs.getInt("ID_INSTRUCTOR"));
                agenda.setIdCar(rs.getInt("ID_CAR"));
                agenda.setNameClass(rs.getString("NAME_CLASS"));
                agenda.setModified(rs.getInt("MODIFIED"));
                agenda.setState(rs.getString("STATE"));
                agenda.setDateStart(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_START"))));
                agenda.setDateEnd(new Formats().getCalendarToDateSQL(String.valueOf(rs.getTimestamp("DATE_END"))));
                agenda.setComment(rs.getString("COMMENT"));

                for (User user : users) {
                    if (user.getId() == agenda.getIdUser()) {
                        agenda.setUser(user);
                        break;
                    }
                }

                for (People people : peoples) {
                    if (people.getId() == agenda.getIdPeople()) {
                        agenda.setPeople(people);
                        break;
                    }
                }

                for (People people : peoples) {
                    if (people.getId() == agenda.getIdInstructor()) {
                        agenda.setInstructor(people);
                        break;
                    }
                }

                for (Car car : cars) {
                    if (car.getId() == agenda.getIdCar()) {
                        agenda.setCar(car);
                        break;
                    }
                }

                agendas.add(agenda);
            }
            return agendas;
        } catch (SQLException e) {
            new ErrorRegistry().newError(String.valueOf(this.getClass().getName()), String.valueOf(new Object() {
            }.getClass().getEnclosingMethod().getName()), String.valueOf(e));
        }
        return null;
    }

    @Override
    public boolean update(Agenda agenda) {
        String query = "UPDATE " + TABLE_NAME + " SET ID_USERS = ?, ID_PEOPLES = ?, ID_INSTRUCTOR = ?, ID_CAR = ?, " +
                       "NAME_CLASS = ?, MODIFIED = ?, STATE = ?, DATE_START = ?, DATE_END = ?, COMMENT = ? WHERE ID = ?";
        return update(getConnectionFile(), getConnectionCloud(), query, agenda);
    }

    @Override
    public PreparedStatement prepareUpdateStatement(PreparedStatement preparedStatement, Connection connection, String query, Agenda agenda) {
        try {
            preparedStatement.setInt(1, agenda.getIdUser());
            preparedStatement.setInt(2, agenda.getIdPeople());
            preparedStatement.setInt(3, agenda.getIdInstructor());
            preparedStatement.setInt(4, agenda.getIdCar());
            preparedStatement.setString(5, agenda.getNameClass());
            preparedStatement.setInt(6, agenda.getModified());
            preparedStatement.setString(7, agenda.getState());
            preparedStatement.setTimestamp(8, new Timestamp(agenda.getDateStart().getTimeInMillis()));
            preparedStatement.setTimestamp(9, new Timestamp(agenda.getDateEnd().getTimeInMillis()));
            preparedStatement.setString(10, agenda.getComment());
            preparedStatement.setInt(11, agenda.getId());
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