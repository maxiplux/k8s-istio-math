import requests
import random
def calll_api(server="localhost",services="division",a=5, b=7,port=31264):
    url = f"http://{server}:{port}/api/v1/math/{services}?a={a}&b={b}"
    payload={}
    headers = {}
    response = requests.request("POST", url, headers=headers, data=payload)
    return response.text

for i in range (0,1000):
    random_number = random.randint(0,500)
    server="localhost"
    port=31264
    print (calll_api(server=server,services="multiplication",a=random_number,b=random_number,port=port))
    print (calll_api(server=server,services="division",a=random_number,b=random.randint(0,1),port=port))

    port=32342
    print (calll_api(server=server,services="add",a=random_number,b=random_number,port=port))
    print (calll_api(server=server,services="subtract",a=random_number*random_number,b=random_number,port=port))
