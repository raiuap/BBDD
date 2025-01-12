import java.sql.*;
import java.util.Random;

public class Main {

    //Sistituye xxx por los parámetros de tu conexión

    private static final String DB_SERVER = "localhost";

    private static final int DB_PORT = 3306;

    private static final String DB_NAME = "bd2324";

    private static final String DB_USER = "root";

    private static final String DB_PASS = null;

    private static Connection conn;

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);

        // @TODO Prueba sus funciones
        // 1. Añadete como autor a la base de datos
        // 2. Muestra por pantalla la lista de artículos del autor “Ortega F.” en 2021
        // 3. Muestra por pantalla una lista de las afiliaciones y el número de autores que
        //    tiene cada una

        nuevoAutor("Laura");
        nuevoAutor("Rayan");
        nuevoAutor("Paula");
        nuevoAutor("Rubén");
        nuevoAutor("Natalia");

        listaArticulosPorAutor("Ortega F.",2021);

        listaAfiliaciones();



        conn.close();
    }

    private static void nuevoAutor (String authorName) throws SQLException {
        // Crea un metodo que añada un nuevo autor a la base de datos con importancia 0.
        // Genera el id de forma aleatoria
        Random rand = new Random();
        int M = 9999;
        int N = 0;
        long id = (int) (Math.random()*(N-M+1)+M);

        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO author (author_id, author_name) VALUES (" + id + ",'" + authorName + "');");
    }

    private static void listaArticulosPorAutor (String authorName, int year) throws SQLException {
        // Muestra por pantalla una lista de articulos (título y fecha de publicación) para un
        //  autor y año
        Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("SELECT title, publication_date FROM article " +
                "INNER JOIN author_article ON author_article.DOI = article.DOI " +
                "INNER JOIN author ON author.author_id = author_article.author_id " +
                "WHERE author_name = '" + authorName + "' AND YEAR(publication_date) = " + year +";");
       System.out.printf("%50s %85s","ARTICULOS","PUBLICATION_DATE\n");
       while (rs.next()) {
           System.out.printf("%-122s %-20s",rs.getString("title") ,rs.getDate("publication_date"));
           System.out.println();
       }
    }

    private static void listaAfiliaciones() throws SQLException {
        // @TODO Muestra por pantalla una lista de las instituciones y el numero de autores que
        //  tienen ordenados de más a menos autores (de mayor a menor numero de autores)
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT affiliation_name, COUNT(author_name) FROM affiliation " +
                "INNER JOIN author_affiliation ON author_affiliation.affiliation_id = affiliation.affiliation_id " +
                "INNER JOIN author ON author.author_id = author_affiliation.author_id " +
                "GROUP BY affiliation_name " +
                "ORDER BY COUNT(author_name) DESC;" );
        System.out.printf("%20s %20s", "AFFILIATION","NUM_AUTORES\n");
        while (rs.next()) {
            System.out.print(rs.getString("affiliation_name") + "\t" + rs.getInt("COUNT(author_name)"));
            System.out.println();
        }
    }
}


