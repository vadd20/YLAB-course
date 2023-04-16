package io.ylab.intensive.lesson03_collections_and_files.orgStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            HashMap<Long, ArrayList<Employee>> subordinateMap;    // map, где values - список
                                                                // подопечных, key - id их общего босса
            List<Employee> listOfAllEmployees = new ArrayList<>();

            readAndParse(br, listOfAllEmployees);
            Employee mainBoss = findBoss(listOfAllEmployees);
            subordinateMap = addSubordinatesInMap(listOfAllEmployees);

            for (Employee employee : listOfAllEmployees) {
                addSubordinatesInList(employee, subordinateMap);
                for (Employee currentSubordinates : employee.getSubordinate()) {
                    currentSubordinates.setBoss(employee);  // устанавливаем босса каждому подопечному
                }
            }
            return mainBoss;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static void readAndParse(BufferedReader br,
                                    List<Employee> listOfAllEmployees) throws IOException {
        String currentStr;
        while ((currentStr = br.readLine()) != null) {
            String[] data = currentStr.split(";");
            if (!isNumeric(data[0])) {
                continue;
            }
            Employee employee = new Employee();
            employee.setId(Long.parseLong(data[0]));
            if (data[1].isEmpty()) {
                employee.setBossId(null);
            } else {
                employee.setBossId(Long.parseLong(data[1]));
            }
            employee.setName(data[2]);
            employee.setPosition(data[3]);
            listOfAllEmployees.add(employee);
        }
    }

    public Employee findBoss(List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getBossId() == null) {
                return employee;
            }
        }
        return null;
    }

    public HashMap<Long, ArrayList<Employee>> addSubordinatesInMap(List<Employee> employees) {
        HashMap<Long, ArrayList<Employee>> subordinateMap = new HashMap<>();
        for (Employee employee : employees) {
            if (employee.getBossId() != null) {
                if (!subordinateMap.containsKey(employee.getBossId())) {
                    subordinateMap.put(employee.getBossId(), new ArrayList<>());
                }
                subordinateMap.get(employee.getBossId()).add(employee);  // добавляем подопечных в map
            }
        }
        return subordinateMap;
    }

    public static void addSubordinatesInList(Employee employee,
                                             HashMap<Long, ArrayList<Employee>> subordinateMap) {
        if (subordinateMap.containsKey(employee.getId())) { // проверка, есть ли в map ключ с id employee
            for (Employee currentSubordinates :
                    subordinateMap.get(employee.getId())) {
                employee.getSubordinate().add(currentSubordinates); // добавляем подчиненного из map
                                                                    // в список подчиненных getSubordinate()
            }
        }
    }


}
