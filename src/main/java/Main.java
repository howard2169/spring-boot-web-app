
import com.springproject.config.App;
import com.springproject.impl.web.SpringAppBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            new SpringAppBuilder(App.class).build()
                .run(args);
        } catch (Throwable e) {
            LOGGER.error("spring boot catch exception: ", e);
            System.exit(1);
        }
    }
}
