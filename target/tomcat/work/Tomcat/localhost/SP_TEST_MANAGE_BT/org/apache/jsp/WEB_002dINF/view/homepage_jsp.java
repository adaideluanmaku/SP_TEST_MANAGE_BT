/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-12-14 09:58:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.view;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class homepage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("\r\n");
      out.write("<title>CH+ | HOME</title>\r\n");
      out.write("<!-- SYS CSS -->\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/bootstrap-3.3.7-dist/css/bootstrap.css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/font-awesome/css/font-awesome.css\" rel=\"stylesheet\">\r\n");
      out.write("<!-- Gritter -->\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/css/animate.css\" rel=\"stylesheet\">\r\n");
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
      out.write("<!-- Mainly scripts -->\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/metisMenu/jquery.metisMenu.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/slimscroll/jquery.slimscroll.min.js\"></script>\r\n");
      out.write("<!-- Custom and plugin javascript -->\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/inspinia.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap_home/js/plugins/pace/pace.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- MY JS -->\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chjs/homepage.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chjs/bootstrap-loading.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<title>TEST_MANAGE</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<input id=\"addurl\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\t<input id=\"userid\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.user.userid }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\t<input id=\"loginname\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginname}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("\t\r\n");
      out.write("\t<nav class=\"navbar-default navbar-static-side\" >\r\n");
      out.write("\t\t<div class=\"row\" style=\"width:235px;height:100px;margin-top:30px\">\r\n");
      out.write("\t\t\t<div class=\"col-sm-4\"></div>\r\n");
      out.write("\t\t\t<div class=\"col-sm-4\">\r\n");
      out.write("\t\t\t\t<span><img alt=\"image\" class=\"img-circle\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/chimg/hxj-small.png\" /></span> \r\n");
      out.write("\t\t\t\t<a data-toggle=\"dropdown\" class=\"dropdown-toggle\" href=\"#\"> \r\n");
      out.write("\t\t\t\t\t<span class=\"clear\"> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"block m-t-xs\"> <strong class=\"font-bold\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginname }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</strong></span> \r\n");
      out.write("\t\t\t\t\t\t<!-- <span class=\"text-muted text-xs block\">快捷操作<b class=\"caret\"></b></span> -->\r\n");
      out.write("\t\t\t\t\t</span>\t\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-sm-4\"></div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"row\" style=\"width:235px;\">\r\n");
      out.write("\t\t\t<ul class=\"nav metismenu\" id=\"side-menu\">\r\n");
      out.write("\t\t\t\t<li class=\"active\">\r\n");
      out.write("\t\t\t\t\t<a href=\"java\tscript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">统计管理</span> <span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level\">\r\n");
      out.write("\t\t\t\t\t\t<li class=\"active\"><a href=\"javascript:void(0);\" onclick=\"home_graph()\">总览</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">PASS测试管理</span> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pass_graph()\">PASS统计</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pass_team()\">团队管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pass_project()\">项目管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pass_testmng()\">案例管理</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\">\r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">PA测试管理</span> <span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pa_graph()\">PA统计</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pa_team()\">团队管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pa_project()\">项目管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pa_testmng()\">案例管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"pa_tools()\">工具箱</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\">\r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">用药研究</span> <span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"prescription()\">处方管理</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"\"javascript:void(0)> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">其他项目测试管理</span> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript: void(0);\" onclick=\"zfxm_graph()\">项目统计</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript: void(0)\" onclick=\"zfxm_team()\">团队管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript: void(0)\" onclick=\"zfxm_project()\">项目管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript: void(0)\" onclick=\"zfxm_testmng()\">案例管理</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">学习记录</span>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"learn_graph()\">学习统计</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\">学习分类</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\">学习笔记</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\">待续</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">工作计划</span> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"works_graph()\">工作统计</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\">计划管理</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"works_zhouhuibao()\">周汇报</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0);\" onclick=\"works_yuehuibao()\">月汇报</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">系统管理</span> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0)\" onclick=\"sysmanage_users()\">用户信息</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0)\" onclick=\"sysmanage_serverip()\">访问地址</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0)\" onclick=\"sysmanage_database()\">数据库维护</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:void(0)\">待续</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"javascript:void(0)\"> \r\n");
      out.write("\t\t\t\t\t\t<i class=\"fa fa-th-large\"></i>\r\n");
      out.write("\t\t\t\t\t\t<span class=\"nav-label\">使用工具集</span> \r\n");
      out.write("\t\t\t\t\t\t<span class=\"fa arrow\"></span>\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t<ul class=\"nav nav-second-level collapse\">\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:alert(1)\">工具1</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:alert(1)\">工具2</a></li>\r\n");
      out.write("\t\t\t\t\t\t<li><a href=\"javascript:alert(1)\">待续</a></li>\r\n");
      out.write("\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</nav>\r\n");
      out.write("\t\r\n");
      out.write("\t<div id=\"page-wrapper\" class=\"gray-bg\">\r\n");
      out.write("\t\t<div class=\"row border-bottom\" id=\"bookheader\">\r\n");
      out.write("\t\t\t<nav class=\"navbar navbar-static-top\" role=\"navigation\" style=\"margin-bottom: 0\">\r\n");
      out.write("\t\t\t\t<div class=\"navbar-header\">\r\n");
      out.write("\t\t\t\t\t<a class=\"navbar-minimalize minimalize-styl-2 btn btn-primary \" href=\"#\"><i class=\"fa fa-bars\"></i> </a>\r\n");
      out.write("\t\t\t\t\t<form role=\"search\" class=\"navbar-form-custom\" action=\"javascript:void(0)\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" placeholder=\"Search for something...\"\r\n");
      out.write("\t\t\t\t\t\t\t\tclass=\"form-control\" name=\"top-search\" id=\"top-search\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<ul class=\"nav navbar-top-links navbar-right\">\r\n");
      out.write("\t\t\t\t\t<li><span class=\"m-r-sm text-muted welcome-message\">Welcome to INSPINIA+ Admin Theme.</span></li>\r\n");
      out.write("\t\t\t\t\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/login/logout\"> <i class=\"fa fa-sign-out\"></i> Log out </a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</nav>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"row  border-bottom white-bg dashboard-header\"  id=\"bookcenter\">\r\n");
      out.write("\t\t\t<iframe id=\"iframe_box\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/homepage/home_graph\" frameborder=\"0\" scrolling=\"yes\"\r\n");
      out.write("\t\t\t\t></iframe>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"footer navbar-fixed-bottom\" id=\"bookfooter\">\r\n");
      out.write("\t\t\t<div class=\"pull-right\"> 10GB of <strong>250GB</strong> Free. </div>\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t<strong>Copyright</strong> Example Company &copy; 2014-2015\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
