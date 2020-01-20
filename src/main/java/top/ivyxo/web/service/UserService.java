package top.ivyxo.web.service;

import top.ivyxo.web.common.data.ResponseObj;
import top.ivyxo.web.model.UserRegisterQuery;
import top.ivyxo.web.model.UUserVO;
import top.ivyxo.web.model.UserUpdateQuery;

/**
 * 用户服务 Richard - 2019-12-3 16:50:33
 * @author HYR
 */
public interface UserService {

    /**
     * 添加用户 Richard - 2019-12-1 23:35:24
     * @param userRegisterQuery 用户注册信息
     * @return
     */
    ResponseObj<UUserVO> register(UserRegisterQuery userRegisterQuery);

    /**
     * 用户登录 Richard - 2019-12-2 16:14:21
     * @param account 账户
     * @param password 密码
     * @return
     */
    ResponseObj<UUserVO> login(String account, String password);

    /**
     * 退出登录 Richard - 2019-12-5 12:09:46
     * @param userId 用户id
     * @return
     */
    ResponseObj<Integer> loginOut(Long userId);

    /**
     * 更新用户 Richard - 2019-12-5 15:07:15
     * @param userId 用户Id
     * @param userUpdateQuery 用户更新信息
     * @return
     */
    ResponseObj<Integer> update(Long userId, UserUpdateQuery userUpdateQuery);

    /**
     * 更新用户密码 Richard
     * @param userId 用户id
     * @param oldPsw 旧密码
     * @param newPsw 新密码
     * @param confirmPsw 确认新密码
     * @return
     */
    ResponseObj<Integer> updatePassword(Long userId, String oldPsw, String newPsw, String confirmPsw);

    /**
     * 根据用户id获取用户数据
     * @param id 用户Id
     * @return
     */
    UUserVO selectById(Long id);
}
