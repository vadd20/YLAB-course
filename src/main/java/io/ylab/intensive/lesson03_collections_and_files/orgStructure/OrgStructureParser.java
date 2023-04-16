package io.ylab.intensive.lesson03_collections_and_files.orgStructure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    public Employee parseStructure(File csvFile) throws IOException;
}
