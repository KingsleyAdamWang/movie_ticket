package entity;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/9 0009.
 */

@Entity
@Table(name = "film")
public class Film {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    private String name;

    private String director;

    private String actor;

    /**
     * 电影类型
     */
    private String type;

    /**
     * 电影语言
     */
    private String language;

    /**
     * 电影时长
     */
    private String hours;

    /**
     * 电影简介 summary
     */
    private String summary;

    /**
     * 电影详情
     */
    private String content;

    /**
     * 上映时间
     */
    private String open_date;

    /**
     * picture 电影的图片/海报/简图地址
     */
    private String picture;

    private String country;

    public Film() {
    }

    public Film(int id, String name, String director, String actor, String type, String language, String hours, String summary, String content, String open_date, String picture, String country) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.type = type;
        this.language = language;
        this.hours = hours;
        this.summary = summary;
        this.content = content;
        this.open_date = open_date;
        this.picture = picture;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", type='" + type + '\'' +
                ", language='" + language + '\'' +
                ", hours='" + hours + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", open_date='" + open_date + '\'' +
                ", picture='" + picture + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return id == film.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
