# SDK使用说明
## 1、下载sdk
见sdk下载链接
## 2、引入jar包
以自己的方式引入获取的jar包，以下以maven坐标方式引入为例

### 2.1、导入jar包
在模块目录中创建一个lib目录，将token-sdk-xxx.jar放入所在模块的lib目录下。
目录结构如下所示：
```
projectxxxx
----module1
----module2
----module3
--------lib
------------token-sdk-0.0.1.jar
```

### 2.2、添加maven坐标
```xml
        <dependency>
            <groupId>com.iscas</groupId>
            <artifactId>token-sdk</artifactId>
            <version>0.0.1</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/token-sdk-0.0.1.jar</systemPath>
        </dependency>
```


## 3、使用方式
### 3.1 测试
调用`TokenUtils#readToken`方法实现Token的解析，解析结果为TokenInfo实体

测试代码如下：
```java
package com.iscas.integrated.platform.sdk.token;

import com.iscas.integrated.platform.sdk.token.exception.TokenException;
import com.iscas.integrated.platform.sdk.token.model.TokenInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/2/15 15:55
 */
class TokenUtilsTest {

    /**
     * 测试token为空
     * */
    @Test
    public void testVerify1() {
        try {
            TokenInfo tokenInfo = TokenUtils.readToken(null);
            System.out.println(tokenInfo);
        } catch (TokenException e) {
            Assertions.assertTrue(e.getType() == TokenException.ErrorType.EMPTY_TOKEN);
        }
    }

    /**
     * 测试token格式错误
     * */
    @Test
    public void testVerify2() {
        try {
            TokenInfo tokenInfo = TokenUtils.readToken("wegwegwe");
            System.out.println(tokenInfo);
        } catch (TokenException e) {
            Assertions.assertTrue(e.getType() == TokenException.ErrorType.TOKEN_DECODE_ERROR);
        }
    }

    /**
     * 测试token格式正确
     * */
    @Test
    public void testVerify3() {
        try {
            TokenInfo tokenInfo = TokenUtils.readToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyZWFsTmFtZSI6Iui2hee6p-euoeeQhiIsImF1ZCI6WyJtYWluQXBwIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTY3NjUzMDI1NiwiYXV0aG9yaXRpZXMiOlsi6LaF57qn566h55CG5ZGYIiwiU1VQRVIiXSwianRpIjoiNDE5NWIwZDItMGQ3YS00ZDExLTk4ZDEtMjMyOWU5OTk4OTVmIiwiY2xpZW50X2lkIjoibWFpbkFwcCJ9.RklH28nbi-8lAkLhu_Kbzpv7vLVfv7aHWMBnl39NZ00");
            System.out.println(tokenInfo);
        } catch (TokenException e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试TOKEN过期
     * */
    @Test
    public void testVerify4() {
        try {
            TokenInfo tokenInfo = TokenUtils.readToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRlIjoxNjc2NDQ4MzM0LCJzZXJ2ZXIiOiJ4eCIsIndpbmRvdyI6Inl5IiwiZXhwIjoxNjc2NDQ4Mzk0LCJpYXQiOjE2NzY0NDgzMzR9.i3zS_8xrFOHWMgB2AKRwEOuoEsAuumXhJNj15T52y_8");
            System.out.println(tokenInfo);
        } catch (TokenException e) {
            e.printStackTrace();
        }
    }
}
```

### 3.2、TokenInfo实体

TokenInfo的属性见注释：

```java
public class TokenInfo {
    /**
     * 用户名
     * */
    private String username;

    /**
     * 真实姓名
     * */
    private String realName;
    
    /**
     * 过期时间
     * */
    private LocalDateTime expireTime;
    
    private List<String> scope;

    private String jti;
    
}
```

### 3.3、异常说明

readToken方法会抛出TokenException异常，可通过异常的`getType`方法获取异常类型枚举，异常代号说明如下：
```bash
        /**
         * TOKEN为空
         * */
        EMPTY_TOKEN(1001, "无法读取空token"),

        /**
         * 秘钥编码格式错误
         * */
        SECRET_ENCODING_ERROR(1002, "秘钥编码格式错误"),

        /**
         * TOKEN格式错误
         * */
        TOKEN_DECODE_ERROR(1003, "无法解析token,格式错误"),

        /**
         * TOKEN过期
         * */
        TOKEN_EXPIRED(1004, "token已过期");
```