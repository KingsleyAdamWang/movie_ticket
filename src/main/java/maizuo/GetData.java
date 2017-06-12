package maizuo;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import util.CinemaUtil;
import util.DataUtil;
import util.FilmUtil;
import util.TimeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.*;
import java.util.Date;

/**
 * Created by mengf on 2017/6/10 0010.
 */
public class GetData {
    private static Map<Integer, Integer> filmIdMap = new HashMap<Integer, Integer>();
    private static Map<Integer, Integer> cinemaIdMap = new HashMap<Integer, Integer>();

    private static String url = "https://www.maizuo.com/api/schedule?_t=%s&cinema=%s&film=%s";
    //https://www.maizuo.com/api/schedule?&cinema=4256&film=3706

    public static void main(String[] args){
        try {
            initialize();

            ArrayList<Integer> filmList = new ArrayList<Integer>();
            ArrayList<Integer> cinemaList = new ArrayList<Integer>();
            for (Map.Entry<Integer, Integer> entry : filmIdMap.entrySet()) {
                filmList.add(entry.getKey());
            }

            for (Map.Entry<Integer, Integer> entry : cinemaIdMap.entrySet()) {
                cinemaList.add(entry.getKey());
            }

            System.out.println("走到这里了");
            Cinema cinema;
            Film film;

            System.out.println("走到这里了");

            for (int filmId : filmList) {
                film = FilmUtil.getFilm(filmIdMap.get(filmId));
                for (int cinemaId : cinemaList) {
                    cinema = CinemaUtil.getCinema(cinemaIdMap.get(cinemaId));
                    String realUrl = String.format(url, String.valueOf(new Date().getTime()), String.valueOf(cinemaId), String.valueOf(filmId));
                    System.out.println(realUrl);

                    URL urlNet = new URL(realUrl);
                    URLConnection connection = urlNet.openConnection();
                    connection.setRequestProperty("User-Agent", "Chrome/58.0.3029.81");//设置请求header的属性--请求内容类型
                    //connection.setRequestProperty("method", "GET");//设置请求header的属性值--请求方式
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        JSONObject jsonObject = new JSONObject(line);
                        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("schedules");
                        if (jsonArray.length() != 0) {
                            for (Object object : jsonArray) {
                                JSONObject temp = (JSONObject) object;
                                Tickets ticket = getTicket(temp);
                                ticket.setCinema(cinema);
                                ticket.setFilm(film);
                                ticket.setBuy_url("https://www.maizuo.com/#/film/" + filmId);
                                //save
                                System.out.println(ticket);
                            }
                        }
                    }
                }
            }

        }catch (Exception e){

        }finally {
            //DataUtil.getSessionFactory().close();
        }


    }

    private static Tickets getTicket(JSONObject jsonObject) {

        Tickets ticket = new Tickets();
        Date get_Time = new Date();
        Date start = new Date(jsonObject.getLong("showAt"));
        Date end = new Date(jsonObject.getLong("endAt"));
        entity.Date date = TimeUtil.getDate(start);
        String start_time = TimeUtil.getHHmmss(start);
        String end_time = TimeUtil.getHHmmss(end);
        String hall = jsonObject.getJSONObject("hall").getString("name");
        Double price = jsonObject.getJSONObject("price").getDouble("maizuo");
        ticket.setStart_time(start_time);
        ticket.setEnd_time(end_time);
        ticket.setDate(date);
        ticket.setDiscount_msg("");
        ticket.setApp_type(3);
        ticket.setGet_time(get_Time);
        ticket.setHall_address(hall);
        ticket.setPrice(price);

        return ticket;
    }

    public static void initialize() throws IOException {
        filmMatchInit();
        cinemaMatchInit();
    }

    private static void filmMatchInit() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("filmIdMatch.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            filmIdMap.put(Integer.parseInt(line.split(",")[1]), Integer.parseInt(line.split(",")[0]));
        }
    }

    private static void cinemaMatchInit() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cinemasIdMatch.csv");
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            cinemaIdMap.put(Integer.parseInt(line.split(",")[1]), Integer.parseInt(line.split(",")[0]));
        }
    }
}
