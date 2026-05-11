package com.mycompany.migradataperu.servlet;

import com.mycompany.migradataperu.dao.CambioDAO;
import com.mycompany.migradataperu.dao.CarnetDAO;
import com.mycompany.migradataperu.dao.SolicitudDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String nacionalidad = request.getParameter("nacionalidad");
        String sexo = request.getParameter("sexo");

        if (nacionalidad == null) {
            nacionalidad = "";
        }

        if (sexo == null) {
            sexo = "";
        }

        SolicitudDAO solicitudDAO = new SolicitudDAO();
        CambioDAO cambioDAO = new CambioDAO();
        CarnetDAO carnetDAO = new CarnetDAO();

        int totalSolicitudes = solicitudDAO.contarSolicitudes(nacionalidad, sexo);
        int totalNacionalidades = solicitudDAO.contarNacionalidades();
        int totalCambios = cambioDAO.contarCambios();
        int totalCarnets = carnetDAO.contarCarnets();

        Map<String, Integer> graficoNacionalidades =
                solicitudDAO.solicitudesPorNacionalidad(nacionalidad, sexo);

        Map<String, Integer> graficoSexo =
                solicitudDAO.solicitudesPorSexo(nacionalidad, sexo);

        Map<String, Integer> graficoDepartamento =
                solicitudDAO.solicitudesPorDepartamento(nacionalidad, sexo);

        List<Map<String, String>> tablaResultados =
                solicitudDAO.listarTablaResultados(nacionalidad, sexo);

        List<String> listaNacionalidades =
                solicitudDAO.listarNacionalidades();

        List<String> listaSexos =
                solicitudDAO.listarSexos();

        String nacionalidadTop = solicitudDAO.nacionalidadTop(nacionalidad, sexo);
        String departamentoTop = solicitudDAO.departamentoTop(nacionalidad, sexo);
        String sexoTop = solicitudDAO.sexoTop(nacionalidad, sexo);

        request.setAttribute("totalSolicitudes", totalSolicitudes);
        request.setAttribute("totalNacionalidades", totalNacionalidades);
        request.setAttribute("totalCambios", totalCambios);
        request.setAttribute("totalCarnets", totalCarnets);

        request.setAttribute("graficoNacionalidades", graficoNacionalidades);
        request.setAttribute("graficoSexo", graficoSexo);
        request.setAttribute("graficoDepartamento", graficoDepartamento);
        request.setAttribute("tablaResultados", tablaResultados);

        request.setAttribute("listaNacionalidades", listaNacionalidades);
        request.setAttribute("listaSexos", listaSexos);

        request.setAttribute("nacionalidadSeleccionada", nacionalidad);
        request.setAttribute("sexoSeleccionado", sexo);

        request.setAttribute("nacionalidadTop", nacionalidadTop);
        request.setAttribute("departamentoTop", departamentoTop);
        request.setAttribute("sexoTop", sexoTop);

        request.getRequestDispatcher("index.jsp")
               .forward(request, response);
    }
}