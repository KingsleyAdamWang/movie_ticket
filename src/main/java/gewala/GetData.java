package gewala;

import entity.Cinema;
import entity.Film;
import entity.Tickets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.CinemaUtil;
import util.DataUtil;
import util.FilmUtil;
import util.TimeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2017/6/10 0010.
 */
public class GetData {

    private static String url = "http://www.gewara.com/movie/v5/getCommonOpiItem.xhtml?fyrq=%s&movieid=%s&cid=%s";
    private static SessionFactory factory = DataUtil.getSessionFactory();
    private static Date time = new Date();
    public static void main(String[] args) throws IOException {
        Session session = factory.openSession();

        List<Cinema> cinemas = CinemaUtil.getAllCinemas();
        List<Film> films = FilmUtil.getAllFilms();
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        dates.add(calendar.getTime());
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DATE, 1);
            dates.add(calendar.getTime());
        }
        for (Cinema cinema : cinemas) {
            //事务
            Transaction transaction = session.beginTransaction();
            for (Film film : films) {
                for (Date date : dates) {
                    String realUrl = getRealUrl(TimeUtil.getDateStr(date), String.valueOf(film.getId()), String.valueOf(cinema.getId()));
                    //设置cookie的城市为南京
                    System.out.println(realUrl);
                    Document doc = Jsoup.connect(realUrl).cookie("citycode", "320100").get();
                    Elements elements = doc.select("div[data-type=\"playListBody\"] ul li");
                    for (Element element : elements) {
                        Tickets ticket = getTicket(element, cinema, film, TimeUtil.getDate(date));
                        if (ticket != null)
                            session.save(ticket);
                        System.err.println(ticket);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            //事务结束
            transaction.commit();
        }
        session.close();
        factory.close();
    }


    public static Tickets getTicket(Element element, Cinema cinema, Film film, entity.Date date) {
        try {
            Tickets ticket = null;
            String start_time = element.select("span.td.opiTime b.hd").get(0).text();
            String end_time = element.select("span.td.opiTime em.bd").get(0).text();
            double price = Double.parseDouble(element.select("span.td.opiPrice b").get(0).text());
            String discount_msg = "";
            Element discount_div = element.nextElementSibling();
            if (discount_div != null && discount_div.tagName().equals("div")) {
                Elements elements = discount_div.select("div.saleLists");
                for (Element discount_element : elements) {
                    discount_msg += discount_element.select("span.sales_text").get(0).text();
                    discount_msg += ";";
                }
            }

            int apptype = 1;
            String hallAddress = element.select("span.td.opiRoom b").get(0).text();
            String buy_url = "http://www.gewara.com" + element.select("span.td.opiurl a").get(0).attr("href");
            //Date time = new Date();
            ticket = new Tickets(cinema, film, date, start_time, end_time, price, discount_msg, apptype, hallAddress, buy_url, time);
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRealUrl(String date, String movieid, String cinemaid) {
        return String.format(url, date, movieid, cinemaid);
    }
}
