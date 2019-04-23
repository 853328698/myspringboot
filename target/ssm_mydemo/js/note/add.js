$("#myfile").change(function () {
    $("#headImgForm").ajaxSubmit({
        type:"POST",
        url:"/ssm_mydemo/note/headImgUpload",
        dataType:"json",
        success:function (result) {
            var filePath=result.data.filePath;
            console.log(filePath);
            console.log(result);
            var imgUrl="url(/ssm_mydemo/"+filePath+")";
            // <div  style="background-img:url('/ssm_demo/upload/....jpg')"


            $(".set-note-bg").css("backgroundImage",imgUrl);
            $(".setNoteHeaderImgForm").hide();
            $(".setNoteHeaderImgTip").hide();
            $("#headImgPath").val(imgUrl);
            //后期的写游记模块中需要用到头图路径，所以，在此处设置写游记form 表单隐藏域的 头图路径

        }
    })

});



//提交添加游记
//提交表单的时候,使用的是表单序列化，所以需要加入jquery.form.js，提供了serilalize相关函数
$("#submitNoteBtn").click(function () {
    console.log("点击提交游记")
    $.ajax({
        type:"POST",
        url:"/ssm_mydemo/note/add",
        dataType:"json",
        data:$('#noteForm').serialize(),
        success:function (result) {
            console.log("result"+result);
            var status=result.status;
            console.log(status);
            if(result.status=="SUCCESS"){
                //添加游记成功后，跳转到查看我刚写的游记
                console.log("添加成功");
                window.location.href="/ssm_mydemo/note/goToDetail"

            }else{
                //添加游记失败
                $("#note_tip").text(result.data.reason);
            }

        }

    })

})