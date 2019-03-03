package auth;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    private boolean result;
    String url = "jdbc:mysql://localhost:3306/authorizationusers?serverTimezone=UTC";
    String user = "root";
    String pass = "TO124818be";

    //public Map<String, String> users = new HashMap<>();

    public AuthServiceImpl() {
        /*users.put("ivan", "123");
        users.put("petr", "345");
        users.put("julia", "789");*/
    }

    /*@Override
    public boolean authUser(String username, String password) {
        String pwd = users.get(username);
        return pwd != null && pwd.equals(password);
    }*/

    @Override
    public boolean authUser(String username, String password) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(url, user, pass)){
            System.out.println("подключилось!");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM authorizationusers.authorizationusers WHERE Name = ? AND Password= ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rst = ps.executeQuery();

            result=rst.next();
            System.out.println(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }

    @Override
    public boolean renameUser(String username, String newName, String password) throws ClassNotFoundException{
        if (authUser(username, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, pass)) {
                PreparedStatement checkNameExist = con.prepareStatement("SELECT * FROM authorizationusers.authorizationusers WHERE Name = ? ");
                checkNameExist.setString(1, newName);
                ResultSet rst = checkNameExist.executeQuery();

                if (!rst.next()) {
                    PreparedStatement updateUserName = con.prepareStatement("UPDATE authorizationusers.authorizationusers SET Name = ? WHERE Name = ? AND Password= ?");
                    updateUserName.setString(1, newName);
                    updateUserName.setString(2, username);
                    updateUserName.setString(3, password);

                    if (updateUserName.executeUpdate()==1) {
                        result=true;
                        System.out.println("Updated name user ");
                    }else {
                        System.out.println("Can not updated name user ");
                        result=false;
                    }
                }else {
                    System.out.println("Name exist!!! change Name ");
                    result=false;
                }


            }catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            System.out.println("Введите правильный пароль для изменения имени");
        }
        return result;
    }




}
