package nuomi;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.cyberneko.html.HTMLElements;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import util.TimeUtil;

import java.io.*;
import java.util.*;

/**
 * Created by mengf on 2017/6/11 0011.
 */
public class GetCinema {

    private static  final WebClient webClient=new WebClient(BrowserVersion.CHROME);
    private static Map<Integer, String> cinemaMap = new HashMap<Integer, String>();
    private static Map<Integer, String> filmMap = new HashMap<Integer, String>();
    private static String buy_url_format = "https://dianying.nuomi.com/buy/seat?movieId=%s&cinemaId=%s&seqNo=%s&date=%s";
    public static void main(String[] args) throws IOException {
        List<Integer> cinemaId = new ArrayList<Integer>();
        List<String> name = new ArrayList<String>();


        WebDriver driver = new ChromeDriver();

        driver.get("https://dianying.nuomi.com/movie/movielist");
        WebElement next = driver.findElement(By.cssSelector("a.mod-page-item.mod-page-item-next"));
        putFilmMap(driver);
        while (next.isDisplayed()){
            next.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {

                next = driver.findElement(By.cssSelector("a.mod-page-item.mod-page-item-next"));
            }catch (Exception e){
                e.printStackTrace();
                putFilmMap(driver);
                break;
            }
            putFilmMap(driver);
        }
        driver.quit();


        driver = new ChromeDriver();

        driver.get("https://dianying.nuomi.com/cinema");

        WebElement more = driver.findElement(By.id("moreCinema"));
        while (more.isDisplayed()) {
            more.click();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            more = driver.findElement(By.id("moreCinema"));
            //System.out.print(more.getTagName());
        }

        List<WebElement> elements = driver.findElements(By.cssSelector("#pageletCinemalist li"));
        for (WebElement element : elements) {
            //System.out.print();
            name.add(element.findElement(By.cssSelector("p.title span")).getText());
            JSONObject jsonObject = new JSONObject(element.findElement(By.cssSelector("p.title span")).getAttribute("data-data"));
            int id = jsonObject.getInt("cinemaId");
            cinemaId.add(id);
            cinemaMap.put(id,element.findElement(By.cssSelector("p.title span")).getText());
        }
        driver.close();


        printCinemaMap(new File("cinemaFromNumi.csv"));
        printFilmMap(new File("filmFromNumi.csv"));

        int index = 0;
        List<Ticket> result = new ArrayList<Ticket>();
        for (int i = 0 ; i < cinemaId.size();i++){
            if (cinemaId.get(i)==9023){
                index = i ;
            }
        }
        for ( ; index<cinemaId.size() ;index++) {
            int id = cinemaId.get(index);
            System.out.println("movidId="+id);
            List<Ticket> tickets = getTicketInfos(id,driver);

            printTickets(new File("dataFromNumi.txt"),tickets);
            result.addAll(tickets);
        }


        System.out.print(result.size());



    }

