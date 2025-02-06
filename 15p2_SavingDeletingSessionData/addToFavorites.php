<!-- This file takes in the image components as arguments and ads the image to favorites if the iamge isn't already there -->
<!-- Declares the favorites array if not already instantiated -->
<!-- Re-directs the user to the view-favorites.php afterwards to see all their favorite paintings -->

<?php
session_start(); 

if (isset($_GET['PaintingID']) && isset($_GET['ImageFileName']) && isset($_GET['Title'])) {

    // Declare the variables again some reason
    $paintingID = $_GET['PaintingID'];
    $imageFileName = $_GET['ImageFileName'];
    $title = $_GET['Title'];

    // If the list doesn't exist, initialize it
    if (!isset($_SESSION['favorites'])) {
        $_SESSION['favorites'] = []; 
    }

    // add the image to favorites list, 2d array
    $favorite = [
        'PaintingID' => $paintingID,
        'ImageFileName' => $imageFileName,
        'Title' => $title
    ];

    // avoid duplicates, btreaks if already exists, not sure needed but being safe
    $exists = false;
    foreach ($_SESSION['favorites'] as $fav) {
        if ($fav['PaintingID'] == $paintingID) {
            $exists = true;
            break;
        }
    }

    if (!$exists) {
        $_SESSION['favorites'][] = $favorite;
    }

    // Redirect to view-favorites.php, the instructions semi-say this, avoid other issues so I'm fine with it
    header("Location: view-favorites.php");
    exit();
} else {
    header("Error Error Error, theres an error (addToFavorites)"); 
    exit();
}
?>
