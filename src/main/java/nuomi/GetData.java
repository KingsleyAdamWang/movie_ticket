package nuomi;

import entity.Cinema;
import entity.Film;
import entity.Tickets;
import org.apache.http.HttpConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.CinemaUtil;
import util.DataUtil;
import util.FilmUtil;
import util.TimeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by mengf on 2017/6/11 0011.
 */
public class GetData {



    private static Date time = new Date();
    public static void main(String[] args) throws ParseException, IOException {
        SessionFactory factory = DataUtil.getSessionFactory();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("dataFromNumi.txt");
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        FilmUtil.init();
        CinemaUtil.init();
        int count = 1;
        int count2 = 1;
        while ((line = reader.readLine())!= null){
            Tickets tickets = getTickets(line);
            session.save(tickets);
            System.out.println("第"+count+"行");
            count++;
            if (count%300==0){
                transaction.commit();
                transaction = session.beginTransaction();
                System.out.println("提交了"+count+"次");
            }
        }

        transaction.commit();
        session.close();
        factory.close();

    }
    public static Tickets getTickets(String info){
        Tickets ticket = null;

        String[] data = info.split(",");
        Cinema cinema = CinemaUtil.getCinema(CinemaUtil.getCinemaId(data[0]));
        Film film = FilmUtil.getFilm(FilmUtil.getFilmId(data[1]));
        entity.Date date = TimeUtil.getDate(Integer.parseInt(data[2].split("-")[0]),
                Integer.parseInt(data[2].split("-")[1]),Integer.parseInt(data[2].split("-")[2]));

        String start_time = data[3];
        String end_time = data[4];
        Double price = Double.parseDouble(data[5]);
        String dicount_msg = data[6];
        int app_type = Integer.parseInt(data[7]);
        String hall_address = data[8];
        String buy_url = data[9];
        ticket = new Tickets(cinema,film,date,start_time,end_time,price,dicount_msg,app_type,hall_address,buy_url,time);

        return ticket;
    }

}
