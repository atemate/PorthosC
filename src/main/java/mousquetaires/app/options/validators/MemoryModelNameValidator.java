package mousquetaires.app.options.validators;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;
import mousquetaires.memorymodels.wmm.MemoryModel;

import java.util.Arrays;


public class MemoryModelNameValidator implements IValueValidator<MemoryModel.Kind> {

    @Override
    public void validate(String name, MemoryModel.Kind value) throws ParameterException {
        if (value == null) {
            throw new ParameterException("Invalid memory model name. Available options: " +
                    Arrays.toString(MemoryModel.Kind.values()).toLowerCase());
        }
    }
}
