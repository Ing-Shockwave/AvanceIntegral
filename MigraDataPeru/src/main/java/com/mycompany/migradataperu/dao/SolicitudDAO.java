package com.mycompany.migradataperu.dao;

import com.mycompany.migradataperu.conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SolicitudDAO {

    public int contarSolicitudes(String nacionalidad, String sexo, String anio, String mes, String edad, String estadoCivil) {

        int total = 0;

        String sql = "SELECT COALESCE(SUM(cantidad),0) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?)";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error contarSolicitudes: " + e.getMessage());
        }

        return total;
    }

    public int contarNacionalidades() {

        int total = 0;

        String sql = "SELECT COUNT(DISTINCT nacionalidad) total "
                + "FROM solicitud_calidad_migratoria";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error contarNacionalidades: " + e.getMessage());
        }

        return total;
    }

    public Map<String, Integer> solicitudesPorNacionalidad(
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) {

        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT nacionalidad, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY nacionalidad "
                + "ORDER BY total DESC "
                + "LIMIT 10";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(
                        rs.getString("nacionalidad"),
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorNacionalidad: " + e.getMessage());
        }

        return datos;
    }

    public Map<String, Integer> solicitudesPorSexo(
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) {

        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT sexo, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY sexo "
                + "ORDER BY total DESC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(
                        rs.getString("sexo"),
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorSexo: " + e.getMessage());
        }

        return datos;
    }

    public Map<String, Integer> solicitudesPorDepartamento(
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) {

        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT departamento_atencion, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY departamento_atencion "
                + "ORDER BY total DESC "
                + "LIMIT 10";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(
                        rs.getString("departamento_atencion"),
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorDepartamento: " + e.getMessage());
        }

        return datos;
    }

    public Map<String, Integer> solicitudesPorMes(
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) {

        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT anio_tramite, mes_tramite, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY anio_tramite, mes_tramite "
                + "ORDER BY anio_tramite ASC, FIELD(mes_tramite, "
                + "'ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO',"
                + "'JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE') ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String etiqueta = rs.getString("mes_tramite") + " " + rs.getString("anio_tramite");
                datos.put(etiqueta, rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorMes: " + e.getMessage());
        }

        return datos;
    }

    public List<Map<String, String>> listarTablaResultados(
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) {

        List<Map<String, String>> lista = new ArrayList<>();

        String sql = "SELECT nacionalidad, sexo, departamento_atencion, anio_tramite, mes_tramite, edad, estado_civil, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY nacionalidad, sexo, departamento_atencion, anio_tramite, mes_tramite, edad, estado_civil "
                + "ORDER BY total DESC "
                + "LIMIT 20";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> fila = new LinkedHashMap<>();
                fila.put("nacionalidad", rs.getString("nacionalidad"));
                fila.put("sexo", rs.getString("sexo"));
                fila.put("departamento", rs.getString("departamento_atencion"));
                fila.put("anio", rs.getString("anio_tramite"));
                fila.put("mes", rs.getString("mes_tramite"));
                fila.put("edad", rs.getString("edad"));
                fila.put("estadoCivil", rs.getString("estado_civil"));
                fila.put("cantidad", String.valueOf(rs.getInt("total")));
                lista.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error listarTablaResultados: " + e.getMessage());
        }

        return lista;
    }

    public String nacionalidadTop(String nacionalidad, String sexo, String anio, String mes, String edad, String estadoCivil) {

        String sql = "SELECT nacionalidad FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY nacionalidad "
                + "ORDER BY SUM(cantidad) DESC "
                + "LIMIT 1";

        return obtenerTop(sql, nacionalidad, sexo, anio, mes, edad, estadoCivil);
    }

    public String departamentoTop(String nacionalidad, String sexo, String anio, String mes, String edad, String estadoCivil) {

        String sql = "SELECT departamento_atencion FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY departamento_atencion "
                + "ORDER BY SUM(cantidad) DESC "
                + "LIMIT 1";

        return obtenerTop(sql, nacionalidad, sexo, anio, mes, edad, estadoCivil);
    }

    public String sexoTop(String nacionalidad, String sexo, String anio, String mes, String edad, String estadoCivil) {

        String sql = "SELECT sexo FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "AND (? = '' OR anio_tramite = ?) "
                + "AND (? = '' OR mes_tramite = ?) "
                + "AND (? = '' OR edad = ?) "
                + "AND (? = '' OR estado_civil = ?) "
                + "GROUP BY sexo "
                + "ORDER BY SUM(cantidad) DESC "
                + "LIMIT 1";

        return obtenerTop(sql, nacionalidad, sexo, anio, mes, edad, estadoCivil);
    }

    private String obtenerTop(String sql, String nacionalidad, String sexo, String anio, String mes, String edad, String estadoCivil) {

        String resultado = "Sin datos";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resultado = rs.getString(1);
            }

        } catch (Exception e) {
            System.out.println("Error obtenerTop: " + e.getMessage());
        }

        return resultado;
    }

    public List<String> listarNacionalidades() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT nacionalidad "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE nacionalidad IS NOT NULL AND nacionalidad <> '' "
                + "ORDER BY nacionalidad ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("nacionalidad"));
            }

        } catch (Exception e) {
            System.out.println("Error listarNacionalidades: " + e.getMessage());
        }

        return lista;
    }

    public List<String> listarSexos() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT sexo "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE sexo IS NOT NULL AND sexo <> '' "
                + "ORDER BY sexo ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("sexo"));
            }

        } catch (Exception e) {
            System.out.println("Error listarSexos: " + e.getMessage());
        }

        return lista;
    }

    public List<String> listarAnios() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT anio_tramite "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE anio_tramite IS NOT NULL "
                + "ORDER BY anio_tramite ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("anio_tramite"));
            }

        } catch (Exception e) {
            System.out.println("Error listarAnios: " + e.getMessage());
        }

        return lista;
    }

    public List<String> listarMeses() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT mes_tramite "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE mes_tramite IS NOT NULL AND mes_tramite <> '' "
                + "ORDER BY FIELD(mes_tramite, "
                + "'ENERO','FEBRERO','MARZO','ABRIL','MAYO','JUNIO',"
                + "'JULIO','AGOSTO','SEPTIEMBRE','OCTUBRE','NOVIEMBRE','DICIEMBRE') ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("mes_tramite"));
            }

        } catch (Exception e) {
            System.out.println("Error listarMeses: " + e.getMessage());
        }

        return lista;
    }

    public List<String> listarEdades() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT edad "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE edad IS NOT NULL AND edad <> '' "
                + "ORDER BY edad ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("edad"));
            }

        } catch (Exception e) {
            System.out.println("Error listarEdades: " + e.getMessage());
        }

        return lista;
    }

    public List<String> listarEstadosCiviles() {

        List<String> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT estado_civil "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE estado_civil IS NOT NULL AND estado_civil <> '' "
                + "ORDER BY estado_civil ASC";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                lista.add(rs.getString("estado_civil"));
            }

        } catch (Exception e) {
            System.out.println("Error listarEstadosCiviles: " + e.getMessage());
        }

        return lista;
    }

    private void asignarFiltros(
            PreparedStatement ps,
            String nacionalidad,
            String sexo,
            String anio,
            String mes,
            String edad,
            String estadoCivil
    ) throws Exception {

        String nacionalidadLimpia = limpiarParametro(nacionalidad);
        String sexoLimpio = limpiarParametro(sexo);
        String anioLimpio = limpiarParametro(anio);
        String mesLimpio = limpiarParametro(mes);
        String edadLimpia = limpiarParametro(edad);
        String estadoCivilLimpio = limpiarParametro(estadoCivil);

        ps.setString(1, nacionalidadLimpia);
        ps.setString(2, nacionalidadLimpia);
        ps.setString(3, sexoLimpio);
        ps.setString(4, sexoLimpio);
        ps.setString(5, anioLimpio);
        ps.setString(6, anioLimpio);
        ps.setString(7, mesLimpio);
        ps.setString(8, mesLimpio);
        ps.setString(9, edadLimpia);
        ps.setString(10, edadLimpia);
        ps.setString(11, estadoCivilLimpio);
        ps.setString(12, estadoCivilLimpio);
    }

    private String limpiarParametro(String valor) {
        return valor == null ? "" : valor.trim();
    }
    
public Map<String, Integer> solicitudesMapaDepartamento(
        String nacionalidad,
        String sexo,
        String anio,
        String mes,
        String edad,
        String estadoCivil
) {

    Map<String, Integer> datos = new LinkedHashMap<>();

    String sql = "SELECT departamento_atencion, SUM(cantidad) total "
            + "FROM solicitud_calidad_migratoria "
            + "WHERE (? = '' OR nacionalidad = ?) "
            + "AND (? = '' OR sexo = ?) "
            + "AND (? = '' OR anio_tramite = ?) "
            + "AND (? = '' OR mes_tramite = ?) "
            + "AND (? = '' OR edad = ?) "
            + "AND (? = '' OR estado_civil = ?) "
            + "GROUP BY departamento_atencion "
            + "ORDER BY total DESC";

    try (
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
    ) {

        asignarFiltros(ps, nacionalidad, sexo, anio, mes, edad, estadoCivil);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            datos.put(
                    rs.getString("departamento_atencion"),
                    rs.getInt("total")
            );
        }

    } catch (Exception e) {
        System.out.println("Error solicitudesMapaDepartamento: " + e.getMessage());
    }

    return datos;
    }
}
