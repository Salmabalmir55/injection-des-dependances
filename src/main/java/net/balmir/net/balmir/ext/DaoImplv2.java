package net.balmir.net.balmir.ext;
import net.balmir.dao.IDao ;

public class DaoImplv2 implements IDao {

    @Override
    public double getData() {
        System.out.println("Version Capteurs ....") ;
        double t = 12 ;
        return t;
    }
}
