#永丰余java后台程序

工程说明 base 模块 -- 存放通用的，共性的类，帮助类等
yfy-dao 模块
* 存放mybatis反向生成的mapper接口，实体类 （需要手动调整，后续会修改mybatis的生成源码，生成后无需手动修改）

yfy-service模块
* 存放一些通用的业务逻辑(目前接口工程自个编写业务逻辑层)

yfy-web
开放api，给app调用 http://localhost:9000/doc.html#/home
集成了yapi文档， 调用地址 http://ip:port/3001
http://202.196.192.225:3001

开发指导规范

controller层
|- A 前端和controller类的接口实体类存放在各自目录下的dechuan中
B 方法返回各自业务对象即可，框架层会拦截，统一处理返回结果，切记返回基本类型！！
C 对于前端传过来的数据，使用校验框架valid校验数据基本格式
具体可参考 RegisterController#registerBySms
（public void registerBySms(@RequestBody @Validated SmsRegisterReq registerReq)）
D 关于api权限控制的使用方法
在controller类上添加注解CustomerApiAuth，如果不加或者NONE,说明该方法对外开放，无需登录即可访问，如浏览产品信息
参考示例 DemoController#auth
@CustomerApiAuth(value = {EUserType.NORMAL_CUST})
public Object auth() {

service层
该层模块存放一些服务层逻辑，以jar包方式，开发多个业务模块

待补充
dao层 com.dechuan.yfydao
|- dao
|- mapper
3.1 由于管理端和web都会使用dao层，所有的mapper和dao接口都放于该层
3.2 尽量让实体 extends BaseEntity， mapper接口继承AbstractSingleMapper
接着手动编写manage包下的数据访问类（该层可做一些通用的数据访问处理,以便后续独立成数据访问微服务）
但不强制次规范