package io.ylab.intensive.lesson05_Spring.eventsourcing.api;

import io.ylab.intensive.lesson05_Spring.eventsourcing.Person;
import io.ylab.intensive.lesson05_Spring.eventsourcing.db.DAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DAO dao = applicationContext.getBean(DAO.class);

        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        personApi.savePerson(1L, "Vadim", "Podogov", "Alexandrovich");
        personApi.savePerson(2L, "Maksim", "Ivanov", "Dmitrievich");
        personApi.savePerson(3L, "Alexander", "Gozhan", "Igorevich");
        personApi.savePerson(4L, "Kirill", "Butskovskiy", "Antonovich");
        personApi.deletePerson(2L);

        Person person = personApi.findPerson(1L, dao);
        System.out.println(person.getId() + " " + person.getName() + " " + person.getLastName() + " " + person.getMiddleName());

        List<Person> list = personApi.findAll(dao);
        for (Person person1 : list) {
            System.out.println(person1.getId() + " " + person1.getName() + " " + person1.getLastName() + " " + person1.getMiddleName());
        }
    }
}
