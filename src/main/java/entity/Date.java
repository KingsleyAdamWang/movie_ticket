package entity;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/9 0009.
 */

@Entity
@Table(name = "date")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private int year;

    private int month;

    private int day;

    private int weekNum;

    public Date() {

    }

    public Date(int year, int month, int day, int weekNum) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.weekNum = weekNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    @Override
    public String toString() {
        return year+"-"+month+"-"+day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Date date = (Date) o;

        return id == date.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
