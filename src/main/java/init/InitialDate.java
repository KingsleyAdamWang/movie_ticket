package init;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class InitialDate {
    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,2);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
    }

    private int getWeekNumInChina(int num){
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
