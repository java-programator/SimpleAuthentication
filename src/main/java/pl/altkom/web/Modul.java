package pl.altkom.web;

import com.google.inject.AbstractModule;
import pl.altkom.web.dao.UserDao;
import pl.altkom.web.dao.UserDaoImpl;
import pl.altkom.web.service.SecurityManager;
import pl.altkom.web.service.SecurityManagerImpl;

public class Modul extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(SecurityManager.class).to(SecurityManagerImpl.class);
    }
}
