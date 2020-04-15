# Homework#2 day9  
## Team E  
### **Notes**  
* Choose user's Facebook ID and email to be unique  

Risk | Mitigate
---- | --------
clone Facebook account used to book the slot( false information) | require user to authenticate by OTP code through email or phone number that used only for 1 account  
If there's problem with the user's FB account( blocked, cancel, deactive ) | depend on how the users manage their FB account  
Some people may not use Facebook | no way to mitigate this  
Facebook's password can be leaked out | tow factor(OTP code)  
User's token is leaked out | use short-lived token  
