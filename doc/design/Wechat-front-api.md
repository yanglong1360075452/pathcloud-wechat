
微信前端调用 接口规格
=============

[TOC]

## 1 登录模块

### 1.1 微信网页授权
* __Method__
  GET

* __URL__
  /api/wechat/auth/{code}

* __Request__

 Param | Type | Description

 code | String | 微信返回code

* __Response__

> 成功

```
第一次绑定
{
  "code": 0,
  "data": {
    “openId”:"XXXXXX" #用户微信openID
  }
}

已绑定
{
  "code": 0,
  "data": {
    “token”:"XXXXXX" #token认证
  }
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 1.2 登录
* __Method__
  POST

* __URL__
  /api/wechat/login

* __Request__

 Param | Type | Description

 username | String | 用户名

 password | String | 密码

  openId | String | 用户OpenID

* __Response__

> 成功

```
{
  "code": 0,
  "data": {
    “token”:"XXXXXX" #token认证
  }
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 1.3 退出
* __Method__
  POST

* __URL__
  /api/wechat/logout

* __Request__

Header | Type | Description
Authorization | String | 返回的token值

 Param | Type | Description

  无

* __Response__

> 成功

```
{
  "code": 0,
  "data":null
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```


## 2 查看

### 2.1 获取信息列表
* __Method__
  GET
* __URL__
  /api/wechat/application
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

  page | int | 页数

   length | int | 每页记录数

   createTimeStart | Long | 申请开始时间

   createTimeEnd | Long | 申请结束时间

   status | int | 1 代表处理中，2代表已完成

   type | int | 1 代表常规染色 ，2代表 冰冻预约 ，3代表染色申请，什么都不传是全部

   filter | String | 病理号

   sort | int | 1 代表升序，2代表降序


> 成功

 ```
 {
   "code": 0,
   "data": {
     "total": 10,
     "data": [
 {
     "pid": null,  //病理ID
     "aid": 10, //申请ID
     "applicationTime": 1495681592000, //申请时间
     "aserialNumber": "AP17000010",  //申请号
     "pserialNumber": null,  //病理号
     "bookingStartTime": 149817600000,  //预约开始时间
     "bookingEndTime": 149821200000, //预约结束时间
     "instrumentId": 1,  //切片机类型，1代表切片机一，2代表切片机二
     "instrumentIdDesc": "切片机一",
     "pstatus": null,   //病理状态
     "pstatusDesc": null,  //处理中和已完成
     "bookingId": 9, //预约ID
     "astatus": 30, //申请状态  1,2代表常规申请，30冰冻预约
     "astatusDesc": "冰冻预约",
     "aiId": null,  //染色申请ID
     "aiApplicationTime": null,  // 染色申请时间
     "pserialNumbers": null  //一个染色申请会有多个病理号，这是一个数组
   },
      {
        "pid": null,
        "aid": null,
        "applicationTime": null,
        "aserialNumber": null,
        "pserialNumber": "Z17000015",
        "bookingStartTime": null,
        "bookingEndTime": null,
        "instrumentId": null,
        "instrumentIdDesc": null,
        "pstatus": 12,
        "pstatusDesc": "处理中",
        "bookingId": null,
        "astatus": null,
        "astatusDesc": "染色申请",
        "aiId": 6, //染色申请ID
        "aiApplicationTime": 1496996122000,
        "pserialNumbers": [
          "Z17000015",
          "Z17000016"
        ]
      }
     ],
     "length": 10,
     "page": 1
   }
 }
 ```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```



### 2.2 根据申请ID获取详细信息

* __Method__
  GET
* __URL__
  /api/wechat/application/detail
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description
 id | long | 申请ID

type | int | 申请类型 1-常规染色 2-冰冻预约 3-染色申请

* __Response__

> 成功

```
常规染色
{
  "code": 0,
  "data": [
          {
            "blockSubId": "1", #蜡块号
            "slideSubId": null,#玻片号
            "operation": 1,
            "operationDesc": "取材",#事件名称
            "operator": "管理员",#操作者
            "operationTime": 1494224125000,#操作时间
            "note": "", #备注
            "noteType": null #备注类型
        }
  ]
}

冰冻预约
{
    "code": 0,
    "data": {
        "samples": [
            {
                "id": 25,
                "applicationId": 22,
                "serialNumber": "SA17000025",
                "name": "fff",
                "category": 1,
                "categoryName": "大样本", #样本名称
                "createBy": 2,
                "updateBy": 2,
                "createTime": 1496910280000,
                "updateTime": 1496910280000,
                "registerUser": null,
                "registerTime": null
            }
        ],
        "books": [
            {
                "freezeStartTime": 1496881800000, #预约开始时间
                "cells": null,
                "freezeEndTime": 1496885400000, #预约结束时间
                "instrumentId": 1 #冰冻切片机
            }
        ]
    }
}


染色申请
{
    "code": 0,
    "data": [
        {
            "id": 9,
            "pathNo": "Z17000001", #病理号
            "blockSubId": "2", #蜡块号
            "slideSubId": "1", #玻片号
            "dyeCategory": 0,
            "dyeCategoryDesc": "HE", #染色类别
            "ihcMarker": null, #试剂抗体名称
            "operateTime": 1496814549000 #操作时间
        }
    ]
}
```



## 3  申请人信息

### 3.1 获取科室列表

* __Method__
  GET
* __URL__
  /api/wechat/applicant/department
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

 无


> 成功

 ```
{
  "code": 0,
  "data":  [   
  		{"code": 1,
      	 "name": "泌尿科"}
      	 ]
}
 ```

### 3.2 获取申请人信息列表

* __Method__
  GET
* __URL__
  /api/wechat/applicant
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

 page | int | 页数

 length | int | 每页记录数

 isSelf | boolean | 是否是当前用户


> 成功

 ```
{
  "code": 0,
  "data": {
    "total": 1, #记录总数
    "data": [
      {
        "id": 1, #申请人ID
        "firstName": "XXXX", #姓名
      	"departments":1,
      	"departmentsDesc":"XXX", #科室描述
      	"identity":1, 
      	"identityDesc":"XXX", ##身份描述
      	"isSelf":true #是否是当前登录用户
      }
    ],
    "length": 10, #每页记录数
    "page": 1 #第几页
  }
}
 ```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 3.3 添加申请人
* __Method__
  POST

* __URL__

  /api/wechat/applicant

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

> 请求参数Json格式

```
{
 "firstName":"XXXX", //姓名
 "phone":"13870652480", //电话
 "departments":1, #科室
 "identity": 1, #身份 0-本科生 1-硕士研究生 2-博士研究生 3-博士后 4-临床医生 5-研究人员 6-技术人员 7-其他
 "tutor": "导师",
 "faculty": "院系",
 "specialty": "专业",
 "studentNumber": "学号",
 "unit": "单位",
 "wno": "职工号",
 "taskName": "课题名称",
 "taskType": 1, #课题类型 0-国家自然科学基金 1-国家科技部课题 2-省部级计划课题 3-横向课题 4-其他
 "projectCode": "项目代码"
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code": 6,
  "reason": "姓名已存在"
}
```

### 3.4 修改申请人信息
* __Method__
  PUT

* __URL__

  /api/wechat/applicant

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

> 请求参数Json格式

```
{
 "id":1, #申请人ID
 "firstName":"XXXX", #姓名
 "phone":"13870652480", #电话
 "departments":1, #科室
 "identity": 1, #身份 0-本科生 1-硕士研究生 2-博士研究生 3-博士后 4-临床医生 5-研究人员 6-技术人员 7-其他
 "tutor": "导师",
 "faculty": "院系",
 "specialty": "专业",
 "studentNumber": "学号",
 "unit": "单位",
 "wno": "职工号",
 "taskName": "课题名称",
 "taskType": 1, #课题类型 0-国家自然科学基金 1-国家科技部课题 2-省部级计划课题 3-横向课题 4-其他
 "projectCode": "项目代码"
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

### 3.5  删除申请人
* __Method__
  DELETE

* __URL__

  /api/wechat/applicant/{id}

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

  Param | Type | Description

  id | long | 申请人ID

> __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

### 3.6  获取单个申请人信息
* __Method__
  GET

* __URL__

  /api/wechat/applicant/{id}

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

  Param | Type | Description

  id | long | 申请人ID

> __Response__

> 成功

```
{
  "code": 0,
  "data": {
      "id": 3,
      "firstName": "管理员",
      "phone": "1234567",
      "identity": null,
      "identityDesc": null,
      "tutor": null,
      "faculty": null,
      "studentNumber": null,
      "unit": null,
      "wno": null,
      "departments": 2,
      "departmentsDesc": "消化内科",
      "taskType": null,
      "taskTypeDesc": null,
      "taskName": null,
      "specialty": null,
      "projectCode": null,
      "self": true
  }
}
```


## 4  病理申请

### 4.1 创建常规染色/冰冻预约
* __Method__
  POST

* __URL__
  /api/wechat/application

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

> 请求参数Json格式

```
{
	"applicantId":1, #申请者ID
	"researchType":1, # 1-常规染色 2-冰冻预约
	"samples":[{
	        "name":"eee", #样本名称
	        "category":1 #样本类别  1-大样本 2-小样本
	        }],
	"books": [{ #若申请为冰冻预约，需传此项
	    "freezeStartTime":149839898877, #冰冻预约日期
	    "instrumentId": 1, #机器号
      	"cells": [
      	   16,17 #预约时间 0代表0:00-0:30 1代表0:30-1:00 以此类推
      	]
	  },{
	    "freezeStartTime":149839898877,
        "instrumentId": 2,
        "cells": [
           16,17
        ]
	  }
	]
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 4.2 撤销常规染色/冰冻预约申请

* __Method__
  POST
* __URL__
  /api/wechat/application/cancel/{id}
* __Request__

 Header | Type | Description

Authorization | String | 返回的token值

Param | Type | Description

 id | LONG | 病理申请id

* __Response__

> 成功

 ```
{
  "code": 0,
  "data": null
}
 ```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 4.3 获取冰冻预约列表

* __Method__
  GET
* __URL__
  /api/wechat/application/booking
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

timeStart | long | 开始日期
timeEnd | long | 结束日期


* __Response__

> 成功

```
{
  "code": 0,
  "data": [
    {
      "date": 1496505600000, #日期
      "instrumentId": 1, #切片机
      "bookingDto": [ #预约情况
        {
          "timeflag": 16,  #时间的标识 16代表8点到8点半
          "checked": null,
          "booked": false, #是否预约
          "instrumentId": 1,
          "bookingPerson": null
        }
      ]
    }
  ]
}
```


## 5 染色申请
### 5.1 获取染色类别列表

* __Method__
  GET
* __URL__
  /api/wechat/dye/type
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

无

* __Response__

> 成功

```
{
  "code": 0,
  "data": [
    {
      "code": 0,
      "name": "HE"
    }
  ]
}
```

### 5.2 创建染色申请

* __Method__
  POST
* __URL__
  /api/wechat/dye
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

> 请求参数Json格式

```
{
"applicantId":1, #申请者ID
"ihcBlocks":[{"serialNumber":"Z17000008", #病理号
			 "subId":"2", #蜡块号
			 "specialDye":1, #染色类别 
			 "ihcMarker":["ddff","ddfes"], #染色要求
			 "note":"" #备注
			 }]
}
```

* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```



### 5.3 查询我的蜡块

* __Method__
  GET
* __URL__
  /api/wechat/dye/blocks
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

 Param | Type | Description

page | Integer | 页数

length | Integer | 每页的记录数

createTimeStart | Long | 开始时间

createTimeEnd  | Long | 结束时间

* __Response__

> 成功

```
{
  "code": 0,
  "data": {
    "total": 1,
    "data": [
      {
        "blockId": 112, #蜡块ID
        "pathologySerialNumber": "Z17000021", #病理号
        "subId": "1", #蜡块号
        "bodyPart": "", #取材部位
        "unit": 1, 
        "unitDesc": "块", #单位
        "count": 1, #组织数
        "biaoshi": 1,
        "biaoshiDesc": "常规", #取材标识
      }
    ],
    "length": 10,
    "page": 1
  }
}
```

> 失败

```
{
  "code": 2,
  "reason": "参数错误"
}
```

### 5.4 撤销染色申请

* __Method__
  POST
* __URL__
  /api/wechat/dye/{id}
* __Request__

Header | Type | Description

Authorization | String | 返回的token值

Param | Type | Description

id | long | 申请ID



* __Response__

> 成功

```
{
  "code": 0,
  "data": null
}
```

## 6 个人信息
### 6.1  获取个人信息
* __Method__
  GET

* __URL__

  /api/wechat/user

* __Request__

  Header | Type | Description

  Authorization | String | 返回的token值

  Param | Type | Description

  无

> __Response__

> 成功

```
{
  "code": 0,
  "data": {
    "id": 2,
    "firstName": "管理员",
    "phone": "11111111",
    "identity": 0,
    "identityDesc": "本科生",
    "tutor": "ff",
    "faculty": "无",
    "specialty": "无",
    "studentNumber": "0001",
    "unit": "单位哈哈",
    "departments": 1,
    "departmentsDesc": "呼吸内科",
    "wno": "001",
    "taskName": "无",
    "taskType": 0,
    "taskTypeDesc": "国家自然科学基金",
    "projectCode": "无"
  }
}
```

### 6.2  修改个人信息
*  __Method__
   POST

*  __URL__

     /api/wechat/user

*  __Request__

     Header | Type | Description

     Authorization | String | 返回的token值

   > 请求参数Json格式

```
{
 "firstName":"XXXX", //姓名
 "phone":"13870652480", //电话
 "departments":1, #科室
 "identity": 1, #身份 0-本科生 1-硕士研究生 2-博士研究生 3-博士后 4-临床医生 5-研究人员 6-技术人员 7-其他
 "tutor": "导师",
 "faculty": "院系",
 "specialty": "专业",
 "studentNumber": "学号",
 "unit": "单位",
 "wno": "职工号",
 "taskName": "课题名称",
 "taskType": 1, #课题类型 0-国家自然科学基金 1-国家科技部课题 2-省部级计划课题 3-横向课题 4-其他
 "projectCode": "项目代码"
}
```

> __Response__

> 成功

```
{
  "code": 0,
  "data":null
}
```

### 6.3  获取是否接收通知
* __Method__
  GET

* __URL__

  /api/wechat/user/received

* __Request__

  Header | Type | Description
  Authorization | String | 返回的token值

  Param | Type | Description
  无

> __Response__

> 成功

```
{
  "code": 0,
  "data": true #true-接收 false-不接收
}
```
### 6.4  修改是否接收通知

* __Method__
  POST

* __URL__

  /api/wechat/user/received

* __Request__

  Header | Type | Description
  Authorization | String | 返回的token值

  Param | Type | Description
  无

> __Response__

> 成功

```
{
  "code": 0,
  "data":null
}
```

### 6.5  意见反馈

* __Method__
  POST

* __URL__

  /api/wechat/user/feedback

* __Request__

  Header | Type | Description
  Authorization | String | 返回的token值

 > 请求参数Json格式

```
{
  "contactWay":"联系方式",
  "feedback":"意见反馈"
}
```

> __Response__

> 成功

```
{
  "code": 0,
  "data":null
}
```


