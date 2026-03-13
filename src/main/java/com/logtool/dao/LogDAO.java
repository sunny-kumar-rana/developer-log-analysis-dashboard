package com.logtool.dao;

import com.logtool.model.LogEntry;

import java.sql.Connection;
import java.util.List;

public class LogDAO {
    public boolean save(Connection conn, LogEntry lg){

    }
    public boolean saveBatch(Connection conn, List<LogEntry> logs){

    }
    public List<LogEntry> findRecentErrors(Connection conn){

    }
    public List<LogEntry> countByLevel(Connection conn){

    }
    public List<LogEntry> countErrorsByService(Connection conn){

    }
    public List<LogEntry> searchLogs(Connection conn){

    }
}
