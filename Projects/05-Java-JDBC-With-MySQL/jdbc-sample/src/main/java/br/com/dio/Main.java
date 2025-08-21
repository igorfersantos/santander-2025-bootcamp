package br.com.dio;

import br.com.dio.persistence.dao.contact.ContactDAO;
import br.com.dio.persistence.dao.employee.EmployeeAuditDAO;
import br.com.dio.persistence.dao.employee.EmployeeParamDAO;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import net.datafaker.Faker;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.OffsetDateTime;
import java.util.Locale;

public class Main {

    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();
    private final static ContactDAO contactDAO = new ContactDAO();
    private static final Faker faker = new Faker(Locale.of("pt", "BR"));

    public static void main(String[] args) {
        var flyway = Flyway.configure().dataSource(
                "jdbc:mysql://localhost/jdbc-java",
                "root",
                "root"
        ).load();
        flyway.migrate();

//        var employee = new EmployeeEntity();
//        employee.setName("Kalandra");
//        employee.setSalary(new BigDecimal(8000));
//        employee.setBirthday(OffsetDateTime.now().minusYears(39));
//        System.out.println(employee);
//        employeeDAO.insertWithProcedure(employee);
//        System.out.println(employee);
//
//        var update = new EmployeeEntity();
//        update.setId(employee.getId());
//        update.setName("Kavhra");
//        update.setSalary(new BigDecimal("5000"));
//        update.setBirthday(OffsetDateTime.now().minusYears(30).minusMonths(5).minusDays(2));
//        employeeDAO.update(update);
//        employeeDAO.delete(update.getId());
//
//        employeeAuditDAO.findAll().forEach(System.out::println);
//        System.out.println();
//        System.out.println(employeeAuditDAO.findById(update.getId()));
//        var entities = Stream.generate(() -> {
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().digits(4)));
//            var date = LocalDate.now().minusYears(faker.number().numberBetween(18, 40));
//            employee.setBirthday(OffsetDateTime.of(date, LocalTime.MIN, UTC));
//            return employee;
//        }).limit(4000).toList();

//        var employee = new EmployeeEntity();
//        employee.setName("Kalandra");
//        employee.setSalary(new BigDecimal(8000));
//        employee.setBirthday(OffsetDateTime.now().minusYears(39));
//        employeeDAO.insert(employee);
//        System.out.println(employee);
//
//        var contact1 = new ContactEntity();
//        contact1.setDescription("kalandra@kalandra.com");
//        contact1.setType("e-mail");
//        contact1.setEmployee(employee);
//        contactDAO.insert(contact1);
//
//        var contact2 = new ContactEntity();
//        contact2.setDescription("kalandra2@kalandra.com");
//        contact2.setType("e-mail");
//        contact2.setEmployee(employee);
//        contactDAO.insert(contact2);

        System.out.println(employeeDAO.findById(2));

    }

}
