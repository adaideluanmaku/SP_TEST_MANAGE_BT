/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-04-08 05:42:26 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.view.pass;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class pass_005fproject_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>INSPINIA | project</title>\r\n");
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
      out.write("<!-- bootstrapValidator -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrapValidator/css/bootstrapValidator.css\"/>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrapValidator/js/bootstrapValidator.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrapValidator/js/language/zh_CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Sweet alert -->\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/css/sweetalert.css\" rel=\"stylesheet\">\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/sweetalert/sweetalert.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- select2 -->\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-select2/css/select2.css\" rel=\"stylesheet\" />\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-select2/js/select2.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-select2/js/i18n/zh-CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- MY JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chjs/pass/pass_project.js\"></script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body style=\"background-color: #ffffff;\">\r\n");
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
      out.write("\t\t\t\t\t<li><a>PASS测试管理</a></li>\r\n");
      out.write("\t\t\t\t\t<li class=\"active\"><strong>项目管理</strong></li>\r\n");
      out.write("\t\t\t\t</ol>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-lg-2\"></div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"animated fadeInRight\">\r\n");
      out.write("\t\t\t<div id=\"table_div\">\r\n");
      out.write("\t\t\t\t<!-- 列表表格-开始制作 -->\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" style=\"padding-bottom:0px;\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-default\">\r\n");
      out.write("\t\t\t\t\t    <div class=\"panel-heading\">查询条件</div>\r\n");
      out.write("\t\t\t\t\t    <div class=\"panel-body\">\r\n");
      out.write("\t\t\t                <label class=\"control-label col-sm-1\" for=\"txt_search_departmentname\" style=\"padding-top:7px\">项目名称</label>\r\n");
      out.write("\t\t\t                <div class=\"col-sm-3\">\r\n");
      out.write("\t\t\t                    <input type=\"text\" class=\"form-control\" id=\"projectname\">\r\n");
      out.write("\t\t\t                </div>\r\n");
      out.write("\t\t\t                <div class=\"col-sm-2\" style=\"text-align: left;\">\r\n");
      out.write("\t\t\t                    <button type=\"button\" id=\"btn_query\" class=\"btn btn-primary\" onclick=\"table_search()\">查询</button>\r\n");
      out.write("\t\t\t                </div>\r\n");
      out.write("\t\t\t\t\t    </div>\r\n");
      out.write("\t\t\t\t\t</div>  \r\n");
      out.write("\t\t\t\t\t<div id=\"toolbar\" class=\"btn-group\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-12\">\r\n");
      out.write("\t\t\t\t\t\t\t<button id=\"btn_add\" type=\"button\" class=\"btn btn-default\" onclick=\"open_dialog()\">\r\n");
      out.write("\t\t\t\t\t        \t<span class=\"glyphicon glyphicon-plus\" aria-hidden=\"true\"></span>新增\r\n");
      out.write("\t\t\t\t\t\t    </button>\r\n");
      out.write("\t\t\t\t\t\t    <button id=\"btn_edit\" type=\"button\" class=\"btn btn-default\" onclick=\"edit_dialog()\">\r\n");
      out.write("\t\t\t\t\t\t        <span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span>修改\r\n");
      out.write("\t\t\t\t\t\t    </button>\r\n");
      out.write("\t\t\t\t\t\t    <button id=\"btn_delete\" type=\"button\" class=\"btn btn-default\" onclick=\"del_data()\">\r\n");
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
      out.write("\t\t\t\t\t<table id=\"table_data\" style=\"table-layout:fixed; \"></table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<!-- 数据表格-结束 -->\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<!-- 模态框（Modal） -->\r\n");
      out.write("<div class=\"modal fade\" id=\"modal_dialog\" tabindex=\"-1\" data-backdrop=\"static\" data-keyboard=\"false\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" height=\"600px\">\r\n");
      out.write("\t<div class=\"modal-dialog\">\r\n");
      out.write("\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\r\n");
      out.write("\t\t\t\t<h4 class=\"modal-title\">团队维护</h4>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"modal-body\" style=\"height:400px;overflow:auto\">\r\n");
      out.write("\t\t\t\t<form class=\"form-horizontal\" role=\"form\" id=\"dialog_form\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"projectid\" name=\"projectid\" value=\"\">\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t            <label for=\"teamid\" class=\"col-sm-2 control-label\">团队名称</label>\r\n");
      out.write("\t\t\t            <div class=\"col-sm-4\">\r\n");
      out.write("\t\t\t            \t<select id='teamid' name=\"teamid\" class=\"js-data-example-ajax\" style=\"width:100%\"></select>\r\n");
      out.write("\t\t\t            </div>\r\n");
      out.write("\t\t\t        </div>\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t<label for=\"projectname\" class=\"col-sm-2 control-label\">项目名称</label>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-4\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"projectname\"  name=\"projectname\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t<label for=\"remark\" class=\"col-sm-2 control-label\">备注</label>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-sm-10\">\r\n");
      out.write("\t\t\t\t\t\t\t<textarea class=\"col-sm-12\" rows=\"10\" id=\"remark\" name=\"remark\"></textarea>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" id=\"btn_add\" onclick=\"add_data()\">确定</button>\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" id=\"btn_update\" onclick=\"update_data()\">更新</button>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div><!-- /.modal-content -->\r\n");
      out.write("\t</div><!-- /.modal -->\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
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
