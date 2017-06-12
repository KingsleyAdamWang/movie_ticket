package shiguang;

import entity.Cinema;
import entity.Film;
import entity.Tickets;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mengf on 2017/6/10 0010.
 */
public class LoadData {

    private static Date time = new Date();
    public static void main(String[] args) throws ParseException, IOException {
        SessionFactory factory = DataUtil.getSessionFactory();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.txt");

        CinemaUtil.getAllCinemas();
        FilmUtil.getAllFilms();
        TimeUtil.getPeriodDates();
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

    //南京星河国际影城（江宁大学城店）,神奇女侠,
    // 2017-6-10,07:09,预计09:35散场,33.0,
    // 温州银行10元购票,
    // 2,6号厅,
    // http://theater.mtime.com/China_Jiangsu_Province_Nanjing_Jiangning/2602/,
    // Sat Jun 10 21:01:33 CST 2017

    public static Tickets getTickets(String info){
        Tickets ticket = null;

        String[] data = info.split(",");
        Cinema cinema = CinemaUtil.getCinema(CinemaUtil.getCinemaId(data[0]));
        //System.out.println(cinema);
        Film film = FilmUtil.getFilm(FilmUtil.getFilmId(data[1]));
        entity.Date date = TimeUtil.getDate(Integer.parseInt(data[2].split("-")[0]),
                Integer.parseInt(data[2].split("-")[1]),Integer.parseInt(data[2].split("-")[2]));
        //System.out.println(film);
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
