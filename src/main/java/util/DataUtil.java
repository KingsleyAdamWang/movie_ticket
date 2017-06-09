package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by mengf on 2017/6/9 0009.
 */
public class DataUtil {
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(
            Thread.currentThread().getContextClassLoader().getResource("hibernate.cfg.xml")
    ).build();
    private static SessionFactory sessionFactory;

    /**
     * 线程安全的懒汉单例模式
     * @return
     */
    public static SessionFactory  getSessionFactory() {
        //1. 配置类型安全的准服务注册类，这是当前应用的单例对象，不作修改，所以声明为final
        //在configure("cfg/hibernate.cfg.xml")方法中，如果不指定资源路径，默认在类路径下寻找名为hibernate.cfg.xml的文件
        //2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        if (sessionFactory==null){
            synchronized(DataUtil.class){
                if (sessionFactory==null){
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}
