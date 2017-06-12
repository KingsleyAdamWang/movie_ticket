package util;

import entity.Cinema;
import entity.Film;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mengf on 2017/6/10 0010.
 */
public class FilmUtil {
    private static final String ENCODING = "UTF-8";
    private static HashMap<Integer, String> filmMap = new HashMap<Integer, String>();
    private static HashMap<Integer, Film> filmHashMap = new HashMap<Integer, Film>();
    private static Session session = DataUtil.getSessionFactory().openSession();

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void init() throws IOException {
        initFilm();
    }

    private static void initFilm() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("films.csv");
        Reader reader = new InputStreamReader(inputStream, ENCODING);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            int pos = line.replace(" ", "").indexOf(",");
            int id = Integer.parseInt(line.substring(0, pos));
            String name = line.substring(pos + 1);
            filmMap.put(id, name);
        }
    }


    public static Film getFilm(String name){
        int id = getFilmId(name);
        return getFilm(id);
    }

    public static Film getFilm(int id){
        Film film = null;
        if (filmHashMap.get(id)!=null){
            return filmHashMap.get(id);
        }
        Criteria criteria = session.createCriteria(Film.class);
        criteria.add(Restrictions.eq("id",id));
        List<Film> films = criteria.list();
        if (films.size()>0){
            film = films.get(0);
        }
        filmHashMap.put(id,film);
        return film;
    }
    public static int getFilmId(String name) {
        int result = -1;
        name = name.replace(" ", "");
        for (Map.Entry<Integer, String> entry : filmMap.entrySet()) {
            String nameValue = entry.getValue();
            String strs[] = nameValue.split(",");
            for (String string : strs) {
                if (name.equals(string)) {
                    result = entry.getKey();
                    break;
                }
            }
            if (result != -1) {
                break;
            }
        }
        return result;
    }


    public static List<Film> getAllFilms(){
        Criteria criteria = session.createCriteria(Film.class);
        List<Film> films = criteria.list();
        for (Film film: films){
            filmHashMap.put(film.getId(),film);
        }
        return films;
    }

    public static List<Integer> getAllFilmId(){
        List<Film> films = getAllFilms();
        List<Integer> result = new ArrayList<Integer>();
        for (Film film:films){
            result.add(film.getId());
        }
        return result;
    }


    public static void main(String[] args){
        int id = getFilmId("神奇女侠");
        Film film = getFilm("神奇女侠");
        System.out.println(filmMap.size());

        System.out.println(id);
        System.out.println(film);
    }
}
