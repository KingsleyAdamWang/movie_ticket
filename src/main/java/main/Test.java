package main;

import util.DataUtil;

/**
 * Created by mengf on 2017/6/11 0011.
 */
public class Test {

    public static void main(String[] args){
        DataUtil.getSessionFactory().getCurrentSession().close();
    }

}
