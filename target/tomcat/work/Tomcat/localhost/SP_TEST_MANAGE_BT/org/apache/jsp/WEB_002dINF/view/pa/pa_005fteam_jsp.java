/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-02-14 06:51:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.view.pa;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class pa_005fteam_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"utf-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("\r\n");
      out.write("<title>INSPINIA | team</title>\r\n");
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
      out.write("\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-table/bootstrap-table.js\"></script>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-table/bootstrap-table.css\" rel=\"stylesheet\" />\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-table/bootstrap-table-zh-CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.css\" rel=\"stylesheet\" >\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-dialog/bootstrap-dialog.min.js\"></script>\r\n");
      out.write("    \r\n");
      out.write("<!-- MY JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chjs/pa/pa_team.js\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"background-color: #f3f3f4\">\r\n");
      out.write("<input id=\"addurl\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<div id=\"wrapper\" >\r\n");
      out.write("\t<div  class=\"gray-bg\">\r\n");
      out.write("\t\t<div class=\"row wrapper border-bottom white-bg page-heading\">\r\n");
      out.write("\t\t\t<div class=\"col-lg-10\">\r\n");
      out.write("\t\t\t\t<ol class=\"breadcrumb\">\r\n");
      out.write("\t\t\t\t\t<li><a>home</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a>PA测试管理</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"active\"><strong>团队管理</strong></li>\r\n");
      out.write("\t\t\t\t</ol>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"animated fadeInRight\">\r\n");
      out.write("\t\t\t<div id=\"table\">\r\n");
      out.write("\t\t\t\t<!-- 列表表格-开始制作 -->\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" style=\"padding-bottom:0px;\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-default\">\r\n");
      out.write("\t\t\t\t\t    <div class=\"panel-heading\">查询条件</div>\r\n");
      out.write("\t\t\t\t\t    <div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t        <form id=\"formSearch\" class=\"form-horizontal\">\r\n");
      out.write("\t\t\t\t\t            <div class=\"form-group\" style=\"margin-top:15px\">\r\n");
      out.write("\t\t\t\t\t                <label class=\"control-label col-sm-1\" for=\"txt_search_departmentname\">部门名称</label>\r\n");
      out.write("\t\t\t\t\t                <div class=\"col-sm-3\">\r\n");
      out.write("\t\t\t\t\t                    <input type=\"text\" class=\"form-control\" id=\"txt_search_departmentname\">\r\n");
      out.write("\t\t\t\t\t                </div>\r\n");
      out.write("\t\t\t\t\t                <label class=\"control-label col-sm-1\" for=\"txt_search_statu\">状态</label>\r\n");
      out.write("\t\t\t\t\t                <div class=\"col-sm-3\">\r\n");
      out.write("\t\t\t\t\t                    <input type=\"text\" class=\"form-control\" id=\"txt_search_statu\">\r\n");
      out.write("\t\t\t\t\t                </div>\r\n");
      out.write("\t\t\t\t\t                <div class=\"col-sm-2\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t\t\t                    <button type=\"button\" id=\"btn_query\" class=\"btn btn-primary\" >查询</button>\r\n");
      out.write("\t\t\t\t\t                </div>\r\n");
      out.write("\t\t\t\t\t            </div>\r\n");
      out.write("\t\t\t\t\t        </form>\r\n");
      out.write("\t\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t\t</div>  \r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div id=\"toolbar\" class=\"btn-group\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-12\">\r\n");
      out.write("\t\t\t\t\t\t\t<button id=\"btn_add\" type=\"button\" class=\"btn btn-default\">\r\n");
      out.write("\t\t\t\t\t        \t<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>新增\r\n");
      out.write("\t\t\t\t\t\t    </button>\r\n");
      out.write("\t\t\t\t\t\t    <button id=\"btn_edit\" type=\"button\" class=\"btn btn-default\">\r\n");
      out.write("\t\t\t\t\t\t        <span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>修改\r\n");
      out.write("\t\t\t\t\t\t    </button>\r\n");
      out.write("\t\t\t\t\t\t    <button id=\"btn_delete\" type=\"button\" class=\"btn btn-default\">\r\n");
      out.write("\t\t\t\t\t\t        <span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>删除\r\n");
      out.write("\t\t\t\t\t\t    </button>\r\n");
      out.write("\t\t\t\t\t\t    <div  class=\"btn-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<button id=\"dropdownMenu1\" type=\"button\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\taria-haspopup=\"true\" aria-expanded=\"true\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>工具\r\n");
      out.write("\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t<ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu1\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"#\">Action</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"#\">Another action</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"#\">Something else here</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li role=\"separator\" class=\"divider\"></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<li><a href=\"#\">Separated link</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<!-- 增加表格样式：style=\"table-layout: fixed\"时，设置列宽才能生效 -->\r\n");
      out.write("\t\t\t\t\t<table id=\"tb_departments\" style=\"table-layout:fixed; \"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<!-- dialog -->\r\n");
      out.write("\t\t\t\t<!-- 模态框（Modal） -->\r\n");
      out.write("\t\t\t\t<div class=\"hide\" id=\"myModal\" >\r\n");
      out.write("\t\t\t\t\t<form role=\"form\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"name\">名称</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"请输入名称\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<label for=\"inputfile\">文件输入</label>\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"file\" id=\"inputfile\">\r\n");
      out.write("\t\t\t\t\t\t\t<p class=\"help-block\">这里是块级帮助文本的实例。</p>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"checkbox\">\r\n");
      out.write("\t\t\t\t\t\t\t<label>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"checkbox\"> 请打勾\r\n");
      out.write("\t\t\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<button type=\"submit\" class=\"btn btn-default\">提交</button>\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div> \r\n");
      out.write("\t\t\t\t<!-- 数据表格-结束 -->\r\n");
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
