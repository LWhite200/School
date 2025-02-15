<?php
session_start();

// Check if the user is logged in
if (!isset($_SESSION['user'])) {
    // Is it wierd if i randomize which opens?
    header("Location: login-form-bcrypt.php");
    exit();
}

$user = $_SESSION['user'];
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home Page</title>
    <meta charset="utf-8">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link href="css/tailwind.css" rel="stylesheet">
</head>
<body>  

<section class="text-gray-600 body-font">
  <div class="container px-5 py-12 mx-auto flex flex-col">
    <div class="lg:w-4/6 mx-auto">
      <div class="rounded-lg h-64 overflow-hidden">
        <img alt="content" class="object-cover object-center h-full w-full" src="images/florence.jpg">
      </div>
      <div class="flex flex-col sm:flex-row mt-10">
        <div class="sm:w-1/3 text-center sm:pr-8 sm:py-8">
            <div class="flex flex-col items-center text-center justify-center">
                <h2 class="font-medium title-font mt-4 text-gray-900 text-lg">
                    <?php 

                    // Tries to display names, not usure needed, but looked like it
                    // Displays default or nothing, but I found the bug so it's vistigual
                    echo htmlspecialchars(isset($user['firstname']) ? $user['firstname'] : 'First Name') . " " .
                         htmlspecialchars(isset($user['lastname']) ? $user['lastname'] : 'Last Name');
                    ?>
                </h2>
                <div class="w-12 h-1 bg-indigo-500 rounded mt-2 mb-4"></div>
                <p class="text-base">
                    <?php 
                    // May not work, bug found
                    echo htmlspecialchars(isset($user['city']) ? $user['city'] : 'City') . ", " . 
                         htmlspecialchars(isset($user['country']) ? $user['country'] : 'Country');
                    ?>
                </p>   
                
                <!-- Logout Button -->
                <form method="POST" action="">
                    <button type="submit" name="logout" class="text-indigo-500 inline-flex items-center mt-8">Logout</button>         
                </form>
            </div>

            <div class="flex flex-col items-center text-center justify-center mt-8">
              <a class="text-indigo-500 inline-flex items-center" href="login-form-bcrypt.php">Login with bcrypt</a>
              <a class="text-indigo-500 inline-flex items-center" href="login-form-salt.php">Login with salt</a> 
              <a class="text-indigo-500 inline-flex items-center" href="registration-form-bcrypt.php">Register a new user with bcrypt</a> 
              <a class="text-indigo-500 inline-flex items-center" href="registration-form-salt.php">Register a new user with salt</a> 
            </div> 

        </div>
        <div class="sm:w-2/3 sm:pl-8 sm:py-8 sm:border-l border-gray-200 sm:border-t-0 border-t mt-4 pt-4 sm:mt-0 text-center sm:text-left">
          <p class="leading-relaxed text-lg mb-4">
          After login, redirect to this home page. Logout "button" to the left
          </p>
        </div>
      </div>
    </div>
  </div>
</section>

<?php
if (isset($_POST['logout'])) {
    session_unset();
    session_destroy();
    header("Location: login-form-bcrypt.php");
    exit();
}
?>

</body>
</html>
