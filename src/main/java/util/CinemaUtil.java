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
public class CinemaUtil {
    private static final String ENCODING = "UTF-8";
    private static HashMap<Integer, String> cinemaMap = new HashMap<Integer, String>();
    private static HashMap<Integer,Cinema> cinemaHashMap = new HashMap<Integer, Cinema>();
    private static Session session = DataUtil.getSessionFactory().openSession();


    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void init() throws IOException {
        initCinema();
    }


    private static void initCinema() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cinemas.csv");
        Reader reader = new InputStreamReader(inputStream, ENCODING);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line = null;
        line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            int pos = line.replace(" ", "").indexOf(",");
            int id = Integer.parseInt(line.substring(0, pos));
            String name = line.substring(pos + 1);
            cinemaMap.put(id, name);
        }

    }
    public static Cinema getCinema(String name){
        int id = getCinemaId(name);
        return getCinema(id);
    }

    public static Cinema getCinema(int id){
        Cinema cinema = null;
        if (cinemaHashMap.get(id)!=null){
            return cinemaHashMap.get(id);
        }
        Criteria criteria = session.createCriteria(Cinema.class);
        criteria.add(Restrictions.eq("id",id));
        List<Cinema> cinemas = criteria.list();
        if (cinemas.size()>0){
            cinema=cinemas.get(0);
        }

        cinemaHashMap.put(id,cinema);
        return cinema;
    }

    public static int getCinemaId(String name) {
        int result = -1;
        name = name.replace(" ", "");
        for (Map.Entry<Integer, String> entry : cinemaMap.entrySet()) {
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

    public static List<Cinema> getAllCinemas(){
        Criteria criteria = session.createCriteria(Cinema.class);
        List<Cinema> cinemas = criteria.list();
        for (Cinema cinema:cinemas){
            cinemaHashMap.put(cinema.getId(),cinema);
        }
        return cinemas;
    }

    public static List<Integer> getAllCinemaId(){
        List<Cinema> cinemas = getAllCinemas();
        List<Integer> result = new ArrayList<Integer>();
        for (Cinema cinema:cinemas){
            result.add(cinema.getId());
        }
        return result;
    }

    public static void main(String[] args){
        cinemaMap.size();
        int id = getCinemaId("南京保利扬子电影院线");
        Cinema cinema = getCinema("南京保利扬子电影院线");
        System.out.println(id);
        System.out.println(cinema);
    }
}
