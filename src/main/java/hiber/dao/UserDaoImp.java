package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void add(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<Car> listCars() {
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getCarsOwner (String model, int series) {

      TypedQuery<Car> carQuery = sessionFactory
              .getCurrentSession()
              .createQuery("from Car where model = :car_model and series = :car_series")
              .setParameter("car_model", model)
              .setParameter("car_series", series);

      List<Car> carList = carQuery.getResultList();

      if (!carList.isEmpty()) {
         return carList.get(0).getUser();
      }
      return null;
   }

}