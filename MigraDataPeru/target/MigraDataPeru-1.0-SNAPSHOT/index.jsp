<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<%
    Map<String, Integer> graficoNacionalidades =
            (Map<String, Integer>) request.getAttribute("graficoNacionalidades");

    Map<String, Integer> graficoSexo =
            (Map<String, Integer>) request.getAttribute("graficoSexo");

    Map<String, Integer> graficoDepartamento =
            (Map<String, Integer>) request.getAttribute("graficoDepartamento");

    List<Map<String, String>> tablaResultados =
            (List<Map<String, String>>) request.getAttribute("tablaResultados");

    List<String> listaNacionalidades =
            (List<String>) request.getAttribute("listaNacionalidades");

    List<String> listaSexos =
            (List<String>) request.getAttribute("listaSexos");

    String nacionalidadSeleccionada =
            (String) request.getAttribute("nacionalidadSeleccionada");

    String sexoSeleccionado =
            (String) request.getAttribute("sexoSeleccionado");

    if (nacionalidadSeleccionada == null) nacionalidadSeleccionada = "";
    if (sexoSeleccionado == null) sexoSeleccionado = "";

    StringBuilder labelsNacionalidad = new StringBuilder();
    StringBuilder valoresNacionalidad = new StringBuilder();

    if (graficoNacionalidades != null) {
        for (Map.Entry<String, Integer> entry : graficoNacionalidades.entrySet()) {
            labelsNacionalidad.append("'").append(entry.getKey()).append("',");
            valoresNacionalidad.append(entry.getValue()).append(",");
        }
    }

    StringBuilder labelsSexo = new StringBuilder();
    StringBuilder valoresSexo = new StringBuilder();

    if (graficoSexo != null) {
        for (Map.Entry<String, Integer> entry : graficoSexo.entrySet()) {
            labelsSexo.append("'").append(entry.getKey()).append("',");
            valoresSexo.append(entry.getValue()).append(",");
        }
    }

    StringBuilder labelsDepartamento = new StringBuilder();
    StringBuilder valoresDepartamento = new StringBuilder();

    if (graficoDepartamento != null) {
        for (Map.Entry<String, Integer> entry : graficoDepartamento.entrySet()) {
            labelsDepartamento.append("'").append(entry.getKey()).append("',");
            valoresDepartamento.append(entry.getValue()).append(",");
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MigraData Perú</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            background: #020617;
            color: #e5e7eb;
            font-family: 'Segoe UI', sans-serif;
            overflow-x: hidden;
        }

        .app {
            display: flex;
            min-height: 100vh;
            background:
                radial-gradient(circle at top right, rgba(37,99,235,0.16), transparent 35%),
                radial-gradient(circle at bottom left, rgba(14,165,233,0.10), transparent 35%),
                #020617;
        }

        .sidebar {
            width: 250px;
            background: linear-gradient(180deg, #061126, #020617);
            border-right: 1px solid rgba(59,130,246,0.25);
            padding: 22px 16px;
            position: fixed;
            height: 100vh;
            left: 0;
            top: 0;
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 32px;
        }

        .brand-icon {
            width: 42px;
            height: 42px;
            border-radius: 14px;
            background: linear-gradient(135deg, #2563eb, #06b6d4);
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 0 22px rgba(37,99,235,0.7);
        }

        .brand-title {
            font-weight: 800;
            line-height: 1.1;
        }

        .brand-subtitle {
            font-size: 12px;
            color: #94a3b8;
        }

        .menu-item {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 12px 14px;
            margin-bottom: 8px;
            color: #cbd5e1;
            border-radius: 12px;
            text-decoration: none;
            font-size: 14px;
            transition: .25s;
        }

        .menu-item:hover,
        .menu-item.active {
            background: linear-gradient(135deg, rgba(37,99,235,0.75), rgba(14,165,233,0.25));
            color: #fff;
            box-shadow: 0 0 18px rgba(37,99,235,0.35);
        }

        .sidebar-footer {
            position: absolute;
            bottom: 20px;
            left: 16px;
            right: 16px;
            padding: 14px;
            background: rgba(15,23,42,0.75);
            border: 1px solid rgba(148,163,184,0.14);
            border-radius: 16px;
            font-size: 12px;
            color: #94a3b8;
        }

        .main {
            margin-left: 250px;
            width: calc(100% - 250px);
            padding: 26px;
        }

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 22px;
        }

        .page-title {
            font-size: 26px;
            font-weight: 800;
            margin: 0;
        }

        .top-actions {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .circle-btn {
            width: 38px;
            height: 38px;
            border-radius: 50%;
            background: rgba(15,23,42,0.85);
            border: 1px solid rgba(148,163,184,0.18);
            color: #e2e8f0;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .admin-badge {
            display: flex;
            align-items: center;
            gap: 8px;
            color: #e2e8f0;
            font-size: 14px;
        }

        .avatar {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background: linear-gradient(135deg, #2563eb, #9333ea);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
        }

        .panel {
            background: rgba(15,23,42,0.82);
            border: 1px solid rgba(59,130,246,0.22);
            border-radius: 18px;
            box-shadow: 0 0 24px rgba(0,0,0,0.45);
            color: #e2e8f0;
        }

        .filters {
            padding: 16px;
            margin-bottom: 20px;
        }

        .form-label {
            color: #94a3b8;
            font-size: 12px;
            margin-bottom: 6px;
        }

        .form-select,
        .form-control {
            background: rgba(2,6,23,0.8);
            color: #e2e8f0;
            border: 1px solid rgba(148,163,184,0.20);
            border-radius: 11px;
            font-size: 14px;
        }

        .form-select option {
            background: #020617;
            color: #fff;
        }

        .form-select:focus,
        .form-control:focus {
            background: rgba(2,6,23,0.95);
            color: #fff;
            border-color: #3b82f6;
            box-shadow: 0 0 14px rgba(59,130,246,0.55);
        }

        .btn-neon {
            background: linear-gradient(135deg, #2563eb, #0ea5e9);
            border: none;
            color: white;
            border-radius: 11px;
            box-shadow: 0 0 18px rgba(37,99,235,0.45);
        }

        .kpi-card {
            padding: 16px;
            height: 112px;
            position: relative;
            overflow: hidden;
        }

        .kpi-card:hover {
            transform: translateY(-4px);
            transition: .25s;
            box-shadow: 0 0 22px rgba(59,130,246,0.45);
        }

        .kpi-title {
            color: #94a3b8;
            font-size: 13px;
        }

        .kpi-value {
            font-size: 28px;
            font-weight: 800;
            margin-top: 8px;
        }

        .kpi-icon {
            position: absolute;
            right: 16px;
            bottom: 16px;
            width: 42px;
            height: 42px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: rgba(37,99,235,0.18);
            color: #60a5fa;
            font-size: 22px;
        }

        .mini-insight {
            padding: 15px;
            height: 92px;
            border-left: 4px solid #3b82f6;
        }

        .mini-insight h6 {
            color: #94a3b8;
            font-size: 12px;
            margin-bottom: 8px;
        }

        .mini-insight h5 {
            font-size: 18px;
            margin: 0;
            font-weight: 700;
        }

        .chart-card {
            padding: 18px;
            height: 330px;
        }

        .chart-card canvas {
            max-height: 245px;
        }

        .section-title {
            font-size: 16px;
            font-weight: 700;
            margin-bottom: 14px;
        }

        .table {
            color: #dbeafe;
            font-size: 13px;
        }

        .table thead {
            background: rgba(37,99,235,0.22);
        }

        .table-primary {
            --bs-table-bg: rgba(37,99,235,0.28);
            --bs-table-color: white;
        }

        .table-hover tbody tr:hover {
            background: rgba(59,130,246,0.16);
            color: white;
        }

        .btn-export {
            border-radius: 11px;
            font-size: 14px;
        }

        .quick-actions {
            padding: 18px;
        }

        .quick-actions .btn {
            width: 100%;
            margin-bottom: 12px;
            border-radius: 12px;
        }

        .record-box {
            padding: 16px;
            margin-top: 16px;
        }

        .record-number {
            font-size: 26px;
            font-weight: 800;
        }


        .circle-btn {
            cursor: pointer;
        }

        body.light-mode {
            background: #f4f6f9;
            color: #111827;
        }

        body.light-mode .app {
            background:
                radial-gradient(circle at top right, rgba(37,99,235,0.08), transparent 35%),
                radial-gradient(circle at bottom left, rgba(14,165,233,0.08), transparent 35%),
                #f4f6f9;
        }

        body.light-mode .sidebar {
            background: linear-gradient(180deg, #ffffff, #e5e7eb);
            border-right: 1px solid #d1d5db;
        }

        body.light-mode .brand-title,
        body.light-mode .page-title,
        body.light-mode .section-title,
        body.light-mode h1,
        body.light-mode h2,
        body.light-mode h3,
        body.light-mode h4,
        body.light-mode h5,
        body.light-mode h6,
        body.light-mode .kpi-value,
        body.light-mode .record-number {
            color: #111827;
        }

        body.light-mode .brand-subtitle,
        body.light-mode .text-muted,
        body.light-mode .kpi-title,
        body.light-mode .mini-insight h6,
        body.light-mode .sidebar-footer {
            color: #6b7280 !important;
        }

        body.light-mode .menu-item {
            color: #374151;
        }

        body.light-mode .menu-item:hover,
        body.light-mode .menu-item.active {
            background: linear-gradient(135deg, #2563eb, #60a5fa);
            color: white;
            box-shadow: 0 0 16px rgba(37,99,235,0.25);
        }

        body.light-mode .panel,
        body.light-mode .circle-btn,
        body.light-mode .sidebar-footer {
            background: #ffffff;
            color: #111827;
            border: 1px solid #e5e7eb;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.08);
        }

        body.light-mode .form-label {
            color: #374151;
        }

        body.light-mode .form-select,
        body.light-mode .form-control {
            background: #ffffff;
            color: #111827;
            border: 1px solid #d1d5db;
        }

        body.light-mode .form-select option {
            background: #ffffff;
            color: #111827;
        }

        body.light-mode .admin-badge {
            color: #111827;
        }

        body.light-mode .table {
            color: #111827;
        }

        body.light-mode .table-primary {
            --bs-table-bg: #dbeafe;
            --bs-table-color: #111827;
        }

        body.light-mode .table-hover tbody tr:hover {
            background: #e0f2fe;
            color: #111827;
        }



        html {
            scroll-behavior: smooth;
            scroll-padding-top: 90px;
        }

        .section-flash {
            position: relative;
            animation: neonPulseDark 1.4s ease-in-out 2;
            border-color: rgba(59, 130, 246, 0.95) !important;
        }

        #seccionSolicitudes.section-flash {
            border-color: rgba(34, 197, 94, 0.95) !important;
            box-shadow: 0 0 24px rgba(34, 197, 94, 0.65), inset 0 0 18px rgba(34, 197, 94, 0.12);
            border-radius: 18px;
            padding: 6px;
        }

        #seccionNacionalidades.section-flash {
            box-shadow: 0 0 26px rgba(59, 130, 246, 0.75), inset 0 0 18px rgba(59, 130, 246, 0.15);
        }

        #seccionDepartamentos.section-flash {
            box-shadow: 0 0 26px rgba(34, 197, 94, 0.75), inset 0 0 18px rgba(34, 197, 94, 0.15);
            border-color: rgba(34, 197, 94, 0.95) !important;
        }

        #seccionReportes.section-flash {
            box-shadow: 0 0 26px rgba(168, 85, 247, 0.75), inset 0 0 18px rgba(168, 85, 247, 0.15);
            border-color: rgba(168, 85, 247, 0.95) !important;
        }

        #seccionExportaciones.section-flash {
            box-shadow: 0 0 26px rgba(245, 158, 11, 0.75), inset 0 0 18px rgba(245, 158, 11, 0.15);
            border-color: rgba(245, 158, 11, 0.95) !important;
        }

        #seccionConfiguracion.section-flash {
            box-shadow: 0 0 26px rgba(6, 182, 212, 0.75), inset 0 0 18px rgba(6, 182, 212, 0.15);
            border-color: rgba(6, 182, 212, 0.95) !important;
        }

        body.light-mode .section-flash {
            animation: neonPulseLight 1.4s ease-in-out 2;
            border-color: rgba(37, 99, 235, 0.95) !important;
        }

        body.light-mode #seccionSolicitudes.section-flash {
            border-color: rgba(22, 163, 74, 0.95) !important;
            box-shadow: 0 0 22px rgba(22, 163, 74, 0.45), inset 0 0 16px rgba(22, 163, 74, 0.10);
            border-radius: 18px;
            padding: 6px;
        }

        body.light-mode #seccionNacionalidades.section-flash {
            box-shadow: 0 0 22px rgba(37, 99, 235, 0.45), inset 0 0 16px rgba(37, 99, 235, 0.10);
        }

        body.light-mode #seccionDepartamentos.section-flash {
            box-shadow: 0 0 22px rgba(22, 163, 74, 0.45), inset 0 0 16px rgba(22, 163, 74, 0.10);
            border-color: rgba(22, 163, 74, 0.95) !important;
        }

        body.light-mode #seccionReportes.section-flash {
            box-shadow: 0 0 22px rgba(126, 34, 206, 0.42), inset 0 0 16px rgba(126, 34, 206, 0.10);
            border-color: rgba(126, 34, 206, 0.95) !important;
        }

        body.light-mode #seccionExportaciones.section-flash {
            box-shadow: 0 0 22px rgba(217, 119, 6, 0.45), inset 0 0 16px rgba(217, 119, 6, 0.10);
            border-color: rgba(217, 119, 6, 0.95) !important;
        }

        body.light-mode #seccionConfiguracion.section-flash {
            box-shadow: 0 0 22px rgba(8, 145, 178, 0.45), inset 0 0 16px rgba(8, 145, 178, 0.10);
            border-color: rgba(8, 145, 178, 0.95) !important;
        }

        @keyframes neonPulseDark {
            0% { filter: brightness(1); }
            50% { filter: brightness(1.25); }
            100% { filter: brightness(1); }
        }

        @keyframes neonPulseLight {
            0% { filter: brightness(1); }
            50% { filter: brightness(1.07); }
            100% { filter: brightness(1); }
        }


        @media (max-width: 992px) {
            .sidebar {
                display: none;
            }

            .main {
                margin-left: 0;
                width: 100%;
            }
        }
    </style>
