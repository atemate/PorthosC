package mousquetaires.languages.syntax.xgraph.memories;

import mousquetaires.languages.ProgramLanguage;
import mousquetaires.languages.syntax.xgraph.datamodels.DataModel;
import mousquetaires.utils.exceptions.xgraph.UndeclaredMemoryUnitError;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for virtual memoryevents adresation (by variable names) and allocation.
 */
public final class XMemoryManager {

    private final Map<String, XRegister> localLocations;
    private final Map<String, XLocation> sharedLocations;

    public XMemoryManager(ProgramLanguage language, DataModel dataModel) {
        // todo: use parameters for better memoryevents allocation
        this.localLocations = new HashMap<>();
        this.sharedLocations = new HashMap<>();
    }


    //public XConstant getConstantValue(Object value, XType type) {
    //    return new XConstant(value, type);
    //}

    public XRegister newLocalMemoryUnit() {
        String name = newTempLocalName();
        while (localLocations.containsKey(name)) {
            name = newTempLocalName();
        }
        //return declareLocalMemoryUnit(name, null);
        return new XRegister(name, XMemoryUnitBase.Bitness.bit16);// TODO: process bitness!!
    }

    // TODO: process bitness
    public XConstant getConstant(Object value) {//, XMemoryUnit.Bitness bitness) {
        return new XConstant(value, XMemoryUnitBase.Bitness.bit16); //bitness);
    }

    public XRegister getLocalMemoryUnit(String name) {
        XRegister result = localLocations.get(name);
        if (result == null) {
            // todo: probably, do not need to declare local memoryevents (registers)
            //throw new UndeclaredMemoryUnitError(name);
            return newLocalMemoryUnit(name);//todo: type
        }
        return result;
    }

    public XLocation getSharedMemoryUnit(String name) {
        XLocation result = sharedLocations.get(name);
        if (result == null) {
            // todo: probably processName undeclared shared memoryevents units as well
            throw new UndeclaredMemoryUnitError(name);
        }
        return result;
    }

    //// TO-DO: do we really need to declare local memory units? or we have infinitely many registers in our model?
    //public XRegister declareLocalMemoryUnit(String name, XType type) {
    //    if (isLocalMemoryDeclared(name)) {
    //        throw new MemoryUnitDoubleDeclarationError(name, true);
    //    }
    //    return newLocalMemoryUnit(name, type);
    //}
    //
    //public XRegister declareSharedMemoryUnit(String name, XType type) {
    //    if (isSharedMemoryDeclared(name)) {
    //        throw new MemoryUnitDoubleDeclarationError(name, false);
    //    }
    //    return newSharedMemory(name, type);
    //}

    //private String defineMemory(String postProcessId, XType returnType, boolean isLocal) {
    //    String baseName = postProcessId;
    //    int count = 1;
    //    XMemoryUnit location = null;
    //    while (location == null) {
    //        location = isLocal ? getLocalMemory(postProcessId) : tryFindSharedMemory(postProcessId);
    //        postProcessId = getNewName(baseName, count++);
    //    }
    //    return postProcessId;
    //}


    //private boolean isLocalMemoryDeclared(String name) {
    //    return localLocations.containsKey(name);
    //}
    //
    //private boolean isSharedMemoryDeclared(String name) {
    //    return sharedLocations.containsKey(name);
    //}

    public XRegister newLocalMemoryUnit(String name) {// type) {
        XRegister location = new XRegister(name, XMemoryUnitBase.Bitness.bit16); //todo:bitness
        localLocations.put(name, location);
        return location;
    }

    public XLocation newSharedMemory(String name) {
        XLocation location = new XLocation(name, XMemoryUnitBase.Bitness.bit16); //todo:bitness
        sharedLocations.put(name, location);
        return location;
    }



    private int tempNamesCounter = 1;
    private String newTempLocalName() {
        return getNewName("reg", tempNamesCounter++);
    }

    private String getNewName(String baseName, int count) {
        return baseName + '_' + count;
    }
}
