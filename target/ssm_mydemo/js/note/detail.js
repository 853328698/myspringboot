

$.get("/ssm_mydemo/note/myLastNote", {}, function (result) {
    var note = result.data.myLastNote;
    console.log("note.noteHeadImg:"+note.noteHeadImg);
    console.log("note.noteCreateTime:"+note.noteCreateTime);
    console.log("note.noteCity:"+note.noteCity);
    console.log("note.noteTitle:"+note.noteTitle);
    console.log("note.noteContent:"+note.noteContent);

    //页面渲染 游记数据
    $(".set-note-bg").css("backgroundImage",note.noteHeadImg);// 游记头图

    $(".note_title").html(note.noteTitle );// 游记标题

    $(".noteTripDate").html(new Date(note.noteTripDate).format("Y-m-d")  );  //日期获得的是毫秒数，所以要转换成日期格式显示在页面中
    $(".noteTripDays").html(note.noteTripDays  );// 游记出行时间
    $(".noteTripPartner").html(note.noteTripPartner  );// 游记出行伙伴
    $(".noteTripFeePerPeople").html(note.noteTripFeePerPeople  ); // 出行人均消费
    $(".note_content").html(note.noteContent );//游记内容


} );