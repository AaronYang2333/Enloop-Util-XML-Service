/**
 * Created by aaron on 1/10/2018.
 */

var host = '192.168.22.134'
var port = '8177'

var leftMenuData = {
    Table2XML: {
        url: "/micro-service-xml/xml/from/table",
        request: { json: 'rowContent' },
        tips: 'Convert Table JSON to XML!Simply insert your JSON to the left. Click the button and there you go!',
        JSONContent: "",
        QueryType: { type: 'body', applicationType: 'application/json' },
        file: [],
        placeholder: `
            [
                {
                  "column1": 0,
                  "column2": 0.0,
                  "column3": 0,
                  "column4": 0,
                  "column5": 0,
                  "column6": "2018-01-11T01:06:14.509Z",
                  "column7": {
                    "pojo_column1": "string",
                    "pojo_column2": 0,
                    "pojo_column3": [
                      {}
                    ]
                  },
                  "column8": "string",
                  "column9": [
                    {}
                  ],
                  "column10": {}
                }
            ]
            `
    },
    XSD2XML: {
        url: "/micro-service-xml/xml/from/xsd/file",
        request: { xsdFile: 'rowContent' },
        tips: 'Mock XML Content Based on Xsd File!Simply upload your xsd file. Click the button and there you go!',
        JSONContent: 'Upload your XSD file above...',
        QueryType: { type: 'formData', applicationType: false },
        file: ["xsdFile"],
        placeholder: ""
    },
    'Generate XML By XSL': {
        url: "/micro-service-xml/xml/2sth/by/xsl",
        request: { xmlFile: 'rowContent', xslFile: 'rowContent' },
        tips: 'Convert XML into other form XML!You need to upload two files. Click the button and there you go!',
        JSONContent: 'Just upload two files is ok and Click the button!',
        QueryType: { type: 'formData', applicationType: false },
        file: ["xmlFile", "xslFile"],
        placeholder: ""
    },
    JSON2XML: {
        url: "/micro-service-xml/xml/from/json",
        request: { json: 'rowContent' },
        tips: 'Convert JSON to XML!Simply insert your JSON to the left. Click the button and there you go!',
        JSONContent: 'Insert your JSON here...',
        QueryType: { type: 'query', applicationType: 'application/x-www-form-urlencoded' },
        file: [],
        placeholder: ""
    },
    XML2JSON: {
        url: "/micro-service-xml/xml/2json/v2",
        request: { xml: 'rowContent' },
        tips: 'Convert XML to JSON!Simply insert your XML to the left. Click the button and there you go!',
        JSONContent: 'Insert your XML here...',
        QueryType: { type: 'query', applicationType: 'application/x-www-form-urlencoded' },
        file: [],
        placeholder: ""
    },
    XML2XSD: {
        url: "/micro-service-xml/xml/2xsd/v2",
        request: { xml: 'rowContent' },
        tips: 'Generate XSD according to your XML!Simply upload your XML file to the left. Click the button and there you go!',
        JSONContent: 'Upload your XML file or Fill in the blank with XML Content!',
        QueryType: { type: 'query', applicationType: 'application/x-www-form-urlencoded' },
        file: [],
        placeholder: ""
    },
    'Validate XML By XSD': {
        url: "/micro-service-xml/xml/validate",
        request: { json: 'rowContent' },
        tips: 'Validate the XML Content whether meet the Schema uploaded!',
        JSONContent: 'Just upload two files is ok and Click the button!',
        QueryType: { type: 'formData', applicationType: false },
        file: ["xmlFile", "xsdFile"],
        placeholder: ""
    },
    FormatXML: {
        url: "/micro-service-xml/xml/format",
        request: { xml: 'rowContent' },
        tips: 'Format your XML Content!',
        JSONContent: 'Insert your XML here...',
        QueryType: { type: 'query', applicationType: 'application/x-www-form-urlencoded' },
        file: [],
        placeholder: ""
    },

}
let gTitle = "";
$(document).ready(function () {
    MenuClick();
    $(".uk-parent").click();
    $(".uk-nav-sub:first li:first").click();
    $('#convertButton').on('click', function () {
        if (leftMenuData[gTitle].file.length != 0) {
            $("#fileForm").submit();
        }
        subimt('http://' + host + ':' + port + leftMenuData[gTitle].url);
    });
    $("#fileForm").submit(function (e) {
        subimt($(this).attr("action"));
        return false;
    })
});

function MenuClick() {
    //默认功能页面
    $('#menuType li ul li').on('click', function () {
        //清空之前的值
        $('#rowContent').val('');
        $('#resultContent').val('');
        //获得用户点击的抽屉的值
        gTitle = $(this).children("a").html()
        //该表标题和提示语，渲染页面
        $('#convertTips h1').html(gTitle)
        $('#convertTips label').html(leftMenuData[gTitle].tips)
        $('#rowContent').attr('placeholder', leftMenuData[gTitle].JSONContent);
        if (leftMenuData[gTitle].file.length == 0) {
            $("#upload-drop").hide();
            $("#upload-drop").css("background-image", "url('images/bg.png')");
            $("#rowContent").css("height","740px");
        } else if (leftMenuData[gTitle].file.length == 1) {
            $("#upload-drop").show();
            $("#file-div-2").hide();
            $("#upload-select2").attr("name", "");
            $("#upload-select1").attr("name", leftMenuData[gTitle].file[0]);
            $("#file-div-1").attr("class", "uk-width-*")
            $("#upload-drop").css("background-image", "url('images/bg-xml.png')");
            $("#rowContent").css("height","680px");
        } else if (leftMenuData[gTitle].file.length == 2) {
            $("#upload-drop").show();
            if (gTitle == 'Validate XML By XSD') {
                $("#upload-drop").css("background-image", "url('images/bg-xmlxsd.png')");
            } else {
                $("#upload-drop").css("background-image", "url('images/bg-xmlxsl.png')");
            }
            $("#rowContent").css("height","680px");
            $("#file-div-2").show();
            $("#upload-select1").attr("name", leftMenuData[gTitle].file[0]);
            $("#upload-select2").attr("name", leftMenuData[gTitle].file[1]);
            $("#file-div-1").attr("class", "uk-width-1-2")

        }
        $("#fileForm").attr("action", 'http://' + host + ':' + port + leftMenuData[gTitle].url)
        $('#rowContent').val(leftMenuData[gTitle].placeholder)
    });
}
// 提交数据  
function subimt(url) {
    let rowData = getRowData(leftMenuData[gTitle].request);
    let submitType = leftMenuData[gTitle].QueryType.applicationType
    //发送AJAX请求
    $.ajax({
        url: url,
        type: "POST",
        data: rowData,
        dataType: "json",
        async: false,
        cache: false,
        processData: false,
        contentType: submitType,
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
// 获取提交的数据
function getRowData(params) {
    let rowData;
    for (let param in params) {
        if (leftMenuData[gTitle].file.length != 0) {
            rowData = new FormData($("#fileForm")[0]);
        } else if (gTitle == "Table2XML") {
            rowData = $("#rowContent").val();
        } else if (gTitle == "JSON2XML") {
            rowData = $("#rowContentForm").serialize();
        } else if (gTitle == "XML2JSON") {
            rowData = $("#rowContentForm").serialize();
        } else if (gTitle == 'XML2XSD') {
            rowData = $("#rowContentForm").serialize();
        } else if (gTitle == 'FormatXML') {
            rowData = $("#rowContentForm").serialize();
        }
    }
    return rowData;
}


