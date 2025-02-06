<!-- This brings up a screen to show the user a list of all their favorite paintings -->
<!-- Can remove a single or all paintings from the favorites list here -->


<?php
session_start();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <link href="http://fonts.googleapis.com/css?family=Merriweather" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="css/semantic.js"></script>
    
    <link href="css/semantic.css" rel="stylesheet">
    <link href="css/icon.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
    
<?php include 'includes/art-header.inc.php'; ?>
    
<main class="ui container">
    
    <section class="ui basic segment">
      <h2>Favorites</h2>
        <table class="ui basic collapsing table">
          <thead>
            <tr>
              <th>Image</th>
              <th>Title</th>
              <th>Action</th>
          </tr></thead>
          <tbody>

              <?php
                // Checks if the session has favorites 
                if (isset($_SESSION['favorites']) && count($_SESSION['favorites']) > 0) {
                    
                    foreach ($_SESSION['favorites'] as $favorite) {
                        $paintingID = $favorite['PaintingID'];
                        $imageFileName = $favorite['ImageFileName']; 
                        $title = $favorite['Title'];

                        // Create the image source path, had some issues making it show up
                        $imagePath = "images/art/square-medium/" . $imageFileName . ".jpg";  
                        
                        // Displays iamge and the favorite details... The darn not . to concotinat things
                        echo "<tr>";
                        echo "<td><img src='" . $imagePath . "' alt='" . $title . "' style='width: 100px; height: 100px;'></td>";
                        echo "<td><a href='single-painting.php?id=" . $paintingID . "'>" . $title . "</a></td>";
                        echo "<td><a class='ui small button' href='remove-favorites.php?id=" . $paintingID . "'>Remove</a></td>";
                        echo "</tr>";
                    }
                } else {
                    echo "<tr><td colspan='3'>There are no Favorites.</td></tr>";
                }
              ?>

          </tbody>
          <tfoot class="full-width">
              <th colspan="3">
                <a class="ui left floated small primary labeled icon button" href="remove-favorites.php?action=remove_all">
                  <i class="remove circle icon"></i> Remove All Favorites
                </a>                  
              </th>
          </tfoot>
         </table>
    </section>

</main>    
    
<footer class="ui black inverted segment">
    <div class="ui container">footer</div>
</footer>

</body>
</html>
