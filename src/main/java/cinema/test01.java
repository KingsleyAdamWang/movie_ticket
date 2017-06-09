package cinema;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hibernate.SessionFactory;
import util.DataUtil;

import java.io.IOException;

/**
 * Created by mengf on 2017/6/8 0008.
 */
public class test01 {
    public static void main(String[] args) throws IOException {
//        final WebClient webClient=new WebClient();
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        final HtmlPage page=webClient.getPage("https://dianying.taobao.com/");
//        System.out.println(page.asXml());
        SessionFactory factory = DataUtil.getSessionFactory();

    }
}
