# paypal开发者网站

https://sandbox.paypal.com

## 商户app

```
transwai-test
clientID: AYwYlu7Qsgj2ddw7ffiVWVOwotWVYCNjoDXN2CrwhhSjfzdbCDV54_vUxeWSPvr5PKqUv48Iq2Ems0bP
secretKey: EOGR1E3dneUTU_1akfJxxPZZthOBTFnK7mRKZEu9SVGX6UoFzDCCZNrSPpx1pRaTNy6UHtKbOXygxHBw
```

## 商户

```
sb-oxx0p27866490@business.example.com
hj&S7CtY
```



## 用户

```
sb-koitu27890640@personal.example.com
?yE?8,BJ
```



# 参考
https://www.cnblogs.com/july-sunny/p/16216691.html




# 创建订单返回

```
Status Code: 201
Status: CREATED
Order ID: 5GV2949718011071L
Intent: CAPTURE
Links: 
	self: https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L	Call Type: GET
	approve: https://www.sandbox.paypal.com/checkoutnow?token=5GV2949718011071L	Call Type: GET
	update: https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L	Call Type: PATCH
	capture: https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L/capture	Call Type: POST
Total Amount: USD 2.00
Full response body:
{
    "create_time": "2023-11-02T01:55:21Z",
    "purchase_units": [{
        "payee": {
            "email_address": "sb-oxx0p27866490@business.example.com",
            "merchant_id": "UNL7E58XQ42AL"
        },
        "amount": {
            "breakdown": {
                "tax_total": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "shipping": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "shipping_discount": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "handling": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "item_total": {
                    "value": "2.00",
                    "currency_code": "USD"
                }
            },
            "value": "2.00",
            "currency_code": "USD"
        },
        "reference_id": "PUHF",
        "shipping": {
            "address": {
                "country_code": "US",
                "admin_area_1": "CA",
                "address_line_1": "123 Townsend St",
                "admin_area_2": "San Francisco",
                "address_line_2": "Floor 6",
                "postal_code": "94107"
            },
            "name": {"full_name": "John Doe"}
        },
        "soft_descriptor": "HighFashions",
        "custom_id": "CUST-HighFashions",
        "description": "Sporting Goods",
        "items": [
            {
                "quantity": "1",
                "name": "转写视频01.mp4",
                "description": "视频转写",
                "tax": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "unit_amount": {
                    "value": "1.00",
                    "currency_code": "USD"
                },
                "category": "DIGITAL_GOODS",
                "sku": "sku01"
            },
            {
                "quantity": "1",
                "name": "转写视频01.mp4",
                "description": "视频转写",
                "tax": {
                    "value": "0.00",
                    "currency_code": "USD"
                },
                "unit_amount": {
                    "value": "1.00",
                    "currency_code": "USD"
                },
                "category": "DIGITAL_GOODS",
                "sku": "sku02"
            }
        ]
    }],
    "links": [
        {
            "method": "GET",
            "rel": "self",
            "href": "https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L"
        },
        {
            "method": "GET",
            "rel": "approve",
            "href": "https://www.sandbox.paypal.com/checkoutnow?token=5GV2949718011071L"
        },
        {
            "method": "PATCH",
            "rel": "update",
            "href": "https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L"
        },
        {
            "method": "POST",
            "rel": "capture",
            "href": "https://api.sandbox.paypal.com/v2/checkout/orders/5GV2949718011071L/capture"
        }
    ],
    "id": "5GV2949718011071L",
    "intent": "CAPTURE",
    "status": "CREATED"
}
```







```
http://59.36.211.29:8081/paypal/cancel/callback?token=04233096L71835012
```



# 用户支付成功回调

```
http://59.36.211.29:8081/paypal/payment/callback?token=7T770713F8531474J&PayerID=6E38U38LX2J8L
```











# 扣款后返回参数

```
Status Code: 201
Status: COMPLETED
Order ID: 431351996N440112E
Links: 
	self: https://api.sandbox.paypal.com/v2/checkout/orders/431351996N440112E
Capture ids:
	845873715X384423D
Buyer: 
	Email Address: sb-koitu27890640@personal.example.com
	Name: John Doe
Full response body:
{
    "purchase_units": [{
        "reference_id": "PUHF",
        "shipping": {
            "address": {
                "country_code": "US",
                "admin_area_1": "CA",
                "address_line_1": "123 Townsend St",
                "admin_area_2": "San Francisco",
                "address_line_2": "Floor 6",
                "postal_code": "94107"
            },
            "name": {"full_name": "John Doe"}
        },
        "payments": {"captures": [{
            "amount": {
                "value": "2.00",
                "currency_code": "USD"
            },
            "seller_protection": {
                "dispute_categories": [
                    "ITEM_NOT_RECEIVED",
                    "UNAUTHORIZED_TRANSACTION"
                ],
                "status": "ELIGIBLE"
            },
            "update_time": "2023-11-01T09:48:08Z",
            "create_time": "2023-11-01T09:48:08Z",
            "final_capture": true,
            "seller_receivable_breakdown": {
                "paypal_fee": {
                    "value": "0.59",
                    "currency_code": "USD"
                },
                "gross_amount": {
                    "value": "2.00",
                    "currency_code": "USD"
                },
                "net_amount": {
                    "value": "1.41",
                    "currency_code": "USD"
                }
            },
            "links": [
                {
                    "method": "GET",
                    "rel": "self",
                    "href": "https://api.sandbox.paypal.com/v2/payments/captures/845873715X384423D"
                },
                {
                    "method": "POST",
                    "rel": "refund",
                    "href": "https://api.sandbox.paypal.com/v2/payments/captures/845873715X384423D/refund"
                },
                {
                    "method": "GET",
                    "rel": "up",
                    "href": "https://api.sandbox.paypal.com/v2/checkout/orders/431351996N440112E"
                }
            ],
            "id": "845873715X384423D",
            "status": "COMPLETED"
        }]}
    }],
    "links": [{
        "method": "GET",
        "rel": "self",
        "href": "https://api.sandbox.paypal.com/v2/checkout/orders/431351996N440112E"
    }],
    "id": "431351996N440112E",
    "payer": {
        "address": {"country_code": "SG"},
        "email_address": "sb-koitu27890640@personal.example.com",
        "name": {
            "surname": "Doe",
            "given_name": "John"
        },
        "payer_id": "6E38U38LX2J8L"
    },
    "status": "COMPLETED"
}
```

