import sqlite3
connection = sqlite3.connect('master.db')
cursor = connection.cursor()

cursor.execute("SELECT * FROM sample")

results = cursor.fetchall()
for i in results:
    print(i)


connection.close()
