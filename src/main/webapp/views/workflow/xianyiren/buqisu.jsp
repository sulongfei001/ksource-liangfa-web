<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglibs.jsp" %>
<div id="buqisuxianyirenC" class="xianyiren_c">
</div>
    <script type="text/javascript">
        function buqisuxianyirenBefore() {
            var flag = false;
            //更新所有嫌疑人为不起诉
            $.ajax({
                async:false,//确保函数反正正确的flag值
                dataType:'json',
                url: '${path}/workflow/buqisu/buqisu',
                data: {caseId:caseId},
                success: function(data) {
                    flag = true;//提交动态表单前处理不起诉操作
                    //重置提起公诉操作，防止数据操作不一致
                    if ($("#tiqigongsuxianyirenC")) {
                        $.ajax({
                            async:false,
                            dataType:'html',
                            url:'${path}/workflow/tiqigongsu',
                            data: { caseId: caseId, taskId: taskId},
                            success: function(data) {
                                //$('#xianyirenOptC').append(data);
                                $("#tiqigongsuxianyirenC").remove();
                                $(data).hide().appendTo('#xianyirenOptC');
                            }
                        });
                    }
                }
            });
            return flag;
        }
    </script>