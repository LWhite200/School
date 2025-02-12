<?php

// Error with the delete key is because there is nothing set, so delete a name and it works fine

// " Database cis355, table names, [id (int, auto) : name (varchar) "
# connect to the database
$dbhost = "localhost";
$dbname = "cis355";
$dbuser = "root";
$dbpassword = "";
$str = "mysql:host=".$dbhost.";";
$str .= "dbname=".$dbname;
$connection = new PDO($str, $dbuser, $dbpassword);

if ($connection) echo "Connection Worked" . "<br />";
else echo "WRoNG". "<br />";

# update list (if $_GET not empty)
$name2 = $_GET['name2'];
$sql = "INSERT INTO names (name) values ('$name2')";
if ($name2) {
    $connection->query($sql);
}
$delete = $_GET['delete'];
$sql = "DELETE FROM names WHERE id = $delete";
if($delete) {
    $connection->query($sql);
}

echo '<form action="crud.php" method="get">' . "\n";

# Print list from the database
$sql = "SELECT * FROM names";
foreach($connection->query($sql) as $row) {
    echo '<button name="delete" value="' . $row['id'] . '">' . $row['id'] . '</button> ' . $row['name'] . "<br />\n";
}

# Display Button and allow user to add shit 
echo '<form action="crud.php" method="get">';
echo '<input id="name" name="name2" type="text"  placeholder="fluffy" />' . "\n";
echo '<button type="Submit">Submit</button>' . "\n";
echo '</form>';

print_r($_GET);
?>