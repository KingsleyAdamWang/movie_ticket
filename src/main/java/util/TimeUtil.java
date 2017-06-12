package util;

import entity.Cinema;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mengf on 2017/6/10 0010.
 */
public class TimeUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");

    private static HashMap<Integer, entity.Date> timeMap = new HashMap<Integer, entity.Date>();

    private static Session session = DataUtil.getSessionFactory().openSession();


    public static Date getDate(String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
    public static entity.Date getDate(int year, int month, int day) {
        entity.Date date = null;
        int temp = year*10000+month*100+day;
        if (timeMap.get(temp)!=null){
            return timeMap.get(temp);
        }
        Criteria criteria = session.createCriteria(entity.Date.class);
        criteria.add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("day", day));

        List<entity.Date> dates = criteria.list();
        if (dates.size() > 0) {
            date = dates.get(0);
            temp = date.getYear()*10000+date.getMonth()*100+date.getDay();
            timeMap.put(temp,date);
        }

        return date;
    }

    public static int getDateId(int year, int month, int day) {
        int result = -1;
        Criteria criteria = session.createCriteria(entity.Date.class);
        criteria.add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("day", day));
        List<entity.Date> dates = criteria.list();
        if (dates.size() > 0) {
            result = dates.get(0).getId();
        }

        return result;
    }

    public static entity.Date getDate(int id) {
        entity.Date date = null;

        Criteria criteria = session.createCriteria(entity.Date.class);
        criteria.add(Restrictions.eq("id", id));
        List<entity.Date> dates = criteria.list();

        if (dates.size() > 0) {
            date = dates.get(0);
            int temp = date.getYear()*10000+date.getMonth()*100+date.getDay();
            timeMap.put(temp,date);
        }

        return date;
    }

    public static String getDateStr(Date date) {
        return dateFormat.format(date);
    }

    public static List<entity.Date> getPeriodDates(){
        Criteria criteria = session.createCriteria(entity.Date.class);
        criteria.add(Restrictions.eq("year",2017))
                .add(Restrictions.between("month",1,12));

        List<entity.Date> dates = criteria.list();
        for (entity.Date date:dates){
            timeMap.put(date.getId(),date);
        }
        return dates;
    }

    public static entity.Date getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return getDate(year,month,day);
    }



    public static String getHHmmss(Date date){
        return  dateFormat2.format(date);
    }
    private static int getWeekNumInChina(int num) {
        switch (num) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    public static void main(String[] args){

        Date date = new Date();
        System.out.println("Date-Data"+getDate(date));
    }
}
