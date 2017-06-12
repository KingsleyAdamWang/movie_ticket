package nuomi;

import entity.Cinema;
import entity.Date;
import entity.Film;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/11 0011.
 */
public class Ticket {
    private String cinema;
    private String film;
    private String date;
    private String start_time;
    private String end_time;
    private String price;
    private String discount_msg;
    private int app_type;
    private String hall_address;
    private String buy_url;
    private java.util.Date get_time;

    public Ticket(String cinema, String film, String date, String start_time, String end_time, String price, String discount_msg, int app_type, String hall_address, String buy_url, java.util.Date get_time) {
        this.cinema = cinema;
        this.film = film;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.discount_msg = discount_msg;
        this.app_type = app_type;
        this.hall_address = hall_address;
        this.buy_url = buy_url;
        this.get_time = get_time;
    }

    public Ticket() {
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount_msg() {
        return discount_msg;
    }

    public void setDiscount_msg(String discount_msg) {
        this.discount_msg = discount_msg;
    }

    public int getApp_type() {
        return app_type;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public String getHall_address() {
        return hall_address;
    }

    public void setHall_address(String hall_address) {
        this.hall_address = hall_address;
    }

    public String getBuy_url() {
        return buy_url;
    }

    public void setBuy_url(String buy_url) {
        this.buy_url = buy_url;
    }

    public java.util.Date getGet_time() {
        return get_time;
    }

    public void setGet_time(java.util.Date get_time) {
        this.get_time = get_time;
    }

//        this.cinema = cinema;
//        this.film = film;
//        this.date = date;
//        this.start_time = start_time;
//        this.end_time = end_time;
//        this.price = price;
//        this.discount_msg = discount_msg;
//        this.app_type = app_type;
//        this.hall_address = hall_address;
//        this.buy_url = buy_url;
//        this.get_time = get_time;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.cinema).append(",")
                .append(this.film).append(",")
                .append(this.date).append(",")
                .append(this.start_time).append(",")
                .append(this.end_time).append(",")
                .append(this.price).append(",")
                .append(this.discount_msg).append(",")
                .append(this.app_type).append(",")
                .append(this.hall_address).append(",")
                .append(this.buy_url).append(",")
                .append(this.get_time);
        return builder.toString();
    }
}
