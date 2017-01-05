package com.romao.nhlspider.di;

import com.romao.nhlspider.BaseActivity;
import com.romao.nhlspider.datamanager.DataManager;
import com.romao.nhlspider.storage.LocalStorage;
import com.romao.nhlspider.util.ConnectionManager;
import com.romao.nhlspider.web.WebService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    WebService webService();

    LocalStorage localStorage();

    ConnectionManager connectionManager();

    DataManager dataManager();
}
