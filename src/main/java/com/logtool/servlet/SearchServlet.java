package com.logtool.servlet;

import com.logtool.service.LogService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("search")
public class SearchServlet extends HttpServlet {

    LogService ls = new LogService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        try{

            String keyword = request.getParameter("q");

            request.setAttribute("logs", ls.searchLogs(keyword));

            request.getRequestDispatcher("search.jsp").forward(request, response);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
