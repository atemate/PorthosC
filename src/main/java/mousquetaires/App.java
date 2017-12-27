package mousquetaires;

import com.beust.jcommander.ParameterException;
import mousquetaires.app.modules.*;
import mousquetaires.app.options.AppOptions;


public class App {

    public static void main(String[] args) {
        AppOptions options;
        AppModule module = null;
        try {
            options = AppOptions.parse(args);
            if (options.help) {
                System.out.println(AppOptions.getUsageString());
                System.exit(0);
            }

            module = AppModuleFactory.newAppModule(options);
        }
        catch (ParameterException e) {
            // TODO: log
            System.out.println(e.getMessage() + "\n");
            System.exit(1);
        }

        IAppVerdictStringifier stringifier = new JsonVerdictStringifier();
        AppVerdict verdict = module.run();

        System.out.println(stringifier.stringify(verdict));
    }
}