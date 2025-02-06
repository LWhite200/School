<!-- File that takes specific request to remove a single painting from favorites or all paintings. -->

<?php
session_start(); // To get the current session, must have, urg

// if request is remove_all, remove all. Else, remopve the single painting requested
if (isset($_GET['action']) && $_GET['action'] == 'remove_all') {
    unset($_SESSION['favorites']);
} elseif (isset($_GET['id'])) {

    $paintingID = $_GET['id'];
    
    // Remove each component from the 2d array, can't remove the entire row in one go?
    if (isset($_SESSION['favorites']) && count($_SESSION['favorites']) > 0) {
        foreach ($_SESSION['favorites'] as $index => $favorite) {
            if ($favorite['PaintingID'] == $paintingID) {
                unset($_SESSION['favorites'][$index]);
                break;
            }
        }
    }
}

// Redirect back to the view-favorites.php page after
header("Location: view-favorites.php");
exit();
?>
