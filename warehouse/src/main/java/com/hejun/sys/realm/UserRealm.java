package com.hejun.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hejun.sys.common.ActiveUser;
import com.hejun.sys.common.Constant;
import com.hejun.sys.entity.Permission;
import com.hejun.sys.entity.User;
import com.hejun.sys.service.IPermissionService;
import com.hejun.sys.service.IRoleService;
import com.hejun.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    IUserService userService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    @Autowired
    @Lazy
    private IRoleService roleService;


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activerUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> superPermission = new ArrayList<>();
        superPermission.add("*:*");
        List<String> permissions = activerUser.getPermissions();
        if (user.getType().equals(Constant.USER_TYPE_SUPER)){
            authorizationInfo.addStringPermissions(superPermission);
        }else {
            if (null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("loginname",authenticationToken.getPrincipal().toString());
        User user = userService.getOne(queryWrapper);

        if(null!= user){

            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);


            //根据用户ID查询percode
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //设置只能查询权限
            qw.eq("type", Constant.TYPE_PERMISSION);
            //设置只能查询可用的权限
            qw.eq("available", Constant.AVAILABLE_TRUE);
            Integer userId = user.getId();
            //根据用户ID查询角色ID
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //根据角色ID查询出权限ID
            Set<Integer> pids = new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            if (pids.size()>0){
                qw.in("id",pids);
                list = permissionService.list(qw);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }
            //放到activerUser
            activeUser.setPermissions(percodes);

            //生成盐
            ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());

            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser,user.getPwd(),credentialsSalt,this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
