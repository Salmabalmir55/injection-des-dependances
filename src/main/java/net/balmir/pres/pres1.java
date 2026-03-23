package net.balmir.pres;

import net.balmir.dao.DaoImpl;

import net.balmir.ext.DaoImplV2;
import net.balmir.metier.MetierImpl;


public class pres1 {
        public static void main(String[] args){
            //en utilisant classe DaoImpl : Version base de données
            //DaoImpl d = new DaoImpl();
            //en utilisant classe DaoImplV2 : Version capteurs
            DaoImplV2 d = new DaoImplV2();
            MetierImpl metier = new MetierImpl(d);
            //metier.setDao(d); //injection des dependances via le setter
            System.out.println("RES = "+metier.calcul());
        }
    }

