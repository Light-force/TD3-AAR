package services;

import entities.User;
import exceptions.UserAllreadyExistsException;
import exceptions.UserDoesNotExistException;
import org.springframework.stereotype.Service;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

   @Transactional
   public User createUser(String login,String password) throws UserAllreadyExistsException {
        User user=em.find(User.class,login);
        if (user!=null) {
            throw new UserAllreadyExistsException();
        }
        user =new User(login,password);
        em.persist(user);
        return user;
   }

   @Transactional
   public void deleteUser(String login) throws UserDoesNotExistException {
        User user = em.find(User.class, login);
        if (user != null) {
            em.remove(user);
        }
        else {
            throw new UserDoesNotExistException();
        }
   }
}
