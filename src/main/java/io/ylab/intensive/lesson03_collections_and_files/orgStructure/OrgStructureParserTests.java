package io.ylab.intensive.lesson03_collections_and_files.orgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTests {
    public static void main(String[] args) throws IOException {
        File file = new File("./src/main/java/io/ylab/intensive/lesson03_collections_and_files/orgStructure/data");
        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee boss = parser.parseStructure(file);
        if (boss == null) {
            System.out.println("Boss is null");
            return;
        }
        System.out.println("Boss:");
        System.out.println(boss);

        System.out.println("Employee:");
        System.out.println(boss.getSubordinate().get(2));

        System.out.println("Other employee:");
        Employee firstEmployee = boss.getSubordinate().get(2).getSubordinate().get(0);
        System.out.println("Name: " + firstEmployee.getName() + ", ID:" + firstEmployee.getId() + ", BossID: " +
                firstEmployee.getBossId() + ", Position: " + firstEmployee.getPosition() + ", Boss: " +
                firstEmployee.getBoss());

        System.out.println("List of subordinates of boss:");
        System.out.println(boss.getSubordinate());
    }
}
