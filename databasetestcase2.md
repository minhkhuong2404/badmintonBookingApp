Test Case ID | Test Description |
------------ | ---------------- | 
CC-001 | city existed |
CCtr-001 | center existed |
CCourt-001 | court existed | 
CPL-001 | player existed |
CS-001 | staff existed |

Test Case ID | Test Description |
------------ | ---------------- | 
CA-001 | customer does not exist |
CA-002 | booking does not exist |
CA-003 | this customer does not own the booking | 
CA-004 | violates 24 hours before start time |

Test Case ID | Test Description |
------------ | ---------------- | 
CP-001 | staff does not exist |
CP-002 | booking does not exist |
CP-003 | this staff has no relationship with the booking (join booking - court - center - staff) |

Test Case ID | Test Description |
------------ | ---------------- |
CB-001 | startTime < DATE(NOW())|
CB-002 | startTime < openTime | 
CB-003 | endTime > closeTime | 
CB-004 | endTime < startTime |  
CB-005 | playtime invalid (valid: 45m, 1h, 1h15m, 1h30m) |  

CB-006 : overlapping booking  
*               9:00===========10:00  existed booking    
*         8:00========9:30      
*                     9:30==============10:30        
*                   9:15=======10:00          
*            8:30==============10:00            

	
Test Case ID | Test Description |
------------ | ---------------- |
CB-007 | Player has pending booking in past |
CB-008 | Player can book no more than 3 bookings |
CB-109 | Customer does not existed |
CB-110 | Court does not existed |
CB-111 | Court & Customer do not existed |
	
