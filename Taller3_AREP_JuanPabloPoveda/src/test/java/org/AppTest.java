package org;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Test que compara la respuesta de la pagina con la que arroja la API
     */
    public void testAppAPI(){
        HashMap<String, String> answers = new HashMap<>();
        answers.put("Avengers: Endgame","{\"Title\":\"Avengers: Endgame\",\"Year\":\"2019\",\"Rated\":\"PG-13\",\"Released\":\"26 Apr 2019\",\"Runtime\":\"181 min\",\"Genre\":\"Action, Adventure, Drama\",\"Director\":\"Anthony Russo, Joe Russo\",\"Writer\":\"Christopher Markus, Stephen McFeely, Stan Lee\",\"Actors\":\"Robert Downey Jr., Chris Evans, Mark Ruffalo\",\"Plot\":\"After the devastating events of Avengers: Infinity War (2018), the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.\",\"Language\":\"English, Japanese, Xhosa, German\",\"Country\":\"United States\",\"Awards\":\"Nominated for 1 Oscar. 70 wins & 133 nominations total\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.4/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"94%\"},{\"Source\":\"Metacritic\",\"Value\":\"78/100\"}],\"Metascore\":\"78\",\"imdbRating\":\"8.4\",\"imdbVotes\":\"1,246,637\",\"imdbID\":\"tt4154796\",\"Type\":\"movie\",\"DVD\":\"30 Jul 2019\",\"BoxOffice\":\"$858,373,000\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}");
        answers.put("Guardians of the Galaxy","{\"Title\":\"Guardians of the Galaxy\",\"Year\":\"2014\",\"Rated\":\"PG-13\",\"Released\":\"01 Aug 2014\",\"Runtime\":\"121 min\",\"Genre\":\"Action, Adventure, Comedy\",\"Director\":\"James Gunn\",\"Writer\":\"James Gunn, Nicole Perlman, Dan Abnett\",\"Actors\":\"Chris Pratt, Vin Diesel, Bradley Cooper\",\"Plot\":\"A group of intergalactic criminals must pull together to stop a fanatical warrior with plans to purge the universe.\",\"Language\":\"English\",\"Country\":\"United States\",\"Awards\":\"Nominated for 2 Oscars. 52 wins & 103 nominations total\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDIzMTk4NDYtMjg5OS00ZGI0LWJhZDYtMzdmZGY1YWU5ZGNkXkEyXkFqcGdeQXVyMTI5NzUyMTIz._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.0/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"92%\"},{\"Source\":\"Metacritic\",\"Value\":\"76/100\"}],\"Metascore\":\"76\",\"imdbRating\":\"8.0\",\"imdbVotes\":\"1,261,888\",\"imdbID\":\"tt2015381\",\"Type\":\"movie\",\"DVD\":\"15 Nov 2015\",\"BoxOffice\":\"$333,718,600\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}");
        ArrayList<ThreadsTest> threadTests = new ArrayList<>();
        threadTests.add(new ThreadsTest("Avengers: Endgame"));
        threadTests.add(new ThreadsTest("Guardians of the Galaxy"));

        for(ThreadsTest threadTest: threadTests){
            threadTest.run();
        }
        for(ThreadsTest threadsTest: threadTests){
            try {
                threadsTest.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(ThreadsTest threadTest: threadTests){
            assertEquals(answers.get(threadTest.getTitle()), threadTest.getAnswer());
        }
    }

    /**
     * Compara el tamaño del cache al consultar varias peliculas y verifica que no se guarden duplicados en el cache
     */
    public void testAppAPICache(){
        Cache.peliculas.clear();
        ArrayList<ThreadsTest> threadTests = new ArrayList<>();
        threadTests.add(new ThreadsTest("Avengers: Endgame"));
        threadTests.add(new ThreadsTest("Avengers: Endgame"));
        threadTests.add(new ThreadsTest("Guardians of the Galaxy"));
        threadTests.add(new ThreadsTest("Avengers: Endgame"));
        threadTests.add(new ThreadsTest("Guardians of the Galaxy"));

        for(ThreadsTest threadTest: threadTests){
            threadTest.run();
        }
        for(ThreadsTest threadsTest: threadTests){
            try {
                threadsTest.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        assertEquals(2,Cache.peliculas.size());

    }
}