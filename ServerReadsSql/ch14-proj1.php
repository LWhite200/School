<?php
include 'includes/travel-config.inc.php';

try {
    // Connect to the database
    $pdo = new PDO(DBCONNSTRING, DBUSER, DBPASS);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query to fetch data from the `imagedetails`, `countries`, and `cities` tables
    $sql = "SELECT imagedetails.ImageID, imagedetails.path, imagedetails.Title, imagedetails.Description, 
                   imagedetails.Exif, imagedetails.Colors, 
                   countries.CountryName, cities.AsciiName AS CityName
            FROM imagedetails
            JOIN countries ON imagedetails.CountryCodeISO = countries.ISO
            JOIN cities ON imagedetails.CityCode = cities.CityCode";
    $result = $pdo->query($sql);

    // Fetch all rows into an array
    $images = $result->fetchAll(PDO::FETCH_ASSOC);

    // Decode JSON data in Exif and Colors fields
    foreach ($images as &$image) {
        $image['Exif'] = json_decode($image['Exif'], true);
        $image['Colors'] = json_decode($image['Colors'], true);
    }
} catch (PDOException $e) {
    die("Connection failed: " . $e->getMessage());
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Chapter 14</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/styles.css" />
</head>

<body>
    <header>
        <form action="ch14-proj1.php" method="get">
            <div class="form-inline">
                <select name="continent">
                    <option value="0">Select Continent</option>
                </select>
                <select name="countries">
                    <option value="0">Select Country</option>
                </select>
                <input type="text" placeholder="Search title" name="title">
                <button type="submit" class="btn-primary">Filter</button>
                <button type="submit" class="btn-secondary">Reset</button>
            </div>
        </form>
    </header>

    <main>
        <ul>
            <?php
            // Loop through the images and display each image as a clickable link to detail.php
            foreach ($images as $image) {
                echo '<li>';
                echo '<a href="detail.php?ImageID=' . $image['ImageID'] . '">'; // Link to detail.php with ImageID
                echo '<img src="' . $image['path'] . '" alt="Travel Image" style="width: 300px; height: auto;">';
                echo '</a>';
                echo '</li>';
            }
            ?>
        </ul>
    </main>
</body>

</html>