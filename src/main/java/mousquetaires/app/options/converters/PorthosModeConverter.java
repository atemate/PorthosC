package mousquetaires.app.options.converters;

import com.beust.jcommander.IStringConverter;
import mousquetaires.app.modules.porthos.PorthosMode;


public class PorthosModeConverter implements IStringConverter<PorthosMode> {

    @Override
    public PorthosMode convert(String value) {
        return PorthosMode.parse(value);
    }
}
