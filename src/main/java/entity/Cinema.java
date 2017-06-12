package entity;

import javax.persistence.*;

/**
 * Created by mengf on 2017/6/9 0009.
 */

@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    private int id ;

    private String name;

    private String area;

    private String address;

    private String picture_url;

    public Cinema() {
    }

    public Cinema(int id,String name,String area, String address,String picture_url) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.address = address;
        this.picture_url = picture_url;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", picture_url='" + picture_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cinema cinema = (Cinema) o;

        return id == cinema.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
