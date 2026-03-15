package com.logtool.dao;

import com.logtool.model.LogEntry;
import com.logtool.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDAO {
    public void save(LogEntry log) {

        String sql = """
                INSERT INTO logs
                (id, upload_id, timestamp, level, service, message)
                VALUES (logs_seq.NEXTVAL, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, log.getUploadId());
            ps.setTimestamp(2, Timestamp.valueOf(log.getTimestamp()));
            ps.setString(3, log.getLevel());
            ps.setString(4, log.getService());
            ps.setString(5, log.getMessage());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBatch(List<LogEntry> logs) {

        String sql = """
                INSERT INTO logs
                (id, upload_id, timestamp, level, service, message)
                VALUES (logs_seq.NEXTVAL, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (LogEntry log : logs) {

                ps.setLong(1, log.getUploadId());
                ps.setTimestamp(2, Timestamp.valueOf(log.getTimestamp()));
                ps.setString(3, log.getLevel());
                ps.setString(4, log.getService());
                ps.setString(5, log.getMessage());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LogEntry> findRecentErrors() {

        List<LogEntry> logs = new ArrayList<>();

        String sql = """
                SELECT *
                FROM logs
                WHERE level='ERROR'
                ORDER BY timestamp DESC
                FETCH FIRST 20 ROWS ONLY
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                LogEntry log = new LogEntry();

                log.setId(rs.getLong("id"));
                log.setUploadId(rs.getLong("upload_id"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                log.setLevel(rs.getString("level"));
                log.setService(rs.getString("service"));
                log.setMessage(rs.getString("message"));

                logs.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }

    public Map<String, Integer> countByLevel() {

        Map<String, Integer> map = new HashMap<>();

        String sql = """
                SELECT level, COUNT(*)
                FROM logs
                GROUP BY level
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String level = rs.getString(1);
                int count = rs.getInt(2);

                map.put(level, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, Integer> countErrorsByService() {

        Map<String, Integer> map = new HashMap<>();

        String sql = """
                SELECT service, COUNT(*)
                FROM logs
                WHERE level='ERROR'
                GROUP BY service
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String service = rs.getString(1);
                int count = rs.getInt(2);

                map.put(service, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<LogEntry> searchLogs(String keyword) {

        List<LogEntry> logs = new ArrayList<>();

        String sql = """
                SELECT *
                FROM logs
                WHERE message LIKE ?
                ORDER BY timestamp DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LogEntry log = new LogEntry();

                log.setId(rs.getLong("id"));
                log.setUploadId(rs.getLong("upload_id"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                log.setLevel(rs.getString("level"));
                log.setService(rs.getString("service"));
                log.setMessage(rs.getString("message"));

                logs.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }
}
