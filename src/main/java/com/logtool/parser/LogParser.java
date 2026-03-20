package com.logtool.parser;

import com.logtool.model.LogEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    long corruptLogs = 0;

    public LogEntry parseLine(String line){

        if(line == null || line.isBlank()){
            return null;
        }

        String[] logTokens = line.trim().split(" ", 5);

        if(logTokens.length < 5){
            return null;
        }

        try{

            String dateTime = logTokens[0] + " " + logTokens[1];
            LocalDateTime timestamp = LocalDateTime.parse(dateTime, formatter);
            String level = logTokens[2];
            String service = logTokens[3];
            String message = logTokens[4];

            LogEntry le = new LogEntry();
            le.setTimestamp(timestamp);
            le.setLevel(level);
            le.setService(service);
            le.setMessage(message);

            return le;
        } catch (Exception e){
            return null;
        }

    }

    public List<LogEntry> parseLines(List<String> lines){
        List<LogEntry> entries = new ArrayList<>();

        for (String line : lines) {

            LogEntry le = this.parseLine(line);
            if(le != null){
                entries.add(le);
            }
            if(le == null){
                corruptLogs++;
            }

        }
        return entries;
    }

    public List<LogEntry> parseFile(InputStream file) throws IOException {

        List<String> logLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null){
                logLines.add(line);
            }
        }

        return this.parseLines(logLines);

    }
}
