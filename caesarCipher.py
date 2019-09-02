# @Author: Dhruv Gupta
# Decrypt Caesar Cipher based on letter fruency analysis

from pprint import pprint

f1 = open("caesar1.txt", 'r+')
f2 = open("q2_output.txt", "w+");
contents = f1.read()

count = dict()

for ch in contents:
    if ch.islower():
        count[ch] = count.get(ch, 0) + 1

pprint(count)

x = max(count, key=count.get)
key = ord('e')-ord(x)
print(key)


for ch in contents:
    if ch.isalpha():
        out = ord(ch)+key
        if ch.islower():
            if out > ord('z'):
                out -= 26
            elif out < ord('a'):
                out += 26
        if ch.isupper():
            if out > ord('Z'):
                out -= 26
            elif out < ord('A'):
                out += 26

        f2.write(chr(out))
    else:

        f2.write(ch)
