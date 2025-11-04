package br.com.drianodev;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static br.com.drianodev.Attributes.BODY;
import static br.com.drianodev.Attributes.PATIENT;
import static br.com.drianodev.Attributes.TYPE;

class ReportImporter implements Importer {

    private static final String NAME_PREFIX = "Patient: ";

    @Override
    public Document importFile(final File file) throws IOException {
        final TextFile textFile = new TextFile(file);
        textFile.addLineSuffix(NAME_PREFIX, PATIENT);
        textFile.addLines(2, line -> false, BODY);

        final Map<String, String> attributes = textFile.getAttributes();
        attributes.put(TYPE, "REPORT");
        return new Document(attributes);
    }
}