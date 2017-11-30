<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
    <!-- 导入easyui类库 -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/css/default.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
    <script
            src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>
    <script type="text/javascript">

        function doAdd(){
            $('#addStaffWindow').window("open");
        }

        function doExport() {

            var c = $("#company").val();


            if (c == null || c =="") {

               alert("请输入要导出的公司名称");

               return;
           }

            var n = $("#name").val();

            window.location.href="${pageContext.request.contextPath}/staffAction_pageQuery.action?company="+
                encodeURI(encodeURI(c)) +"&export=1&name=" +encodeURI(encodeURI(n))+"&sid="+$("#sid").val()+"&beginTime="+
                $("#beginTime").val()+"&endTime="+$("#endTime").val()+"&isOutage="+$("#isOutage").val()+"&isNoPm="+
                $("#isNoPm").val()+"&isFire="+$("#isFire").val()+"&isContine="+$("#isContine").val()+"&needNotice="+
                $("#needNotice").val();

        }

        function doSearch() {
            $("#export").val("0");
            var p = $("#searchForm").serializeJson();//{id:xx,name:yy,age:zz}
            //重新发起ajax请求，提交参数
            $("#grid").datagrid("load", p);
        }

        //扩展校验规则
        $(function(){
            var reg = /[0-9]{18}$/;
            $.extend($.fn.validatebox.defaults.rules, {
                sidnum: {
                    validator: function(value, param){
                        return reg.test(value);
                    },
                    message: '身份证号输入有误！'
                }
            });
        });

        //批量删除取派员
        function doDelete() {
            //获得选中的行
            var rows = $("#grid").datagrid("getSelections");
            if (rows.length == 0) {
                //没有选中，提示
                $.messager.alert("提示信息", "请选择需要删除的记录！", "warning");
            } else {
                var array = new Array();
                //选中了记录,获取选中行的id
                for (var i = 0; i < rows.length; i++) {
                    var id = rows[i].id;
                    array.push(id);
                }
                var ids = array.join(",");
                //发送请求，传递ids参数
                window.location.href = '${pageContext.request.contextPath}/staffAction_delete.action?ids=' + ids;
            }
        }


        //工具栏
        var toolbar = [{
            id: 'button-view',
            text: '查询',
            iconCls: 'icon-search',
            handler: doSearch
        },{
            id: 'button-add',
            text: '新增',
            iconCls: 'icon-add',
            handler: doAdd
        },
            {
                id: 'button-import',
                text: '导入',
                iconCls: 'icon-redo'
            },
            {
                id: 'button-export',
                text: '导出',
                iconCls: 'icon-undo',
                handler: doExport
            },


            {
                id: 'button-delete',
                text: '删除',
                iconCls: 'icon-cancel',
                handler: doDelete
            }


        ];
        // 定义列
        var columns = [[{
            field: 'id',
            checkbox: true,
        },
            {
                field: 'company',
                title: '公司',
                width: 180,
                align: 'center'
            },
            {
                field: 'name',
                title: '姓名',
                width: 100,
                align: 'center'
            },
            {
                field: 'sex',
                title: '性别',
                width: 40,
                align: 'center'

            }, {
                field: 'sid',
                title: '身份证号',
                width: 150,
                align: 'center'

            }, {
                field: 'age',
                title: '年龄',
                width: 40,
                align: 'center'
            },
            /*{
                field: 'job',
                title: '职务',
                width: 120,
                align: 'center'
            },
            {
                field: 'salary',
                title: '工资',
                width: 60,
                align: 'center'
            }, {
                field: 'dateSalary',
                title: '工龄工资',
                width: 60,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == 0) {
                        return "";
                    }
                }
            }
            , {
                field: 'persionalMoney',
                title: '个人',
                width: 60,
                align: 'center'
            }, {
                field: 'publicMoney',
                title: '公积金',
                width: 60,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == 0) {
                        return "";
                    }
                }
            }, {
                field: 'pay',
                title: '实发工资',
                width: 60,
                align: 'center'
            },

            {
                field: 'cardNumber',
                title: '卡号',
                width: 160,
                align: 'center'
            }, {
                field: 'bank',
                title: '开户银行',
                width: 60,
                align: 'center'
            }, */

            {
                field: 'term',
                title: '合同期限',
                width: 150,
                align: 'center'
            }, {
                field: 'phone',
                title: '联系方式',
                width: 120,
                align: 'center',

            }, {
                field: 'register',
                title: '户口',
                width: 40,
                align: 'center'
            }, {
                field: 'remark',
                title: '备注',
                width: 120,
                align: 'center'
            }, {
                field: 'isOutage',
                title: '超龄',
                width: 50,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == "1") {
                        return "是";
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'isNoPm',
                title: '单位五险',
                width: 60,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == "1") {
                        return "是";
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'isFire',
                title: '解雇',
                width: 50,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == "1") {
                        return "是";
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'isContine',
                title: '续签',
                width: 50,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == "1") {
                        return "是";
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'needNotice',
                title: '到期',
                width: 50,
                align: 'center',
                formatter: function (data, row, index) {
                    if (data == "1") {
                        return "是";
                    } else {
                        return "";
                    }
                }
            }


        ]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            // 取派员信息表格
            $('#grid').datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: false,
                rownumbers: true,//显示行号
                striped: true,
                pageList: [20, 50, 100],
                pagination: true,
                toolbar: toolbar,//工具栏
                url: "${pageContext.request.contextPath}/staffAction_pageQuery.action",
                idField: 'id',
                columns: columns,
                onDblClickRow: doDblClickRow//指定数据表格的双击行事件
            });

            // 添加取派员窗口
            $('#addStaffWindow').window({
                title: '添加取派员',
                width: 820,
                modal: true,//遮罩效果
                shadow: true,//阴影效果
                closed: true,//关闭状态
                height: 500,
                resizable: false//是否可以调整大小
            });

            // 修改取派员窗口
            $('#editStaffWindow').window({
                title: '修改取派员',
                width: 820,
                modal: true,//遮罩效果
                shadow: true,//阴影效果
                closed: true,//关闭状态
                height: 500,
                resizable: false//是否可以调整大小
            });

            // 查询分区
            $('#searchWindow').window({
                title: '查询分区',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 480,
                resizable: false
            });

        });

        //双击事件处理函数
        function doDblClickRow(rowIndex, rowData) {//{id:xxx,name:xx,}
            $('#editStaffWindow').window("open");//打开修改窗口
            $("#editStaffForm").form("load", rowData);
        }

        //扩展校验规则
        $(function () {
            var reg = /[0-9]{18}$/;
            $.extend($.fn.validatebox.defaults.rules, {
                sid: {
                    validator: function (value, param) {
                        return reg.test(value);
                    },
                    message: '身份证位数错误！'
                }
            });
        });
    </script>
    <script type="text/javascript">
        $(function () {
            $("#button-import").upload({

                action: '${pageContext.request.contextPath}/staffAction_importXls.action',
                name: 'myFile',
                onComplete: function (data) {
                    if (data == '1') {

                        //上传成功
                        $.messager.alert("提示信息", "导入成功！", "info");

                    } else {

                        //失败
                        $.messager.alert("提示信息", data, "warning");

                    }
                }
            });
        });
    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">

