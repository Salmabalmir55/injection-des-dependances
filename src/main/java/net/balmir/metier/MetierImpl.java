package net.balmir.metier;
import net.balmir.dao.IDao ;
public class MetierImpl implements IMetier {
    private IDao dao  ; //couplage faible

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implemente l'interface IDO
     * au moment de l'instantiation
     */

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 12 * Math.PI/2 * Math.cos(t);
        return res;
    }

    /**
     *
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implemente l'interface IDO
     * après instantiation
     */
    public void setDao (IDao dao){
        this.dao = dao ;
    }
}
