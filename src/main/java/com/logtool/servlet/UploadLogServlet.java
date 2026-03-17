package com.logtool.servlet;

import com.logtool.service.LogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/upload")
@MultipartConfig
public class UploadLogServlet extends HttpServlet {
    private LogService logService = new LogService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        try{
            Part filePart = request.getPart("logfile");
            InputStream file = filePart.getInputStream();
            String fileName = filePart.getSubmittedFileName();

            logService.processUpload(file, fileName);

            response.sendRedirect("dashboard");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
