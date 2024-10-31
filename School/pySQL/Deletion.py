import sqlite3
connection = sqlite3.connect('master.db')
cursor = connection.cursor()


cursor.execute('''DELETE FROM sample WHERE name = "video2";''')

# Commit changes and close the connection
connection.commit()
connection.close()