</head>

<body>

<div class="app">

    <!-- SIDEBAR -->
    <aside class="sidebar">
        <div class="brand">
            <div class="brand-icon">
                <i class="bi bi-bar-chart-fill"></i>
            </div>
            <div>
                <div class="brand-title">MigraData</div>
                <div class="brand-subtitle">Perú</div>
            </div>
        </div>

        <a href="dashboard" class="menu-item active">
            <i class="bi bi-grid-1x2-fill"></i> Dashboard
        </a>

        <a href="#seccionSolicitudes" class="menu-item" onclick="resaltarSeccion('seccionSolicitudes')">
            <i class="bi bi-file-earmark-text"></i> Solicitudes
        </a>

        <a href="#seccionNacionalidades" class="menu-item" onclick="resaltarSeccion('seccionNacionalidades')">
            <i class="bi bi-globe-americas"></i> Nacionalidades
        </a>

        <a href="#seccionDepartamentos" class="menu-item" onclick="resaltarSeccion('seccionDepartamentos')">
            <i class="bi bi-geo-alt"></i> Departamentos
        </a>

        <a href="#seccionReportes" class="menu-item" onclick="resaltarSeccion('seccionReportes')">
            <i class="bi bi-table"></i> Reportes
        </a>

        <a href="#seccionExportaciones" class="menu-item" onclick="resaltarSeccion('seccionExportaciones')">
            <i class="bi bi-download"></i> Exportaciones
        </a>

        <a href="#seccionConfiguracion" class="menu-item" onclick="resaltarSeccion('seccionConfiguracion')">
            <i class="bi bi-gear"></i> Configuración
        </a>

        <div class="sidebar-footer">
            <div>Última actualización</div>
            <strong>Dataset 2025-08</strong>
        </div>
    </aside>

    <!-- MAIN -->
    <main class="main" id="dashboardPDF">

        <!-- TOPBAR -->
        <div class="topbar">
            <h1 class="page-title">Dashboard</h1>

            <div class="top-actions">
                <button type="button" class="circle-btn" onclick="toggleTheme()" id="themeButton">
                    <i class="bi bi-moon-stars" id="themeIcon"></i>
                </button>

                <div class="circle-btn">
                    <i class="bi bi-bell"></i>
                </div>

                <div class="admin-badge">
                    <div class="avatar">AD</div>
                    <span>Admin</span>
                </div>
            </div>
        </div>

        <!-- FILTROS -->
        <form action="dashboard" method="get" class="panel filters" id="seccionConfiguracion">
            <div class="row align-items-end">

                <div class="col-md-4">
                    <label class="form-label">Nacionalidad</label>
                    <select name="nacionalidad" class="form-select">
                        <option value="">Todas</option>

                        <%
                            if (listaNacionalidades != null) {
                                for (String nac : listaNacionalidades) {
                        %>
                        <option value="<%= nac %>"
                            <%= nac.equals(nacionalidadSeleccionada) ? "selected" : "" %>>
                            <%= nac %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Sexo</label>
                    <select name="sexo" class="form-select">
                        <option value="">Todos</option>

                        <%
                            if (listaSexos != null) {
                                for (String sx : listaSexos) {
                        %>
                        <option value="<%= sx %>"
                            <%= sx.equals(sexoSeleccionado) ? "selected" : "" %>>
                            <%= sx %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                
                <div class="col-md-3">
                    <label class="form-label">Año</label>

                    <select name="anio" class="form-select">

                        <option value="">Todos</option>

                        <%
                            List<String> listaAnios =
                                     (List<String>) request.getAttribute("listaAnios");

                            if (listaAnios != null) {

                                for (String item : listaAnios) {
                        %>

                        <option value="<%= item %>">
                                <%= item %>
                        </option>

                         <%
                                }
                            }
                        %>

                    </select>
                </div>

                        
                <div class="col-md-3">
                    <label class="form-label">Mes</label>

                    <select name="mes" class="form-select">

                        <option value="">Todos</option>

                        <%
                            List<String> listaMeses =
                                (List<String>) request.getAttribute("listaMeses");

                            if (listaMeses != null) {

                            for (String item : listaMeses) {
                        %>

                            <option value="<%= item %>">
                                <%= item %>
                            </option>

                        <%
                                }
                            }
                        %>

                    </select>
                </div>
                        
                <div class="col-md-2">
                    <button class="btn btn-neon w-100">
                        <i class="bi bi-funnel"></i> Filtrar
                    </button>
                </div>

            </div>
        </form>

        <!-- KPI CARDS -->
        <div class="row g-3 mb-3" id="seccionSolicitudes">

            <div class="col-md-3">
                <div class="panel kpi-card">
                    <div class="kpi-title">Total Solicitudes</div>
                    <div class="kpi-value">${totalSolicitudes}</div>
                    <small class="text-success">+12.5% vs mes anterior</small>
                    <div class="kpi-icon"><i class="bi bi-file-earmark-text"></i></div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="panel kpi-card">
                    <div class="kpi-title">Nacionalidades</div>
                    <div class="kpi-value">${totalNacionalidades}</div>
                    <small class="text-success">+3 nuevas</small>
                    <div class="kpi-icon"><i class="bi bi-globe-americas"></i></div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="panel kpi-card">
                    <div class="kpi-title">Cambios Migratorios</div>
                    <div class="kpi-value">${totalCambios}</div>
                    <small class="text-success">+8.2% vs mes anterior</small>
                    <div class="kpi-icon"><i class="bi bi-arrow-left-right"></i></div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="panel kpi-card">
                    <div class="kpi-title">Carnets Extranjería</div>
                    <div class="kpi-value">${totalCarnets}</div>
                    <small class="text-success">+11.7% vs mes anterior</small>
                    <div class="kpi-icon"><i class="bi bi-person-vcard"></i></div>
                </div>
            </div>

        </div>

        <!-- INSIGHTS -->
        <div class="row g-3 mb-3">

            <div class="col-md-4">
                <div class="panel mini-insight">
                    <h6><i class="bi bi-star-fill text-warning"></i> Nacionalidad principal</h6>
                    <h5>${nacionalidadTop}</h5>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel mini-insight" style="border-left-color:#ef4444;">
                    <h6><i class="bi bi-geo-alt-fill text-danger"></i> Departamento principal</h6>
                    <h5>${departamentoTop}</h5>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel mini-insight" style="border-left-color:#22c55e;">
                    <h6><i class="bi bi-person-fill text-primary"></i> Sexo predominante</h6>
                    <h5>${sexoTop}</h5>
                </div>
            </div>

        </div>

        <!-- CHARTS -->
        <div class="row g-3 mb-3">

            <div class="col-md-4">
                <div class="panel chart-card" id="seccionNacionalidades">
                    <div class="section-title">Solicitudes por Nacionalidad</div>
                    <canvas id="graficoNacionalidad"></canvas>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel chart-card">
                    <div class="section-title">Solicitudes por Sexo</div>
                    <canvas id="graficoSexo"></canvas>
                </div>
            </div>

            <div class="col-md-4">
                <div class="panel chart-card" id="seccionDepartamentos">
                    <div class="section-title">Solicitudes por Departamento</div>
                    <canvas id="graficoDepartamento"></canvas>
                </div>
            </div>

        </div>

        <!-- TABLE + ACTIONS -->
        <div class="row g-3 mb-4">

            <div class="col-md-9">
                <div class="panel p-3" id="seccionReportes">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="section-title mb-0">Detalle de Resultados</div>

                        <input type="text" id="buscadorTabla" class="form-control w-50"
                               placeholder="Buscar en la tabla..."
                               onkeyup="filtrarTabla()">
                    </div>

                    <div class="table-responsive">
                        <table class="table table-hover align-middle" id="tablaResultados">
                            <thead class="table-primary">
                                <tr>
                                    <th>Nacionalidad</th>
                                    <th>Sexo</th>
                                    <th>Departamento</th>
                                    <th>Cantidad</th>
                                </tr>
                            </thead>

                            <tbody>
                                <%
                                    if (tablaResultados != null) {
                                        for (Map<String, String> fila : tablaResultados) {
                                %>
                                <tr>
                                    <td><%= fila.get("nacionalidad") %></td>
                                    <td><%= fila.get("sexo") %></td>
                                    <td><%= fila.get("departamento") %></td>
                                    <td><strong><%= fila.get("cantidad") %></strong></td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <div class="panel quick-actions" id="seccionExportaciones">
                    <div class="section-title">Acciones rápidas</div>

                    <button type="button" class="btn btn-success btn-export" onclick="exportarCSV()">
                        <i class="bi bi-download"></i> Exportar CSV
                    </button>

                    <button type="button" class="btn btn-danger btn-export" onclick="exportarPDF()">
                        <i class="bi bi-file-earmark-pdf"></i> Exportar PDF
                    </button>

                    <div class="panel record-box">
                        <div class="text-muted small">Total registros</div>
                        <div class="record-number">
                            <%
                                int totalFilas = tablaResultados != null ? tablaResultados.size() : 0;
                            %>
                            <%= totalFilas %>
                        </div>
                        <div class="text-muted small">registros mostrados</div>
                    </div>
                </div>
            </div>

        </div>

    </main>

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>

<script>
const chartColors = [
    '#3b82f6',
    '#8b5cf6',
    '#22c55e',
    '#f59e0b',
    '#ef4444',
    '#06b6d4',
    '#ec4899',
    '#84cc16',
    '#f97316',
    '#14b8a6'
];

new Chart(document.getElementById('graficoNacionalidad'), {
    type:'bar',
    data:{
        labels:[<%= labelsNacionalidad.toString() %>],
        datasets:[{
            label:'Solicitudes',
            data:[<%= valoresNacionalidad.toString() %>],
            backgroundColor: chartColors,
            borderRadius:8
        }]
    },
    options:{
        responsive:true,
        maintainAspectRatio:false,
        plugins:{ legend:{ labels:{ color:'#e2e8f0' } } },
        scales:{
            x:{ ticks:{ color:'#cbd5e1', font:{size:10} }, grid:{ color:'rgba(148,163,184,0.10)' } },
            y:{ ticks:{ color:'#cbd5e1' }, grid:{ color:'rgba(148,163,184,0.10)' } }
        }
    }
});

new Chart(document.getElementById('graficoSexo'), {
    type:'doughnut',
    data:{
        labels:[<%= labelsSexo.toString() %>],
        datasets:[{
            label:'Cantidad',
            data:[<%= valoresSexo.toString() %>],
            backgroundColor:['#3b82f6','#ec4899','#22c55e'],
            borderColor:'#020617',
            borderWidth:2
        }]
    },
    options:{
        responsive:true,
        maintainAspectRatio:false,
        plugins:{ legend:{ labels:{ color:'#e2e8f0' } } }
    }
});

new Chart(document.getElementById('graficoDepartamento'), {
    type:'bar',
    data:{
        labels:[<%= labelsDepartamento.toString() %>],
        datasets:[{
            label:'Solicitudes',
            data:[<%= valoresDepartamento.toString() %>],
            backgroundColor:'#22c55e',
            borderRadius:8
        }]
    },
    options:{
        indexAxis:'y',
        responsive:true,
        maintainAspectRatio:false,
        plugins:{ legend:{ labels:{ color:'#e2e8f0' } } },
        scales:{
            x:{ ticks:{ color:'#cbd5e1' }, grid:{ color:'rgba(148,163,184,0.10)' } },
            y:{ ticks:{ color:'#cbd5e1', font:{size:10} }, grid:{ color:'rgba(148,163,184,0.10)' } }
        }
    }
});

function exportarCSV() {
    let tabla = document.querySelector("#tablaResultados");
    let filas = tabla.querySelectorAll("tr");
    let csv = [];

    filas.forEach(fila => {
        if (fila.style.display !== "none") {
            let columnas = fila.querySelectorAll("th, td");
            let datos = [];

            columnas.forEach(columna => {
                datos.push('"' + columna.innerText.replace(/"/g, '""') + '"');
            });

            csv.push(datos.join(","));
        }
    });

    let contenido = csv.join("\n");
    let blob = new Blob([contenido], { type: "text/csv;charset=utf-8;" });

    let enlace = document.createElement("a");
    enlace.href = URL.createObjectURL(blob);
    enlace.download = "reporte_migradata.csv";
    enlace.click();
}

async function exportarPDF() {
    const { jsPDF } = window.jspdf;
    const dashboard = document.getElementById("dashboardPDF");

    const canvas = await html2canvas(dashboard, {
        scale: 2,
        useCORS: true
    });

    const imgData = canvas.toDataURL("image/png");
    const pdf = new jsPDF("p", "mm", "a4");

    const pageWidth = pdf.internal.pageSize.getWidth();
    const pageHeight = pdf.internal.pageSize.getHeight();

    const imgWidth = pageWidth;
    const imgHeight = canvas.height * imgWidth / canvas.width;

    let heightLeft = imgHeight;
    let position = 0;

    pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
    heightLeft -= pageHeight;

    while (heightLeft > 0) {
        position = heightLeft - imgHeight;
        pdf.addPage();
        pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
    }

    pdf.save("reporte_migradata.pdf");
}

function filtrarTabla() {
    let input = document.getElementById("buscadorTabla").value.toLowerCase();
    let filas = document.querySelectorAll("#tablaResultados tbody tr");

    filas.forEach(fila => {
        let texto = fila.innerText.toLowerCase();

        if (texto.includes(input)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
}


function resaltarSeccion(id) {
    const seccion = document.getElementById(id);

    if (!seccion) {
        return;
    }

    seccion.scrollIntoView({
        behavior: "smooth",
        block: "center"
    });

    seccion.classList.remove("section-flash");

    void seccion.offsetWidth;

    setTimeout(() => {
        seccion.classList.add("section-flash");
    }, 120);

    setTimeout(() => {
        seccion.classList.remove("section-flash");
    }, 3200);
}

function toggleTheme() {
    const body = document.body;
    const icon = document.getElementById("themeIcon");

    body.classList.toggle("light-mode");

    if (body.classList.contains("light-mode")) {
        icon.className = "bi bi-sun-fill";
        localStorage.setItem("theme", "light");
    } else {
        icon.className = "bi bi-moon-stars";
        localStorage.setItem("theme", "dark");
    }
}

window.addEventListener("load", function () {
    const savedTheme = localStorage.getItem("theme");
    const icon = document.getElementById("themeIcon");

    if (savedTheme === "light") {
        document.body.classList.add("light-mode");
        if (icon) {
            icon.className = "bi bi-sun-fill";
        }
    }
});
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>