import model.Affiliation;
import model.Author;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main {

    private static final String AUTHORS_CSV = "./practica-bd2324-hibernate-main/src/main/resources/authors.csv";
    public static void main (String[] args) throws IOException, SQLException {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        // @TODO Crea una afiliación con nombre "Universidad Politécnica de Madrid" de la
        // ciudad de Madrid, España y guarda dicha afiliación en la base de datos.

        Affiliation affiliation = new Affiliation("Universidad Politécnica de Madrid","Madrid", "España");

        // Guardar la afiliación en la base de datos
        session.beginTransaction();
        session.save(affiliation);
        session.getTransaction().commit();


        // @TODO Lee el fichero CSV authors.csv que encontrarás en resources y recorrelo usando
        // CSVParser para crear los autores que en el se encuentran. Asigna dichos autores a la
        // afiliación creada anteriormente. Guarda estos autores y sus afiliaciones en la base
        // de datos.

        Reader reader = Files.newBufferedReader(Paths.get(AUTHORS_CSV));

        CSVFormat format = CSVFormat.DEFAULT.withHeader();

        CSVParser csvParser = new CSVParser(reader,format);

        for (CSVRecord csvRecord : csvParser){
            String nombre = csvRecord.get("author_name");
            double importance = Double.parseDouble(csvRecord.get("importance"));
            Author author = new Author(nombre,importance);
            author.getAffiliations().add(affiliation);

            // Guardar la afiliación en la base de datos
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();

        }
        csvParser.close();

        session.close();

    }
}
