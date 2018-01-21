package mousquetaires.utils.exceptions.xrepr;


public class MemoryUnitDoubleDeclarationException extends XCompilationException {

    public MemoryUnitDoubleDeclarationException(String memoryUnitName, boolean isLocal) {
        super("Duplicating declaration of " + (isLocal ? "local" : "shared") + " memoryevents unit: " + memoryUnitName);
    }
}