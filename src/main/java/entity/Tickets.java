package entity;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/9 0009.
 */
@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cinema")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "film")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "date")
    private Date date;

    private String start_time;

    private String end_time;

    private double price;

    private String discount_msg;

    private int app_type;

    private String hall_address;

    private String buy_url;

    private java.util.Date get_time;


    public Tickets() {
    }

    public Tickets(Cinema cinema, Film film, Date date, String start_time, String end_time, double price, String discount_msg, int app_type, String hall_address, String buy_url, java.util.Date get_time) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    @Override
    public String toString() {
        return "Tickets{" +
                "cinema=" + cinema.getName() +
                ", film=" + film.getName() +
                ", date=" + date.toString() +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", price=" + price +
                ", discount_msg='" + discount_msg + '\'' +
                ", app_type=" + app_type +
                ", hall_address='" + hall_address + '\'' +
                ", buy_url='" + buy_url + '\'' +
                ", get_time=" + get_time +
                '}';
    }


}
