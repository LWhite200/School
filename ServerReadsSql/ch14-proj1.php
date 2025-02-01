<?php
include 'includes/travel-config.inc.php';

try {
    // These connect to the database
    $pdo = new PDO(DBCONNSTRING, DBUSER, DBPASS);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query to fetch all continents
    $continentsQuery = "SELECT ContinentCode, ContinentName FROM continents";
    $continentsResult = $pdo->query($continentsQuery);
    $continents = $continentsResult->fetchAll(PDO::FETCH_ASSOC);

    // Query to fetch countries that have matching records in the imagedetails table
    $countriesQuery = "SELECT DISTINCT countries.ISO, countries.CountryName 
                       FROM countries 
                       INNER JOIN imagedetails ON countries.ISO = imagedetails.CountryCodeISO";
    $countriesResult = $pdo->query($countriesQuery);
    $countries = $countriesResult->fetchAll(PDO::FETCH_ASSOC);

    // Base query to fetch images
    $sql = "SELECT imagedetails.ImageID, imagedetails.path, imagedetails.Title, imagedetails.Description, 
                   imagedetails.Exif, imagedetails.Colors, 
                   countries.CountryName, cities.AsciiName AS CityName
            FROM imagedetails
            JOIN countries ON imagedetails.CountryCodeISO = countries.ISO
            JOIN cities ON imagedetails.CityCode = cities.CityCode";

    // Apply filters if they are set
    // 
    $filters = []; # This where the filter


    // Check if the 'continent' filter is set and not equal to '0' (default value)
    if (isset($_GET['continent']) && $_GET['continent'] != '0') {
        // Add a filter condition for the continent
        $filters[] = "countries.ContinentCode = :continent";
    }
    if (isset($_GET['countries']) && $_GET['countries'] != '0') {
        $filters[] = "imagedetails.CountryCodeISO = :country";
    }
    if (isset($_GET['title']) && !empty($_GET['title'])) {
        $filters[] = "imagedetails.Title LIKE :title";
    }

    // If there are any filters applied, add them to the SQL query
    if (!empty($filters)) {
        // Append the WHERE clause to the SQL query
        // implode(" AND ", $filters) joins the filter conditions with " AND "
        $sql .= " WHERE " . implode(" AND ", $filters);
    }

    // Prepare the SQL query for execution
    $stmt = $pdo->prepare($sql);

    // Bind the filter values to the placeholders in the SQL query
    if (isset($_GET['continent']) && $_GET['continent'] != '0') {
        // Bind the selected continent value to the :continent placeholder
        $stmt->bindValue(':continent', $_GET['continent']);
    }
    if (isset($_GET['countries']) && $_GET['countries'] != '0') {
        $stmt->bindValue(':country', $_GET['countries']);
    }
    if (isset($_GET['title']) && !empty($_GET['title'])) {
        $stmt->bindValue(':title', '%' . $_GET['title'] . '%');
    }

    $stmt->execute();
    $images = $stmt->fetchAll(PDO::FETCH_ASSOC); // Fetch all the filtered images as an associative array




    // Decode JSON data in Exif and Colors fields
    foreach ($images as &$image) {
        $image['Exif'] = json_decode($image['Exif'], true);
        $image['Colors'] = json_decode($image['Colors'], true);
    }


} catch (PDOException $e) {
    die("DataBase Connection Error. make sure running and named 'travel': " . $e->getMessage());
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

                    <!-- Show the list and get user's choice -->
                    <?php foreach ($continents as $continent): ?>
                        <option value="<?php echo $continent['ContinentCode']; ?>" <?php echo (isset($_GET['continent']) && $_GET['continent'] == $continent['ContinentCode']) ? 'selected' : ''; ?>>
                            <?php echo $continent['ContinentName']; ?>
                        </option>
                    <?php endforeach; ?>

                </select>
                <select name="countries">
                    <option value="0">Select Country</option>

                    <?php foreach ($countries as $country): ?>
                        <option value="<?php echo $country['ISO']; ?>" <?php echo (isset($_GET['countries']) && $_GET['countries'] == $country['ISO']) ? 'selected' : ''; ?>>
                            <?php echo $country['CountryName']; ?>
                        </option>
                    <?php endforeach; ?>

                </select>
                <input type="text" placeholder="Search title" name="title" value="<?php echo isset($_GET['title']) ? $_GET['title'] : ''; ?>">
                <button type="submit" class="btn-primary">Filter</button>
                <a href="ch14-proj1.php" class="btn-secondary">Reset</a>
            </div>
        </form>
    </header>

    <main>
        <ul>
            <?php
            // The image display, idk why took me so long to make clickable links that work correctly.
            foreach ($images as $image) {
                echo '<li>';
                echo '<a href="detail.php?ImageID=' . $image['ImageID'] . '">'; 
                echo '<img src="' . $image['path'] . '" alt="Travel Image" style="width: 300px; height: auto;">';
                echo '</a>';
                echo '</li>';
            }
            ?>
        </ul>
    </main>
</body>

</html>