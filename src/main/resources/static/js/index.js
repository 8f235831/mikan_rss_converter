
import "/js/lib/jquery.min.js"
import "/js/lib/jquery.js"

function getRssFollows(){
    $.get("/try/ajax/demo_test.php",function(data,status){
        alert("数据: " + data + "\n状态: " + status);
    });
}