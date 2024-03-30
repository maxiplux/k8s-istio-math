import requests
import random
from multiprocessing import Process


def calll_api(server="localhost",services="division",a=5, b=7,port=31264):

    url = f"http://{server}:{port}/api/v1/math/{services}?a={a}&b={b}"
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
        port=31264
        port2=32342

        calls = [
            (server,"multiplication",random_number,random.randint(0,500),port),
            (server,"division",random_number,random.randint(0,1),port),

            (server,"subtract",random_number,random.randint(0,500),port2),
            (server,"add",random_number,random.randint(0,1),port2),
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
