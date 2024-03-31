# JavaBot
import struct

from icmplib import traceroute
hops = traceroute(address='1.1.1.1')

print('Distance/TTL    Address    Average round-trip time')
last_distance = 0

for hop in hops:
    if last_distance + 1 != hop.distance:
        print('Some gateways are not responding')
    # See the Hop class for details
    print(f'{hop.distance}    {hop.address}    {hop.avg_rtt} ms')
    last_distance = hop.distance
exit(0)
def format(data):
    data = struct.unpack_from(f'@{len(data)}B', data)
    data = ['0' + hex(i)[2:] if len(hex(i)[2:]) == 1 else hex(i)[2:] for i in data]
    for i in range(0, len(data), 16):
        offset = hex(i)[2:]
        #print('0'*(8-len(offset))+offset, end = ' ')
        for j in range(i, min(i+16, len(data))):
            print(data[j], end=' ')
            if j-i == 7:
                print(' ', end = ' ')
        print()

data = b'\x08\x00\x4D\x2A\x00\x01\x00\x31\x61\x62\x63\x64\x65\x66\x67\x68\x69\x6A\x6B\x6C\x6D\x6E\x6F\x70\x71\x72\x73\x74\x75\x76\x77\x61\x62\x63\x64\x65\x66\x67\x68\x69'
s = '08 00 4D 2A 00 01 00 31 61 62 63 64 65 66 67 68 69 6A 6B 6C 6D 6E 6F 70 71 72 73 74 75 76 77 61 62 63 64 65 66 67 68 69'.replace(' ', '')
s = [f'\\x{s[i:i+2]}' for i in range(0, len(s), 2)]
#print(''.join(s))
#print(format(data))
#exit(0)

from threading import Thread
import socket

def main():
    HOST = socket.gethostbyname(socket.gethostname())
    print(f'HOST: {HOST}')
    # create a raw socket and bind it to the public interface
    s1 = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.IPPROTO_ICMP)
    s1.bind(('', 0))
    # Include IP headers
    s1.setsockopt(socket.IPPROTO_IP, socket.IP_HDRINCL, 1)
    

    # receive all packages
    #s1.ioctl(socket.SIO_RCVALL, socket.RCVALL_ON)

    # receive a package
    print('recv')
    for i in range(10):
        r = s1.recvfrom(65565)
        #if r[1][0] == '46.48.7.1':
        print(r[1])
        #format(r[0])
    # disabled promiscuous mode
    #s1.ioctl(socket.SIO_RCVALL, socket.RCVALL_OFF)   
    s1.close()        

def f():
    #data = b'\x28\xd1\x27\x0d\xb4\xf3\xe8\x6f\x38\x60\xc2\x29\x08\x00\x45\x00\x00\x40\x27\xc5\x00\x00\x80\x11\x52\xea\xc0\xa8\x1f\xac\xc0\xa8\x1f\x01\xf1\x6a\x00\x35\x00\x2c\x7a\xb1\x10\x45\x01\x00\x00\x01\x00\x00\x00\x00\x00\x00\x04\x65\x64\x67\x65\x09\x6d\x69\x63\x72\x6f\x73\x6f\x66\x74\x03\x63\x6f\x6d\x00\x00\x01\x00\x01'
    # публичный сетевой интерфейс
    HOST = socket.gethostbyname('vk.com')
    #print(HOST, socket.gethostname())
    #HOST = '10.98.240.10'
    #HOST = '10.249.3.221'

    # создать необработанный сокет и свяжите его с публичным интерфейсом
    s = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.IPPROTO_ICMP)
    #r = socket.socket(socket.AF_INET, socket.SOCK_RAW)
    #r.bind(('', 0))
    #r.bind((socket.gethostbyname(socket.gethostname()), 0))
    #s.ioctl(socket.SIO_RCVALL, socket.RCVALL_ON)
    s.setsockopt(socket.IPPROTO_IP, socket.IP_TTL, 2)
    print(s.getsockopt(socket.IPPROTO_IP, socket.IP_TTL))
    print(s.sendto(data, (HOST, 0)))
    r = s.recvfrom(2048)
    print('dddd', r[1])
    format(r[0])
    '''r = r.recvfrom(2048)
    while r:
        print(r[1])
        format(r[0])
        break
        r = s.recvfrom(2048)
    #s.ioctl(socket.SIO_RCVALL, socket.RCVALL_OFF)'''
    #s.close()
    sleep(10000)

#f()
from time import sleep, time
t = Thread(target=main, daemon=True)
t.start()
sleep(2)
f()
while t.is_alive():
    pass

