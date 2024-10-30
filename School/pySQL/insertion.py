import sqlite3
connection = sqlite3.connect('master.db')
cursor = connection.cursor()

videos = [['video1', 100, '2022-01-01'],
          ['video2', 200, '2022-02-02'],
          ['video3', 300, '2022-03-03'],
          ['video4', 400, '2022-04-04']]

for i in range(len(videos)):
    name = videos[i][0]
    minutes = videos[i][1]
    date = videos[i][2]
    cursor.execute("INSERT INTO SAMPLE VALUES (?, ?, ?)", (name, minutes, date))

# Commit changes and close the connection
connection.commit()
connection.close()
