import sqlite3
connection = sqlite3.connect('master.db')
cursor = connection.cursor()

cursor.execute('''UPDATE sample SET name="I cannot believe this works" WHERE name = "video1"''')

connection.commit()
connection.close()
