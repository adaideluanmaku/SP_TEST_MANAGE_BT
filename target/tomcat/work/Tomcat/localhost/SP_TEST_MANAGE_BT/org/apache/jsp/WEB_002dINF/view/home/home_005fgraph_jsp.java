/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-09-14 06:36:38 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.view.home;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class home_005fgraph_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" >\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("\r\n");
      out.write("<title>CH+ | GRAPH</title>\r\n");
      out.write("<!-- SYS CSS -->\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/css/style.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("<!-- SYS JS -->\r\n");
      out.write("<!-- 引入 Bootstrap -->\r\n");
      out.write("<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/jquery-3.2.1.min.js\"></script>\r\n");
      out.write("<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-3.3.7-dist/js/bootstrap.min.js\"></script>\r\n");
      out.write("<!-- Flot -->\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/flot/jquery.flot.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/flot/jquery.flot.tooltip.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/flot/jquery.flot.resize.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/flot/jquery.flot.pie.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/flot/jquery.flot.time.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- MY JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chjs/home/home_graph.js\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"background-color: #f3f3f4\">\r\n");
      out.write("<div >\r\n");
      out.write("\t<div  class=\"gray-bg\">\r\n");
      out.write("\t\t<div class=\"row wrapper border-bottom white-bg page-heading\">\r\n");
      out.write("\t\t\t<div class=\"col-lg-10\">\r\n");
      out.write("\t\t\t\t<ol class=\"breadcrumb\">\r\n");
      out.write("\t\t\t\t\t<li><a>home</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a>统计管理</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"active\"><strong>总览</strong></li>\r\n");
      out.write("\t\t\t\t</ol>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"wrapper wrapper-content animated fadeInRight\">\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-6\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ibox float-e-margins\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-title\">\r\n");
      out.write("\t\t\t\t\t\t\t<h5>柱状图 </h5>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ibox-tools\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"collapse-link\"> <i class=\"fa fa-chevron-up\"></i></a> \r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle\" data-toggle=\"dropdown\"href=\"graph_flot.html#\"> <i class=\"fa fa-wrench\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 1</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 2</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"close-link\"> <i class=\"fa fa-times\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-content\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"flot-chart\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"flot-chart-content\" id=\"flot-bar-chart\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-6\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ibox float-e-margins\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-title\">\r\n");
      out.write("\t\t\t\t\t\t\t<h5>线型图</h5>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ibox-tools\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"collapse-link\"> <i class=\"fa fa-chevron-up\"></i></a> \r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"graph_flot.html#\"> <i class=\"fa fa-wrench\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 1</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 2</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"close-link\"> <i class=\"fa fa-times\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-content\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"flot-chart\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"flot-chart-content\" id=\"flot-line-chart\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-6\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ibox float-e-margins\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-title\">\r\n");
      out.write("\t\t\t\t\t\t\t<h5>饼状图</h5>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ibox-tools\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"collapse-link\"> <i class=\"fa fa-chevron-up\"></i></a> \r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"graph_flot.html#\"><i class=\"fa fa-wrench\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 1</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 2</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"close-link\"> <i class=\"fa fa-times\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-content\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"flot-chart\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"flot-chart-pie-content\" id=\"flot-pie-chart\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-6\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ibox float-e-margins\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-title\">\r\n");
      out.write("\t\t\t\t\t\t\t<h5>实时监控</h5>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ibox-tools\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"collapse-link\"> <i class=\"fa fa-chevron-up\"></i></a> \r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"graph_flot.html#\"> <i class=\"fa fa-wrench\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 1</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 2</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"close-link\"> <i class=\"fa fa-times\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-content\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"flot-chart\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"flot-chart-content\" id=\"flot-line-chart-moving\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t<div class=\"col-sm-12\">\r\n");
      out.write("\t\t\t\t\t<div class=\"ibox float-e-margins\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-title\">\r\n");
      out.write("\t\t\t\t\t\t\t<h5>历史统计</h5>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"ibox-tools\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"collapse-link\"> <i class=\"fa fa-chevron-up\"></i></a> \r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"graph_flot.html#\"> <i class=\"fa fa-wrench\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu dropdown-user\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 1</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"graph_flot.html#\">Config option 2</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t\t<a class=\"close-link\"> <i class=\"fa fa-times\"></i></a>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ibox-content\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"flot-chart\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"flot-chart-content\" id=\"flot-line-chart-multi\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
