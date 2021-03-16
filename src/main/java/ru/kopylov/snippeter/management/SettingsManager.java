package ru.kopylov.snippeter.management;

import ru.kopylov.snippeter.model.ViewerSettings;
import ru.kopylov.snippeter.utils.EmTAProxy;
import ru.kopylov.snippeter.utils.EntityManagerHolder;

import java.util.Optional;

public class SettingsManager {

    private EmTAProxy emProxy;

    public SettingsManager() {
        emProxy = EntityManagerHolder.getInstance().getEmTAProxy();
    }

    public ViewerSettings getDefaultSettings(){
        Optional<ViewerSettings> opSettings = emProxy.<ViewerSettings>getAllInstancesStream(ViewerSettings.class)
                .filter(setts -> setts.getName().equalsIgnoreCase("default"))
                .findAny();

        return opSettings.orElse(new ViewerSettings());
    }

    public void saveSettings(ViewerSettings viewerSettings){
        emProxy.persist(viewerSettings);
    }
}
