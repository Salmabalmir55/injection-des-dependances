package net.balmir.metier;
import net.balmir.dao.IDao ;


public class MetierImpl implements IMetier {
    private IDao dao;    //couplage faible

    /* BONNE PRATIQUE : INJECTION VIA CONSTRUCTEUR
     *Pour injecter dans l'attribut dao
     *un objet d'une classe qui implemente l interface IDAO
     *au moment de l'instantiation (au moment de creation de l'objet)
     */
    public MetierImpl() {
    }

    public MetierImpl (IDao dao) {
        this.dao = dao;
    }
    /*Pour injecter dans l'attribut dao
     *un objet d'une classe qui implemente l interface IDAO
     *après  l'instantiation (au moment de creation de l'objet)
     * Mauvaise pratique : INJECTION VIA Setter
     */

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 12 * Math.PI / 2 * Math.cos(t);
        return res;
    }

    /* MAUVAISE PRATIQUE: INJECTION VIA SETTER
     *Pour injecter dans l'attribut dao
     *un objet d'une classe qui implemente l interface IDO
     * apres instanciation (apres creation de l'objet)
     */
    public void setDao(IDao dao) {

        this.dao = dao;
    }

}