<div region="center" border="true" >
  <div style="width: 100%">
        <form id="searchForm">
            <table class="table-edit" align="center">


                <tr>
                    <td>公司</td>
                    <td><input type="text" id="company" name="company"/>
                        <input id="export" type="hidden" name="export"  value="0"/></td>


                    <td>姓名</td>
                    <td><input type="text" name="name" id="name"/></td>


                    <td>身份证号</td>
                    <td><input type="text" name="sid" id="sid"/></td>


                    <td>合同开始时间</td>
                    <td><input type="text" name="beginTime"  id="beginTime" class="easyui-datebox"/></td>


                    <td>合同结束时间</td>
                    <td><input type="text" name="endTime" id="endTime" class="easyui-datebox"/></td>
                </tr>
                <tr>
                    <td>是否超龄</td>
                    <td>
                        <select id="isOutage" name="isOutage">
                            <option value=""></option>
                            <option value="1">是</option>
                        </select>
                    </td>

                    <td>单位交个人五险</td>
                    <td>
                        <select name="isNoPm" id="isNoPm">
                            <option value=""></option>
                            <option value="1">是</option>
                        </select>
                    </td>

                    <td>已解雇</td>
                    <td>
                        <select name="isFire" id="isFire">

                            <option value=""></option>
                            <option value="1">是</option>
                        </select>
                    </td>

                    <td>续签合同</td>
                    <td>
                        <select name="isContine" id="isContine">
                            <option value=""></option>
                            <option value="1">是</option>
                        </select>
                    </td>

                    <td>即将到期</td>
                    <td>
                        <select name="needNotice" id="needNotice">
                            <option value=""></option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>


                <script>
                    $(function () {
                        //工具方法，可以将指定的表单中的输入项目序列号为json数据
                        $.fn.serializeJson = function () {
                            var serializeObj = {};
                            var array = this.serializeArray();
                            $(array).each(function () {
                                if (serializeObj[this.name]) {
                                    if ($.isArray(serializeObj[this.name])) {
                                        serializeObj[this.name].push(this.value);
                                    } else {
                                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                                    }
                                } else {
                                    serializeObj[this.name] = this.value;
                                }
                            });
                            return serializeObj;
                        };

                    });
                </script>

            </table>
        </form>
    </div>
    <div style="height: 600px">
    <table id="grid"></table>
    </div>
