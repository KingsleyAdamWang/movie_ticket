package init;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import entity.Cinema;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.DataUtil;

import java.io.IOException;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class initialCinema {

    private static final int PAGES = 9;
    private static String url = "http://www.gewara.com/movie/searchCinema.xhtml?pageNo=";

    public static void main(String[] args) throws IOException {
        SessionFactory factory = DataUtil.getSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();


        for (int i = 0; i < PAGES; i++) {
            //设置cookie的城市为南京
            Document doc = Jsoup.connect(url + i).cookie("citycode", "320100").get();

            Elements elements = doc.select("#cinemaListArea ul li.effectLi.mt10");
            System.out.println(elements.size());
            for (Element element : elements) {
                Cinema cinema = getCinema(element);
                session.save(cinema);
            }
        }
        transaction.commit();
        session.close();
        factory.close();
    }

    private static Cinema getCinema(Element element) {
        Cinema cinema = null;
        String url = element.select("div.ui_media div.ui_pic.cinema a img").get(0).attr("style");
        url = url.substring(url.indexOf("(")+1,url.indexOf(")"));
        String name = element.select("div.ui_media div.ui_text div.title h2 a").get(0).text();
        String idStr = element.select("div.ui_media div.ui_text div.title h2 a").get(0).attr("href");
        int id = Integer.parseInt(idStr.substring(idStr.lastIndexOf("/")+1,idStr.length()));
        String address = element.select("div.ui_media div.ui_text p.mt10").text();
        System.out.println("url:" + url);
        System.out.println("name:" + name);
        System.out.println("outHtml:" + address);
        System.out.println();

        String[] strs = address.split(" ");
        address = strs[1];
        String area = strs[0];

        cinema = new Cinema(id,name,area,address,url);
        return cinema;
    }
}
