package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args)  {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Konstantin","Belov", (byte) 18);
        service.saveUser("Teodor","Gruv", (byte) 121);
        service.saveUser("Nikolay","Belov", (byte) 14);
        service.saveUser("Aleksander","Belkin", (byte) 50);
        ArrayList<User> users = (ArrayList) service.getAllUsers();
        System.out.println(users);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
