**Implement a toy symmetric cryptosystem based on XOR cipher**

a. Keys are 16-bit randomly generated values.

b. Messages are randomly generated strings with an even number of characters. (Valid
characters are upper and lower case letters, as well as spaces.) One can always add a blank
at the end of an odd-length string.

c. The encryption of a message M of length n (in bytes) is given by
where the key K is repeated n/2 times. “||” here is String Concatenation Operator.

d. The decryption algorithm for a ciphertext C is the same as the encryption algorithm:
Implement a brute-force decryption attack for this cryptosystem and test it on randomly
generated English character message. Automate the process of detecting whether a decrypted
message is English characters.

The randomly generated message has to be an English words message rather than English
character message. Thus, he brute force will continue until find an English words message.