</div>


<!-- 修改窗口 -->
<div class="easyui-window" title="修改员工" id="editStaffWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
            <script type="text/javascript">
                $(function () {
                    //绑定事件
                    $("#edit").click(function () {
                        //校验表单输入项
                        var v = $("#editStaffForm").form("validate");
                        if (v) {
                            //校验通过，提交表单
                            $("#editStaffForm").submit();
                        }
                    });
                });
            </script>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="editStaffForm" action="${pageContext.request.contextPath }/staffAction_edit.action"
              method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="100%" align="center">

                <tr>
                    <td>公司</td>
                    <td><input type="text" name="company" class="easyui-validatebox" required="true"/></td>
                    <td>主协议</td>
                    <td><input type="text" name="agreement"/></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>

                    <td>性别</td>
                    <td><input type="text" name="sex" /></td>
                    <td></td>
                    <td></td>
                </tr>


                <tr>
                    <td>身份证号</td>
                    <td><input type="text" name="sid" class="easyui-validatebox" required="true"
                               data-options="validType:'sidnum'"/></td>

                    <td>年龄</td>
                    <td><input type="text" name="age" disabled="disabled"/></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <td>职务</td>
                    <td><input type="text" name="job" /></td>

                    <td>工资</td>
                    <td><input type="text" name="salary" /></td>

                    <td>其他工资</td>
                    <td><input type="text" name="dateSalary" /></td>
                </tr>
                <tr>
                    <td>个人</td>
                    <td><input type="text" name="persionalMoney" /></td>

                    <td>公积金</td>
                    <td><input type="text" name="publicMoney" /></td>

                    <td>其他</td>
                    <td><input type="text" name="other" /></td>


                </tr>
                <tr>
                    <td>实发工资</td>
                    <td><input type="text" name="pay" disabled="disabled" /></td>

                    <td>卡号</td>
                    <td><input type="text" name="cardNumber" /></td>

                    <td>开户行</td>
                    <td><input type="text" name="bank" /></td>

                </tr>
                <tr>
                    <td>合同期限</td>
                    <td><input type="text" name="beginTime" class="easyui-datebox"/></td>
                    <td>-</td>
                    <td><input type="text" name="endTime" class="easyui-datebox"/></td>
                    <td></td>
                    <td></td>
                </tr>


                <tr>
                    <td>联系方式</td>
                    <td><input type="text" name="phone" /></td>

                    <td>户口</td>
                    <td><input type="text" name="register" /></td>
                    <td>医保卡号</td>
                    <td><input type="text" name="card" /></td>
                </tr>


                <tr >
                    <td>备注</td>
                    <td><input type="text" name="remark"/></td>

                    <td>参加工作时间</td>
                    <td><input type="text" name="comeTime" /></td>

                    <td>离职时间</td>
                    <td><input type="text" name="goTime" /></td>
                </tr>


                <tr>
                    <td>超龄</td>

                    <td>
                        <input type="checkbox" name="isOutage" value="1" />
                    </td>


                    <td>单位交个人五险</td>
                    <td>
                        <input type="checkbox" name="isNoPm" value="1"/>

                    </td>

                    <td>已解雇</td>
                    <td>
                        <input type="checkbox"  name="isFire" value="1"/>

                    </td>
                </tr>

                <tr>
                    <td>续签合同</td>
                    <td>
                        <input type="checkbox"  name="isContine" value="1"/>
                    </td>

                    <td>即将到期</td>
                    <td>
                        <input type="checkbox"   name="needNotice" value="1"/>

                    </td>
                    <td></td>
                    <td></td>
                </tr>

            </table>
        </form>
    </div>
