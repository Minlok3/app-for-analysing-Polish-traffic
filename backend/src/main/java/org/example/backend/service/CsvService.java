package org.example.backend.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.example.backend.entity.DaneDrogowe;
import org.example.backend.entity.DaneGranica;
import org.example.backend.repository.DaneDrogoweRepository;
import org.example.backend.repository.DaneGranicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CsvService {

    private static final Logger logger = LoggerFactory.getLogger(CsvService.class);

    private final DaneDrogoweRepository daneDrogoweRepository;

    private final DaneGranicaRepository daneGranicaRepository;

    @Autowired
    public CsvService(DaneDrogoweRepository daneDrogoweRepository, DaneGranicaRepository daneGranicaRepository) {
        this.daneDrogoweRepository = daneDrogoweRepository;
        this.daneGranicaRepository = daneGranicaRepository;
    }

    @Value("${app.daneGranicaFilePath}")
    private String daneGranicaFilePath;

    @Value("${app.daneDrogoweFilePath}")
    private String daneDrogoweFilePath;

    // Scheduled task to upload CSV data every 5 minutes
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Scheduled(fixedRate = 300000) // 5 minutes = 300,000 milliseconds
    public void uploadCsvData() {
        try {
            // Read and process dane_granica.csv
            processDaneGranicaCsv();

            // Read and process dane_drogowe.csv
            processDaneDrogoweCsv();
        } catch (Exception e) {
            logger.error("Error uploading CSV data", e);
            throw new RuntimeException("Error uploading CSV data", e);
        }
    }

    private void processDaneGranicaCsv() {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

            // Open and read dane_granica.csv
            File file = new File(daneGranicaFilePath);
            if (!file.exists()) {
                throw new RuntimeException("Unable to find resource: dane_granica.csv, in: " + daneGranicaFilePath);
            }
            BufferedReader readerForDaneGranica = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            CSVReader csvReaderForDaneGranica = new CSVReaderBuilder(readerForDaneGranica).withCSVParser(csvParser).build();
            List<String[]> recordsForDaneGranica = csvReaderForDaneGranica.readAll();
            csvReaderForDaneGranica.close();
            readerForDaneGranica.close();

            for (String[] record : recordsForDaneGranica.subList(1, recordsForDaneGranica.size())) {
                DaneGranica dane = createDaneGranicaFromCsvRecord(record);
                saveOrUpdateDaneGranica(dane);
            }

        } catch (Exception e) {
            logger.error("Error processing dane_granica.csv", e);
            throw new RuntimeException("Error processing dane_granica.csv");
        }
    }

    private void processDaneDrogoweCsv() {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

            // Open and read dane_drogowe.csv
            File file = new File(daneDrogoweFilePath);
            if (!file.exists()) {
                throw new RuntimeException("Unable to find resource: dane_drogowe.csv, in: " + daneDrogoweRepository);
            }
            BufferedReader readerForDaneDrogowe = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            CSVReader csvReaderForDaneDrogowe = new CSVReaderBuilder(readerForDaneDrogowe).withCSVParser(csvParser).build();
            List<String[]> recordsForDaneDrogowe = csvReaderForDaneDrogowe.readAll();
            csvReaderForDaneDrogowe.close();
            readerForDaneDrogowe.close();

            for (String[] record : recordsForDaneDrogowe.subList(1, recordsForDaneDrogowe.size())) {
                DaneDrogowe dane = createDaneDrogoweFromCsvRecord(record);
                saveOrUpdateDaneDrogowe(dane);
            }

        } catch (Exception e) {
            logger.error("Error processing dane_drogowe.csv", e);
            throw new RuntimeException("Error processing dane_drogowe.csv");
        }
    }

    private DaneGranica createDaneGranicaFromCsvRecord(String[] record) {
        String dateString = record[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.valueOf(localDate);

        DaneGranica dane = new DaneGranica();
        dane.setData(date);
        dane.setTydzien(Integer.parseInt(record[1]));
        dane.setSumaPrzyjazduOgolem(Integer.parseInt(record[2]));
        dane.setSumaWyjazduOgolem(Integer.parseInt(record[3]));
        dane.setSumaPrzyjazduPolska(Integer.parseInt(record[4]));
        dane.setSumaWyjazduPolska(Integer.parseInt(record[5]));
        dane.setSumaPrzyjazduUkraina(Integer.parseInt(record[6]));
        dane.setSumaWyjazduUkraina(Integer.parseInt(record[7]));
        dane.setSumaPrzyjazduNiemcy(Integer.parseInt(record[8]));
        dane.setSumaWyjazduNiemcy(Integer.parseInt(record[9]));
        dane.setSumaPrzyjazduStanyZjednoczone(Integer.parseInt(record[10]));
        dane.setSumaWyjazduStanyZjednoczone(Integer.parseInt(record[11]));
        dane.setSumaPrzyjazduRumunia(Integer.parseInt(record[12]));
        dane.setSumaWyjazduRumunia(Integer.parseInt(record[13]));
        dane.setSumaPrzyjazduWielkaBrytania(Integer.parseInt(record[14]));
        dane.setSumaWyjazduWielkaBrytania(Integer.parseInt(record[15]));
        dane.setSumaPrzyjazduMoldawia(Integer.parseInt(record[16]));
        dane.setSumaWyjazduMoldawia(Integer.parseInt(record[17]));
        dane.setSumaPrzyjazduInne(Integer.parseInt(record[18]));
        dane.setSumaWyjazduInne(Integer.parseInt(record[19]));
        dane.setSumaPrzyjazduOddzialNadbuzanski(Integer.parseInt(record[20]));
        dane.setSumaWyjazduOddzialNadbuzanski(Integer.parseInt(record[21]));
        dane.setSumaPrzyjazduOddzialBieszczadzki(Integer.parseInt(record[22]));
        dane.setSumaWyjazduOddzialBieszczadzki(Integer.parseInt(record[23]));
        dane.setSumaPrzyjazduPgDrogowe(Integer.parseInt(record[24]));
        dane.setSumaWyjazduPgDrogowe(Integer.parseInt(record[25]));
        dane.setSumaPrzyjazduPgKolejowe(Integer.parseInt(record[26]));
        dane.setSumaWyjazduPgKolejowe(Integer.parseInt(record[27]));

        return dane;
    }

    private DaneDrogowe createDaneDrogoweFromCsvRecord(String[] record) {
        String dateString = record[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.valueOf(localDate);

        DaneDrogowe dane = new DaneDrogowe();
        dane.setData(date);
        dane.setTydzien(Integer.parseInt(record[0]));
        dane.setSredniaStrefaGranicaUA(Integer.parseInt(record[2]));
        dane.setSredniaStrefaGranicaBYLT(Integer.parseInt(record[3]));
        dane.setSredniaStrefaGranicaRU(Integer.parseInt(record[4]));
        dane.setSredniaStrefaGranicaMB(Integer.parseInt(record[5]));
        dane.setSredniaStrefaGranicaDE(Integer.parseInt(record[6]));
        dane.setSredniaStrefaGranicaCZ(Integer.parseInt(record[7]));
        dane.setSredniaStrefaGranicaSK(Integer.parseInt(record[8]));
        dane.setSredniaStrefaCentralna(Integer.parseInt(record[9]));
        dane.setSredniaWojDS(Integer.parseInt(record[10]));
        dane.setSredniaWojKP(Integer.parseInt(record[11]));
        dane.setSredniaWojLB(Integer.parseInt(record[12]));
        dane.setSredniaWojLS(Integer.parseInt(record[13]));
        dane.setSredniaWojLD(Integer.parseInt(record[14]));
        dane.setSredniaWojMP(Integer.parseInt(record[15]));
        dane.setSredniaWojMZ(Integer.parseInt(record[16]));
        dane.setSredniaWojOP(Integer.parseInt(record[17]));
        dane.setSredniaWojPK(Integer.parseInt(record[18]));
        dane.setSredniaWojPL(Integer.parseInt(record[19]));
        dane.setSredniaWojPM(Integer.parseInt(record[20]));
        dane.setSredniaWojSL(Integer.parseInt(record[21]));
        dane.setSredniaWojSK(Integer.parseInt(record[22]));
        dane.setSredniaWojWM(Integer.parseInt(record[23]));
        dane.setSredniaWojWP(Integer.parseInt(record[24]));
        dane.setSredniaWojZP(Integer.parseInt(record[25]));

        return dane;
    }

    private void saveOrUpdateDaneGranica(DaneGranica dane) {
        // Check if the data already exists in the database
        Optional<DaneGranica> existingData = Optional.ofNullable(daneGranicaRepository.findByData(dane.getData()));

        if (existingData.isPresent()) {
            // Update existing data
            DaneGranica existing = existingData.get();
            existing.setTydzien(dane.getTydzien());
            existing.setSumaPrzyjazduOgolem(dane.getSumaPrzyjazduOgolem());
            existing.setSumaWyjazduOgolem(dane.getSumaWyjazduOgolem());
            existing.setSumaPrzyjazduPolska(dane.getSumaPrzyjazduPolska());
            existing.setSumaWyjazduPolska(dane.getSumaWyjazduPolska());
            existing.setSumaPrzyjazduUkraina(dane.getSumaPrzyjazduUkraina());
            existing.setSumaWyjazduUkraina(dane.getSumaWyjazduUkraina());
            existing.setSumaPrzyjazduNiemcy(dane.getSumaPrzyjazduNiemcy());
            existing.setSumaWyjazduNiemcy(dane.getSumaWyjazduNiemcy());
            existing.setSumaPrzyjazduStanyZjednoczone(dane.getSumaPrzyjazduStanyZjednoczone());
            existing.setSumaWyjazduStanyZjednoczone(dane.getSumaWyjazduStanyZjednoczone());
            existing.setSumaPrzyjazduRumunia(dane.getSumaPrzyjazduRumunia());
            existing.setSumaWyjazduRumunia(dane.getSumaWyjazduRumunia());
            existing.setSumaPrzyjazduWielkaBrytania(dane.getSumaPrzyjazduWielkaBrytania());
            existing.setSumaWyjazduWielkaBrytania(dane.getSumaWyjazduWielkaBrytania());
            existing.setSumaPrzyjazduMoldawia(dane.getSumaPrzyjazduMoldawia());
            existing.setSumaWyjazduMoldawia(dane.getSumaWyjazduMoldawia());
            existing.setSumaPrzyjazduInne(dane.getSumaPrzyjazduInne());
            existing.setSumaWyjazduInne(dane.getSumaWyjazduInne());
            existing.setSumaPrzyjazduOddzialNadbuzanski(dane.getSumaPrzyjazduOddzialNadbuzanski());
            existing.setSumaWyjazduOddzialNadbuzanski(dane.getSumaWyjazduOddzialNadbuzanski());
            existing.setSumaPrzyjazduOddzialBieszczadzki(dane.getSumaPrzyjazduOddzialBieszczadzki());
            existing.setSumaWyjazduOddzialBieszczadzki(dane.getSumaWyjazduOddzialBieszczadzki());
            existing.setSumaPrzyjazduPgDrogowe(dane.getSumaPrzyjazduPgDrogowe());
            existing.setSumaWyjazduPgDrogowe(dane.getSumaWyjazduPgDrogowe());
            existing.setSumaPrzyjazduPgKolejowe(dane.getSumaPrzyjazduPgKolejowe());
            existing.setSumaWyjazduPgKolejowe(dane.getSumaWyjazduPgKolejowe());

            daneGranicaRepository.save(existing);
            logger.info("Updated DaneGranica record for date: {}", existing.getData());
        } else {
            // Insert new data if it doesn't exist
            daneGranicaRepository.save(dane);
            logger.info("Inserted new DaneGranica record for date: {}", dane.getData());
        }
    }

    private void saveOrUpdateDaneDrogowe(DaneDrogowe dane) {
        // Check if the data already exists in the database
        Optional<DaneDrogowe> existingData = Optional.ofNullable(daneDrogoweRepository.findByData(dane.getData()));

        if (existingData.isPresent()) {
            // Update existing data
            DaneDrogowe existing = existingData.get();
            existing.setTydzien(dane.getTydzien());
            existing.setSredniaStrefaGranicaUA(dane.getSredniaStrefaGranicaUA());
            existing.setSredniaStrefaGranicaBYLT(dane.getSredniaStrefaGranicaBYLT());
            existing.setSredniaStrefaGranicaRU(dane.getSredniaStrefaGranicaRU());
            existing.setSredniaStrefaGranicaMB(dane.getSredniaStrefaGranicaMB());
            existing.setSredniaStrefaGranicaDE(dane.getSredniaStrefaGranicaDE());
            existing.setSredniaStrefaGranicaCZ(dane.getSredniaStrefaGranicaCZ());
            existing.setSredniaStrefaGranicaSK(dane.getSredniaStrefaGranicaSK());
            existing.setSredniaStrefaCentralna(dane.getSredniaStrefaCentralna());
            existing.setSredniaWojDS(dane.getSredniaWojDS());
            existing.setSredniaWojKP(dane.getSredniaWojKP());
            existing.setSredniaWojLB(dane.getSredniaWojLB());
            existing.setSredniaWojLS(dane.getSredniaWojLS());
            existing.setSredniaWojLD(dane.getSredniaWojLD());
            existing.setSredniaWojMP(dane.getSredniaWojMP());
            existing.setSredniaWojMZ(dane.getSredniaWojMZ());
            existing.setSredniaWojOP(dane.getSredniaWojOP());
            existing.setSredniaWojPK(dane.getSredniaWojPK());
            existing.setSredniaWojPL(dane.getSredniaWojPL());
            existing.setSredniaWojPM(dane.getSredniaWojPM());
            existing.setSredniaWojSL(dane.getSredniaWojSL());
            existing.setSredniaWojSK(dane.getSredniaWojSK());
            existing.setSredniaWojWM(dane.getSredniaWojWM());
            existing.setSredniaWojWP(dane.getSredniaWojWP());
            existing.setSredniaWojZP(dane.getSredniaWojZP());

            daneDrogoweRepository.save(existing);
            logger.info("Updated DaneDrogowe record for date: {}", existing.getData());
        } else {
            // Insert new data if it doesn't exist
            daneDrogoweRepository.save(dane);
            logger.info("Inserted new DaneDrogowe record for date: {}", dane.getData());
        }
    }
}
