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

    public int contarSolicitudes(String nacionalidad, String sexo) {
        int total = 0;

        String sql = "SELECT COALESCE(SUM(cantidad),0) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) total = rs.getInt("total");

        } catch (Exception e) {
            System.out.println("Error contarSolicitudes: " + e.getMessage());
        }

        return total;
    }

    public int contarNacionalidades() {
        int total = 0;

        String sql = "SELECT COUNT(DISTINCT nacionalidad) total "
                + "FROM solicitud_calidad_migratoria";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) total = rs.getInt("total");

        } catch (Exception e) {
            System.out.println("Error contarNacionalidades: " + e.getMessage());
        }

        return total;
    }

    public Map<String, Integer> solicitudesPorNacionalidad(String nacionalidad, String sexo) {
        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT nacionalidad, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "GROUP BY nacionalidad "
                + "ORDER BY total DESC "
                + "LIMIT 10";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(rs.getString("nacionalidad"), rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorNacionalidad: " + e.getMessage());
        }

        return datos;
    }

    public Map<String, Integer> solicitudesPorSexo(String nacionalidad, String sexo) {
        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT sexo, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "GROUP BY sexo "
                + "ORDER BY total DESC";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(rs.getString("sexo"), rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorSexo: " + e.getMessage());
        }

        return datos;
    }

    public Map<String, Integer> solicitudesPorDepartamento(String nacionalidad, String sexo) {
        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = "SELECT departamento_atencion, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "GROUP BY departamento_atencion "
                + "ORDER BY total DESC "
                + "LIMIT 10";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                datos.put(rs.getString("departamento_atencion"), rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("Error solicitudesPorDepartamento: " + e.getMessage());
        }

        return datos;
    }

    public List<Map<String, String>> listarTablaResultados(String nacionalidad, String sexo) {
        List<Map<String, String>> lista = new ArrayList<>();

        String sql = "SELECT nacionalidad, sexo, departamento_atencion, SUM(cantidad) total "
                + "FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) "
                + "AND (? = '' OR sexo = ?) "
                + "GROUP BY nacionalidad, sexo, departamento_atencion "
                + "ORDER BY total DESC "
                + "LIMIT 20";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> fila = new LinkedHashMap<>();
                fila.put("nacionalidad", rs.getString("nacionalidad"));
                fila.put("sexo", rs.getString("sexo"));
                fila.put("departamento", rs.getString("departamento_atencion"));
                fila.put("cantidad", String.valueOf(rs.getInt("total")));
                lista.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error listarTablaResultados: " + e.getMessage());
        }

        return lista;
    }

    public String nacionalidadTop(String nacionalidad, String sexo) {
        return obtenerTop(
                "SELECT nacionalidad FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) AND (? = '' OR sexo = ?) "
                + "GROUP BY nacionalidad ORDER BY SUM(cantidad) DESC LIMIT 1",
                nacionalidad,
                sexo
        );
    }

    public String departamentoTop(String nacionalidad, String sexo) {
        return obtenerTop(
                "SELECT departamento_atencion FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) AND (? = '' OR sexo = ?) "
                + "GROUP BY departamento_atencion ORDER BY SUM(cantidad) DESC LIMIT 1",
                nacionalidad,
                sexo
        );
    }

    public String sexoTop(String nacionalidad, String sexo) {
        return obtenerTop(
                "SELECT sexo FROM solicitud_calidad_migratoria "
                + "WHERE (? = '' OR nacionalidad = ?) AND (? = '' OR sexo = ?) "
                + "GROUP BY sexo ORDER BY SUM(cantidad) DESC LIMIT 1",
                nacionalidad,
                sexo
        );
    }

    private String obtenerTop(String sql, String nacionalidad, String sexo) {
        String resultado = "Sin datos";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nacionalidad);
            ps.setString(2, nacionalidad);
            ps.setString(3, sexo);
            ps.setString(4, sexo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) resultado = rs.getString(1);

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

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(rs.getString("nacionalidad"));

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

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(rs.getString("sexo"));

        } catch (Exception e) {
            System.out.println("Error listarSexos: " + e.getMessage());
        }

        return lista;
    }
}