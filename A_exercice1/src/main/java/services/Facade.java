package services;

import entities.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class Facade {
    // Injection de l'entity manager, pour accès à la BD
    @PersistenceContext
    private EntityManager em;

    // On n'utilise plus de map : on a une base de données
    //private Map<String,String> users;

    // le peuplement se fait maintenant avec un script sql
   /* @PostConstruct
    public void fillMap(){
        users=new HashMap<>();
        users.put("alice","alice");
        users.put("bob","bob");
    }
    */

    public boolean checkLP(String login,String password) {
        // On va maintenant chercher l'utilisateur dans la BD à partir du login
        User user=em.find(User.class,login);
        if (user==null) {
            return false;
        } else {
            return (user.getPassword().equals(password));
        }
   }

   public List<User> getUersInscrits() {
        List<User> list = (List<User>) em.createQuery("SELECT login FROM User u").getResultList();
        return list;
   }
}
