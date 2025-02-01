<?php
include 'includes/travel-config.inc.php';

// Get the ImageID from the URL query string
$imageID = $_GET['ImageID'] ?? null;

if ($imageID) {
    try {
        
        // Reconnect to database as this is a different 'page'
        $pdo = new PDO(DBCONNSTRING, DBUSER, DBPASS);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // SQL query to fetch detailed information about the image
        $sql = "SELECT imagedetails.*, countries.CountryName, cities.AsciiName AS CityName
                FROM imagedetails
                JOIN countries ON imagedetails.CountryCodeISO = countries.ISO
                JOIN cities ON imagedetails.CityCode = cities.CityCode
                WHERE imagedetails.ImageID = :imageID";
        
        // Prepare the SQL query for execution
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':imageID', $imageID, PDO::PARAM_INT);
        $stmt->execute();
        $image = $stmt->fetch(PDO::FETCH_ASSOC);

        // Decode the JSON fields (Exif and Colors)
        if ($image) {
            $image['Exif'] = json_decode($image['Exif'], true);
            $image['Colors'] = json_decode($image['Colors'], true);
        }


    } catch (PDOException $e) {
        die("Connection failed: " . $e->getMessage());
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Image Details</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/styles.css" />
</head>

<body>
    <main class="detail">
        <?php if ($image): ?>
            <div>
                <img src="<?php echo $image['path']; ?>" alt="<?php echo $image['Title']; ?>" style="width: 100%; height: auto;"> 
            </div>
            <div>
    
                <h1><?php echo $image['Title']; ?></h1>
                <h3><?php echo $image['CityName'] . ', ' . $image['CountryName']; ?></h3>
                <p><?php echo $image['Description']; ?></p>

                <div class="box">
                    <h3>Creator</h3>
                    <p><a href="<?php echo $image['CreatorURL']; ?>"><?php echo $image['ActualCreator']; ?></a></p>
                    <p><a href="<?php echo $image['CreatorURL']; ?>"><?php echo $image['CreatorURL']; ?></a></p>
                </div>

                <!-- Display camera details from the Exif data -->
                <div class="box">
                    <h3>Camera</h3>
                    <?php if (isset($image['Exif']['make'])): ?>
                        <p>Make: <?php echo $image['Exif']['make']; ?></p>
                    <?php endif; ?>
                    <?php if (isset($image['Exif']['model'])): ?>
                        <p>Model: <?php echo $image['Exif']['model']; ?></p>
                    <?php endif; ?>
                    <?php if (isset($image['Exif']['aperture'])): ?>
                        <p>Aperture: <?php echo $image['Exif']['aperture']; ?></p>
                    <?php endif; ?>
                    <?php if (isset($image['Exif']['exposure'])): ?>
                        <p>Exposure: <?php echo $image['Exif']['exposure']; ?></p>
                    <?php endif; ?>
                </div>

                <!-- Loop through each color and display it as a colored block -->
                <div class="box">
                    <h3>Colors</h3>
                    <?php if (isset($image['Colors']) && is_array($image['Colors'])): ?>
                        <ul>
                            
                            <?php foreach ($image['Colors'] as $color): ?>
                                <li style="background-color: <?php echo $color; ?>; padding: 5px; margin: 2px; display: inline-block;">
                                    <?php echo $color; ?>
                                </li>
                            <?php endforeach; ?>

                        </ul>
                    <?php else: ?>
                        <p>Color N/A</p>
                    <?php endif; ?>
                </div>

            </div>
        <?php else: ?>
            <p>Image not found Error.</p>
        <?php endif; ?>
    </main>
</body>

</html>