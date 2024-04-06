import requests
import pdb
import random
from multiprocessing import Process
import argparse
import sys

def calll_api(api_gateway=False,route="division-multiplication",server="localhost",services="division",a=5, b=7,port=31264):

    url = f"http://{server}:{port}/api/v1/math/{services}?a={a}&b={b}"

    if api_gateway:
        url = f"http://{server}:{port}/{route}/api/v1/math/{services}?a={a}&b={b}"



    payload={}
    headers = {}
    try:
        response = requests.request("POST", url, headers=headers, data=payload)
    except Exception as e:
        print(f"Error: {e}")
        return

    value=response.text

    print (f"Server: {server}, Port: {port}, Service: {services}, a: {a}, b: {b}, Response: {value}")

    return value
def main(api_gateway=False,math_add_subtract=31181,math_division_multiplication=31181):
    processes = []
    print(f"Math Add Subtract Port: {math_add_subtract}")
    print(f"Math Division Multiplication Port: {math_division_multiplication}")

    for i in range (0,1000):
        random_number = random.randint(0,500)
        server="localhost"

        calls = [
            (False,'',server,"multiplication",random_number,random.randint(0,500),math_division_multiplication),
            (False,'',server,"division",random_number,random.randint(0,1),math_division_multiplication),

            (False,'',server,"subtract",random_number,random.randint(500,9500),math_add_subtract),
            (False,'',server,"add",random_number,random.randint(-500,1000),math_add_subtract),
        ]

        if (api_gateway):
            calls = [
                (True,'division-multiplication',server,"multiplication",random_number,random.randint(0,500),math_division_multiplication),
                (True,'division-multiplication',server,"division",random_number,random.randint(0,1),math_division_multiplication),

                (True,'add-subtract',server,"subtract",random_number,random.randint(500,9500),math_add_subtract),
                (True,'add-subtract',server,"add",random_number,random.randint(-500,1000),math_add_subtract),
            ]




        for call in calls:
            p = Process(target=calll_api, args=call)
            try:
                p.start()
                processes.append(p)
            except Exception as e:
                print(f"Error: {e}")
                pass

        # Wait for all processes to finish
    for p in processes:
        p.join()


if __name__ == '__main__':

    math_add_subtract = 0
    math_division_multiplication = 0
    api_gateway = False


    for arg in sys.argv[1:]:
        if "=" in arg:
            operation, num = arg.split("=")

            if operation == "math-add-subtract":
                math_add_subtract = int(num)
            if operation == "math-division-multiplication":
                math_division_multiplication = int(num)
            if operation == "api-gateway":
                api_gateway = True
            #pdb.set_trace()




    if math_add_subtract == 0 or math_division_multiplication == 0:
        print("Please provide the math_add_subtract and math_division_multiplication values")
        sys.exit(1)




    main(math_add_subtract=math_add_subtract,math_division_multiplication=math_division_multiplication)
# python tester.py  math-add-subtract=31979 math-division-multiplication=31181 api-gateway=True
# python tester.py  math-add-subtract=31979 math-division-multiplication=31181
