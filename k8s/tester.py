import requests
import random
from multiprocessing import Process


def calll_api(route="division-multiplication",server="localhost",services="division",a=5, b=7,port=31264):


    url = f"http://{server}:{port}/{route}/api/v1/math/{services}?a={a}&b={b}"
    payload={}
    headers = {}
    response = requests.request("POST", url, headers=headers, data=payload)
    value=response.text

    print (f"Server: {server}, Port: {port}, Service: {services}, a: {a}, b: {b}, Response: {value}")

    return value
def main():
    processes = []
    for i in range (0,1000):
        random_number = random.randint(0,500)
        server="localhost"
        port=30380


        calls = [
            ('division-multiplication',server,"multiplication",random_number,random.randint(0,500),port),
            ('division-multiplication',server,"division",random_number,random.randint(0,1),port),

            ('add-subtract',server,"subtract",random_number,random.randint(500,9500),port),
            ('add-subtract',server,"add",random_number,random.randint(-500,1000),port),
        ]




        for call in calls:
            p = Process(target=calll_api, args=call)
            p.start()
            processes.append(p)

        # Wait for all processes to finish
    for p in processes:
        p.join()


if __name__ == '__main__':
    main()
