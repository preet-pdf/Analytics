package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class AuditDataProcess {

    private static final Map<Integer, Map<AuditEvents, List<Map<String, Integer>>>> eventTypeCountsPerHour = new HashMap<>();

    public void processAuditEvent(AuditEvent event) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(event.getEventTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        eventTypeCountsPerHour.putIfAbsent(hour, new HashMap<>());
        Map<AuditEvents, List<Map<String, Integer>>> eventTypeCounts = eventTypeCountsPerHour.get(hour);

        eventTypeCounts.putIfAbsent(event.getAuditEvent(), new ArrayList<>());
        List<Map<String, Integer>> eventDataList = eventTypeCounts.get(event.getAuditEvent());

        Map<String, Integer> eventDataMap = null;
        for (Map<String, Integer> map : eventDataList) {
            if (map.containsKey(event.getEventData())) {
                eventDataMap = map;
                break;
            }
        }

        if (eventDataMap == null) {
            eventDataMap = new HashMap<>();
            eventDataList.add(eventDataMap);
        }

        eventDataMap.put(event.getEventData(), eventDataMap.getOrDefault(event.getEventData(), 0) + 1);
    }


    public void printEventTypeCountsPerHour() {
        for (Map.Entry<Integer, Map<AuditEvents, List<Map<String, Integer>>>> entry : eventTypeCountsPerHour.entrySet()) {
            int hour = entry.getKey();
            Map<AuditEvents, List<Map<String, Integer>>> eventTypeCounts = entry.getValue();

            System.out.println("Hour " + hour + ":");
            for (Map.Entry<AuditEvents, List<Map<String, Integer>>> eventTypeEntry : eventTypeCounts.entrySet()) {
                AuditEvents auditType = eventTypeEntry.getKey();
                System.out.println("\t AuditType: " + auditType);
                List<Map<String, Integer>> value = eventTypeEntry.getValue();
                value.forEach(
                        audit -> {
                            System.out.println(audit);
                        }
                );
//                int count = eventTypeEntry.getValue();
//                System.out.println("\tEventType: " + eventType + ", Count: " + count);
            }
        }
    }


}
