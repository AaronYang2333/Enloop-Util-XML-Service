/**
 * Created by aaron on 1/18/2018.
 */

var host = '192.168.22.134'
var port = '8177'

$(document).ready(function () {
    $('#quickButton a').on('click', function () {
        let url = $(this).attr("url");
        let rowData = $('#resultForm').serialize();
        //compress || json2xml || format
        if(url == '/micro-service-xml/xml/compress' || url == '/micro-service-xml/xml/from/json' || url == '/micro-service-xml/xml/format'){
            subimtWithOneParam(url,rowData)
        }else if(url == '#empty'){
            $('#resultContent').val('')
        }else if(url == '/micro-service-xml/download'){
            $('#navbar').removeAttr('style');
           downloadFile(rowData)
        }
    })

    copyButtonClick();
});

function subimtWithOneParam(url,rowData) {
    //发送AJAX请求
    $.ajax({
        url: 'http://' + host + ':' + port + url,
        type: "POST",
        data: rowData,
        dataType: "json",
        async: false,
        cache: false,
        processData: false,
        contentType: 'application/x-www-form-urlencoded',
        success: function (data) {
            if (data.statusCode == 200) {
                $('#resultContent').val(data.data);
            } else if (data.statusCode != 200) {
                $('#resultContent').val(data.message);
            }
        }
    });
    return false;
}

function copyButtonClick(){
    $(function () {
        $("#copy-btn").zclip({
            path: 'js/ZeroClipboard.swf', //记得把ZeroClipboard.swf引入到项目中
            copy: function () {
                return $('#resultContent').val();
            },
            beforeCopy: function () {/* 按住鼠标时的操作 */
                //检查Flash状态，没有打开则提示打开

            },
            afterCopy: function () {/* 复制成功后的操作 */
                UIkit.notify("<i class='uk-icon-check'></i> Copy Successfully!", {status:'success'});
            }
        });
    });
}

function downloadFile(rowData) {
    $('#navbar ul li').on('click', function () {
        let suffix = $(this).children("a").html();
        self.location = 'http://' + host + ':' + port + '/micro-service-xml/download' +'?rowData=' +rowData+'&suffix=' + suffix;
    });
}
