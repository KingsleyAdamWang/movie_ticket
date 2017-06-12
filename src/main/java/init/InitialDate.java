package init;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DataUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class InitialDate {

    private static final int NUMS = 1000;

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);

        Session session = DataUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        //transaction.begin();
        for (int i = 0 ; i < NUMS ; i++){
            calendar.add(Calendar.DATE,1);
            entity.Date date = new entity.Date(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1,
                    calendar.get(Calendar.DATE),
                    getWeekNumInChina(calendar.get(Calendar.DAY_OF_WEEK)));
            session.save(date);
        }
        transaction.commit();
        session.close();
        DataUtil.getSessionFactory().close();
    }

    private static int getWeekNumInChina(int num){
        switch (num){
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
}
