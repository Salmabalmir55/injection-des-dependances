package net.balmir.pres;

import net.balmir.dao.IDao;
import net.balmir.metier.IMetier;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class pres2 {

        public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException , NoSuchMethodException , InvocationTargetException {

            //lire le fichier 'config.txt'
            Scanner scanner = new Scanner(new File("config.txt"));
            //creer objet
            String daoClassName = scanner.nextLine();
            Class cDao = Class.forName(daoClassName); //INSTAN DYNAMIQUE
            IDao d = (IDao) cDao.newInstance();


            String MetierClassName = scanner.nextLine();
            Class cMetier = Class.forName(MetierClassName); //INSTAN DYNAMIQUE
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(d);
            //IMetier metier = (IMetier) cMetier.getConstructor().newInstance();
            //Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            //setDao.invoke(metier,d);

            System.out.println("Res = " + metier.calcul());
        }
}