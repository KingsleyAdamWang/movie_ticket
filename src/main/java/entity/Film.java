package entity;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/9 0009.
 */

@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private String name;

    private String director;

    private String actor;

    /**
     * 电影时长
     */
    private String munites;

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

    public Film() {
    }

    public Film(String name, String director, String actor, String munites, String content, String open_date, String picture) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.munites = munites;
        this.content = content;
        this.open_date = open_date;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMunites() {
        return munites;
    }

    public void setMunites(String munites) {
        this.munites = munites;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }
}