</div>


<!-- 添加窗口 -->
<div class="easyui-window" title="修改员工" id="addStaffWindow" collapsible="false"
     minimizable="false" maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="add" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
            <script type="text/javascript">
                $(function () {
                    //绑定事件
                    $("#add").click(function () {
                        //校验表单输入项
                        var v = $("#addStaffForm").form("validate");
                        if (v) {
                            //校验通过，提交表单
                            $("#addStaffForm").submit();
                        }
                    });
                });
            </script>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addStaffForm" action="${pageContext.request.contextPath }/staffAction_add.action"
              method="post">
            <input type="hidden" name="id">
            <table class="table-edit" width="100%" align="center">

                <tr>
                    <td>公司</td>
                    <td><input type="text" name="company" class="easyui-validatebox" required="true"/></td>
                    <td>主协议</td>
                    <td><input type="text" name="agreement"/></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>

                    <td>性别</td>
                    <td><input type="text" name="sex" /></td>
                    <td></td>
                    <td></td>
                </tr>


                <tr>
                    <td>身份证号</td>
                    <td><input type="text" name="sid" class="easyui-validatebox" required="true"
                               data-options="validType:'sidnum'"/></td>

                    <td>年龄</td>
                    <td><input type="text" name="age" disabled="disabled"/></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <td>职务</td>
                    <td><input type="text" name="job" /></td>

                    <td>工资</td>
                    <td><input type="text" name="salary" /></td>

                    <td>其他工资</td>
                    <td><input type="text" name="dateSalary" /></td>
                </tr>
                <tr>
                    <td>个人</td>
                    <td><input type="text" name="persionalMoney" /></td>

                    <td>公积金</td>
                    <td><input type="text" name="publicMoney" /></td>

                    <td>其他</td>
                    <td><input type="text" name="other" /></td>


                </tr>
                <tr>
                    <td>实发工资</td>
                    <td><input type="text" name="pay" disabled="disabled" /></td>

                    <td>卡号</td>
                    <td><input type="text" name="cardNumber" /></td>

                    <td>开户行</td>
                    <td><input type="text" name="bank" /></td>

                </tr>
                <tr>
                    <td>合同期限</td>
                    <td><input type="text" name="beginTime" class="easyui-datebox"/></td>
                    <td>-</td>
                    <td><input type="text" name="endTime" class="easyui-datebox"/></td>
                    <td></td>
                    <td></td>
                </tr>


                <tr>
                    <td>联系方式</td>
                    <td><input type="text" name="phone" /></td>

                    <td>户口</td>
                    <td><input type="text" name="register" /></td>
                    <td>医保卡号</td>
                    <td><input type="text" name="card" /></td>
                </tr>


                <tr >
                    <td>备注</td>
                    <td><input type="text" name="remark"/></td>

                    <td>参加工作时间</td>
                    <td><input type="text" name="comeTime" /></td>

                    <td>离职时间</td>
                    <td><input type="text" name="goTime" /></td>
                </tr>


                <tr>
                    <td>超龄</td>

                    <td>
                        <input type="checkbox" name="isOutage" value="1" />
                    </td>


                    <td>单位交个人五险</td>
                    <td>
                        <input type="checkbox" name="isNoPm" value="1"/>

                    </td>

                    <td>已解雇</td>
                    <td>
                        <input type="checkbox"  name="isFire" value="1"/>

                    </td>
                </tr>

                <tr>
                    <td>续签合同</td>
                    <td>
                        <input type="checkbox"  name="isContine" value="1"/>
                    </td>

                    <td>即将到期</td>
                    <td>
                        <input type="checkbox"   name="needNotice" value="1"/>

                    </td>
                    <td></td>
                    <td></td>
                </tr>

            </table>
        </form>
    </div>
</div>


</body>
</html>	