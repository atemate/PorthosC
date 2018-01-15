//package mousquetaires.interpretation;
//
//import mousquetaires.languages.toxrepr.events.memoryevents.XLoadMemoryEvent;
//import mousquetaires.languages.toxrepr.events.memoryevents.XReadEvent;
//import mousquetaires.languages.toxrepr.events.memoryevents.XStoreMemoryEvent;
//import mousquetaires.languages.toxrepr.memoryevents.XMemoryUnit;
//import mousquetaires.utils.exceptions.NotImplementedException;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * This class is responsible for keeping information on data-flow of the program.
// * It stores read and write events, and keeps track of ordering between them (read-from 'rf' relations)
// */
//class DataFlowManager {
//    // TODO: to use hashmapes, set up hashes properly!!!
//
//    private final static Map<XMemoryUnit, XLoadMemoryEvent> readEvents = new HashMap<>();
//
//    // See: La thèse de Jade Alglave, 3.2.2 Write Serialisation:
//    // "We assume all values written to a given location l to be serialised, following a coherence order."
//    private final static Map<XMemoryUnit, List<XStoreMemoryEvent>> writeSerialisation = new HashMap<>();
//
//    private final static Map<XStoreMemoryEvent, XLoadMemoryEvent> readFromRelations = new HashMap<>();
//
//    public void registerReadEvent(XReadEvent event) {
//        //readEvents.add(event);
//        // TODO: here, add new relation from last write
//        throw new NotImplementedException();
//    }
//
//    public void registerWriteEvent(XStoreMemoryEvent writeEvent) {
//        XMemoryUnit destinationLocation = writeEvent.destination;
//        List<XStoreMemoryEvent> previousEvents = writeSerialisation.get(destinationLocation);
//        if (previousEvents == null) {  // first write to that location
//            previousEvents = new LinkedList<>();
//            previousEvents.add(writeEvent);
//            writeSerialisation.put(destinationLocation, previousEvents);
//        } else {
//            previousEvents.add(writeEvent);
//        }
//    }
//}
