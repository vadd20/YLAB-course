package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.movie;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws IOException, SQLException {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            readAndParse(br, movieArrayList);
        }
        String insertQuery = "INSERT INTO movie" +
                "(year, length, title, subject, actors, actress, director, popularity, awards) values " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Movie movie : movieArrayList) {

                preparedStatement.setInt(1, movie.getYear());

                if (movie.getLength() == null) {
                    preparedStatement.setNull(2, Types.INTEGER);
                } else {
                    preparedStatement.setInt(2, movie.getLength());
                }

                preparedStatement.setString(3, movie.getTitle());
                if (movie.getSubject() == null) {
                    preparedStatement.setNull(4, Types.VARCHAR);
                } else {
                    preparedStatement.setString(4, movie.getSubject());
                }

                if (movie.getActors() == null) {
                    preparedStatement.setNull(5, Types.VARCHAR);
                } else {
                    preparedStatement.setString(5, movie.getActors());
                }

                if (movie.getActress() == null) {
                    preparedStatement.setNull(6, Types.VARCHAR);
                } else {
                    preparedStatement.setString(6, movie.getActress());
                }

                if (movie.getDirector() == null) {
                    preparedStatement.setNull(7, Types.VARCHAR);
                } else {
                    preparedStatement.setString(7, movie.getDirector());
                }

                if (movie.getPopularity() == null) {
                    preparedStatement.setNull(8, Types.INTEGER);
                } else {
                    preparedStatement.setInt(8, movie.getPopularity());
                }

                preparedStatement.setBoolean(9, movie.getAwards());

                preparedStatement.executeUpdate();
            }
        }
    }

    public static void readAndParse(BufferedReader br, ArrayList<Movie> movieArrayList) throws IOException {
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            String[] data = currentLine.split(";");
            Movie currentMovie = new Movie();

            if (!isNumeric(data[0])) {
                continue;
            }

            if (data[0] == null) {
                currentMovie.setYear(null);
            } else {
                currentMovie.setYear(Integer.parseInt(data[0]));
            }

            if (Objects.equals(data[1], "")) {
                currentMovie.setLength(null);
            } else {
                currentMovie.setLength(Integer.parseInt(data[1]));
            }

            currentMovie.setTitle(data[2]);
            currentMovie.setSubject(data[3]);
            currentMovie.setActors(data[4]);
            currentMovie.setActress(data[5]);
            currentMovie.setDirector(data[6]);

            if (Objects.equals(data[7], "")) {
                currentMovie.setPopularity(null);
            } else {
                currentMovie.setPopularity(Integer.parseInt(data[7]));
            }

            if (Objects.equals(data[8], "")) {
                currentMovie.setAwards(null);
            } else {
                currentMovie.setAwards(Boolean.parseBoolean(data[8]));
            }

            movieArrayList.add(currentMovie);
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
