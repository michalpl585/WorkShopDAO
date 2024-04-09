package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;
import pl.coderslab.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO workshop2.users(username, email, password) VALUES (?, ?, ?)";

    private static final String READ_USER_QUERY =
            "SELECT username, email, password FROM workshop2.users WHERE id = ?";

    private static final String UPDATE_USER_QUERY =
            "UPDATE workshop2.users SET email = ?, username = ?, password = ? WHERE id = ?";

    private static final String DELETE_USER_QUERY =
            "DELETE FROM workshop2.users WHERE id = ?";

    private static final String READ_ALL_USERS_QUERY =
            "SELECT * FROM workshop2.users";

    private static User[] users = new User[0];

    public static void main(String[] args) {

    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

    public void delete(int userId) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.execute();
            System.out.println(userId + " usuniety");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            int update = statement.executeUpdate();
            if (update != 0) {
                System.out.println("Dane uzytkownika zaktualizowne");
            } else {
                System.out.println("Problem z edycja danych");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void findAll2() {
        for (User user : users) {
            System.out.println(user);
            System.out.println(user.getId() + " " + user.getUserName() + " " + user.getEmail());
        }
    }

    public static void findAll() {
        try (Connection connection = DbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(READ_ALL_USERS_QUERY);
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(id, username, email, password);
                users = addToArray(user, users);
                System.out.println(id + " " + email + " " + username + " " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User read(int userId) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                //int id = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(userId, username, email, password);
                System.out.println(", Username: " + username + " , email: " + email + " , haslo: " + password);
                System.out.println("pl.coderslab.User: " + user);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}