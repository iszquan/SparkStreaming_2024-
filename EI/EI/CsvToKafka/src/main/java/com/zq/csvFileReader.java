package com.zq;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.function.Supplier;


public class csvFileReader implements Supplier<EIcsv> {

    private final String filePath;
    private CsvReader csvReader;

    public csvFileReader(String filePath) throws IOException {

        this.filePath = filePath;
        try {
            csvReader = new CsvReader(filePath, ',', Charset.forName("GBK"));
            csvReader.readHeaders();
        } catch (IOException e) {
            throw new IOException("Error reading TaxiRecords from file: " + filePath, e);
        }
    }

    @Override
    public EIcsv get() {
        EIcsv eIcsv = null;
        try{
            if(csvReader.readRecord()) {
                csvReader.getRawRecord();
                eIcsv = new EIcsv(
                        Long.valueOf(csvReader.get(0)),
                        csvReader.get(1),
                        csvReader.get(2),
                        Long.valueOf(csvReader.get(3)),
                        csvReader.get(4),
                        csvReader.get(5),
                        csvReader.get(6),
                        csvReader.get(7),
                        csvReader.get(8),
                        csvReader.get(9),
                        csvReader.get(10),
                        Long.valueOf(csvReader.get(11)));
            }
        } catch (IOException e) {
            throw new NoSuchElementException("IOException from " + filePath);
        }

        if (null==eIcsv) {
            throw new NoSuchElementException("All records read from " + filePath);
        }

        return eIcsv;
    }
}

