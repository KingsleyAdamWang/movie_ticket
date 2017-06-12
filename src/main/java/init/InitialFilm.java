package init;

import entity.Cinema;
import entity.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.DataUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class InitialFilm {

    private static final int PAGES = 5;
    private static String url = "http://www.gewara.com";
    private static String url2 = "/movie/searchMovie.xhtml?pageNo=";
    private static File  file = new File("gewalaMovieUrl.txt");

    public static void main(String[] args) throws IOException {
        SessionFactory factory = DataUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();


        for (int i = 0; i < PAGES; i++) {
            //设置cookie的城市为南京
            Document doc = Jsoup.connect(url +url2+ i).cookie("citycode", "320100").get();

            Elements elements = doc.select("div.movieList ul li.effectLi");
            System.out.println(elements.size());
            //这个if的锅我不背 因为电影有重复的

            for (Element element : elements) {
                Film film = getFilm(element);
                session.saveOrUpdate(film);
            }
        }
        transaction.commit();
        session.close();
        factory.close();
    }

    private static Film getFilm(Element element) throws IOException {
        Film film = null;

        String name = element.select("div.ui_media div.ui_text div.title h2 a").get(0).text();
        String urlMovie = element.select("div.ui_media div.ui_text div.title h2 a").get(0).attr("href");
        int id = Integer.parseInt(urlMovie.substring(urlMovie.lastIndexOf("/")+1,urlMovie.length()));
        Elements elements= element.select("div.ui_media div.ui_text > p.ui_summary");
        String summary = elements.size()==0 ? "" : elements.get(0).text();
        String type = element.select("div.ui_media div.ui_text > p:not(.ui_summary)").get(0).text().split("：")[1];
        String language = element.select("div.ui_media div.ui_text > p:not(.ui_summary)").get(1).text().split("：")[1];
        String hours = element.select("div.ui_media div.ui_text > p:not(.ui_summary)").get(2).text().split("：")[1];
        String director = element.select("div.ui_media div.ui_text > p:not(.ui_summary)").get(3).text().split("：")[1];
        String[] actors = element.select("div.ui_media div.ui_text > p:not(.ui_summary)").get(4).text().split("：");
        String actor = actors.length>1 ? actors[1] : "";

        saveFileUrl(file,url +urlMovie,name);
        //设置cookie的城市为南京
        Document doc = Jsoup.connect(url +urlMovie).cookie("citycode", "320100").get();

        String open_date = doc.select("div#ui_movieInfo_open ul > li").get(0).text().split("：")[1];
        String country = doc.select("div#ui_movieInfo_open ul > li").get(2).text().split("：")[1];
        String content = doc.select("div#ui_movieInfoBox").get(0).text().replace("剧情：","").replace("收起>q                                                                                                ","").trim();
        System.out.println(content);
        String picture = doc.select("div.detail_head_info div.ui_media div.ui_pic img").get(0).attr("src");

        film = new Film(id,name,director,actor,type,language, hours, summary, content, open_date,picture, country);
        //System.out.println(film);
        return film;
    }


    private static void saveFileUrl(File file,String url,String name) throws IOException {
        //确认是添加
        FileWriter writer = new FileWriter(file,true);
        writer.append(name+" "+url);
        writer.append("\n");
        writer.close();
    }
}
