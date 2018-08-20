package modules;

import com.google.inject.AbstractModule;

/**
 * @author Denis Danilin | denis@danilin.name
 * 13.09.2017 15:43
 * ${IJ_PROJECT_NAME} ☭ sweat and blood
 */
public class LinkingModule extends AbstractModule {
    @Override
    protected void configure() {

        // Подключение всех необходимых сервисов, DAO и job-ов по образу:

//        bind(RateService.class).to(RateServiceImpl.class);

//        bind(UserDao.class).to(UserDaoImpl.class);

//        bind(ExpiredUserInviteCleanerJob.class).asEagerSingleton();
    }
}
