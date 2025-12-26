package info.developia.lib.design.pattern.singleton;

public class Configuration {
    private static volatile Configuration instance;

    private Configuration() {
        if (instance != null){
            throw new IllegalStateException("Instance already exists!");
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
    }
}
