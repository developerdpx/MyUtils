package com.v.myutils;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by Developer-X on 2016/4/19.
 */
public class MyApplication extends Application {

    private DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化XUtils
        x.Ext.init(this);
        daoConfig = new DbManager.DaoConfig()
                .setDbName("My_db")    //创建数据库的名称
                .setDbVersion(1)       //创建数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    /*      db.dropTable(...);
                            db.addColumn(...);*/
                    }
                });                     //数据库更新操作
        DbManager db = x.getDb(daoConfig);
        Myperson myperson = new Myperson();
        myperson.setName("daipiaoxiang");
        myperson.setAge(22);
        Myperson myperson1 = new Myperson();
        myperson1.setName("xutilsdeome");
        myperson1.setAge(56);
        try {
            db.save(myperson);
            db.save(myperson1);
        } catch (DbException e) {
            e.printStackTrace();
        }


//        DbManager db = x.getDb(daoConfig);
//        LYJPerson person1=new LYJPerson();
//        person1.setName("liyuanjinglyj");
//        person1.setAge("23");
//        LYJPerson person2=new LYJPerson();
//        person2.setName("xutilsdemo");
//        person2.setAge("56");
//        try {
//            db.save(person1);
//            db.save(person2);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
    }

}
