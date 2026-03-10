package hr.tvz.sportapp.database;
import hr.tvz.sportapp.exceptions.DataBaseException;
import hr.tvz.sportapp.model.person.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDbRepository implements DbSupport<User>{
    private final String SELECT_ALL_USERS_QUERY = "SELECT * FROM USERS";
    private final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM USERS WHERE OIB=?";
    private final String INSERT_USER_INTO_DB_QUERY = "INSERT INTO USERS(oib,first_name,last_name,username) VALUES (?,?,?,?)";
    private final String USERS_COLUMN_NAME_OIB = "OIB";
    private final String USERS_COLUMN_NAME_FIRST_NAME = "FIRST_NAME";
    private final String USERS_COLUMN_NAME_LAST_NAME = "LAST_NAME";
    private final String USERS_COLUMN_NAME_USERNAME = "USERNAME";
    private static UserDbRepository userDbRepository;
    public static UserDbRepository getInstance() {
        if (userDbRepository == null) {
            userDbRepository = new UserDbRepository();
        }
        return userDbRepository;
    }
    @Override
    public Set<User> getAll() throws DataBaseException {
        Connection conn = connectToDatbase();
        Set<User> users = new HashSet<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                String oib = rs.getString(USERS_COLUMN_NAME_OIB);
                String first_name = rs.getString(USERS_COLUMN_NAME_FIRST_NAME);
                String last_name = rs.getString(USERS_COLUMN_NAME_LAST_NAME);
                String username = rs.getString(USERS_COLUMN_NAME_USERNAME);
                User user = new User.UserBuilder(oib,first_name,last_name)
                        .username(username)
                        .build();
                users.add(user);
            }
            closeConnection(conn);
            return users;
        }catch (SQLException e)
        {
            throw new DataBaseException(e);
        }
    }
    @Override
    public Optional<User> getOneById(String id) throws DataBaseException {
        Connection conn = connectToDatbase();
        Optional<User> optionalUser = Optional.empty();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_BY_ID_QUERY);
            preparedStatement.setString(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                String oib = rs.getString(USERS_COLUMN_NAME_OIB);
                String first_name = rs.getString(USERS_COLUMN_NAME_FIRST_NAME);
                String last_name = rs.getString(USERS_COLUMN_NAME_LAST_NAME);
                String username = rs.getString(USERS_COLUMN_NAME_USERNAME);
                User user = new User.UserBuilder(oib,first_name,last_name)
                        .username(username)
                        .build();
            optionalUser = Optional.of(user);
            }
            closeConnection(conn);
            return optionalUser;
        }catch (SQLException e)
        {
            throw new DataBaseException(e);
        }
    }
    @Override
    public void insertIntoDb(User user) throws DataBaseException {
        Connection conn = connectToDatbase();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER_INTO_DB_QUERY);
            preparedStatement.setString(1, user.getOIB());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.executeUpdate();
            closeConnection(conn);
        }catch (SQLException e)
        {
            throw new DataBaseException(e);
        }
    }
}
