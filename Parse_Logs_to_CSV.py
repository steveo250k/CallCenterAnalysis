"""
The purpose of this process is to read 12 monthly call center log files
formatted as column delimited /fixed width values.
"""
import pandas as pd

columns = ["VRU", "Line", "Call_ID", "Customer_ID", "Priority", "Type", "Date", "VRU_Entry", "VRU_Exit", "VRU_Time",
           "Queue_Start", "Queue_Exit", "Outcome", "Service_Start", "Service_Exit", "Server_Time", "Server_Text"]

# file_ptr = open('data/01_January.txt', 'r')
# call_line = file_ptr.readline() # Throw first line away.
# call_line = file_ptr.readline()
# print(call_line.strip().split('\t'))

df = pd.read_csv('data/01_January.txt', sep='\t')
print(df.head())