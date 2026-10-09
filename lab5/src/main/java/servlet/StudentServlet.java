package servlet;

import dao.RecordDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("stname");
        String course = request.getParameter("course");
        String feeStr = request.getParameter("fee");

        if (name != null && !name.trim().isEmpty()) {
            int fee = 0;
            try {
                fee = Integer.parseInt(feeStr);
            } catch (Exception e) {
                fee = 0;
            }
            RecordDAO dao = new RecordDAO();
            dao.insertRecord(name, course, fee);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/");
    }
}