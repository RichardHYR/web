package top.ivyxo.web.common.tools;

import top.ivyxo.web.common.data.EStatusCode;
import top.ivyxo.web.common.data.ResponseObj;

/**
 * 响应体工具 Richard - 2020-3-11 14:29:30
 * @author HYR
 */
public class ResponseObjUtil {

    public static ResponseObj fail(){
        ResponseObj res = new ResponseObj();
        res.code = EStatusCode.UNKNOWN_ERR.getCode();
        res.msg = EStatusCode.UNKNOWN_ERR.getMsg();
        return res;
    }

    public static ResponseObj success(Object data){
        ResponseObj res = new ResponseObj();
        res.code = EStatusCode.SUCCESS.getCode();
        res.msg = EStatusCode.SUCCESS.getMsg();
        res.data = data;
        return res;
    }

}
