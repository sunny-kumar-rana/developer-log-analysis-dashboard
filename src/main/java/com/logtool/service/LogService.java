package com.logtool.service;

import com.logtool.dao.LogDAO;
import com.logtool.dao.UploadDAO;
import com.logtool.model.LogEntry;
import com.logtool.model.Upload;
import com.logtool.parser.LogParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogService {
    private LogDAO logDAO = new LogDAO();
    private UploadDAO uploadDAO = new UploadDAO();
    private LogParser logParser = new LogParser();

    public void processUpload(InputStream file, String fileName){
        List<LogEntry> logs = new ArrayList<>();
        int lines = 0;

        Upload upload = new Upload();
        upload.setFileName(fileName);
        upload.setUploadTime(new Timestamp(System.currentTimeMillis()));
        upload.setTotalLines(0);

        long uploadId = uploadDAO.saveUpload(upload);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(file))){
            String line;

            while ((line = br.readLine()) != null){
                lines++;

                LogEntry log = logParser.parseLine(line);

                if(log != null){
                    log.setUploadId(uploadId);
                    logs.add(log);
                }
                if(logs.size() == 500){
                    logDAO.saveBatch(logs);
                    logs.clear();
                }
                if (!logs.isEmpty()){
                    logDAO.saveBatch(logs);
                }
            }

        } catch (IOException e){
            System.out.println(e);
        }
    }

    public Map<String, Integer> getLevelStats(){
        return logDAO.countByLevel();
    }
    public Map<String, Integer> getServiceErrors(){
        return logDAO.countErrorsByService();
    }
    public List<LogEntry> searchLogs(String keyword){
        return logDAO.searchLogs(keyword);
    }
    public List<LogEntry> recentErrors(){
        return logDAO.findRecentErrors();
    }
}
