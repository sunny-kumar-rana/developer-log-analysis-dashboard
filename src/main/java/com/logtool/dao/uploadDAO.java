package com.logtool.dao;

import com.logtool.model.Upload;
import com.logtool.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class uploadDAO {
    public long saveUpload(Upload upload){
        String query = "INSERT INTO uploads(id, file_name, upload_time, total_lines, corrupt_lines) VALUES (uploads_seq.NEXTVAL, ?, ?, ?, ?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query, new String[]{"id"})){

            ps.setString(1,upload.getFileName());
            ps.setTimestamp(2, upload.getUploadTime());
            ps.setLong(3, upload.getTotalLines());
            ps.setInt(4, 0);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                return rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
