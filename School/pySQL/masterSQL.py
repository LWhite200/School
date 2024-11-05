import sqlite3
import os

class DatabaseManager:
    def __init__(self, db_name):
        self.directory_path = r'C:\Users\TriBlackInferno\Downloads\pySQL'
        self.table_name = db_name + '.db'
        self.full_path = os.path.join(self.directory_path, self.table_name)

        self.connection = self.create_connection()

    def create_connection(self):
        """Create a database connection to the SQLite database."""
        return sqlite3.connect(self.full_path)

    def create_table(self, table_name, columns):
        """Create a table with specified columns."""
        with self.connection:
            cursor = self.connection.cursor()
            columns_with_types = ', '.join(columns)
            cursor.execute(f"CREATE TABLE IF NOT EXISTS {table_name} (id INTEGER PRIMARY KEY, {columns_with_types})")
            print(f"Table '{table_name}' created with columns: {columns}")

    def add_data(self, table_name, **kwargs):
        """Insert data into the specified table."""
        with self.connection:
            cursor = self.connection.cursor()
            columns = ', '.join(kwargs.keys())
            placeholders = ', '.join(['?'] * len(kwargs))
            values = tuple(kwargs.values())
            cursor.execute(f"INSERT INTO {table_name} ({columns}) VALUES ({placeholders})", values)
            print("Data added:", kwargs)

    def read_data(self, table_name):
        """Read data from the specified table."""
        cursor = self.connection.cursor()
        cursor.execute(f"SELECT * FROM {table_name}")
        results = cursor.fetchall()
        for row in results:
            print(row)

    def update_data(self, table_name, data_id, **kwargs):
        """Update data in the specified table."""
        with self.connection:
            cursor = self.connection.cursor()
            set_clause = ', '.join([f"{key} = ?" for key in kwargs.keys()])
            values = tuple(kwargs.values()) + (data_id,)
            cursor.execute(f"UPDATE {table_name} SET {set_clause} WHERE id = ?", values)
            print("Data updated:", data_id, kwargs)

    def delete_data(self, table_name, data_id):
        """Delete data from the specified table."""
        with self.connection:
            cursor = self.connection.cursor()
            cursor.execute(f"DELETE FROM {table_name} WHERE id = ?", (data_id,))
            print("Data deleted:", data_id)

    def close_connection(self):
        """Close the database connection."""
        if self.connection:
            self.connection.close()
            print("Connection closed.")

def main():
    db_name = input("Enter file name (without extension): ")
    db_manager = DatabaseManager(db_name)

    while True:
        print("\nMenu:")
        print("1. Create Table\n2. Add Data\n3. Read Data\n4. Update Data\n5. Delete Data\n6. Exit")
        choice = input("Choose an option: ")

        if choice == '1':
            table_name = input("Enter table name: ")
            columns_input = input("Enter columns (name TYPE, ...): ")
            columns = [col.strip() for col in columns_input.split(',')]
            db_manager.create_table(table_name, columns)
        elif choice == '2':
            table_name = input("Enter table name to add data: ")
            data_input = input("Enter data as key=value pairs (name1=value1, name2=value2): ")
            kwargs = dict(item.split('=') for item in data_input.split(','))
            db_manager.add_data(table_name, **kwargs)
        elif choice == '3':
            table_name = input("Enter table name to read data: ")
            db_manager.read_data(table_name)
        elif choice == '4':
            table_name = input("Enter table name to update data: ")
            data_id = int(input("Enter ID to update: "))
            data_input = input("Enter data as key=value pairs (name1=value1, name2=value2): ")
            kwargs = dict(item.split('=') for item in data_input.split(','))
            db_manager.update_data(table_name, data_id, **kwargs)
        elif choice == '5':
            table_name = input("Enter table name to delete data: ")
            data_id = int(input("Enter ID to delete: "))
            db_manager.delete_data(table_name, data_id)
        elif choice == '6':
            break
        else:
            print("Invalid choice, please try again.")

    db_manager.close_connection()

if __name__ == "__main__":
    main()
