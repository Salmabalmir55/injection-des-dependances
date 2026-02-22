package net.balmir.pres;

import net.balmir.dao.DaoImpl;
import net.balmir.metier.MetierImpl;
import net.balmir.net.balmir.ext.DaoImplv2;

public class pres1 {
    public static void main(String[] args) {
        DaoImplv2 d = new DaoImplv2() ;
        MetierImpl metier = new MetierImpl(d);
       // metier.setDao(d);//injection des dépendances via le setter
        System.out.println("Res = " + metier.calcul());
    }
}
