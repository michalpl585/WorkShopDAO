package pl.coderslab;

public class MainDao {
    public static void main(String[] args) {


        //nowy uzytkownik
        /*pl.coderslab.User user = new pl.coderslab.User();
        user.setUserName("michal");
        user.setEmail("micha2l@michal.pl");
        user.setPassword("haslo");

        pl.coderslab.UserDao userdao = new pl.coderslab.UserDao();
        userdao.create(user);

        //edycja
        user.setUserName("test");
        user.setEmail("mailek@mail.pl");
        userdao.update(user);*/

        //Usuwanie
        /*pl.coderslab.UserDao userdao = new pl.coderslab.UserDao();
        userdao.delete(6);*/



        //Pobieranie wszystkich uzytkownikow z bazy
        UserDao.findAll();
        System.out.println();
        System.out.println();
        //Wyswietlanie obiektow  user
        UserDao.findAll2();

    }
}
