## wechat-spring-boot-starter
为对接微信提供快速 API 能力，包括微信、微信小程序、微信第三方平台

## 支持列表

### 1.0.0
#### 第三方平台
- 获取验证票据和通用消息等解密接口
`com.github.developer.weapons.service.WechatComponentService.getVerifiedInfo`
- 根据 ticket 获取 component_token
`com.github.developer.weapons.service.WechatComponentService.getComponentToken`
- 根据 component_token 获取 pre_auth_code
`com.github.developer.weapons.service.WechatComponentService.getPreAuthCode`
- 根据回调得到的 auth_code 获取授权信息
`com.github.developer.weapons.service.WechatComponentService.getAuth`
- 根据授权信息获取详细资料
`com.github.developer.weapons.service.WechatComponentService.getAuthorizerInfo`
- 根据回复消息自动加密
`com.github.developer.weapons.service.WechatComponentService.encryptMsg`
- 根据条件获取授权链接
`com.github.developer.weapons.service.WechatComponentService.generateLoginUrl`

## Usage
1. 在 `pom.xml` 里面添加公开仓库
```xml
<repositories>
    <repository>
        <id>developer-weapons-repository</id>
        <url>https://raw.githubusercontent.com/developer-weapons/repository/master</url>
    </repository>
</repositories>
```
2. 引入对应的包版本
```xml
<dependency>
    <groupId>com.github.developer.weapons</groupId>
    <artifactId>wechat-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```