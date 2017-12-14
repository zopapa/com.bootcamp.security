import com.bootcamp.entities.User;
import com.bootcamp.security.TokenManager;

public class AppMain {
    public static void main(String[] args) {
        User user = new User();
        user.setLogin("fefe");
        user.setPassword("fefep");

        String token =TokenManager.generate(user);
        System.out.println("Voici le token "+token+"\n");
        boolean b = TokenManager.verifyToken(token);
        System.out.println("Is good :::: "+b+"\n");
    }

}
