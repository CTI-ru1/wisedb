package eu.wisebed.wisedb.controller;

import org.hibernate.SessionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: amaxilatis
 * Date: 2/22/12
 * Time: 1:46 PM
 */
public interface AbstractControllerInterface {

    public void setSessionFactory(final SessionFactory factory);

    public SessionFactory getSessionFactory();

}
