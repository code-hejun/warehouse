package com.hejun.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObj {

    private Integer code;
    private String msg;

    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constant.OK,"登陆成功");
    public static final ResultObj LOGIN_ERROR_PASS = new ResultObj(Constant.ERROR,"用户名或密码错误");
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constant.ERROR,"验证码错误");

    public static final ResultObj ADD_SUCCESS = new ResultObj(Constant.OK,"添加成功");
    public static final ResultObj ADD_ERROR = new ResultObj(Constant.ERROR,"添加失败");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constant.OK,"删除成功");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constant.ERROR,"删除失败");

    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constant.OK,"修改成功");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constant.ERROR,"修改失败");

    public static final ResultObj RESET_SUCCESS = new ResultObj(Constant.OK,"重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj(Constant.ERROR,"重置失败");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constant.OK,"分配成功");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constant.ERROR,"分配失败");

    public static final ResultObj BACKINPORT_SUCCESS = new ResultObj(Constant.OK,"退货成功");
    public static final ResultObj BACKINPORT_ERROR = new ResultObj(Constant.ERROR,"退货失败");
    public static final ResultObj SYNCCACHE_SUCCESS = new ResultObj(Constant.OK,"同步缓存成功");

    public static final ResultObj DELETE_ERROR_NEWS = new ResultObj(Constant.ERROR,"删除用户失败，该用户是其他用户的直属领导，请先修改该用户的下属的直属领导，再进行删除操作");
    public static final ResultObj DELETE_QUERY = new ResultObj();

}
