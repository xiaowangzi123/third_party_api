

# 1. 配置账户

## 注册账号

网址：https://www.paypal.com/

```
wangyq@waitrans.com
Wang0207

440103199003072198
```



## 开发者网站

网址：https://developer.paypal.com ，并使用注册好的账号进行登录



创建应用，并选择类型为sandbox，并记录ClientId和Secret



```
https://www.sandbox.paypal.com/
商户
https://www.sandbox.paypal.com/mep/dashboard
sb-zjktl15336097@business.example.com
9N$paLA2

消费用户


```



# SDK版本

PayPal有v1、v2两个版本的SDK，
网上几乎都是针对v1 的文档，v2的几乎看不到。

本博客经过二次编辑，回调里面逻辑基本已经完善，无需大的改动。

v1：rest-api-sdk

		<dependency>
			<groupId>com.paypal.sdk</groupId>
			<artifactId>rest-api-sdk</artifactId>
			<version>LATEST</version>
		</dependency>

v2：checkout-sdk

		<dependency>
			<groupId>com.paypal.sdk</groupId>
			<artifactId>checkout-sdk</artifactId>
			<version>1.0.2</version>
		</dependency>

这两个版本的我都接入过，但由于官方不建议再使用v1，所以今天我只讲v2



# 注意：

创建测试账号，卖家和买家不要一样，特别是不能都选择中国

买家的余额要有



# 参考链接

https://blog.csdn.net/qq_36341832/article/details/106334844