    private static void printTickets(File file,List<Ticket> tickets) throws IOException {
        FileWriter writer = new FileWriter(file,true);

        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (Ticket ticket:tickets){
            bufferedWriter.write(ticket.toString());
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    private static void printCinemaMap(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for (Map.Entry<Integer,String> entry : cinemaMap.entrySet()){
            writer.write(entry.getKey()+","+entry.getValue()+"\n");
        }
        writer.close();
    }

    private static void printFilmMap(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        for (Map.Entry<Integer,String> entry : filmMap.entrySet()){
            writer.write(entry.getKey()+","+entry.getValue()+"\n");
        }
        writer.close();
    }

    private static void putFilmMap(WebDriver driver){
        List<WebElement> movies = driver.findElements(By.cssSelector("p.movie-name"));
        for (WebElement element: movies){
            String name = element.getText().trim();
            if(name.contains(" "))
                name=name.split(" ")[0];
            int id = new JSONObject(element.getAttribute("data-data")).getInt("movieId");
            filmMap.put(id,name);
        }
    }
    public static List<Ticket> getTicketInfos( int cinemaId,WebDriver driver) throws IOException {
        List<Ticket> result = new ArrayList<Ticket>();
        String url = "https://dianying.nuomi.com/cinema/cinemadetail?cinemaId=" + cinemaId;
        driver = new ChromeDriver();
        driver.get(url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // List<WebElement> elements = driver.findElements(By.cssSelector("#datelist > div"));
        List<WebElement> elements = driver.findElements(By.cssSelector("#flexslider ul.slides li"));

//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setJavaScriptEnabled(false);
//        HtmlPage page=webClient.getPage(url);
//        List<DomNode> elements = page.querySelectorAll("#datelist > div");
//        for (DomNode element : elements){
//            result.addAll(getTickets(element,cinemaId));
//        }
//        webClient.closeAllWindows();
//        Document doc = Jsoup.connect(url).cookie("areaCode", "700010000").get();
//        System.out.println("走到这里了");
//        System.out.print(doc.html());
//        Elements elements = doc.select("#datelist > div");
        for (WebElement element : elements) {
            String movieId = element.getAttribute("data-movieid");
            System.out.print(movieId);
            if (movieId==null||movieId==""||element.getAttribute("class").contains("empty")){
                break;
            }
            while (!element.isDisplayed()){
                driver.findElement(By.cssSelector("ul.flex-direction-nav li.flex-nav-next")).click();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            element.click();
            result.addAll(getTickets(driver.findElement(By.cssSelector("#datelist > div[data-movieid=\""+movieId+"\"]")),cinemaId,driver));
        }

        driver.close();
        return result;
    }

    private static List<Ticket> getTickets(WebElement element,int cinemaId,WebDriver driver) {
        System.out.println("I am here!!");
        List<Ticket> tickets = new ArrayList<Ticket>();
        int movieId = Integer.parseInt(element.getAttribute("data-movieid"));
        String movieName = filmMap.get(movieId);
        String cinemaName = cinemaMap.get(cinemaId);
        String date;
        String start_time;
        String hall;
        String end_time;
        String price;
        String discount_msg;
        String buy_url;


        List<WebElement> datelist = driver.findElements(By.cssSelector("div#datelist div.date.active div.datelist span"));

        for (WebElement dateElement : datelist){
            System.out.println("I am here 22");
            try {
                dateElement.click();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String day = dateElement.getAttribute("data-id");
            Date time = new Date(Long.parseLong(day));

            date = TimeUtil.getDateStr(time);
            for (WebElement timeElement : element.findElements(By.cssSelector("div[data-id=\""+day+"\"][class~=\"active\"] ul > li"))){
                try {
                    System.out.println("我进来了？");
                    start_time = timeElement.findElements(By.cssSelector("div.time p.start")).get(0).getText();
                    end_time = timeElement.findElements(By.cssSelector("div.time p.end")).get(0).getText();
                    hall = timeElement.findElements(By.cssSelector("div.hall")).get(0).getText();
                    price = (timeElement.findElements(By.cssSelector("div.price span.num")).get(0).getText());
                    discount_msg = "";
                    String buy_info = timeElement.findElements(By.cssSelector("a[data-url=\"/buy/seat\"]")).get(0).getAttribute("data-data");
                    JSONObject jsonObject = new JSONObject(buy_info);
                    buy_url = String.format(buy_url_format, String.valueOf(movieId), String.valueOf(cinemaId),
                            jsonObject.getString("seqNo"), String.valueOf(jsonObject.getLong("date")));
                    Ticket ticket = new Ticket(cinemaName, movieName, date, start_time, end_time, price, discount_msg, 3, hall, buy_url, new Date());
                    System.err.println(ticket);
                    tickets.add(ticket);
                }catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
        }

        System.out.print(tickets.size());
        return tickets;
    }
}
