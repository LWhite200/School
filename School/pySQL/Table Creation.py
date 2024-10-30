import sqlite3

# Connect to the database (or create it if it doesn't exist)
connection = sqlite3.connect('master.db')
cursor = connection.cursor()

# Create a table with columns for name, views, and published date
sql_command = """
CREATE TABLE SAMPLE (
    name VARCHAR(30),
    views INTEGER,
    published DATE
)
"""
cursor.execute(sql_command)

# Commit changes and close the connection
connection.commit()
connection.close()
