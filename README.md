## wechat-spring-boot-starter
为对接微信提供快速 API 能力，包括微信、微信小程序、微信第三方平台

## 支持列表

### 1.0.0
- 获取验证票据和通用消息等解密接口
`com.github.developer.weapons.service.WechatComponentService.getVerifiedInfo`

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